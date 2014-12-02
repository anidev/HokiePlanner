package org.gorilla.hokieplanner.guerilla.test;

import org.gorilla.hokieplanner.guerilla.Requirement;
import android.test.AndroidTestCase;

// -------------------------------------------------------------------------
/**
 * Tests the methods from the Requirement class
 *
 * @author Anirudh Bagde (anibagde)
 * @author Weyland Chiang (chiangw)
 * @author Sayan Ekambarapu (sayan96)
 * @version Dec 1, 2014
 */
public class RequirementTest
    extends AndroidTestCase
{

    private Requirement req;


    // ----------------------------------------------------------
    /**
     * initializes the object
     */
    public void setUp()
    {
        req = new Requirement("chemistry", 2);
    }


    // ----------------------------------------------------------
    /**
     * Tests the getTotal method
     */
    public void testGetTotal()
    {
        assertEquals(2, req.getTotal());
    }


    // ----------------------------------------------------------
    /**
     * Tests the getName method
     */
    public void testGetName()
    {
        assertEquals("chemistry", req.getName());
    }


    // ----------------------------------------------------------
    /**
     * Tests the setTotal method
     */
    public void testSetTotal()
    {
        req.setTotal(3);
        assertEquals(3, req.getTotal());
    }


    // ----------------------------------------------------------
    /**
     * Tests the setName method
     */
    public void testSetName()
    {
        req.setName("Natural science");

        assertEquals("Natural science", req.getName());
    }
}
