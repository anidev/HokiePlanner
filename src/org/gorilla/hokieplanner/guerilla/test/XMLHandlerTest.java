package org.gorilla.hokieplanner.guerilla.test;
import org.gorilla.hokieplanner.guerilla.Checksheet;
import org.gorilla.hokieplanner.guerilla.XMLHandler;
import android.test.AndroidTestCase;

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
    private XMLHandler handle;

    protected void setUp()
    {
        handle = new XMLHandler(getContext());
    }

    // ----------------------------------------------------------
    /**
     * A method to test the parse method from the XMLHandler class
     */
    public void testParse()
    {

       Checksheet sheet = handle.parse("Checksheet-CS-2017.xml");
        assertEquals(12, sheet.size());
    }
}
