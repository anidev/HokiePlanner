package org.gorilla.hokieplanner.guerilla;

// -------------------------------------------------------------------------
/**
 *
 * @author Anirudh Bagde (anibagde)
 * @author Weyland Chiang (chiangw)
 * @author Sayan Ekambarapu (sayan96)
 * @version December 1, 2014
 */
public class CourseGroup extends RequiredItem
{
    private int total;

    public int getTotal()
    {
        return total;
    }

    public void setTotal(int total)
    {
        this.total = total;
    }

    public CourseGroup(int num)
    {
        total = num;
    }

}
