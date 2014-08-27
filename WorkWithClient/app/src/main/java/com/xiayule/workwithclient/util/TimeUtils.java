package com.xiayule.workwithclient.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by tan on 14-8-10.
 */
public class TimeUtils {
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public static String format(Date date) {
        return sdf.format(date);
    }
}
