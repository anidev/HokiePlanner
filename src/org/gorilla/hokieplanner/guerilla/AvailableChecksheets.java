package org.gorilla.hokieplanner.guerilla;

/**
 * Enum representing all the available checksheets, by text and by XML filename
 *
 * @author Anirudh Bagde (anibagde)
 * @author Weyland Chiang (chiangw)
 * @author Sayan Ekambarapu (sayan96)
 * @version Dec 1, 2014
 */
public enum AvailableChecksheets {
    // ----------------------------------------------------------
    /**
     * Computer Science 2014
     */
    CS2014("Computer Science 2014", "CS-2014"),

    // ----------------------------------------------------------
    /**
     * Computer Science 2015
     */
    CS2015("Computer Science 2015", "CS-2015"),

    // ----------------------------------------------------------
    /**
     * Computer Science 2016
     */
    CS2016("Computer Science 2016", "CS-2016"),

    // ----------------------------------------------------------
    /**
     * Computer Science 2017
     */
    CS2017("Computer Science 2017", "CS-2017");

    // ----------------------------------------------------------
    /**
     * Name for this checksheet that can be displayed to the user
     */
    private String text;

    // ----------------------------------------------------------
    /**
     * Filename of the XML that contains the checksheet data, full filename is:
     * Checksheet-filename.xml
     */
    private String filename;

    // ----------------------------------------------------------
    /**
     * Object that represents the available check sheet
     *
     * @param text Name of the check sheet
     * @param file Name of the file for the check sheet
     */
    private AvailableChecksheets(String text, String file) {
        this.text = text;
        this.filename = file;
    }

    // ----------------------------------------------------------
    /**
     * @return Return user-friendly text
     */
    public String getText() {
        return text;
    }

    // ----------------------------------------------------------
    /**
     * Method to return the full filename of the checksheet
     *
     * @return Return full filename
     */
    public String getFile() {
        return "Checksheet-" + filename + ".xml";
    }

    // ----------------------------------------------------------
    /**
     * Returns a string version of the text;
     */
    @Override
    public String toString() {
        return text;
    }
}
