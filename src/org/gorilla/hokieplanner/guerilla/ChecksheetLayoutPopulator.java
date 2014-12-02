package org.gorilla.hokieplanner.guerilla;

import android.widget.Button;
import java.util.Locale;
import java.util.ArrayList;
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
        for (Tree tree : checksheet.getRequirementList()) {
            ViewGroup groupLayout =
                (ViewGroup)inflater.inflate(
                    R.layout.checksheet_status_group,
                    null);
            Requirement req = (Requirement)tree.getFirst();
            ((TextView)groupLayout
                .findViewById(R.id.group_title_label)).setText(req
                .getName() + ", Total: " + req.getTotal());
            if (req.getName().toLowerCase(Locale.getDefault())
                .equals("cle")) {
                addCLEs(tree);
            }
            else {
                addItems(tree, groupLayout, req);
            }
            layout.addView(groupLayout);
        }
    }

    /**
     * Add the widgets for CLE requirements
     *
     * @param tree
     *            The tree for the CLE requirement
     */
    private void addCLEs(Tree tree) {
        ArrayList<Node> nodes =
            ((Node)tree.getNodes().get(tree.getFirst()))
                .getChildren();
        for (Node node : nodes) {
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
    private void addItems(Tree tree, ViewGroup root, Object parent) {
        ArrayList<Node> nodes =
            ((Node)tree.getNodes().get(parent)).getChildren();
        for (Node node : nodes) {
            RequiredItem item = (RequiredItem)node.getData();
            if (item instanceof RequiredCourse) {
                RequiredCourse course = (RequiredCourse)item;
                final String id = CourseCache.getCourseID(course);
                if (id == null) {
                    return;
                }
                View courseWidget =
                    inflater.inflate(R.layout.course_info, null);
                ((TextView)courseWidget
                    .findViewById(R.id.course_name))
                    .setText(getCourseName(course));
                Button stateButton =
                    (Button)courseWidget
                        .findViewById(R.id.course_state_button);
                CourseCache.CourseData data =
                    Prefs.getCourseCache().getData(id);
                stateButton.setText(data.getState().toString());
                stateButton
                    .setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Button button = (Button)v;
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
        if (course.getName() != null && !course.getName().equals("")) {
            return course.getName();
        }
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
            String from = course.getFrom();
            String to = course.getTo();
            if (from.equals(to)) {
                return course.getDepartment() + " " + from;
            }
            else {
                return course.getDepartment() + " " + from + "-" + to;
            }
        }
    }

}
