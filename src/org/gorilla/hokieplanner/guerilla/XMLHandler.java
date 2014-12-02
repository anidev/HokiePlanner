package org.gorilla.hokieplanner.guerilla;

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
        this.context = context;
    }

    // ----------------------------------------------------------
    /**
     * Parses an XML file using a DOM parser and returns a checksheet object to
     * represent the XML checksheet file
     *
     * @param str
     *            a string representing the name of the file
     * @return a Checksheet object to represent the XML checksheet file
     */
    public Checksheet parse(String str) {
        ArrayList<RequiredItem> list = new ArrayList<RequiredItem>();
        ArrayList<Requirement> requiredList =
            new ArrayList<Requirement>();
        try {
            AssetManager assetManager = context.getAssets();
            InputStream inputStream = assetManager.open(str);

            DocumentBuilderFactory factory =
                DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(inputStream);

            Element root = document.getDocumentElement();
            NodeList nList = root.getElementsByTagName("requirement");

            for (int i = 0; i < nList.getLength(); i++) {
                Tree tree = new Tree();
                org.w3c.dom.Node item = nList.item(i);
                Element element = (Element)item;
                NodeList courseList =
                    element.getElementsByTagName("course");
                for (int j = 0; j < courseList.getLength(); j++) {
                    org.w3c.dom.Node courseItem = courseList.item(j);
                    Element courseE = (Element)courseItem;
                    String dep = courseE.getAttribute("department");
                    String num = courseE.getAttribute("number");
                    if (num != "" || num != null) {
                        RequiredCourse course =
                            new RequiredCourse(dep, num, num);
                    }
                    else {
                        String from = courseE.getAttribute("from");
                        String to = courseE.getAttribute("to");
                        RequiredCourse course =
                            new RequiredCourse(dep, from, to);
                    }
                }

            }

        }
        catch (SAXException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (ParserConfigurationException e) {
            e.printStackTrace();
        }

        // Generate fake data here
        ArrayList<Tree> treelist = new ArrayList<Tree>();
        Tree tree = new Tree();
        Requirement req = new Requirement("CLE", null, "2");
        tree.addNode(req);
        RequiredCourse course =
            new RequiredCourse("CS", "2114", "2114");
        tree.addNode(course, req);
        CourseGroup group = new CourseGroup(1);
        RequiredCourse course2 =
            new RequiredCourse("CS", "1234", "1234");
        tree.addNode(group, req);
        tree.addNode(course2, group);
        RequiredCourse course3 =
            new RequiredCourse("MATH", "1234", "1234");
        tree.addNode(course3, group);
        treelist.add(tree);

        Tree treea = new Tree();
        Requirement reqa = new Requirement("Stuff", null, "2");
        treea.addNode(reqa);
        RequiredCourse coursea =
            new RequiredCourse("CS", "2114", "2114");
        treea.addNode(coursea, reqa);
        CourseGroup groupa = new CourseGroup(1);
        RequiredCourse course2a =
            new RequiredCourse("CS", "1234", "1234");
        treea.addNode(groupa, reqa);
        treea.addNode(course2a, groupa);
        RequiredCourse course3a =
            new RequiredCourse("MATH", "1234", "1234");
        treea.addNode(course3a, groupa);
        treelist.add(treea);

        Checksheet sheet = new Checksheet(treelist);

        return sheet;

    }
}
