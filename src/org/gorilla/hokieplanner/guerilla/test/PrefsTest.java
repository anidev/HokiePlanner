package org.gorilla.hokieplanner.guerilla.test;

import org.gorilla.hokieplanner.guerilla.Checksheet;
import org.gorilla.hokieplanner.guerilla.CourseCache;
import org.gorilla.hokieplanner.guerilla.Prefs;
import android.test.AndroidTestCase;

/**
 * Test the methods in Prefs to make sure they save data correctly and return
 * valid values. NOTE: Running this test will clear all stored preferences for
 * the app.
 *
 * @author Anirudh Bagde (anibagde)
 * @author Weyland Chiang (chiangw)
 * @author Sayan Ekambarapu (sayan96)
 * @version Nov 9, 2014
 */
public class PrefsTest
    extends AndroidTestCase {

    /**
     * Initialize and clear prefs
     */
    public void setUp() {
        Prefs.initialize(getContext());
        Prefs.clear();
    }

    /**
     * Test userPID methods
     */
    public void testPID() {
        // These methods should automatically trim leading and trailing spaces,
        // so test for that too
        assertNull(Prefs.getUserPID());
        Prefs.setUserPID("  asdf  ");
        assertEquals("asdf", Prefs.getUserPID());
        Prefs.setUserPID("  ");
        assertNull(Prefs.getUserPID());
        Prefs.setUserPID(null);
        assertNull(Prefs.getUserPID());
    }

    /**
     * Test remembering PID methods
     */
    public void testRemember() {
        assertTrue(Prefs.isRememberingPID());
        Prefs.setRememberingPID(false);
        assertFalse(Prefs.isRememberingPID());
    }

    /**
     * Test selected checksheet methods
     */
    public void testSelected() {
        assertNull(Prefs.getSelectedChecksheet());
        Prefs.setSelectedChecksheet("Checksheet-CS-2016");
        assertEquals(
            "Checksheet-CS-2016",
            Prefs.getSelectedChecksheet());
        Prefs.setSelectedChecksheet("");
        assertNull(Prefs.getSelectedChecksheet());
        Prefs.setSelectedChecksheet(null);
        assertNull(Prefs.getSelectedChecksheet());
    }

    /**
     * Test setting and getting the global objects
     */
    public void testGlobals() {
        CourseCache cache = new CourseCache();
        assertNull(Prefs.getCourseCache());
        Prefs.setCourseCache(cache);
        assertSame(cache, Prefs.getCourseCache());

        Checksheet checksheet = new Checksheet(null);
        assertNull(Prefs.getChecksheet());
        Prefs.setChecksheet(checksheet);
        assertSame(checksheet, Prefs.getChecksheet());

        // Not testing Cas because cannot construct that object without doing
        // network communication
    }
}
