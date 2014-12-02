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
    NOTDONE("Not done"),
    /**
     * Requirement currently in progress
     */
    INPROGRESS("In progress"),
    /**
     * Requirement completed
     */
    DONE("Done");

    private String text;

    private RequirementState(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
