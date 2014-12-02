package org.gorilla.hokieplanner.guerilla.test;
import org.gorilla.hokieplanner.guerilla.Checksheet;
import org.gorilla.hokieplanner.guerilla.Requirement;
import org.gorilla.hokieplanner.guerilla.RequiredItem;
import org.gorilla.hokieplanner.guerilla.XMLHandler;
import java.io.File;
import java.util.ArrayList;
import android.test.AndroidTestCase;
import android.content.Context;

// -------------------------------------------------------------------------
/**
 *  A class to test the methods from the XMLHandler class.
 *
 * @author Anirudh Bagde (anibagde)
 * @author Weyland Chiang (chiangw)
 * @author Sayan Ekambarapu (sayan96)
 * @version Dec 1, 2014
 */
public class XMLHandlerTest extends AndroidTestCase
{
    XMLHandler handle;

    protected void setUp()
    {
        handle = new XMLHandler(getContext());
    }

    public void testParse()
    {

       // Checksheet sheet = handle.parse("Checksheet-cs-2017.xml");
        assertEquals(4, handle.parse("Checksheet-cs-2017.xml"));
    }
}
