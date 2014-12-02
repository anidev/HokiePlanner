package org.gorilla.hokieplanner.guerilla;

public class RequiredCourse
    extends RequiredItem
{
    private String department;
    private String from;
    private String to;
    private String name;

    public String getDepartment()
    {
        return department;
    }


    public void setDepartment(String department)
    {
        this.department = department;
    }


    public String getFrom()
    {
        return from;
    }


    public void setFrom(String from)
    {
        this.from = from;
    }


    public String getTo()
    {
        return to;
    }


    public void setTo(String to)
    {
        this.to = to;
    }


    /**
     * @return the name
     */
    public String getName() {
        return name;
    }


    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }


    public RequiredCourse(String dep, String begin, String end)
    {
        department = dep;
        from = begin;
        to = end;
    }
}
