package org.gorilla.hokieplanner.guerilla;

import java.util.ArrayList;

//-------------------------------------------------------------------------
/**
 * Checksheet class that uses an ArrayList to hold all of the requirements
 *
 * @author Anirudh Bagde (anibagde)
 * @author Weyland Chiang (chiangw)
 * @author Sayan Ekambarapu (sayan96)
 * @version December 1, 2014
 */
public class Checksheet
{
    private ArrayList<Requirement> list;

    // ----------------------------------------------------------
    /**
     * Create a new Checksheet object.
     *
     * @param rList ArrayList of Requirements
     */
    public Checksheet(ArrayList<Requirement> rList)
    {
        list = rList;
    }

    /**
     * Method to find the size of the checksheet
     *
     * @return Size of the Checksheet
     */
    public int size()
    {
        return list.size();
    }
}
