package com.chatapp.utils;

import org.joda.time.LocalDate;
import org.joda.time.Years;

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

    public static int getAge(long timestamp) {
        LocalDate birthday = new LocalDate(timestamp);
        LocalDate now = new LocalDate();
        Years age = Years.yearsBetween(birthday, now);
        return age.getYears();
    }
}
