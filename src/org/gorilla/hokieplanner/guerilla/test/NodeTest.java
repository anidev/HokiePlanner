package org.gorilla.hokieplanner.guerilla.test;

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
        assertEquals(2, node1.addChild(2).getData().intValue());
        assertEquals(3, node1.addChild(3).getData().intValue());
        assertEquals("2 3", node1.getChildren().toString());

        // Test Case 2
        assertEquals(6, node1.addChild(6).getData().intValue());
        assertEquals(7, node1.addChild(7).getData().intValue());
        assertEquals(8, node1.getChildren().get(0).addChild(8).
            getData().intValue());
        assertEquals(9, node1.getChildren().get(0).addChild(9).
            getData().intValue());

        // Test Case 3
        assertEquals(11, node3.addChild(11).getData().intValue());
        assertEquals(12, node3.addChild(11).getData().intValue());
        assertEquals(13, node3.addChild(11).getData().intValue());
        assertEquals(14, node3.getChildren().get(2).addChild(11).
            getData().intValue());
        assertEquals(15, node3.getChildren().get(2).addChild(11).
            getData().intValue());
    }

}
