package org.gorilla.hokieplanner.guerilla;

import java.util.ArrayList;

/**
 * Tree structure that can import an XML file and create a tree from it
 *
 * @author Anirudh Bagde (anibagde)
 * @author Weyland Chiang (chiangw)
 * @author Sayan Ekambarapu (sayan96)
 * @version December 1, 2014
 * @param <E>
 *            The type of data this Node stores
 */
public class Node<E> {
    /**
     * Data to be contained in node
     */
    private E                  data;

    /**
     * ArrayList of children Nodes
     */
    private ArrayList<Node<E>> children;

    // ----------------------------------------------------------
    /**
     * Creates a node with the specified data
     *
     * @param data
     *            Data the node contains
     */
    public Node(E data) {
        this.data = data;
        this.children = new ArrayList<Node<E>>();
    }

    // ----------------------------------------------------------
    /**
     * Getter method for the node's data
     *
     * @return Data stored in the node
     */
    public E getData() {
        return data;
    }

    // ----------------------------------------------------------
    /**
     * Getter Method for the ArrayList of Children
     *
     * @return ArrayList of Nodes that are the children of the current node
     */
    public ArrayList<Node<E>> getChildren() {
        return children;
    }

    // ----------------------------------------------------------
    /**
     * Putter Method to add a child Node to the current node
     *
     * @param childData
     *            Data to be stored in the child node
     * @return Node stored in the ArrayList
     */
    public Node<E> addChild(E childData) {
        Node<E> newChild = new Node<E>(childData);
        children.add(newChild);
        return newChild;
    }
}
