package com.thinhlp.cocshopapp.commons;


import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by thinhlp on 7/4/17.
 */

public class Utils {

    public static String formatDate(Date date, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }
}
