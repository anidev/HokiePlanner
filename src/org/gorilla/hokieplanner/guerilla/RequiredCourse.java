package org.gorilla.hokieplanner.guerilla;

// -------------------------------------------------------------------------
/**
 * A class to represent the course tag in the xml sheet. It has a string to
 * represent a department and ints to represent the range of courses. It extends
 * the RequiredItem class so that it can be added to the tree in the parser.
 *
 * @author Anirudh Bagde (anibagde)
 * @author Weyland Chiang (chiangw)
 * @author Sayan Ekambarapu (sayan96)
 * @version Dec 1, 2014
 */
public class RequiredCourse
    extends RequiredItem
{
    private String department;
    private int    from;
    private int    to;
    private String name;


    // ----------------------------------------------------------
    /**
     * returns the department name of the course
     * @return the department name of the course
     */
    public String getDepartment()
    {
        return department;
    }


    // ----------------------------------------------------------
    /**
     * sets the department of the course
     * @param department changes the department to the passed parameter
     */
    public void setDepartment(String department)
    {
        this.department = department;
    }


    // ----------------------------------------------------------
    /**
     * gets the beginning range of the course
     * @return the beginning range of the course
     */
    public int getFrom()
    {
        return from;
    }


    // ----------------------------------------------------------
    /**
     * sets the beginning range of the course
     * @param from sets the beginning to that number
     */
    public void setFrom(int from)
    {
        this.from = from;
    }


    // ----------------------------------------------------------
    /**
     * gets the end range of the course
     * @return the end range of the course
     */
    public int getTo()
    {
        return to;
    }


    // ----------------------------------------------------------
    /**
     * sets the end range
     * @param to the number it will be set to
     */
    public void setTo(int to)
    {
        this.to = to;
    }


    // ----------------------------------------------------------
    /**
     * Create a new RequiredCourse object.
     * @param dep the department of the course
     * @param begin the beginning range of the course
     * @param end the end range of the course
     *
     * begin and end are the same for a specific course
     */
    public RequiredCourse(String dep, int begin, int end)
    {
        department = dep;
        from = begin;
        to = end;
    }


    // ----------------------------------------------------------
    /**
     * Create a new RequiredCourse object. A second constructor for courses that
     * simply have names.
     *
     * @param name the name of the course
     */
    public RequiredCourse(String name)
    {
        this.setName(name);
    }


    // ----------------------------------------------------------
    /**
     * gets the name of the course
     * @return the name of the course
     */
    public String getName()
    {
        return name;
    }


    // ----------------------------------------------------------
    /**
     * sets the name of the course
     * @param name changes the name to the specifcied string
     */
    public void setName(String name)
    {
        this.name = name;
    }
}
