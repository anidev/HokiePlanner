package org.gorilla.hokieplanner.guerilla;

/**
 * Represents the different states a particular requirement can be in
 *
 * @author Anirudh Bagde (anibagde)
 * @author Weyland Chiang (chiangw)
 * @author Sayan Ekambarapu (sayan96)
 * @version Nov 9, 2014
 */
public enum RequirementState {
    /**
     * Not started yet
     */
    NOTDONE("Not done", R.drawable.btn_notdone),
    /**
     * Requirement currently in progress
     */
    INPROGRESS("In progress", R.drawable.btn_inprogress),
    /**
     * Requirement completed
     */
    DONE("Done", R.drawable.btn_done);

    private String text;
    private int    icon;

    private RequirementState(String text, int icon) {
        this.text = text;
        this.icon = icon;
    }

    @Override
    public String toString() {
        return text;
    }

    /**
     * Return the resource ID of the associated icon for this state
     *
     * @return Resource ID of icon
     */
    public int getIcon() {
        return icon;
    }
}
