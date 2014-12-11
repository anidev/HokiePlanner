package org.gorilla.hokieplanner.guerilla;

import android.content.res.Configuration;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.app.ProgressDialog;
import android.widget.Button;
import android.widget.TextView;
import android.content.Intent;
import android.view.Gravity;
import org.gorilla.hokieplanner.guerilla.R;
import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;

/**
 * Activity representing the main screen with navigation drawerLayout and the
 * various fragments for the app
 *
 * @author Anirudh Bagde (anibagde)
 * @author Weyland Chiang (chiangw)
 * @author Sayan Ekambarapu (sayan96)
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
    private ActionBarDrawerToggle    mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Prefs.initialize(this);

        // Force user to select a checksheet if one hasn't already been
        if (Prefs.getSelectedChecksheet() == null) {
            Intent intent =
                new Intent(this, MajorPickerActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        setContentView(R.layout.activity_planner);

        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mDrawerToggle =
            new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(mDrawerToggle);

        // Set up the navigation drawer
        // FIXME: This uses the old (pre-Material design) navigation drawer
        // system, so some related methods and classes may be deprecated until
        // we figure out how to use navigation drawers with Material design,
        // which appcompat_v7 is forcing us to use.
        navigationDrawerFragment =
            (NavigationDrawerFragment)getSupportFragmentManager()
                .findFragmentById(R.id.main_navdrawer);
        title = getTitle(); // Set up the
        navigationDrawerFragment.setUp(
            R.id.main_navdrawer,
            drawerLayout);

    }

    @Override
    protected void onStart() {
        super.onStart();
        // Make sure the checksheet XML and course cache are loaded before
        // allowing the user to do anything
        loadCourseInfo();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    /**
     * Close navigation drawerLayout on back pressed
     */
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
     * Navigation drawer callback for when a specific navigation drawer item is
     * selected
     *
     * @param position
     *            Position in the drawer that the user selected
     */
    public void onNavigationDrawerItemSelected(int position) {
        // Update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager
            .beginTransaction()
            .replace(
                R.id.main_container,
                PlaceholderFragment.newInstance(position + 1))
            .commit();
    }

    /**
     * Navigation drawer callback for when the user presses the login/logout
     * button at the bottom of the navigation drawer. It will set the saved PID
     * to null and replace the current activity with the login activity.
     */
    public void resetLogin(View source) {
        Intent intent = new Intent(this, LoginActivity.class);
        ActivityOptionsCompat options =
            ActivityOptionsCompat.makeScaleUpAnimation(
                source,
                0,
                0,
                0,
                0);
        ActivityCompat
            .startActivity(this, intent, options.toBundle());
        finish();
    }

    /**
     * Called by the placeholder fragment when the selected fragment has been
     * attached
     *
     * @param number
     *            Index of section selected in the navigation drawer
     */
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
        restoreActionBar();
    }

    /**
     * Called to restore the action bar to normal after closing the navigation
     * drawer, if it was changed
     */
    public void restoreActionBar() {
/*
 * ActionBar actionBar = getSupportActionBar(); if (actionBar == null) { //
 * Sometimes it is null? No idea why but it causes crashes sometimes // so do
 * nothing if it null return; } actionBar
 * .setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
 * actionBar.setDisplayShowTitleEnabled(true); actionBar.setTitle(title);
 */
    }

    /**
     * Called by the change course button in the layout, this method is
     * specified in the XML file for the layout
     *
     * @param button
     *            The button that was clicked to call this method
     */
    public void changeCourse(View button) {
        Intent intent = new Intent(this, MajorPickerActivity.class);
        ActivityOptionsCompat options =
            ActivityOptionsCompat.makeScaleUpAnimation(
                button,
                0,
                0,
                0,
                0);
        ActivityCompat
            .startActivity(this, intent, options.toBundle());
        finish();
    }

    /**
     * Called by the login/logout button in the layout, this method is specified
     * in the XML file for the layout
     *
     * @param button
     *            The button that was clicked to call this method
     */
    public void loginLogout(View button) {
        resetLogin(button);
    }

    /**
     * Load checksheet XML and download course cache
     */
    private void loadCourseInfo() {
        // Open a wait dialog so the user doesn't mess around with the app while
        // stuff is being loaded. This may take some time since it requires
        // network communication.
        if (Prefs.getChecksheet() == null) {
            ProgressDialog progress = new ProgressDialog(this);
            progress.setMessage("Loading checksheet data...");
            progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progress.setCancelable(false);
            new ChecksheetLoadTask(progress).execute(new Void[0]);
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
         *
         * @param sectionNumber
         *            The index of the section this fragment is for
         * @return The initialized fragment object
         */
        public static PlaceholderFragment newInstance(
            int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        /**
         * Initialize the fragment. This constructor does nothing,
         * PlaceholderFragment.newInstance should be used to create a
         * PlaceholderFragment object instead.
         */
        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {
            int layoutId = 0;
            switch (getArguments().getInt(ARG_SECTION_NUMBER)) {
                case 1:
                    layoutId = R.layout.fragment_welcome;
                    break;
                case 2:
                    layoutId = R.layout.fragment_checksheet;
                    break;
                case 3:
                    layoutId = R.layout.fragment_diagram;
                    break;
                case 4:
                    layoutId = R.layout.fragment_timetable;
                    break;
            }
            View rootView =
                inflater.inflate(layoutId, container, false);
            // The fragments need to be dynamically filled with content, so take
            // care of that here with helper methods
            if (layoutId == R.layout.fragment_welcome) {
                populateWelcomeFragment(rootView);
            }
            else if (layoutId == R.layout.fragment_checksheet) {
                populateChecksheet(rootView, inflater);
            }
            return rootView;
        }

        /**
         * Helper method to populate the welcome fragment with total credits,
         * current major, and login/logout status
         *
         * @param rootView
         *            The view that represents the welcome fragment
         */
        private void populateWelcomeFragment(View rootView) {
            // Display current selected major/year combo
            TextView majorValue =
                (TextView)rootView
                    .findViewById(R.id.selected_major_value);
            majorValue.setText(AvailableChecksheets.valueOf(
                Prefs.getSelectedChecksheet()).toString());
            // Display credit count
            TextView creditsLabel =
                (TextView)rootView
                    .findViewById(R.id.credits_label);
            String creditStr = creditsLabel.getText().toString();
            creditStr =
                creditStr.substring(0, creditStr.indexOf(':') + 1)
                    + " " + Prefs.getTotalCredits();
            creditsLabel.setText(creditStr);

            Bundle extras = getActivity().getIntent().getExtras();
            String pid =
                (extras != null ? extras.getString("pid") : null);
            // Modify login button text depending on whether user is logged in
            // or not
            int actionText =
                (pid != null ? R.string.logout_text
                    : R.string.login_text);
            ((Button)rootView.findViewById(R.id.login_logout_btn))
                .setText(actionText);
            // Display VT email address if currently logged in
            String pidText = (pid != null ? pid + "@vt.edu" : "");
            ((TextView)rootView.findViewById(R.id.welcome_name_label))
                .setText(pidText);
        }

        /**
         * Helper method to populate the checksheet fragment with data pulled
         * from the XML files. This calls into the dedicated class for
         * populating checksheets.
         *
         * @param rootView
         *            The view that represents the checksheet fragment
         * @param inflater
         *            The layout inflater passed to the fragment while the view
         *            is being created
         */
        private void populateChecksheet(
            View rootView,
            LayoutInflater inflater) {
            // Too much stuff to do here so everything is done by a helper class
            new ChecksheetLayoutPopulator(rootView, inflater)
                .populate();
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
