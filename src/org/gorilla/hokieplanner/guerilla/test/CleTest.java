package org.gorilla.hokieplanner.guerilla.test;

import org.gorilla.hokieplanner.guerilla.Cle;
import android.test.AndroidTestCase;

// -------------------------------------------------------------------------
/**
 * Tests the methods from the cle class
 *
 * @author Anirudh Bagde (anibagde)
 * @author Weyland Chiang (chiangw)
 * @author Sayan Ekambarapu (sayan96)
 * @version Dec 1, 2014
 */
public class CleTest
    extends AndroidTestCase
{
    private Cle cle;


    // ----------------------------------------------------------
    /**
     * initializes the object
     */
    public void setUp()
    {
        cle = new Cle(1, 6);
    }


    // ----------------------------------------------------------
    /**
     * Tests the getArea method
     */
    public void testGetArea()
    {
        assertEquals(1, cle.getArea());
    }


    // ----------------------------------------------------------
    /**
     * Tests the getTotal method
     */
    public void testGetTotal()
    {
        assertEquals(6, cle.getTotal());
    }


    // ----------------------------------------------------------
    /**
     * Tests the setArea method
     */
    public void testSetArea()
    {
        cle.setArea(2);
        assertEquals(2, cle.getArea());
    }


    // ----------------------------------------------------------
    /**
     * Tests the setTotal method
     */
    public void testSetTotal()
    {
        cle.setTotal(9);
        assertEquals(9, cle.getTotal());
    }
}
