package org.gorilla.hokieplanner.guerilla;

import java.util.ArrayList;

public class Checksheet
{
    private ArrayList<Tree> list;

    public Checksheet(ArrayList<Tree> rList)
    {
        list = rList;
    }

    public int size()
    {
        return list.size();
    }

    public ArrayList<Tree> getRequirementList() {
        return list;
    }
}
