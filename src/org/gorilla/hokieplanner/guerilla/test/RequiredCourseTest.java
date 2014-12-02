package org.gorilla.hokieplanner.guerilla.test;

import org.gorilla.hokieplanner.guerilla.RequiredCourse;
import android.test.AndroidTestCase;

// -------------------------------------------------------------------------
/**
 * A class to test the methods of the RequiredCourse class
 *
 * @author Anirudh Bagde (anibagde)
 * @author Weyland Chiang (chiangw)
 * @author Sayan Ekambarapu (sayan96)
 * @version Dec 1, 2014
 */
public class RequiredCourseTest
    extends AndroidTestCase
{
    private RequiredCourse course;
    private RequiredCourse course2;


    // ----------------------------------------------------------
    /**
     * initializes the object
     */
    public void setUp()
    {
        course = new RequiredCourse("phys", 2305, 2305);
        course2 = new RequiredCourse("Biology");
    }


    // ----------------------------------------------------------
    /**
     * Tests the getDepartment method
     */
    public void testGetDepartment()
    {
        assertEquals("phys", course.getDepartment());
    }


    // ----------------------------------------------------------
    /**
     * Tests the getFrom method
     */
    public void testGetFrom()
    {
        assertEquals(2305, course.getFrom());
    }


    // ----------------------------------------------------------
    /**
     * Tests the getTo method
     */
    public void testGetTo()
    {
        assertEquals(2305, course.getTo());
    }


    // ----------------------------------------------------------
    /**
     * Tests the getName method
     */
    public void testGetName()
    {
        assertEquals("Biology", course2.getName());
    }


    // ----------------------------------------------------------
    /**
     * Tests the setName method()
     */
    public void testSetName()
    {
        course2.setName("Chemistry");
        assertEquals("Chemistry", course2.getName());
    }


    // ----------------------------------------------------------
    /**
     * Tests the setFrom method
     */
    public void testSetFrom()
    {
        course.setFrom(1111);
        assertEquals(1111, course.getFrom());
    }


    // ----------------------------------------------------------
    /**
     * Tests the setTo method
     */
    public void testSetTo()
    {
        course.setTo(1111);
        assertEquals(1111, course.getTo());
    }


    // ----------------------------------------------------------
    /**
     * Tests the setDepartment method
     */
    public void testSetDepartment()
    {
        course.setDepartment("chem");
        assertEquals("chem", course.getDepartment());
    }
}
