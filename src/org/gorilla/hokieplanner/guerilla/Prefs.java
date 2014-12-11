package org.gorilla.hokieplanner.guerilla;

import android.util.Log;
import com.vtaccess.Cas;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Static convenience class for managing shared preferences and globally
 * accessible objects
 *
 * @author Anirudh Bagde (anibagde)
 * @author Weyland Chiang (chiangw)
 * @author Sayan Ekambarapu (sayan96)
 * @version Nov 9, 2014
 */
public class Prefs {
    private static Context           appContext;
    private static SharedPreferences prefs;
    private static CourseCache       courseCache;
    private static Checksheet        checksheet;
    private static Cas               auth;
    private static int               creditCache;

    /**
     * Initialize the static SharedPreferences object for global preferences
     *
     * @param context
     *            Any activity context
     */
    public static void initialize(Context context) {
        appContext = context.getApplicationContext();
        prefs =
            appContext.getSharedPreferences(
                "org.gorilla.hokieplanner.guerilla.PREFERENCES",
                Context.MODE_PRIVATE);
    }

    /**
     * Return the application context. This cannot be used for anything
     * UI-related.
     *
     * @return Application context
     */
    public static Context getApplicationContext() {
        return appContext;
    }

    /**
     * Get the saved user PID
     *
     * @return PID string, or null if it hasn't been set
     */
    public static String getUserPID() {
        String pid = prefs.getString("pid", null);
        if (pid != null && pid.equals("")) {
            pid = null;
        }
        return pid;
    }

    /**
     * Set the saved user PID, or unset it by passing null
     *
     * @param pid
     *            PID string, or null to unset it
     */
    public static void setUserPID(String pid) {
        String finalPid = pid;
        if (finalPid != null) {
            finalPid = finalPid.trim();
        }
        prefs.edit().putString("pid", finalPid).apply();
    }

    /**
     * Get whether the app is remembering the user's PID, true by default
     *
     * @return Whether remembering PID
     */
    public static boolean isRememberingPID() {
        return prefs.getBoolean("pidremember", true);
    }

    /**
     * Set whether the app should remember the user's PID
     *
     * @param remembering
     *            Whether remembering PID
     */
    public static void setRememberingPID(boolean remembering) {
        prefs.edit().putBoolean("pidremember", remembering).commit();
    }

    /**
     * Get the user-selected checksheet
     *
     * @return Name of the AvailableChecksheets enum corresponding to the one
     *         the user selected, or null if the user has not yet selected
     *         anything
     */
    public static String getSelectedChecksheet() {
        String checksheetName = prefs.getString("checksheet", null);
        if (checksheetName != null && checksheetName.equals("")) {
            checksheetName = null;
        }
        return checksheetName;
    }

    /**
     * Set the user-selected checksheet, or null to reset it
     *
     * @param checksheetName
     *            This should be the name of the AvailableChecksheets enum that
     *            corresponds to the selected checksheet
     */
    public static void setSelectedChecksheet(String checksheetName) {
        String oldChecksheet = getSelectedChecksheet();
        if(!checksheetName.equals(oldChecksheet)) {
            prefs.edit().putString("checksheet", checksheetName).commit();
            // Erase old saved checksheet object to force it to be reloaded
            checksheet = null;
        }
    }

    /**
     * Get the global app course cache
     *
     * @return CourseCache object
     */
    public static CourseCache getCourseCache() {
        return courseCache;
    }

    /**
     * Set the global app course cache, with data pulled from online
     *
     * @param courseCache
     *            CourseCache object to set
     */
    public static void setCourseCache(CourseCache courseCache) {
        Prefs.courseCache = courseCache;
    }

    /**
     * Return the checksheet object that was loaded from the XML file
     *
     * @return Checksheet object
     */
    public static Checksheet getChecksheet() {
        return checksheet;
    }

    /**
     * Set the checksheet object loaded from the XML file
     *
     * @param checksheet
     *            Checksheet object
     */
    public static void setChecksheet(Checksheet checksheet) {
        Prefs.checksheet = checksheet;
    }

    /**
     * Get the global Cas authentication object that was set, or null if not
     * currently logged in
     *
     * @return Cas auth object
     */
    public static Cas getAuth() {
        return auth;
    }

    /**
     * Set the global Cas authentication object after logging in
     *
     * @param auth
     *            Cas object after logging in, or null if logging out
     */
    public static void setAuth(Cas auth) {
        Prefs.auth = auth;
    }

    /**
     * Get the total CLE credits for the specified area
     *
     * @param area
     *            CLE area, 1 - 7
     * @return Number of credits for that area
     */
    public static int getCLE(int area) {
        int[] allCLE = getAllCLE();
        return allCLE[area - 1];
    }

    /**
     * Set the total CLE area credits for the specified area
     *
     * @param area
     *            CLE area, 1 - 7
     * @param credits
     *            Number of credits
     */
    public static void setCLE(int area, int credits) {
        int[] allCLE = getAllCLE();
        allCLE[area - 1] = credits;
        setAllCLE(allCLE);
    }

    /**
     * Return array of all CLE area credits
     *
     * @return Array of 7 elements, one for each area, ordered 1-7 (index starts
     *         at 0)
     */
    public static int[] getAllCLE() {
        String allStr = prefs.getString("cle", "0000000");
        int[] allArray = new int[7];
        for (int i = 0; i < 7; i++) {
            allArray[i] = Integer.parseInt("" + allStr.charAt(i));
        }
        return allArray;
    }

    /**
     * Set all the CLE area credits at once through the given ordered array
     *
     * @param credits
     *            Array of CLE area credits ordered 1-7 (index starts at 0)
     */
    public static void setAllCLE(int[] credits) {
        int[] oldCredits = getAllCLE();
        for(int c : oldCredits) {
            creditCache -= c;
        }
        char[] allChars = new char[7];
        for (int i = 0; i < 7; i++) {
            allChars[i] = (char)(credits[i] + '0');
            creditCache += credits[i];
        }
        String allStr = new String(allChars);
        prefs.edit().putString("cle", allStr).commit();
    }

    /**
     * Get the saved course state stored for the course specified by the given
     * ID, which is the ID used by CourseCache.
     *
     * @param id
     *            Course ID used by CourseCache
     * @return Course requirement state
     */
    public static RequirementState getCourseState(String id) {
        String name = prefs.getString("credit-" + id, "NOTDONE");
        return RequirementState.valueOf(name);
    }

    /**
     * Saves course state for the specified course in long term storage. The
     * given ID is the course ID used by CourseCache.
     *
     * @param id
     *            Course ID used by CourseCache
     * @param state
     *            Course requirement state
     */
    public static void setCourseState(
        String id,
        RequirementState state) {
        prefs.edit().putString("credit-" + id, state.name()).commit();
    }

    /**
     * Get the cached number of total credits. This number is only valid while
     * the app is running and must be recalculated every time the app starts.
     *
     * @return Total credits marked as completed
     */
    public static int getTotalCredits() {
        return creditCache;
    }

    /**
     * Set the cached number of total credits. This number will not be saved to
     * long term storage, in order to prevent a bug or other issue from
     * permanently making the count inaccurate.
     *
     * @param credits
     *            Total credits calculated to be completed
     */
    public static void setTotalCredits(int credits) {
        Prefs.creditCache = credits;
        Log.d("HokiePlanner", "Credits: " + credits);
    }

    /**
     * Clears all stored preferences, generally to be used for testing
     */
    public static void clear() {
        prefs.edit().clear().commit();
    }
}
