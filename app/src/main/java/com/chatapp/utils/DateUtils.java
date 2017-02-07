package com.chatapp.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by thanhnguyen on 2/7/17.
 */

public class DateUtils {

    private static final String COMMON_DATE_FORMAT = "d MMMM yyyy";

    public static String displayDate(Date date) {
        return new SimpleDateFormat(COMMON_DATE_FORMAT, Locale.getDefault()).format(date);
    }
}
