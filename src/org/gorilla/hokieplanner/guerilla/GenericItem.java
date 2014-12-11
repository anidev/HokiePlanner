package org.gorilla.hokieplanner.guerilla;

public class GenericItem extends RequiredItem
{
    private String detail;
    private int total;

    public GenericItem(String detail, int total)
    {
        this.detail = detail;
        this.total = total;
    }

    public String getDetail()
    {
        return detail;
    }

    public void setDetail(String detail)
    {
        this.detail = detail;
    }
}
