package org.gorilla.hokieplanner.guerilla.test;

import org.gorilla.hokieplanner.guerilla.CourseGroup;
import android.test.AndroidTestCase;

// -------------------------------------------------------------------------
/**
 * Tests the methods from the courseGroup class
 *
 * @author Anirudh Bagde (anibagde)
 * @author Weyland Chiang (chiangw)
 * @author Sayan Ekambarapu (sayan96)
 * @version Dec 1, 2014
 */
public class CourseGroupTest
    extends AndroidTestCase
{
    private CourseGroup group;


    // ----------------------------------------------------------
    /**
     * initializes the object
     */
    public void setUp()
    {
        group = new CourseGroup(2);
    }


    // ----------------------------------------------------------
    /**
     * Tests the getTotal method
     */
    public void testGetTotal()
    {
        assertEquals(2, group.getTotal());
    }


    // ----------------------------------------------------------
    /**
     * Tests the setTotal method
     */
    public void testSetTotal()
    {
        group.setTotal(3);

        assertEquals(3, group.getTotal());
    }
}
