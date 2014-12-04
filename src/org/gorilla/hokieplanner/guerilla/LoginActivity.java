package org.gorilla.hokieplanner.guerilla;

import android.os.AsyncTask;
import android.app.ProgressDialog;
import com.vtaccess.exceptions.WrongLoginException;
import com.vtaccess.Cas;
import android.widget.CheckBox;
import android.content.Intent;
import android.widget.EditText;
import android.view.View;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;

/**
 * Activity for logging into HokieSpa
 *
 * @author Anirudh Bagde (anibagde)
 * @author Weyland Chiang (chiangw)
 * @author Sayan Ekambarapu (sayan96)
 * @version Nov 9, 2014
 */
public class LoginActivity
    extends ActionBarActivity {

    // --------------------------------------------------------
    /**
     * Creates a Edit Text object so we can set the error message on a
     * unsuccessful login attempt.
     */
    private EditText login_pass_field;

    // --------------------------------------------------------
    /**
     * Initialize the Auth object login
     */
    private Cas      login;

    // --------------------------------------------------------
    /**
     * Once this activity is created, sets the content view to the
     * activity_login
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    // --------------------------------------------------------
    /**
     * Sets the remember box if it was previously checked Also updates the PID
     * field is the remember box was set
     */
    @Override
    protected void onStart() {
        super.onStart();
        getRememberBox().setChecked(Prefs.isRememberingPID());
        Prefs.setAuth(null);
        if (Prefs.isRememberingPID()) {
            getPIDField().setText(Prefs.getUserPID());
        }
    }

    /**
     * Called by the login button in the layout, this method is specified in the
     * XML file for the layout
     *
     * @param button
     *            The button that was clicked to call this method
     */
    public void loginSubmit(View button) {
        login_pass_field =
            (EditText)findViewById(R.id.login_pass_field);
        // Convert PID and password to char array because it is more secure and
        // the Cas class wants it that way
        char[] pid = getAndSavePID().toCharArray();
        char[] password =
            (login_pass_field).getText().toString().toCharArray();
        // Check validity of specified PID and password
        if (pid.length == 0) {
            getPIDField().setError("Username should not be blank");
        }
        else if (password.length == 0) {
            login_pass_field.setError("Password should not be blank");
        }
        else {
            // Display a waiting dialog and attempt login on a separate thread
            ProgressDialog progress = new ProgressDialog(this);
            progress.setMessage("Logging in...");
            progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progress.setCancelable(false);
            new LoginTask(pid, password, progress)
                .execute(new Void[0]);
        }
    }

    private void loginTaskFinished(Cas auth) {
        login = auth;
        if (login != null && login.isValidLoginInfo()
            && login.isActive()) {
            Prefs.setAuth(login);
            startPlannerActivity(getAndSavePID());
        }
        else {
            login_pass_field.setError("Login Failed");
            login_pass_field.setText("");
        }
    }

    /**
     * Called by the cancel button in the layout, this method is specified in
     * the XML file for the layout
     *
     * @param button
     *            The button that was clicked to call this method
     */
    public void loginCancel(View button) {
        getAndSavePID();
        startPlannerActivity(null);
    }

    // --------------------------------------------------------
    /**
     * Gets the PID entered in the username login field and saves it.
     *
     * @return saved PID
     */
    private String getAndSavePID() {
        String pid = getPIDField().getText().toString();
        Prefs.setRememberingPID(getRememberBox().isChecked());
        Prefs.setUserPID((getRememberBox().isChecked() ? pid : null));
        return pid;
    }

    // --------------------------------------------------------
    /**
     * Starts the Planner activity upon successful authentication
     */
    private void startPlannerActivity(String pid) {
        Intent intent = new Intent(this, PlannerActivity.class);
        intent.putExtra("pid", pid);
        startActivity(intent);
        finish();
    }

    // --------------------------------------------------------
    /**
     * Grabs and returns the remember box
     *
     * @return login_remember_box
     */
    private CheckBox getRememberBox() {
        return (CheckBox)findViewById(R.id.login_remember_box);
    }

    // --------------------------------------------------------
    /**
     * Grabs and returns the PID field
     *
     * @return login_pid_field
     */
    private EditText getPIDField() {
        return (EditText)findViewById(R.id.login_pid_field);
    }

    /**
     * Uses VTAccess API to connect to network and attempt to login
     *
     * @author Anirudh Bagde (anibagde)
     * @author Weyland Chiang (chiangw)
     * @author Sayan Ekambarapu (sayan96)
     * @version Nov 9, 2014
     */
    private class LoginTask
        extends AsyncTask<Void, Void, Cas> {
        private char[] username;
        private char[] password;
        ProgressDialog progress;

        /**
         * Construct the task with the given username, password, and progress
         * dialog
         *
         * @param username
         *            Username
         * @param password
         *            Password
         * @param progress
         *            Progress dialog to show
         */
        public LoginTask(
            char[] username,
            char[] password,
            ProgressDialog progress) {
            this.username = username;
            this.password = password;
            this.progress = progress;
        }

        @Override
        protected void onPreExecute() {
            progress.show();
        }

        @Override
        protected Cas doInBackground(Void... params) {
            Cas auth;
            try {
                auth = new Cas(username, password);
            }
            catch (WrongLoginException e) {
                auth = null;
            }
            return auth;
        }

        @Override
        protected void onPostExecute(Cas result) {
            progress.dismiss();
            loginTaskFinished(result);
        }
    }
}
