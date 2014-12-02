package org.gorilla.hokieplanner.guerilla;

import java.util.ArrayList;

public class Requirement
{
    private String name;
    private Tree tree;
    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Tree getTree()
    {
        return tree;
    }

    public void setTree(Tree tree)
    {
        this.tree = tree;
    }

    public String getTotal()
    {
        return total;
    }

    public void setTotal(String total)
    {
        this.total = total;
    }

    private String total;

    public Requirement(String str, Tree tree2, String num)
    {
        name = str;
        tree = tree2;
        total = num;
    }



}
