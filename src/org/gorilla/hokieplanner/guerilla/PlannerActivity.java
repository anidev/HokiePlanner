package org.gorilla.hokieplanner.guerilla;

import android.content.Intent;
import android.view.Gravity;
import org.gorilla.hokieplanner.guerilla.R;
import android.annotation.TargetApi;
import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;

/**
 * Activity representing the main screen with navigation drawerLayout and the
 * various fragments for the app
 *
 * @author Anirudh Bagde
 * @version Nov 9, 2014
 */
public class PlannerActivity
    extends ActionBarActivity
    implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the
     * navigation drawerLayout.
     */
    private NavigationDrawerFragment navigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in
     * {@link #restoreActionBar()}.
     */
    private CharSequence             title;

    private DrawerLayout             drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planner);

        navigationDrawerFragment =
            (NavigationDrawerFragment)getSupportFragmentManager()
                .findFragmentById(R.id.main_navdrawer);
        title = getTitle();

        // Set up the drawerLayout.
        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        navigationDrawerFragment.setUp(
            R.id.main_navdrawer,
            drawerLayout);
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager
            .beginTransaction()
            .replace(
                R.id.main_container,
                PlaceholderFragment.newInstance(position + 1))
            .commit();
    }

    @Override
    public void resetLogin() {
        Prefs.setUserPID(null);
        Intent intent=new Intent(this,LoginActivity.class);
        startActivity(intent);
        finish();
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                title = getString(R.string.title_section1);
                break;
            case 2:
                title = getString(R.string.title_section2);
                break;
            case 3:
                title = getString(R.string.title_section3);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar
            .setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(title);
    }

    /**
     * Close navigation drawerLayout on back pressed
     */
    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(Gravity.START)) {
            drawerLayout.closeDrawer(Gravity.START);
        }
        else {
            super.onBackPressed();
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment
        extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER =
                                                           "section_number";

        /**
         * Returns a new instance of this fragment for the given section number.
         */
        public static PlaceholderFragment newInstance(
            int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {
            View rootView =
                inflater.inflate(
                    R.layout.fragment_main,
                    container,
                    false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((PlannerActivity)activity)
                .onSectionAttached(getArguments().getInt(
                    ARG_SECTION_NUMBER));
        }
    }

}
