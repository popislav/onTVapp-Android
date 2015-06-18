package at.sw2015.ontvapp;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class Helper {

    public static String getDate(Calendar c){
        System.out.println("c: " + c);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(c.getTime());
    }

    public static String makeQueryString(String channel,  String date){
        String makeUrl = null;
        switch(channel){
            case ("orf1"):
                makeUrl = "http://xmltv.tvtab.la/json/orf1.orf.at_" + date + ".js.gz";
                break;
            case ("orf2"):
                makeUrl = "http://xmltv.tvtab.la/json/orf2.orf.at_" + date + ".js.gz";
                break;
            case ("orf3"):
                makeUrl = "http://xmltv.tvtab.la/json/orf3.orf.at_" + date + ".js.gz";
                break;
            case ("puls4"):
                makeUrl = "http://xmltv.tvtab.la/json/puls4.at_" + date + ".js.gz";
                break;
            case ("sportplus"):
                makeUrl = "http://xmltv.tvtab.la/json/sportplus.orf.at_" + date + ".js.gz";
                break;
        }
        return makeUrl;
    }

    public static String getRealTime(int unixTime){
        Date now = new Date((unixTime)*1000L);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        return sdf.format(now);
    }
}
