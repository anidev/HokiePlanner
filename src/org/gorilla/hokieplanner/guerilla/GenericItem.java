package org.gorilla.hokieplanner.guerilla;

/**
 * Represents a generic item in the checksheet, which is something that does not
 * specify any specific courses that are required to fulfill it. This could
 * happen for various reasons, such as the non-technical elective requirements,
 * which can include almost any elective course available and therefore would be
 * too much to list in the checksheet. This will have the same appearance of the
 * CLE areas in the GUI.
 *
 * @author Anirudh Bagde (anibagde)
 * @author Weyland Chiang (chiangw)
 * @author Sayan Ekambarapu (sayan96)
 * @version Dec 7, 2014
 */
public class GenericItem
    extends RequiredItem {
    private String detail;
    private int    total;

    /**
     * Create a new GenericItem object.
     *
     * @param detail
     *            Detail text of this item
     * @param total
     *            Total credits to fulfill this item's requirement
     */
    public GenericItem(String detail, int total) {
        this.detail = detail;
        this.total = total;
    }

    /**
     * Get detail of this item
     *
     * @return Detail
     */
    public String getDetail() {
        return detail;
    }

    /**
     * Set the detail of this item
     *
     * @param detail
     *            Detail
     */
    public void setDetail(String detail) {
        this.detail = detail;
    }

    /**
     * Get total credits required to fulfill this requirement
     *
     * @return Total number of credits
     */
    public int getTotal() {
        return total;
    }

    /**
     * Set total credits required to fulfill this requirement
     *
     * @param total
     *            Total number of credits
     */
    public void setTotal(int total) {
        this.total = total;
    }
}
