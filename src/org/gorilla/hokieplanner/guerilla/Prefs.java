package org.gorilla.hokieplanner.guerilla;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Static convenience class for managing shared preferences
 *
 * @author Anirudh Bagde (anibagde)
 * @author Weyland Chiang (chiangw)
 * @author Sayan Ekambarapu (sayan96)
 * @version Nov 9, 2014
 */
public class Prefs {
    private static SharedPreferences prefs;

    /**
     * Initialize the static SharedPreferences object for global preferences
     *
     * @param context
     *            Any activity context
     */
    public static void initialize(Context context) {
        prefs =
            context.getSharedPreferences(
                "org.gorilla.hokieplanner.guerilla.PREFERENCES",
                Context.MODE_PRIVATE);
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
}
