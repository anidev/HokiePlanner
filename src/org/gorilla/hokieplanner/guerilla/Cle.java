package org.gorilla.hokieplanner.guerilla;

// -------------------------------------------------------------------------
/**
 * A class to represent the cle's. It extends RequiredItem so it can be added to
 * the tree
 *
 * @author Anirudh Bagde (anibagde)
 * @author Weyland Chiang (chiangw)
 * @author Sayan Ekambarapu (sayan96)
 * @version Dec 1, 2014
 */
public class Cle
    extends RequiredItem
{
    private int area;
    private int total;


    // ----------------------------------------------------------
    /**
     * Create a new Cle object.
     * @param area the area number of the cle
     * @param total the total number of credits required from that cle area
     */
    public Cle(int area, int total)
    {
        this.area = area;
        this.total = total;
    }


    // ----------------------------------------------------------
    /**
     * Returns the area number of the cle
     * @return the area number of the cle
     */
    public int getArea()
    {
        return area;
    }


    // ----------------------------------------------------------
    /**
     * sets the area of the cle to the specified number
     * @param area this becomes the area of the cle
     */
    public void setArea(int area)
    {
        this.area = area;
    }


    // ----------------------------------------------------------
    /**
     * returns the total number of credits required from the cle
     * @return the total number of credits required from the cle
     */
    public int getTotal()
    {
        return total;
    }


    // ----------------------------------------------------------
    /**
     * sets the total number of credits
     * @param total sets the number of credits required to the specified number
     */
    public void setTotal(int total)
    {
        this.total = total;
    }
}
