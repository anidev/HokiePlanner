package org.gorilla.hokieplanner.guerilla.test;

import java.util.ArrayList;
import org.gorilla.hokieplanner.guerilla.Node;
import android.test.AndroidTestCase;

// -------------------------------------------------------------------------
/**
 * Test cases for the Node Class
 *
 * @author Anirudh Bagde (anibagde)
 * @author Weyland Chiang (chiangw)
 * @author Sayan Ekambarapu (sayan96)
 * @version December 1, 2014
 */
public class NodeTest extends AndroidTestCase
{
    // ----------------------------------------------------------
    /**
     * Initialize a new Node
     */
    private Node<Integer> node1;

    // ----------------------------------------------------------
    /**
     * Initialize a new Node
     */
    private Node<Integer> node2;

    // ----------------------------------------------------------
    /**
     * Initialize a new Node
     */
    private Node<Integer> node3;

    // ----------------------------------------------------------
    /**
     * Instantiates the new nodes for each test case
     */
    public void setUp()
    {
        node1 = new Node<Integer>(1);
        node2 = new Node<Integer>(5);
        node3 = new Node<Integer>(10);
    }

    // ----------------------------------------------------------
    /**
     * Tests the getData method on the nodes
     */
    public void testGetData()
    {
        assertEquals(1, node1.getData().intValue());
        assertEquals(5, node2.getData().intValue());
        assertEquals(10, node3.getData().intValue());
    }

    // ----------------------------------------------------------
    /**
     * Tests the addChild  and getChildren
     * methods to see if children are added properly
     *   1          5               10
     * 2   3      6   7         11  12   13
     *          8  9                   14  15
     */
    public void testChildren()
    {
        // Test Case 1
        ArrayList<Node<Integer>> children;
        assertEquals(2, node1.addChild(2).getData().intValue());
        assertEquals(3, node1.addChild(3).getData().intValue());
        children = node1.getChildren();
        assertEquals(2, children.get(0).getData().intValue());
        assertEquals(3, children.get(1).getData().intValue());
    }

    /**
     * Test Case 2
     */
    public void testChildren2()
    {
        ArrayList<Node<Integer>> children;
        assertEquals(6, node2.addChild(6).getData().intValue());
        assertEquals(7, node2.addChild(7).getData().intValue());
        assertEquals(8, node2.getChildren().get(0).addChild(8).
            getData().intValue());
        assertEquals(9, node2.getChildren().get(0).addChild(9).
            getData().intValue());
        children = node2.getChildren();
        assertEquals(2, children.size());
        assertEquals(6, children.get(0).getData().intValue());
        assertEquals(7, children.get(1).getData().intValue());
        children = children.get(0).getChildren();
        assertEquals(8, children.get(0).getData().intValue());
        assertEquals(9, children.get(1).getData().intValue());
    }

    /**
     * Test Case 3
     */
    public void testChildren3()
    {
        // Test Case 3
        ArrayList<Node<Integer>> children;
        assertEquals(11, node3.addChild(11).getData().intValue());
        assertEquals(12, node3.addChild(12).getData().intValue());
        assertEquals(13, node3.addChild(13).getData().intValue());
        assertEquals(14, node3.getChildren().get(2).addChild(14).
            getData().intValue());
        assertEquals(15, node3.getChildren().get(2).addChild(15).
            getData().intValue());
        children = node3.getChildren();
        assertEquals(11, children.get(0).getData().intValue());
        assertEquals(12, children.get(1).getData().intValue());
        assertEquals(13, children.get(2).getData().intValue());
        children = children.get(2).getChildren();
        assertEquals(14, children.get(0).getData().intValue());
        assertEquals(15, children.get(1).getData().intValue());
    }
}
