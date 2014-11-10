package org.gorilla.hokieplanner.guerilla;

import android.content.Intent;
import android.widget.EditText;
import android.view.View;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;

/**
 * Activity for logging into HokieSpa
 *
 * @author Anirudh Bagde
 * @author Weyland Chiang
 * @author Sayan Ekambarapu
 * @version Nov 9, 2014
 */
public class LoginActivity
    extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    /**
     * Called by the login button in the layout, this method is specified in the
     * XML file for the layout
     *
     * @param button
     *            The button that was clicked to call this method
     */
    public void loginSubmit(View button) {
        String pid =
            ((EditText)findViewById(R.id.login_pid_field)).getText()
                .toString();
        String password =
            ((EditText)findViewById(R.id.login_pass_field)).getText()
                .toString();

        // TODO Do the login here
        if (pid.trim().equals("")) {
            return;
        }

        // Assume login succeeded
        Prefs.setUserPID(pid);
        startPlannerActivity();
    }

    /**
     * Called by the skip button in the layout, this method is specified in the
     * XML file for the layout
     *
     * @param button
     *            The button that was clicked to call this method
     */
    public void loginSkip(View button) {
        // TODO Dialog to ask if the user really wants to do this
        Prefs.setUserPID(null);
        startPlannerActivity();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    private void startPlannerActivity() {
        Intent intent = new Intent(this, PlannerActivity.class);
        startActivity(intent);
    }
}
