package org.gorilla.hokieplanner.guerilla;

import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;
import org.w3c.dom.Document;
import android.content.Context;
import java.util.ArrayList;
import java.io.IOException;
import org.xml.sax.SAXException;
import javax.xml.parsers.*;
import java.io.*;
import android.content.res.AssetManager;

// -------------------------------------------------------------------------
/**
 * Write a one-sentence summary of your class here. Follow it with additional
 * details about its purpose, what abstraction it represents, and how to use it.
 *
 * @author Anirudh Bagde (anibagde)
 * @author Weyland Chiang (chiangw)
 * @author Sayan Ekambarapu (sayan96)
 * @version Dec 1, 2014
 */
public class XMLHandler {
    private Context context;

    // ----------------------------------------------------------
    /**
     * Create a new XMLHandler object.
     *
     * @param context
     */
    public XMLHandler(Context context) {
        this.context = context.getApplicationContext();
    }

    // ----------------------------------------------------------
    /**
     * Parses an XML file using a DOM parser and returns a Tree to represent the
     * checksheet.
     *
     * @param str
     *            a string representing the name of the file
     * @return a Checksheet object to represent the XML checksheet file
     */
    public Checksheet parse(String str) {
        ArrayList<Tree<RequiredItem>> treeList =
            new ArrayList<Tree<RequiredItem>>();

        // Use the Android Asset Manager to access the packaged XML file
        AssetManager assetManager = context.getAssets();
        InputStream inputStream = null;
        // Use Java built-in XML parser and W3C document object model
        DocumentBuilderFactory factory =
            DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        Document document = null;
        try {
            inputStream = assetManager.open(str);
            builder = factory.newDocumentBuilder();
            document = builder.parse(inputStream);

            Element root = document.getDocumentElement();
            NodeList nList = root.getElementsByTagName("requirement");
            // Process each requirement
            for (int i = 0; i < nList.getLength(); i++) {
                Tree<RequiredItem> tree = new Tree<RequiredItem>();
                org.w3c.dom.Node item = nList.item(i);
                Element element = (Element)item;
                String name = element.getAttribute("name");
                int total;
                if (element.hasAttribute("total")) {
                    total =
                        Integer.parseInt(element
                            .getAttribute("total"));
                }
                else {
                    total = 0;
                }
                Requirement requirement =
                    new Requirement(name, total);
                tree.addNode(requirement);

                NodeList cleList =
                    element.getElementsByTagName("cle");
                addCle(cleList, tree, requirement);

                NodeList courseList =
                    element.getElementsByTagName("course");
                addCourses(courseList, tree, requirement);
                NodeList groupList =
                    element.getElementsByTagName("group");
                addGroup(groupList, tree, requirement);
                treeList.add(tree);
            }

        }
        catch (ParserConfigurationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (SAXException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        Checksheet sheet = new Checksheet(treeList);

        // Generate fake data here, this was used for testing earlier before the
        // parser was completely finished
/*
 * ArrayList<Tree> treelist = new ArrayList<Tree>(); Tree tree = new Tree();
 * Requirement req = new Requirement("CLE", null, "2"); tree.addNode(req);
 * RequiredCourse course = new RequiredCourse("CS", "2114", "2114");
 * tree.addNode(course, req); CourseGroup group = new CourseGroup(1);
 * RequiredCourse course2 = new RequiredCourse("CS", "1234", "1234");
 * tree.addNode(group, req); tree.addNode(course2, group); RequiredCourse
 * course3 = new RequiredCourse("MATH", "1234", "1234"); tree.addNode(course3,
 * group); treelist.add(tree); Tree treea = new Tree(); Requirement reqa = new
 * Requirement("Stuff", null, "2"); treea.addNode(reqa); RequiredCourse coursea
 * = new RequiredCourse("CS", "2114", "2114"); treea.addNode(coursea, reqa);
 * CourseGroup groupa = new CourseGroup(1); RequiredCourse course2a = new
 * RequiredCourse("CS", "1234", "1234"); treea.addNode(groupa, reqa);
 * treea.addNode(course2a, groupa); RequiredCourse course3a = new
 * RequiredCourse("MATH", "1234", "1234"); treea.addNode(course3a, groupa);
 * treelist.add(treea); Checksheet sheet = new Checksheet(treelist);
 */

        return sheet;
    }

    // ----------------------------------------------------------
    /**
     * A helper method to add groups from a list into the tree
     *
     * @param list
     *            a list of groups
     * @param tree
     *            a tree to add to
     * @param parent
     *            the parent node to add other nodes
     */
    public void addGroup(
        NodeList list,
        Tree<RequiredItem> tree,
        RequiredItem parent) {
        for (int j = 0; j < list.getLength(); j++) {
            org.w3c.dom.Node groupItem = list.item(j);
            Element element = (Element)groupItem;
            Integer total =
                Integer.parseInt(element.getAttribute("total"));
            CourseGroup group = new CourseGroup(total);
            tree.addNode(group, parent);
            NodeList courseList =
                element.getElementsByTagName("course");
            addCourses(courseList, tree, group);
            NodeList groupList =
                element.getElementsByTagName("group");
            if (groupList.getLength() > 0) {
                addGroup(groupList, tree, group);
            }
        }
    }

    // ----------------------------------------------------------
    /**
     * A helper method to add cle's from a list into the tree
     *
     * @param list
     *            a list of cle's
     * @param tree
     *            a tree to add to
     * @param parent
     *            the parent node to add other nodes
     */
    public void addCle(
        NodeList list,
        Tree<RequiredItem> tree,
        RequiredItem parent) {
        for (int j = 0; j < list.getLength(); j++) {
            org.w3c.dom.Node cleItem = list.item(j);
            Element element = (Element)cleItem;
            Integer total =
                Integer.parseInt(element.getAttribute("total"));
            Integer area =
                Integer.parseInt(element.getAttribute("area"));
            Cle cle = new Cle(area, total);
            tree.addNode(cle, parent);
        }
    }

    // ----------------------------------------------------------
    /**
     * A helper method to add courses from a list into the tree
     *
     * @param list
     *            a list of courses
     * @param tree
     *            a tree to add to
     * @param parent
     *            the parent node to add other nodes
     */
    public void addCourses(
        NodeList list,
        Tree<RequiredItem> tree,
        RequiredItem parent) {
        for (int i = 0; i < list.getLength(); i++) {
            org.w3c.dom.Node courseItem = list.item(i);
            Element courseE = (Element)courseItem;
            String dep = courseE.getAttribute("department");
            String name = courseE.getAttribute("name");
            String numStr = courseE.getAttribute("number");
            if (dep.equals("")) {
                // No department specified so it just has a name
                RequiredCourse course = new RequiredCourse(name);
                tree.addNode(course, parent);
            }
            else if (!numStr.equals("")) {
                int num = Integer.parseInt(numStr);
                RequiredCourse course =
                    new RequiredCourse(dep, num, num);
                tree.addNode(course, parent);
            }
            else {
                int from =
                    Integer.parseInt(courseE.getAttribute("from"));
                int to = Integer.parseInt(courseE.getAttribute("to"));
                RequiredCourse course =
                    new RequiredCourse(dep, from, to);
                tree.addNode(course, parent);
            }
        }
    }
}
