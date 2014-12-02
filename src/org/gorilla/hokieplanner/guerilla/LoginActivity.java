package org.gorilla.hokieplanner.guerilla;

import javax.security.auth.login.LoginException;
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
    private Auth     login;

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
        char[] pid = getAndSavePID().toCharArray();
        char[] password =
            (login_pass_field).getText().toString().toCharArray();

        if (getPIDField().getText().toString().isEmpty()) {
            getPIDField().setError("Username should not be blank");
        }
        else if (login_pass_field.toString().isEmpty()) {
            login_pass_field.setError("Password should not be blank");
        }
        else {
            try {
                login = new Auth(pid, password);
            }
            catch (LoginException e) {
                login = null;
            }
        }

        if (login != null && login.isValidLoginInfo()
            && login.isActive()) {
            startPlannerActivity(pid.toString());
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
}
