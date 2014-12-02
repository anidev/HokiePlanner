package org.gorilla.hokieplanner.guerilla;

import java.util.ArrayList;

public class Checksheet
{
    private ArrayList<Requirement> list;

    public Checksheet(ArrayList<Requirement> rList)
    {
        list = rList;
    }

    public int size()
    {
        return list.size();
    }
}
