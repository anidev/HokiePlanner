package org.gorilla.hokieplanner.guerilla;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

/**
 * First activity that runs and decides whether to go straight to the planner
 * activity or first ask to login
 *
 * @author Anirudh Bagde
 * @author Weyland Chiang
 * @author Sayan Ekambarapu
 * @version Nov 9, 2014
 */
public class MainActivity
    extends ActionBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Prefs.initialize(this);

        Intent intent;
        // Check whether login activity should be displayed
        if(Prefs.getUserPID()==null) {
            intent=new Intent(this,LoginActivity.class);
        } else {
            intent=new Intent(this,PlannerActivity.class);
        }
        startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
