package com.justadeveloper96.githubbrowser.helpers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by harshit on 06-03-2018.
 */

public class Utils {
    public static String formatToRelativeDateString(long time){
        Date dateTime=new Date(time);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateTime);
        Calendar today = Calendar.getInstance();
        Calendar yesterday = Calendar.getInstance();
        yesterday.add(Calendar.DATE, -1);
        Calendar week = Calendar.getInstance();
        week.add(Calendar.DATE, -6);
        DateFormat timeFormatter = new SimpleDateFormat("h:mm a");
        DateFormat weekFormatter = new SimpleDateFormat("EEEE h:mm a");
        DateFormat dateFormatter = new SimpleDateFormat("MMM dd h:mm a");

        if (calendar.get(Calendar.YEAR) == today.get(Calendar.YEAR) && calendar.get(Calendar.DAY_OF_YEAR) == today.get(Calendar.DAY_OF_YEAR)) {
            return "Today at " + timeFormatter.format(dateTime);
        } else if (calendar.get(Calendar.YEAR) == yesterday.get(Calendar.YEAR) && calendar.get(Calendar.DAY_OF_YEAR) == yesterday.get(Calendar.DAY_OF_YEAR)) {
            return "Yesterday at " + timeFormatter.format(dateTime);
        } else if(calendar.get(Calendar.YEAR) == week.get(Calendar.YEAR) && dateTime.compareTo(week.getTime())>=0){
            String res=weekFormatter.format(dateTime);
            return new StringBuilder(res).insert(res.indexOf(' ')," at").toString();
        }else {
            String res=dateFormatter.format(dateTime);
            return new StringBuilder(res).insert(7,"at ").toString();
        }
    }
}
