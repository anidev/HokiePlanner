package org.gorilla.hokieplanner.guerilla;

// -------------------------------------------------------------------------
/**
 * A class that represents the requirement tag in the XML sheet. It extends the
 * RequiredItem class so that it can be used for the tree.
 *
 * @author Anirudh Bagde (anibagde)
 * @author Weyland Chiang (chiangw)
 * @author Sayan Ekambarapu (sayan96)
 * @version Dec 1, 2014
 */
public class Requirement
    extends RequiredItem
{
    private String name;


    // ----------------------------------------------------------
    /**
     * Returns the name of the requirement
     *
     * @return the name of the requirement
     */
    public String getName()
    {
        return name;
    }


    // ----------------------------------------------------------
    /**
     * Sets the name of the requirement
     *
     * @param name
     *            this becomes the name of the requirement
     */
    public void setName(String name)
    {
        this.name = name;
    }


    // ----------------------------------------------------------
    /**
     * Returns the total number of courses needed from that requirement
     *
     * @return the number of courses need from that requriement
     */
    public int getTotal()
    {
        return total;
    }


    // ----------------------------------------------------------
    /**
     * sets the total of the requirement
     *
     * @param total
     *            sets the total of the requirement
     */
    public void setTotal(int total)
    {
        this.total = total;
    }

    private int total;


    // ----------------------------------------------------------
    /**
     * Create a new Requirement object.
     *
     * @param str
     *            the string to be set as the name
     * @param num
     *            the number to be set as the total number of courses
     */
    public Requirement(String str, int num)
    {
        name = str;
        total = num;
    }

}
