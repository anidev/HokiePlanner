package org.gorilla.hokieplanner.guerilla;

import java.util.Locale;
import com.vtaccess.schedule.Course;
import java.util.List;
import com.vtaccess.CourseInfo;
import com.vtaccess.Semester;
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
    private static final String[] SEMESTERS = new String[] {
        Semester.FALL, Semester.SPRING     };
    private ProgressDialog        progress;
    private Runnable              callback;

    /**
     * Construct the load task with a given Activity context and a progress
     * dialog to display
     *
     * @param progress
     *            ProgressDialog that will we displayed when the task starts
     * @param callback
     *            What should be run after the loading is finished (eg. to
     *            update a view)
     */
    public ChecksheetLoadTask(
        ProgressDialog progress,
        Runnable callback) {
        this.progress = progress;
        this.callback = callback;
    }

    protected void onPreExecute() {
        progress.show();
    }

    protected Void doInBackground(Void... unused) {
        // Get the enum from the value stored in prefs and get the filename from
        // that enum
        String filename =
            AvailableChecksheets.valueOf(
                Prefs.getSelectedChecksheet()).getFile();
        XMLHandler handler =
            new XMLHandler(Prefs.getApplicationContext());
        // Parse the checksheet and make it globally available
        Checksheet checksheet = handler.parse(filename);
        Prefs.setChecksheet(checksheet);
        // Connect to the online timetable (using Cas auth if the user is logged
        // in) and get course info
        CourseCache cache = new CourseCache();
        Prefs.setCourseCache(cache);
        // As course cache is populated, each CourseData object created will
        // automatically sync with data stored in long term storage
        populateCourseCache(checksheet, cache);
        // Calculate total credits, after course data has been loaded
        cache.calculateTotalCredits();
        return null;
    }

    protected void onPostExecute(Void result) {
        progress.dismiss();
        callback.run();
    }

    private void populateCourseCache(
        Checksheet checksheet,
        CourseCache cache) {
        for (Tree<RequiredItem> tree : checksheet.getList()) {
            getInfoForCourses(tree, tree.getFirst(), cache);
        }
    }

    private void getInfoForCourses(
        Tree<RequiredItem> tree,
        RequiredItem parent,
        CourseCache cache) {
        for (Node<RequiredItem> node : tree.getNodes().get(parent)
            .getChildren()) {
            RequiredItem item = node.getData();
            if (item instanceof RequiredCourse) {
                RequiredCourse course = (RequiredCourse)item;
                // Skip if it's a range of courses or if it already has a name
                if (course.getFrom() != course.getTo()
                    || !course.getName().equals("")) {
                    continue;
                }
                // Skip if for some reason there is no way to create an ID for
                // this course
                final String id = CourseCache.getCourseID(course);
                if (id == null) {
                    continue;
                }
                // Skip if data for this course has already been loaded
                CourseCache.CourseData data =
                    Prefs.getCourseCache().getData(id);
                if (!data.getName().equals("")
                    || data.getCredits() != 0) {
                    continue;
                }
                // Download info from online, try fall semester then spring
                // Possibly in future include winter, summer I, and summer II
                List<Course> courses = null;
                for (String sem : SEMESTERS) {
                    courses =
                        CourseInfo.getCourses(
                            Semester.getSemesterCode(sem),
                            course.getDepartment().toUpperCase(
                                Locale.getDefault()),
                            "" + course.getFrom());
                    if (courses != null && courses.size() > 0) {
                        break;
                    }
                }
                // Skip if no info found
                if (courses == null || courses.size() == 0) {
                    continue;
                }
                // Finally populate info
                data.setCredits(courses.get(0).getCredits());
                data.setName(courses.get(0).getName());
            }
            else if (item instanceof CourseGroup) {
                getInfoForCourses(tree, item, cache);
            }
        }
    }
}
