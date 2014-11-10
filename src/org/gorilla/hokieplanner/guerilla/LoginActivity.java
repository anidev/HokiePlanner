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
 * @version Nov 9, 2014
 */
public class LoginActivity
    extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void loginSubmit(View button) {
        String pid =
            ((EditText)findViewById(R.id.login_pid_field)).getText()
                .toString();
        String password =
            ((EditText)findViewById(R.id.login_pass_field)).getText()
                .toString();

        // TODO Do the login here
        if(pid.trim().equals("")) {
            return;
        }

        // Assume login succeeded
        Prefs.setUserPID(pid);
        Intent intent = new Intent(this, PlannerActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
