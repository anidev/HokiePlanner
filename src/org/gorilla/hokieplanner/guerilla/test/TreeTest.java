package org.gorilla.hokieplanner.guerilla.test;

import java.util.HashMap;
import org.gorilla.hokieplanner.guerilla.Tree;
import org.gorilla.hokieplanner.guerilla.Node;
import android.test.AndroidTestCase;

// -------------------------------------------------------------------------
/**
 * Test cases for the Test Class
 *
 * @author Anirudh Bagde (anibagde)
 * @author Weyland Chiang (chiangw)
 * @author Sayan Ekambarapu (sayan96)
 * @version December 1, 2014
 */
public class TreeTest extends AndroidTestCase
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
     * Initialize a new Tree
     */
    private Tree<Node<Integer>> tree;

    // ----------------------------------------------------------
    /**
     * Instantiates the new nodes for each test case
     */
    public void setUp()
    {
        node1 = new Node<Integer>(1);
        node2 = new Node<Integer>(5);
        node3 = new Node<Integer>(10);
        tree = new Tree<Node<Integer>>();
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
        Node<Integer> node11 = new Node<Integer>(2);
        Node<Integer> node12 = new Node<Integer>(3);
        tree.addNode(node1);
        tree.addNode(node11, node1);
        tree.addNode(node12, node1);
        HashMap<Node<Integer>, Node<Node<Integer>>> hashmap = tree.getNodes();
        assertEquals(3, hashmap.size());
        assertEquals(true, hashmap.containsKey(node1));
        assertEquals(true, hashmap.containsKey(node11));
        assertEquals(true, hashmap.containsKey(node12));
    }
}
