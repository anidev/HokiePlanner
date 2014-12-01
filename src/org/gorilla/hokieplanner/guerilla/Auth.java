package org.gorilla.hokieplanner.guerilla;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.util.HashMap;
import java.util.Map;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLPeerUnverifiedException;
import org.jsoup.Jsoup;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import javax.security.auth.login.LoginException;

/**
 * Authentication Framework used to interface with the
 * Virginia Tech Central Authentication System. Inspired by
 * Ethan Gaebel's VTAccess (github.com/egaebel)
 *
 * @author Anirudh Bagde (anibagde)
 * @author Weyland Chiang (chiangw)
 * @author Sayan Ekambarapu (sayan96)
 * @version Nov 9, 2014
 */
public class Auth
{

    //~Constants-----------------------------------------------
    /**
     * User-Agents
     */
    private static final String AGENTS = "Mozilla/5.0 (Windows NT 6.1; WOW64)" +
    		" AppleWebKit/537.36 (KHTML, like Gecko)" +
    		" Chrome/38.0.2125.122 Safari/537.36";
    /**
     * HokieSpa Login URL
     */
    private static final String LOGIN = "https://auth.vt.edu/login?service=" +
    		"https://webapps.banner.vt.edu/banner-cas-prod/" +
    		"authorized/banner/SelfService";
    /**
     * HokieSpa Logout URL
     */
    private static final String CAS_LOGOUT = "https://auth.vt.edu/logout";
    /**
     * Check for Recovery Options
     */
    private static final String RECOVERY_OPTIONS_STRING = "You have not updated" +
    		" account recovery options in the past";
    /**
     * HokieSpa Recovery URL
     */
    private static final String RECOVERY_OPTIONS_LOGIN = "https://banweb." +
    		"banner.vt.edu/ssb/prod/twbkwbis.P_GenMenu?name=bmenu.P_MainMnu";

    //~Data Fields---------------------------------------------
    /**
     * Map of the strings used by HokieSpa
     */
    private Map<String, String> cookies;
    /**
     * CAS SSL cert
     */
    private File cert;
    /**
     * Location to save SSL cert
     */
    private String certFilePath;
    /**
     * Whether or not there is an active connection
     */
    private boolean active;
    /**
     * The stored username of the user of this session.
     */
    private char[] username;
    /**
     * The stored password of the user of this session.
     */
    private char[] password;
    /**
     * Checks user/pass and determines whether or not they are valid
     */
    private boolean validLoginInfo;
    /**
     * Whether or not a refresh is needed for this session
     */
    private boolean refreshSession;

    //~Constructors--------------------------------------------

    /**
     * Constructor taking a username and password to be used with CAS.
     * Logs into CAS immediately.
     *
     * @param username a character array that is a CAS username.
     * @param password a character array that is a CAS password.
     *
     * @throws LoginException Wrong username/password
     */
    public Auth(char[] username, char[]  password) throws LoginException {

        // Initialize booleans
        active = false;
        validLoginInfo = false;
        refreshSession = true;

        this.username = new char[username.length];
        for (int i = 0; i < username.length; i++) {
            this.username[i] = username[i];
        }

        this.password = new char[password.length];
        for (int i = 0; i < password.length; i++) {
            this.password[i] = password[i];
        }

        // Clear user and pass
        for (int i = 0; i < username.length; i++) {

            username[i] = 0;
        }
        for (int i = 0; i < password.length; i++) {

            password[i] = 0;
        }

        certFilePath = null;

        try {
            validLoginInfo = login(this.username, this.password);
        }
        catch (LoginException e) {

            clearUserData();
            throw new LoginException("Incorrect username/password");
        }
    }

    /**
     * Constructor taking a username and password to be used with HokieSpa
     * session management; and a filePath to save the SSL certificate to.
     * Logs into CAS immediately.
     *
     * @param username a character array that is a HokieSpa username.
     * @param password a character array that is a HokieSpa password.
     * @param filePath the path to the file to save the SSL certificate in.
     *
     * @throws LoginException Wrong username/password
     */
    public Auth(char[] username, char[] password, String filePath)
        throws LoginException {

        // Initialize booleans
        active = false;
        validLoginInfo = false;
        refreshSession = true;

        this.username = new char[username.length];
        for (int i = 0; i < username.length; i++) {
            this.username[i] = username[i];
        }

        this.password = new char[password.length];
        for (int i = 0; i < password.length; i++) {
            this.password[i] = password[i];
        }

        // Clear user and pass
        for (int i = 0; i < username.length; i++) {

            username[i] = 0;
        }
        for (int i = 0; i < password.length; i++) {

            password[i] = 0;
        }

        certFilePath = filePath;
        validLoginInfo = false;
        refreshSession = false;

        try {
            validLoginInfo = login(this.username, this.password, filePath);
        }
        catch (LoginException e) {

            clearUserData();
            throw new LoginException("Incorrect username/password");
        }
    }

