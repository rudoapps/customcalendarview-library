package es.rudo.customcalendarlibrary;

import android.content.Context;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Calendar_StartDay {

    public static List<String> getSelectedDay(Context context, int selectedDay) {

        List<String> week = new ArrayList<>();

        switch (selectedDay) {
            case Calendar.MONDAY:
                week.addAll(weekMonday(context));
                break;
            case Calendar.TUESDAY:
                week.addAll(weekTuesday(context));
                break;
            case Calendar.WEDNESDAY:
                week.addAll(weekWednesday(context));
                break;
            case Calendar.THURSDAY:
                week.addAll(weekThursday(context));
                break;
            case Calendar.FRIDAY:
                week.addAll(weekFriday(context));
                break;
            case Calendar.SATURDAY:
                week.addAll(weekSaturday(context));
                break;
            case Calendar.SUNDAY:
                week.addAll(weekSunday(context));
                break;
        }

        return week;
    }

    private static List<String> weekMonday(Context context) {
        List<String> strings = new ArrayList<>();
        strings.add(context.getString(R.string.mon));
        strings.add(context.getString(R.string.tue));
        strings.add(context.getString(R.string.wed));
        strings.add(context.getString(R.string.thu));
        strings.add(context.getString(R.string.fri));
        strings.add(context.getString(R.string.sat));
        strings.add(context.getString(R.string.sun));
        return strings;
    }

    private static List<String> weekTuesday(Context context) {
        List<String> strings = new ArrayList<>();
        strings.add(context.getString(R.string.tue));
        strings.add(context.getString(R.string.wed));
        strings.add(context.getString(R.string.thu));
        strings.add(context.getString(R.string.fri));
        strings.add(context.getString(R.string.sat));
        strings.add(context.getString(R.string.sun));
        strings.add(context.getString(R.string.mon));
        return strings;
    }

    private static List<String> weekWednesday(Context context) {
        List<String> strings = new ArrayList<>();
        strings.add(context.getString(R.string.wed));
        strings.add(context.getString(R.string.thu));
        strings.add(context.getString(R.string.fri));
        strings.add(context.getString(R.string.sat));
        strings.add(context.getString(R.string.sun));
        strings.add(context.getString(R.string.mon));
        strings.add(context.getString(R.string.tue));
        return strings;
    }

    private static List<String> weekThursday(Context context) {
        List<String> strings = new ArrayList<>();
        strings.add(context.getString(R.string.thu));
        strings.add(context.getString(R.string.fri));
        strings.add(context.getString(R.string.sat));
        strings.add(context.getString(R.string.sun));
        strings.add(context.getString(R.string.mon));
        strings.add(context.getString(R.string.tue));
        strings.add(context.getString(R.string.wed));
        return strings;
    }

    private static List<String> weekFriday(Context context) {
        List<String> strings = new ArrayList<>();
        strings.add(context.getString(R.string.fri));
        strings.add(context.getString(R.string.sat));
        strings.add(context.getString(R.string.sun));
        strings.add(context.getString(R.string.mon));
        strings.add(context.getString(R.string.tue));
        strings.add(context.getString(R.string.wed));
        strings.add(context.getString(R.string.thu));
        return strings;
    }

    private static List<String> weekSaturday(Context context) {
        List<String> strings = new ArrayList<>();
        strings.add(context.getString(R.string.sat));
        strings.add(context.getString(R.string.sun));
        strings.add(context.getString(R.string.mon));
        strings.add(context.getString(R.string.tue));
        strings.add(context.getString(R.string.wed));
        strings.add(context.getString(R.string.thu));
        strings.add(context.getString(R.string.fri));
        return strings;
    }

    private static List<String> weekSunday(Context context) {
        List<String> strings = new ArrayList<>();
        strings.add(context.getString(R.string.sun));
        strings.add(context.getString(R.string.mon));
        strings.add(context.getString(R.string.tue));
        strings.add(context.getString(R.string.wed));
        strings.add(context.getString(R.string.thu));
        strings.add(context.getString(R.string.fri));
        strings.add(context.getString(R.string.sat));

        return strings;
    }


    public static int getAmountDay(int selectedDay) {

        int selectedDate = -2;
        switch (selectedDay) {
            case Calendar.SUNDAY:
                selectedDate = -1;
                break;
            case Calendar.MONDAY:
                selectedDate = -2;
                break;
            case Calendar.TUESDAY:
                selectedDate = -3;
                break;
            case Calendar.WEDNESDAY:
                selectedDate = -4;
                break;
            case Calendar.THURSDAY:
                selectedDate = -5;
                break;
            case Calendar.FRIDAY:
                selectedDate = -6;
                break;
            case Calendar.SATURDAY:
                selectedDate = -7;
                break;
        }
        return selectedDate;
    }


}
