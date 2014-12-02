package org.gorilla.hokieplanner.guerilla;

// -------------------------------------------------------------------------
/**
 * A class to represent the group tag in the xml sheet. It only contains a
 * number to show the required number of courses that need to be taken from that
 * group. It extends the RequiredItem class so it can be added to the tree.
 *
 * @author Anirudh Bagde (anibagde)
 * @author Weyland Chiang (chiangw)
 * @author Sayan Ekambarapu (sayan96)
 * @version Dec 1, 2014
 */
public class CourseGroup
    extends RequiredItem
{
    private int total;


    // ----------------------------------------------------------
    /**
     * gets the total number of classes that need to be taken from that group
     *
     * @return the number of classes that need to be taken from that group
     */
    public int getTotal()
    {
        return total;
    }


    // ----------------------------------------------------------
    /**
     * sets the number of classes that need to be taken from the group.
     *
     * @param total
     *            sets the number of classes that need to be taken from the
     *            group.
     */
    public void setTotal(int total)
    {
        this.total = total;
    }


    // ----------------------------------------------------------
    /**
     * Create a new CourseGroup object.
     * @param num the number of classes
     */
    public CourseGroup(int num)
    {
        total = num;
    }

}
