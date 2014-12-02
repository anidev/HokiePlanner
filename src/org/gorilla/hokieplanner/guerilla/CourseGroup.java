package org.gorilla.hokieplanner.guerilla;

public class CourseGroup extends RequiredItem
{
    private int total;

    public int getTotal()
    {
        return total;
    }

    public void setTotal(int total)
    {
        this.total = total;
    }

    public CourseGroup(int num)
    {
        total = num;
    }

}
