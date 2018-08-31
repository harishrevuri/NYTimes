package com.harish.test.project.utils;

import android.content.Context;
import android.content.Intent;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Utils {
    private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private static final DateFormat dateFormatFull = new SimpleDateFormat("yyyy-MM-dd hh:mm a");
    private static final DateFormat dateFormatToday = new SimpleDateFormat("hh:mm a");

    private static Calendar todayCalender = null;

    public static String formatDateString(Date date) {
        if (date != null) {
            if (isDateToday(date)) {
                return dateFormatToday.format(date);
            } else {
                return dateFormat.format(date);
            }
        }
        return "";
    }

    public static String formatFullDateString(Date date) {
        if (date != null) {
            return dateFormatFull.format(date);
        }
        return "";
    }

    public static boolean isDateToday(Date date) {
        if (todayCalender == null) {
            todayCalender = Calendar.getInstance();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return todayCalender.get(Calendar.YEAR) == calendar.get(Calendar.YEAR) &&
                todayCalender.get(Calendar.MONTH) == calendar.get(Calendar.MONTH) &&
                todayCalender.get(Calendar.DAY_OF_MONTH) == calendar.get(Calendar.DAY_OF_MONTH);
    }

    public static boolean isValid(String text) {
        return text != null && text.trim().length() > 0;
    }

    public static void shareText(Context context, String text) {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, text);
        context.startActivity(Intent.createChooser(sharingIntent, "Share using"));
    }
}
