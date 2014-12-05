package org.gorilla.hokieplanner.guerilla;

import java.util.Locale;
import org.gorilla.hokieplanner.guerilla.R;
import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

// NOTE: This class was almost entirely auto-generated

/**
 * Fragment used for managing interactions for and presentation of a navigation
 * drawer. See the <a href=
 * "https://developer.android.com/design/patterns/navigation-drawer.html#Interaction"
 * > design guidelines</a> for a complete explanation of the behaviors
 * implemented here.
 *
 * @author Anirudh Bagde (anibagde)
 * @author Weyland Chiang (chiangw)
 * @author Sayan Ekambarapu (sayan96)
 * @version Nov 9, 2014
 */
public class NavigationDrawerFragment
    extends Fragment {

    /**
     * Remember the position of the selected item.
     */
    private static final String       STATE_SELECTED_POSITION  =
                                                                   "selected_navigation_drawer_position";

    /**
     * A pointer to the current callbacks instance (the Activity).
     */
    private NavigationDrawerCallbacks mCallbacks;

    private DrawerLayout              mDrawerLayout;
    private ViewGroup                 mDrawerListContainer;
    private ListView                  mDrawerListView;
    private ListView                  mAccountListView;
    private View                      mFragmentContainerView;

    private int                       mCurrentSelectedPosition = 0;

    /**
     * Create a new NavigationDrawerFragment object.
     */
    public NavigationDrawerFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            mCurrentSelectedPosition =
                savedInstanceState.getInt(STATE_SELECTED_POSITION);
        }
        // Select either the default item (0) or the last selected item.
        selectItem(mCurrentSelectedPosition);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Indicate that this fragment would like to influence the set of
        // actions in the action bar.
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(
        LayoutInflater inflater,
        ViewGroup container,
        Bundle savedInstanceState) {
        mDrawerListContainer =
            (ViewGroup)inflater.inflate(
                R.layout.fragment_navigation_drawer,
                container,
                false);

        // Setup the navigation list entries
        mDrawerListView =
            (ListView)mDrawerListContainer
                .findViewById(R.id.drawer_list);
        mDrawerListView
            .setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(
                    AdapterView<?> parent,
                    View view,
                    int position,
                    long id) {
                    selectItem(position);
                }
            });
        mDrawerListView.setAdapter(new ArrayAdapter<String>(
            getActivity(),
            android.R.layout.simple_list_item_1,
            android.R.id.text1,
            new String[] { getString(R.string.title_section1),
                getString(R.string.title_section2),
                getString(R.string.title_section3),
                getString(R.string.title_section4), }));
        mDrawerListView
            .setItemChecked(mCurrentSelectedPosition, true);

        // Setup the pid display and login/logout action
        mAccountListView =
            (ListView)mDrawerListContainer
                .findViewById(R.id.drawer_account);
        mAccountListView
            .setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(
                    AdapterView<?> parent,
                    View view,
                    int position,
                    long id) {
                    if (position == 1) {
                        mCallbacks.resetLogin(mAccountListView);
                    }
                }
            });
        Bundle extras = getActivity().getIntent().getExtras();
        String pid =
            (extras != null ? extras.getString("pid") : null);
        int actionText =
            (pid != null ? R.string.logout_text : R.string.login_text);
        String pidText =
            (pid != null ? pid + "@vt.edu" : "Not logged in");
        mAccountListView.setAdapter(new ArrayAdapter<String>(
            getActivity(),
            R.layout.list_item_small,
            new String[] {
                (pidText).toUpperCase(Locale.getDefault()),
                getString(actionText)
                    .toUpperCase(Locale.getDefault()) }));

        return mDrawerListContainer;
    }

    /**
     * Check whether this particular navigation drawer is open
     *
     * @return True if the drawer is open, false if not
     */
    public boolean isDrawerOpen() {
        return mDrawerLayout != null
            && mDrawerLayout.isDrawerOpen(mFragmentContainerView);
    }

    /**
     * Users of this fragment must call this method to set up the navigation
     * drawer interactions.
     *
     * @param fragmentId
     *            The android:id of this fragment in its activity's layout.
     * @param drawerLayout
     *            The DrawerLayout containing this fragment's UI.
     */
    public void setUp(int fragmentId, DrawerLayout drawerLayout) {
        mFragmentContainerView =
            getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;

        // set a custom shadow that overlays the main content when the drawer
        // opens
        mDrawerLayout.setDrawerShadow(
            R.drawable.drawer_shadow,
            GravityCompat.START);

        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    /**
     * Called at init and when the user selects a specific item
     *
     * @param position
     *            The position in the list that the user selected
     */
    private void selectItem(int position) {
        mCurrentSelectedPosition = position;
        if (mDrawerListView != null) {
            mDrawerListView.setItemChecked(position, true);
        }
        if (mDrawerLayout != null) {
            mDrawerLayout.closeDrawer(mFragmentContainerView);
        }
        if (mCallbacks != null) {
            mCallbacks.onNavigationDrawerItemSelected(position);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallbacks = (NavigationDrawerCallbacks)activity;
        }
        catch (ClassCastException e) {
            throw new ClassCastException(
                "Activity must implement NavigationDrawerCallbacks.");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(
            STATE_SELECTED_POSITION,
            mCurrentSelectedPosition);
    }

    private ActionBar getActionBar() {
        return ((ActionBarActivity)getActivity())
            .getSupportActionBar();
    }

    /**
     * Callbacks interface that all activities using this fragment must
     * implement.
     */
    public static interface NavigationDrawerCallbacks {
        /**
         * Called when an item in the navigation drawer is selected.
         *
         * @param position
         *            Position in the drawer that the user selected
         */
        void onNavigationDrawerItemSelected(int position);

        /**
         * Called when the login/logout button at the bottom of the navigation
         * drawer is selected.
         *
         * @param source Optional View that caused this action (eg a button)
         */
        void resetLogin(View source);

        /**
         * Called to restore the action bar to normal after closing the
         * navigation drawer, if it was changed.
         */
        void restoreActionBar();
    }
}
