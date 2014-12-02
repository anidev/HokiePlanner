package org.gorilla.hokieplanner.guerilla;

import java.util.LinkedList;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
/**
 *  Tree structure that can import an XML file and create a tree from it
 *
 * @author Anirudh Bagde (anibagde)
 * @author Weyland Chiang (chiangw)
 * @author Sayan Ekambarapu (sayan96)
 * @version December 1, 2014
 */
public class Tree<E>
{
    // ----------------------------------------------------------
    /**
     * Creates a hashmap nodes of Nodes
     */
    private HashMap<E, Node<E>> nodes;

    // ----------------------------------------------------------
    /**
     * Holds the first item put into the hashmap
     */
    private E first;

    // ----------------------------------------------------------
    /**
     * Creates a tree that contains a hashmap of nodes
     */
    public Tree()
    {
        this.nodes = new HashMap<E, Node<E>>();
        this.first = null;
    }

    // ----------------------------------------------------------
    /**
     * Getter method for the Nodes HashMap
     *
     * @return HashMap of nodes
     */
    public HashMap<E, Node<E>> getNodes()
    {
        return nodes;
    }

    // ----------------------------------------------------------
    /**
     * Adds a node to the tree that does not have a parent
     *
     * @param data Data value of the node
     *
     * @return Node added to the tree
     */
    public Node<E> addNode(E data)
    {
        if (first != null)
        {
            first = data;
        }
        return this.addNode(data, null);
    }
    // ----------------------------------------------------------
    /**
     * Adds a node to the tree with a given parent
     *
     * @param data Data of the node to add to the tree
     * @param parent Parent of the node you are adding
     *
     * @return Node just added to the tree
     */
    public Node<E> addNode(E data, E parent)
    {
        Node<E> newNode = new Node<E>(data);
        nodes.put(data, newNode);

        if (parent != null)
        {
            nodes.get(parent).addChild(data);
        }

        return newNode;
    }

    public E getFirst()
    {
        return first;
    }


    // ----------------------------------------------------------
    /**
     * Public method to return an iterator
     * This is a Breadth-First Tree Iterator
     *
     * @return Node Iterator for the tree
     */
    public Iterator<Node<E>> iterator()
    {
        //return TreeIterator(nodes, first);
        return null;
    }

    // -------------------------------------------------------------------------
    /**
     *  Tree Iterator to make it easy to iterate through things
     *  This is a breadth-first iterator
     *
     * @author Anirudh Bagde (anibagde)
     * @author Weyland Chiang (chiangw)
     * @author Sayan Ekambarapu (sayan96)
     * @version December 1, 2014
     */
    public class TreeIterator implements Iterator<Node<E>>
    {
        /**
         * LinkedList of Nodes
         */
        private LinkedList<Node<E>> list;

        /**
         * Hashmap to iterate through stuffs
         */
        private HashMap<E, ArrayList<Node<E>>> layers;

        /**
         * Creates the tree iterator that has a Linked List
         * This list is recursively generated.
         *
         * @param tree Tree to make an iterator for
         * @param data First data entry to use
         */
        /*
        private Iterator<Node<E>> TreeIterator(
            HashMap<E, Node<E>> tree, E data)
        {
            list = new LinkedList<Node<E>>();

            if (tree.containsKey(data))
            {
                this.genList(tree, data);
            }
        }
        */

        // ----------------------------------------------------------
        /**
         * This will recursively go and generate the list
         */
        private void genList(HashMap<E, Node<E>> tree, E data)
        {
            list.add(tree.get(data));
            ArrayList<Node> children = tree.get(data).getChildren();

        }

        // ----------------------------------------------------------
        /**
         * Returns whether or not there is another item
         *
         * @return Boolean representing the presence of another iterable item
         */
        public boolean hasNext()
        {
            return !(list.isEmpty());
        }

        // ----------------------------------------------------------
        /**
         * Returns the next node in the list
         *
         * @return Node Next node in the iterator
         */
        @Override
        public Node<E> next()
        {
            return list.poll();
        }

        // ----------------------------------------------------------
        /**
         * Left out functionality
         *
         */
        public void remove()
        {
            // Intentionally left blank
        }
    }
}