    //~Methods-------------------------------------------------
    /**
     * Given a username and password in an array of characters, log
     * into CAS. Call this in another thread
     *
     * @param username The user's name
     * @param password the password of the user.
     * @return True if successful, false if IOException or bad Certificate
     *
     * @throws LoginException indicates that the username or password was incorrect.
     */
    private boolean login(char[] username, char[] password)
        throws LoginException {

        try {

            if(grabCertificate()) {

                System.setProperty("javax.net.ssl.trustStore", cert.getAbsolutePath());

                return loginHelper(username, password);
            }
        }
        catch (SocketTimeoutException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Refreshes a timed out session
     *
     * @return Whether or not the session was refreshed
     */
    public boolean refreshSession() {

        try {

            boolean returnVal = false;

            if (certFilePath == null) {
                logout();
                returnVal = login(username, password);
            }
            else {
                logout();
                returnVal = login(username, password, certFilePath);
            }

            return returnVal;
        }
        catch (LoginException e) {}

        return false;
    }

    /**
     * Switches the user that is active on this session to the passed in username and password.
     *
     * @param username the new username to use.
     * @param password the new password to use.
     *
     * @throws LoginException indicates that the username or password was incorrect.
     */
    public void switchUsers(char[] username, char[] password) throws LoginException {

        logout();
        clearUserData();
        this.username = new char[username.length];
        for (int i = 0; i < username.length; i++) {
            this.username[i] = username[i];
        }

        this.password = new char[password.length];
        for (int i = 0; i < password.length; i++) {
            this.password[i] = password[i];
        }

        //clear out passed username and password
        for (int i = 0; i < username.length; i++) {

            username[i] = 0;
        }
        for (int i = 0; i < password.length; i++) {

            password[i] = 0;
        }

        try {
            validLoginInfo = login(this.username, this.password);
        }
        catch (LoginException e) {

            clearUserData();
            throw new LoginException();
        }
    }

    /**
     * Switches the user that is active on this session to the passed in username and password.
     * Logs in with new user immediately.
     *
     * @param username the new username to use.
     * @param password the new password to use.
     * @param filePath a new filePath to use for the SSL certificate.
     *
     * @throws LoginException indicates that the username or password was incorrect.
     */
    public void switchUsers(char[] username, char[] password, String filePath) throws LoginException {

        closeSession();
        this.username = new char[username.length];
        for (int i = 0; i < username.length; i++) {
            this.username[i] = username[i];
        }

        this.password = new char[password.length];
        for (int i = 0; i < password.length; i++) {
            this.password[i] = password[i];
        }


        //Clear out passed username and password
        for (int i = 0; i < username.length; i++) {

            username[i] = 0;
        }
        for (int i = 0; i < password.length; i++) {

            password[i] = 0;
        }

        try {
            validLoginInfo = login(this.username, this.password, certFilePath);
        }
        catch (LoginException e) {

            clearUserData();
            throw new LoginException();
        }
    }


    /**
     * Closes down this Cas session.
     *
     * @return true if the session closed successfully, false if it did not close.
     */
    public boolean closeSession() {

        clearUserData();
        return logout();
    }

    /**
     * Grabs the needed SSL Certificate from the CAS login page.
     *
     * @return true if successful, false if there was an IOException,
     *          MalformedURLException, SSLPeerUnverifiedException,
     *          or a CertificateEncodingException.
     *          See StackTrace to figure out which one.
     */
    private boolean grabCertificate() {

        try {
            URL url = new URL(LOGIN);
            HttpsURLConnection connect = (HttpsURLConnection)url.openConnection();
            connect.connect();
            Certificate[] certs = connect.getServerCertificates();

            if (certs.length > 0) {

                cert = new File("Resources/auth.vt.edu.jks");
                //write the certificate obtained to the cert file.
                OutputStream os = new FileOutputStream(cert);
                os.write(certs[0].getEncoded());
                os.close();

                return true;
            }
        }
        catch (SSLPeerUnverifiedException e) {
            e.printStackTrace();
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (CertificateEncodingException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Grabs the needed SSL Certificate from the CAS login page. Saves the file to the passed in
     * directory.
     *
     * @return true if successful, false if there was an IOException,
     *          MalformedURLException, SSLPeerUnverifiedException,
     *          or a CertificateEncodingException.
     *          See StackTrace to figure out which one.
     */
    private boolean grabCertificate(String filePath) {

        try {
            URL url = new URL(LOGIN);
            HttpsURLConnection connect = (HttpsURLConnection)url.openConnection();
            connect.connect();
            Certificate[] certs = connect.getServerCertificates();

            if (certs.length > 0) {

                cert = new File(filePath + "auth.vt.edu.jks");
                //write the certificate obtained to the cert file.
                OutputStream os = new FileOutputStream(cert);
                os.write(certs[0].getEncoded());
                os.close();

                return true;
            }
        }
        catch (SSLPeerUnverifiedException e) {
            e.printStackTrace();
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (CertificateEncodingException e) {
            e.printStackTrace();
        }

        return false;
    }


    /**
     * Takes in a username and password and logs into CAS
     * with the supplied username and password. Also takes in a String that specifies
     * the directory where the SSL should be saved. This is especially useful in Android
     * usage, because the file system differs from java's default one.
     *
     * For best performance this method call should be done in a separate thread.
     *
     * @param username the username of the user.
     * @param password the password of the user.
     * @param filePath the file path that the SSL certificate should be saved in.
     * @return returns true if successful, false if there was an IOException,
     *          and false if the Certificate wasn't correctly obtained.
     *
     * @throws LoginException indicates that the username or password was incorrect
     */
    private boolean login(char[] username, char[] password, String filePath) throws LoginException {

        try {

            if(grabCertificate(filePath)) {

                System.setProperty("javax.net.ssl.trustStore", cert.getAbsolutePath());

                return loginHelper(username, password);
            }
        }
        catch (SocketTimeoutException e) {
            e.printStackTrace();
            throw new LoginException();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    private boolean loginHelper(char[] username, char[] password) throws IOException, LoginException {

        // get three hidden fields, and cookies from initial Login Page
        Response loginPageResp = Jsoup.connect(LOGIN).execute();

        // save JSESSION cookie from the LOGIN URL's response
        cookies = loginPageResp.cookies();

        // get the document from the response to retrieve hidden fields
        Document doc = loginPageResp.parse();

        // select the correct div section under form-->fieldset
        Elements divs = doc.select("form fieldset div");
        Element div6 = divs.get(5);

        // hashmap to hold hiddenFields in document, as well as username,
        // password
        Map<String, String> hiddenFields = new HashMap<String, String>();

        // place hidden fields & _submit into hashmap for passing
        hiddenFields.put("lt", div6.getElementsByIndexEquals(0).val());
        hiddenFields.put("execution", div6.getElementsByIndexEquals(1)
                .val());
        hiddenFields
                .put("_eventId", div6.getElementsByIndexEquals(2).val());

        // will always be this value on the CAS page
        hiddenFields.put("submit", "_submit");

        // place username and password into hashmap for passing
        hiddenFields.put("username", String.copyValueOf(username));
        hiddenFields.put("password", String.copyValueOf(password));

        // enter in the hidden fields as well as username and pasword --
        // press submit, USE GET METHOD!!!
        Response resp = Jsoup
                .connect(LOGIN)
                .data(hiddenFields)
                .cookie("JSESSIONID", cookies.get("JSESSIONID"))
                .method(Method.GET)
                .header("Content-Type", "application/x-www-form-urlencoded")
                .referrer(LOGIN)
                .userAgent(AGENTS)
                .execute();

        // get all cookies from the resp generated above to use in future
        // authentication
        cookies.putAll(resp.cookies());

        Document loginCheck = resp.parse();

        // check to see if the login was successful
        Elements checkLoginEls = loginCheck.select("#login-error");
        Elements checkRecovery = loginCheck.select("#warn");

        //check for correct username/password
        if (checkLoginEls.size() >= 8) {

            Element checkedEl = checkLoginEls.get(8);

            if (checkedEl.text().equals("Invalid username or password.")) {

                // if unsuccessful login throw LoginException
                throw new LoginException();
            }
        }
        else if (checkLoginEls.size() == 1) {

            Element checkedEl = checkLoginEls.get(0);

            if (checkedEl.text().equals("Invalid username or password.")) {

                // if unsuccessful login throw LoginException
                throw new LoginException();
            }
        }

        active = true;
        refreshSession = false;

        return true;
    }

    /**
     * End this CAS session. If this returns false the session stays open.
     *
     * @return true if successful, false if an error occurred (the session will remain open).
     */
    private boolean logout() {

        boolean val;

        try {
            // logs out of CAS. closing the session
            Jsoup.connect(CAS_LOGOUT).get();

            val = true;
        }
        catch (IOException e) {
            e.printStackTrace();
            val = false;
        }

        active = false;
        cookies = null;

        return val;
    }

    /**
     * Erases user data stored in this class.
     */
    private void clearUserData() {

        for (int i = 0; i < username.length; i++) {
            username[i] = 0;
        }
        for (int i = 0; i < password.length; i++) {
            password[i] = 0;
        }
    }

    //~Getters and Setters--------------------------------------------------------------
    /**
     * Getter for cookies.
     *
     * @return cookies the cookies that have been pulled from Cas, if a login has occurred. Otherwise, returns null
     */
    protected Map<String, String> getCookies() {

        refreshSession = true;
        return cookies;
    }

    /**
     * Tests to see if this CAS object has an active
     * login session with hokiespa.
     *
     * @return true if active, false otherwise.
     */
    public boolean isActive() {

        if (refreshSession) {
            if (!refreshSession()) {
                active = false;
            }
        }
        return active;
    }

    /**
     * Indicates whether the login information stored in this Cas object is valid.
     * Validity is determined when attempting to log into hokiespa.
     *
     * @return true if login information is valid, false otherwise.
     */
    public boolean isValidLoginInfo() {

        return validLoginInfo;
    }

    /**
     * @return the refreshSession
     */
    public boolean isRefreshSession() {

        return refreshSession;
    }

    /**
     * @param refreshSession the refreshSession to set
     */
    public void setRefreshSession(boolean refreshSession) {

        this.refreshSession = refreshSession;
    }
}