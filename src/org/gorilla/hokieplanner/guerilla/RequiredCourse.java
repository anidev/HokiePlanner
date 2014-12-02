package org.gorilla.hokieplanner.guerilla;

public class RequiredCourse
    extends RequiredItem
{
    private String department;
    private String from;
    private String to;


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


    public RequiredCourse(String dep, String begin, String end)
    {
        department = dep;
        from = begin;
        to = end;

    }
}
