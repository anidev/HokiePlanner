package org.gorilla.hokieplanner.guerilla.test;

import java.util.ArrayList;
import org.gorilla.hokieplanner.guerilla.Tree;
import org.gorilla.hokieplanner.guerilla.RequiredItem;
import org.gorilla.hokieplanner.guerilla.Checksheet;
import android.test.AndroidTestCase;

// -------------------------------------------------------------------------
/**
 * Tests the methods from the Checksheet class
 *
 * @author Anirudh Bagde (anibagde)
 * @author Weyland Chiang (chiangw)
 * @author Sayan Ekambarapu (sayan96)
 * @version Dec 1, 2014
 */
public class ChecksheetTest
    extends AndroidTestCase
{
    private Checksheet sheet;


    // ----------------------------------------------------------
    /**
     * initializes the object
     */
    public void setUp()
    {
        ArrayList<Tree<RequiredItem>> list =
            new ArrayList<Tree<RequiredItem>>();
        sheet = new Checksheet(list);
    }


    // ----------------------------------------------------------
    /**
     * Tests the size method
     */
    public void testSize()
    {
        assertEquals(0, sheet.size());
    }


    // ----------------------------------------------------------
    /**
     * Tests the getList method
     */
    public void testGetList()
    {
        ArrayList<Tree<RequiredItem>> list = sheet.getList();
        assertEquals(0, list.size());
    }


    // ----------------------------------------------------------
    /**
     * Tests the setList method
     */
    public void testSetList()
    {
        ArrayList<Tree<RequiredItem>> list =
            new ArrayList<Tree<RequiredItem>>();
        list.add(new Tree<RequiredItem>());
        sheet.setList(list);

        ArrayList<Tree<RequiredItem>> list2 = sheet.getList();
        assertEquals(1, list2.size());

    }
}
