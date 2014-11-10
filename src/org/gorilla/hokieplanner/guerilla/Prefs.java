package org.gorilla.hokieplanner.guerilla;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Static convenience class for managing shared preferences
 *
 * @author Anirudh Bagde
 * @author Weyland Chiang
 * @author Sayan Ekambarapu
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
        String finalPid=pid;
        if (finalPid != null) {
            finalPid = finalPid.trim();
        }
        prefs.edit().putString("pid", finalPid).apply();
    }
}
