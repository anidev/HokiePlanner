package org.gorilla.hokieplanner.guerilla;

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
     * Return the application context. This cannot be used for anything UI-related.
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
        prefs.edit().putString("checksheet", checksheetName).commit();
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
     * Get the global app course cache
     *
     * @return CourseCache object
     */
    public static CourseCache getCourseCache() {
        return courseCache;
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
     * Return the checksheet object that was loaded from the XML file
     *
     * @return Checksheet object
     */
    public static Checksheet getChecksheet() {
        return checksheet;
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
     * Get the global Cas authentication object that was set, or null if not
     * currently logged in
     *
     * @return Cas auth object
     */
    public static Cas getAuth() {
        return auth;
    }

    /**
     * Clears all stored preferences, generally to be used for testing
     */
    public static void clear() {
        prefs.edit().clear().commit();
    }
}
