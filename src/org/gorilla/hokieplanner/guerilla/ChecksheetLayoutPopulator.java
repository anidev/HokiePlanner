package org.gorilla.hokieplanner.guerilla;

import android.widget.Button;
import java.util.Locale;
import java.util.ArrayList;
import android.annotation.SuppressLint;
import android.view.ViewGroup;
import android.widget.TextView;
import android.view.View;
import android.view.LayoutInflater;

/**
 * Helper class for populating the checksheet layout fragment
 *
 * @author Anirudh Bagde (anibagde)
 * @author Weyland Chiang (chiangw)
 * @author Sayan Ekambarapu (sayan96)
 * @version Nov 9, 2014
 */
@SuppressLint("InflateParams")
public class ChecksheetLayoutPopulator {
    private View           rootView;
    private LayoutInflater inflater;

    /**
     * Construct the populator with the given root view and layout inflater
     *
     * @param rootView
     *            The root view for the fragment
     * @param inflater
     *            The layout inflater to use
     */
    public ChecksheetLayoutPopulator(
        View rootView,
        LayoutInflater inflater) {
        this.rootView = rootView;
        this.inflater = inflater;
    }

    /**
     * Populate the layout with the currently selected checksheet
     */
    public void populate() {
        Checksheet checksheet = Prefs.getChecksheet();
        ViewGroup layout =
            (ViewGroup)rootView.findViewById(R.id.checksheet_layout);

        for (Tree<RequiredItem> tree : checksheet.getList()) {
            // Create a new group in the main fragment layout to contain all the
            // items in this requirement
            ViewGroup groupLayout =
                (ViewGroup)inflater.inflate(
                    R.layout.checksheet_status_group,
                    null);
            // Get root node of the tree, which should be a Requirement object
            Requirement req = (Requirement)tree.getFirst();
            ((TextView)groupLayout
                .findViewById(R.id.group_title_label)).setText(req
                .getName() + ", Total: " + req.getTotal());
            // CLE items are displayed differently in the GUI
            if (req.getName().toLowerCase(Locale.getDefault())
                .equals("cle")) {
                addCLEs(tree);
            }
            else {
                addItems(tree, groupLayout, req);
            }
            layout.addView(groupLayout);
            // Set the bottom margin for this group layout, so it is more
            // distinct from the other requirement groups
            ViewGroup.MarginLayoutParams p =
                (ViewGroup.MarginLayoutParams)groupLayout
                    .getLayoutParams();
            p.setMargins(
                p.leftMargin,
                p.topMargin,
                p.rightMargin,
                (int)Prefs.getApplicationContext().getResources()
                    .getDimension(R.dimen.checksheet_group_margin));
        }
    }

    /**
     * Add the widgets for CLE requirements
     *
     * @param tree
     *            The tree for the CLE requirement
     */
    private void addCLEs(Tree<RequiredItem> tree) {
        ArrayList<Node<RequiredItem>> nodes =
            tree.getNodes().get(tree.getFirst()).getChildren();
        for (Node<RequiredItem> node : nodes) {
            // TODO CLE gui stuff
        }
    }

    /**
     * Add the widgets for all non-CLE course requirements. This method is
     * called recursively for each course group encountered.
     *
     * @param tree
     *            The tree for this requirement
     * @param root
     *            The root view that the widgets and course groups will be added
     *            to
     * @param parent
     *            The parent object in the tree
     */
    private void addItems(
        Tree<RequiredItem> tree,
        ViewGroup root,
        RequiredItem parent) {
        ArrayList<Node<RequiredItem>> nodes =
            tree.getNodes().get(parent).getChildren();

        for (Node<RequiredItem> node : nodes) {
            RequiredItem item = node.getData();
            // A requirement or course group can contain both courses and groups
            // of courses. This method will recursively call itself to add items
            // within a course group.
            if (item instanceof RequiredCourse) {
                RequiredCourse course = (RequiredCourse)item;
                final String id = CourseCache.getCourseID(course);
                if (id == null) {
                    return;
                }
                // Create the widget that represents this course in the GUI
                View courseWidget =
                    inflater.inflate(R.layout.course_info, null);
                ((TextView)courseWidget
                    .findViewById(R.id.course_name))
                    .setText(getCourseName(course));
                // This button changes the state of the course between not done,
                // in progress, and done
                Button stateButton =
                    (Button)courseWidget
                        .findViewById(R.id.course_state_button);
                // Set the button's state based on what the state of this
                // particular course is, according to the course cache. The
                // cached state will have been loaded from storage, or set to
                // the default "not done".
                CourseCache.CourseData data =
                    Prefs.getCourseCache().getData(id);
                stateButton.setText(data.getState().toString());
                stateButton
                    .setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Button button = (Button)v;
                            // Get the cached data again in case it was changed
                            @SuppressWarnings("hiding")
                            CourseCache.CourseData data =
                                Prefs.getCourseCache().getData(id);
                            RequirementState newState =
                                RequirementState.NOTDONE;
                            switch (data.getState()) {
                                case NOTDONE:
                                    newState =
                                        RequirementState.INPROGRESS;
                                    break;
                                case INPROGRESS:
                                    newState = RequirementState.DONE;
                                    break;
                                case DONE:
                                    newState =
                                        RequirementState.NOTDONE;
                                    break;
                            }
                            data.setState(newState);
                            button.setText(newState.toString());
                        }
                    });
                root.addView(courseWidget);
            }
            else if (item instanceof CourseGroup) {
                // Call addItem with this a new group layout as the root view
                // and this group item as the parent node of the subtree
                CourseGroup group = (CourseGroup)item;
                ViewGroup groupLayout =
                    (ViewGroup)inflater.inflate(
                        R.layout.checksheet_status_group,
                        null);
                ((TextView)groupLayout
                    .findViewById(R.id.group_title_label))
                    .setText("Required: " + group.getTotal());
                addItems(tree, groupLayout, item);
                root.addView(groupLayout);
            }
        }
    }

    /**
     * Get the course name by polling the course cache or returning
     * department+number if the cache does not have the data
     *
     * @return Course name to display to user
     */
    private String getCourseName(RequiredCourse course) {
        // Some course objects may already have a name specified in the XML file
        if (course.getName() != null && !course.getName().equals("")) {
            return course.getName();
        }
        // Next, check the course cache to see if it has name, either from
        // storage or from an earlier network cache
        CourseCache cache = Prefs.getCourseCache();
        String id = CourseCache.getCourseID(course);
        if (id == null) {
            return null;
        }
        CourseCache.CourseData data = cache.getData(id);
        if (data.getName() != null && !data.getName().equals("")) {
            return data.getName();
        }
        else {
            // Finally, if the cache has nothing, construct an acceptable string
            // to display to the user using the course's department and number
            int from = course.getFrom();
            int to = course.getTo();
            String name =
                course.getDepartment().toUpperCase(
                    Locale.getDefault())
                    + " " + from;
            if (from != to) {
                name += "-" + to;
            }
            return name;
        }
    }

}
