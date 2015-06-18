package at.sw2015.ontvapp.test;

import junit.framework.TestCase;

import java.util.Calendar;

import at.sw2015.ontvapp.Helper;


public class HelperTest extends TestCase{

    public void setUp() throws Exception {
        super.setUp();
    }


    public void testMakeQueryString(){
        String result  = Helper.makeQueryString("orf1","2015-06-17");
        String expected = "http://xmltv.tvtab.la/json/orf1.orf.at_2015-06-17.js.gz";
        assertEquals(expected, result);
    }

    public void testGetRealTime(){
        String result  = Helper.getRealTime(1434058500);
        String expected = "23:35";
        assertEquals(expected, result);
    }

    public void testGetDate(){
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(1434315773996L);
        String result = Helper.getDate(c);
        String expected = "2015-06-14";
        assertEquals(expected, result);
    }

    public void tearDown() throws Exception {
        //tearDown();
    }

}
