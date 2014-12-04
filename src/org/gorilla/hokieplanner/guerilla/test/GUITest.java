package org.gorilla.hokieplanner.guerilla.test;

import android.widget.TextView;
import org.gorilla.hokieplanner.guerilla.R;
import android.app.Activity;
import org.gorilla.hokieplanner.guerilla.AvailableChecksheets;
import android.test.ActivityInstrumentationTestCase2;
import org.gorilla.hokieplanner.guerilla.Prefs;
import org.gorilla.hokieplanner.guerilla.PlannerActivity;

/**
 * Perform all GUI-related tests here
 *
 * @author Anirudh Bagde (anibagde)
 * @author Weyland Chiang (chiangw)
 * @author Sayan Ekambarapu (sayan96)
 * @version Dec 1, 2014
 */
public class GUITest
    extends ActivityInstrumentationTestCase2<PlannerActivity> {

    /**
     * Create a new GUITest object
     */
    public GUITest() {
        super(PlannerActivity.class);
    }

    /**
     * Initialize and clear stored prefs, then selects a checksheet to avoid
     * having MajorPickerActivity started
     */
    public void setUp() {
        Prefs.initialize(getActivity());
        Prefs.clear();
        Prefs.setSelectedChecksheet(AvailableChecksheets.CS2016.name());
    }

    /**
     * Make sure welcome fragment was populated correctly
     */
    public void testWelcome() {
        Activity activity = getActivity();
        assertEquals("Total Credits: 0", ((TextView)activity.findViewById(R.id.credits_label)).getText());
        assertEquals("Computer Science 2016", ((TextView)activity.findViewById(R.id.selected_major_value)).getText());
    }
}
