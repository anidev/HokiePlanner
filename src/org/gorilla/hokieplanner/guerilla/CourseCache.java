package org.gorilla.hokieplanner.guerilla;

import java.util.HashMap;

/**
 * This class contains a cache of all the course data downloaded from the
 * timetable. It should be generated every time the checksheet is changed.
 *
 * @author Anirudh Bagde (anibagde)
 * @author Weyland Chiang (chiangw)
 * @author Sayan Ekambarapu (sayan96)
 * @version Nov 9, 2014
 */
public class CourseCache {
    private HashMap<String, CourseData> map;

    /**
     * Initialize an empty course cache object
     */
    public CourseCache() {
        map = new HashMap<String, CourseData>();
    }

    /**
     * Determine the appropriate ID for this course that can be used for the
     * cache. This is the department+number if available, else the course's
     * name, else null
     *
     * @param course
     *            The course in question
     * @return The ID for this course
     */
    public static String getCourseID(RequiredCourse course) {
        String dep = course.getDepartment();
        int numFrom = course.getFrom();
        int numTo = course.getTo();
        String name = course.getName();
        if (dep != null && !dep.equals("")) {
            return dep + numFrom + "-" + numTo;
        }
        else if (name != null && !name.equals("")) {
            return name;
        }
        else {
            return null;
        }
    }

    /**
     * Add course data for the course corresponding to the specified ID
     *
     * @param id
     *            Course ID, which is the department+id (eg. "CS2114") or the
     *            name if no specific id is given
     * @param data
     *            CourseData object for this course
     */
    public void addData(String id, CourseData data) {
        map.put(id, data);
    }

    /**
     * Get course data for the course corresponding to the specified ID. If it
     * does not exist, create one with an empty name and 0 credits.
     *
     * @param id
     *            Course ID, see addData for how this is formed
     * @return CourseData object for the requested course
     */
    public CourseData getData(String id) {
        CourseData data = map.get(id);
        if (data == null) {
            data =
                new CourseData(id, "", 0, Prefs.getCourseState(id));
            addData(id, data);
        }
        return data;
    }

    /**
     * Calculate the total credit value and set it in Prefs. This is done by
     * iterating through all the course data objects that are in the map and
     * summing the credits of the ones marked as Done.
     */
    public void calculateTotalCredits() {
        int totalCredits = 0;
        for(CourseData data : map.values()) {
            if(!data.state.equals(RequirementState.DONE)) {
                continue;
            }
            totalCredits += data.credits;
        }
        // Add in CLE area credits
        int[] cle = Prefs.getAllCLE();
        for(int credits : cle) {
            totalCredits += credits;
        }
        Prefs.setTotalCredits(totalCredits);
    }

    /**
     * Supplemental class for representing course data downloaded from the
     * timetable. This class contains information such as course name and number
     * of credits that are not included in the checksheet XML files.
     *
     * @author Anirudh Bagde (anibagde)
     * @author Weyland Chiang (chiangw)
     * @author Sayan Ekambarapu (sayan96)
     * @version Nov 9, 2014
     */
    public static class CourseData {
        private String           id;
        private String           name;
        private int              credits;
        private RequirementState state;

        /**
         * Construct a new CourseData object
         *
         * @param id
         *            ID for the course this data corresponds to
         * @param name
         *            Name of the course
         * @param credits
         *            Number of credts for the course
         * @param state
         *            The initial state for the course
         */
        public CourseData(
            String id,
            String name,
            int credits,
            RequirementState state) {
            this.id = id;
            this.name = name;
            this.credits = credits;
            this.state = state;
        }

        /**
         * @return Course name
         */
        public String getName() {
            return name;
        }

        /**
         * @return Course credits
         */
        public int getCredits() {
            return credits;
        }

        /**
         * @return The current state for this course
         */
        public RequirementState getState() {
            return state;
        }

        /**
         * @param name
         *            the name to set
         */
        public void setName(String name) {
            this.name = name;
        }

        /**
         * @param credits
         *            the credits to set
         */
        public void setCredits(int credits) {
            this.credits = credits;
        }

        /**
         * Set the state for this course
         *
         * @param state
         *            The new state
         */
        public void setState(RequirementState state) {
            updateTotalCredits(this.state, state);
            this.state = state;
            Prefs.setCourseState(id, state);
        }

        /**
         * Update the total credits value stored by Prefs in response to the
         * state of this course changing
         *
         * @param oldState
         *            State the course was in before
         * @param newState
         *            State the course was just changed to
         */
        private void updateTotalCredits(
            RequirementState oldState,
            RequirementState newState) {
            if (oldState.equals(newState)) {
                return;
            }
            int curCredits = Prefs.getTotalCredits();
            if ((oldState.equals(RequirementState.NOTDONE) || oldState
                .equals(RequirementState.INPROGRESS))
                && newState.equals(RequirementState.DONE)) {
                // Adding completed credits
                curCredits += credits;
            }
            else if (oldState.equals(RequirementState.DONE)) {
                // Removing completed credits
                curCredits -= credits;
            }
            Prefs.setTotalCredits(curCredits);
        }
    }
}
