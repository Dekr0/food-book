package com.example.assignmentone;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class Util {

    final static private SimpleDateFormat formatter = new SimpleDateFormat(
            "yyyy-MM-dd", Locale.CANADA
    );

    public static String formatDate(Date date) {
        return formatter.format(date);
    }

    public static Calendar strDateToCalendar(String strDate) {
        final Calendar c = Calendar.getInstance();

        try {
            c.setTime(formatter.parse(strDate));
        } catch (ParseException | NullPointerException e) {
            e.printStackTrace();
        }

        return c;
    }
}
