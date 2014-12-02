package org.gorilla.hokieplanner.guerilla;

import android.widget.Button;
import android.widget.TextView;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Prefs.initialize(this);

        if (Prefs.getSelectedChecksheet() == null) {
            Intent intent =
                new Intent(this, MajorPickerActivity.class);
            startActivity(intent);
            finish();
            return;
        }

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

    /**
     * Navigation drawer callback for when a specific navigation drawer item is
     * selected
     *
     * @param position
     *            Position in the drawer that the user selected
     */
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

    /**
     * Navigation drawer callback for when the user presses the login/logout
     * button at the bottom of the navigation drawer. It will set the saved PID
     * to null and replace the current activity with the login activity.
     */
    public void resetLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
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
     * Called by the change course button in the layout, this method is
     * specified in the XML file for the layout
     *
     * @param button
     *            The button that was clicked to call this method
     */
    public void changeCourse(View button) {
        Intent intent = new Intent(this, MajorPickerActivity.class);
        startActivity(intent);
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
        resetLogin();
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
            if (layoutId == R.layout.fragment_welcome) {
                populateWelcomeFragment(rootView);
            }
            return rootView;
        }

        private void populateWelcomeFragment(View rootView) {
            TextView majorValue =
                (TextView)rootView
                    .findViewById(R.id.selected_major_value);
            majorValue.setText(AvailableChecksheets.valueOf(
                Prefs.getSelectedChecksheet()).toString());
            Bundle extras = getActivity().getIntent().getExtras();
            String pid =
                (extras != null ? extras.getString("pid") : null);
            int actionText =
                (pid != null ? R.string.logout_text
                    : R.string.login_text);
            ((Button)rootView.findViewById(R.id.login_logout_btn))
                .setText(actionText);
            String pidText = (pid != null ? pid + "@vt.edu" : "");
            ((TextView)rootView.findViewById(R.id.welcome_name_label))
                .setText(pidText);
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
