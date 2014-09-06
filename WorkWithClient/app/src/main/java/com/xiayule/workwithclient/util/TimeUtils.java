package com.xiayule.workwithclient.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by tan on 14-8-10.
 */
public class TimeUtils {
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public static String format(Date date) {
        return sdf.format(date);
    }

    public static int compareTime(Date date1, Date date2) {
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();

        c1.setTime(date1);
        c2.setTime(date2);

        return c1.compareTo(c2);
    }
}
