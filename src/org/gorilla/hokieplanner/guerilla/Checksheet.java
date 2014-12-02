package org.gorilla.hokieplanner.guerilla;

import java.util.ArrayList;

// -------------------------------------------------------------------------
/**
 * A class to represent the XML checksheet. It is an arraylist of trees.
 *
 * @author Anirudh Bagde (anibagde)
 * @author Weyland Chiang (chiangw)
 * @author Sayan Ekambarapu (sayan96)
 * @version Dec 1, 2014
 */
public class Checksheet
{
    private ArrayList<Tree<RequiredItem>> list;

    // ----------------------------------------------------------
    /**
     * Create a new Checksheet object.
     * @param rList the list to be used for the class
     */
    public Checksheet(ArrayList<Tree<RequiredItem>> rList)
    {
        list = rList;
    }


    // ----------------------------------------------------------
    /**
     * Returns the list of trees
     * @return the list of trees
     */
    public ArrayList<Tree<RequiredItem>> getList()
    {
        return list;
    }


    // ----------------------------------------------------------
    /**
     * sets the list to the specified list
     * @param list sets the list to the specified list
     */
    public void setList(ArrayList<Tree<RequiredItem>> list)
    {
        this.list = list;
    }


    // ----------------------------------------------------------
    /**
     * Returns the size of the list
     * @return the size of the list
     */
    public int size()
    {
        return list.size();
    }
}
