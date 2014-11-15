package org.gorilla.hokieplanner.guerilla;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

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
        String pid = getAndSavePID();
        String password =
            ((EditText)findViewById(R.id.login_pass_field)).getText()
                .toString();

        // TODO Do the login here
        if (pid.trim().equals("")) {
            return;
        }

        // Assume login succeeded
        startPlannerActivity(pid);
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

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    private String getAndSavePID() {
        String pid = getPIDField().getText().toString();
        Prefs.setRememberingPID(getRememberBox().isChecked());
        Prefs.setUserPID((getRememberBox().isChecked() ? pid : null));
        return pid;
    }

    private void startPlannerActivity(String pid) {
        Intent intent = new Intent(this, PlannerActivity.class);
        intent.putExtra("pid", pid);
        startActivity(intent);
    }

    private CheckBox getRememberBox() {
        return (CheckBox)findViewById(R.id.login_remember_box);
    }

    private EditText getPIDField() {
        return (EditText)findViewById(R.id.login_pid_field);
    }
}
