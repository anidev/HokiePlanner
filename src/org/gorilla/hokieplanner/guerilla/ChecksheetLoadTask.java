package org.gorilla.hokieplanner.guerilla;

import android.content.Context;
import android.app.ProgressDialog;
import android.os.AsyncTask;

/**
 * Task to load the checksheet data from XML and download course information
 * into the task. This will run while the main UI displays a progress dialog.
 *
 * @author Anirudh Bagde (anibagde)
 * @author Weyland Chiang (chiangw)
 * @author Sayan Ekambarapu (sayan96)
 * @version Nov 9, 2014
 */
public class ChecksheetLoadTask
    extends AsyncTask<Void, Void, Void> {
    private Context        context;
    private ProgressDialog progress;

    /**
     * Construct the load task with a given Activity context and a progress
     * dialog to display
     *
     * @param context
     *            Context of calling Activity
     * @param progress
     *            ProgressDialog that will we displayed when the task starts
     */
    public ChecksheetLoadTask(Context context, ProgressDialog progress) {
        this.context = context.getApplicationContext();
        this.progress = progress;
    }

    protected void onPreExecute() {
        progress.show();
    }

    protected Void doInBackground(Void... unused) {
        String filename =
            AvailableChecksheets.valueOf(
                Prefs.getSelectedChecksheet()).getFile();
        XMLHandler handler = new XMLHandler(context);
        Checksheet checksheet = handler.parse(filename);
        Prefs.setChecksheet(checksheet);
        CourseCache cache = new CourseCache();
        // TODO download course cache
        Prefs.setCourseCache(cache);
        try {
            Thread.sleep(2000);
        }
        catch (Exception e) {
            // asdf
        }
        return null;
    }

    protected void onPostExecute(Void result) {
        progress.dismiss();
    }
}
