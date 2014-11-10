package org.gorilla.hokieplanner.guerilla;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Static convenience class for managing shared preferences
 *
 * @author Anirudh Bagde
 * @version Nov 9, 2014
 */
public class Prefs {
    private static SharedPreferences prefs;

    public static void initialize(Context context) {
        prefs =
            context.getSharedPreferences(
                "org.gorilla.hokieplanner.guerilla.PREFERENCES",
                Context.MODE_PRIVATE);
    }

    public static String getUserPID() {
        return prefs.getString("pid", null);
    }

    public static void setUserPID(String pid) {
        if (pid == null) {
            pid = "";
        }
        prefs.edit().putString("pid", pid.trim()).apply();
    }
}
