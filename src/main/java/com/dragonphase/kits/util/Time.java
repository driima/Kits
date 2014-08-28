package com.dragonphase.kits.util;

import java.sql.Date;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

public class Time {

    private long milliseconds;

    public Time(String time) throws IllegalArgumentException {
        String[] numbers = Utils.clean(time.split("[^0-9]"));
        String[] types = Utils.clean(time.split("[0-9]"));

        milliseconds = 0;

        try {
            for (int i = 0; i < types.length; i++)
                milliseconds += getTime(Integer.parseInt(numbers[i]), TimeType.match(types[i]));
        } catch (Exception ex) {
            throw new IllegalArgumentException(ex);
        }
    }

    public long getMilliseconds() {
        return milliseconds;
    }

    public double getSeconds() {
        return (double) getMilliseconds() / 1000;
    }

    public double getMinutes() {
        return getSeconds() / 60;
    }

    public double getHours() {
        return getMinutes() / 60;
    }

    public double getDays() {
        return getHours() / 24;
    }

    public double getMonths() {
        return getDays() / 30;
    }

    public double getYears() {
        return getMonths() / 12;
    }
    
    public String toReadableFormat(boolean formatted, boolean showMillis) {
        return toReadableFormat(getMilliseconds(), formatted, showMillis);
    }
    
    public static String toReadableFormat(long ms, boolean formatted, boolean showMillis) {
        String result[] = new String[7];
        
        Date time = new Date(ms);
        
        String[] initDate = Utils.timeFormat.format(Utils.initialDate).split(Pattern.quote(":"));
        String[] timeDate = Utils.timeFormat.format(time).split(Pattern.quote(":"));
        
        int years = Integer.parseInt(timeDate[0]) - Integer.parseInt(initDate[0]);
        int months = Integer.parseInt(timeDate[1]) - Integer.parseInt(initDate[1]);
        int days = Integer.parseInt(timeDate[2]) - Integer.parseInt(initDate[2]);
        int hours = Integer.parseInt(timeDate[3]) - Integer.parseInt(initDate[3]);
        int minutes = Integer.parseInt(timeDate[4]) - Integer.parseInt(initDate[4]);
        int seconds = Integer.parseInt(timeDate[5]) - Integer.parseInt(initDate[5]);
        int milliseconds = Integer.parseInt(timeDate[6]) - Integer.parseInt(initDate[6]);
        
        if (Integer.parseInt(timeDate[3]) > 23) days--;
        
        if (years > 0)
            result[0] = years + (formatted ? "y" : " year") + (years > 1 ? "s" : "");
        if (months > 0)
            result[1] = months + (formatted ? "mo" : " month") + (months > 1 ? "s" : "");
        if (days > 0)
            result[2] = days + (formatted ? "d" : " day") + (days > 1 ? "s" : "");
        if (hours > 0)
            result[3] = hours + (formatted ? "h" : " hour") + (hours > 1 ? "s" : "");
        if (minutes > 0)
            result[4] = minutes + (formatted ? "m" : " minute") + (minutes > 1 ? "s" : "");
        if (seconds > 0)
            result[5] = seconds + (formatted ? "s" : " second") + (seconds > 1 ? "s" : "");
        if (milliseconds > 0 && showMillis)
            result[6] = milliseconds + (formatted ? "ms" : " millisecond") + (milliseconds > 1 ? "s" : "");
        
        return StringUtils.join(Utils.clean(result), formatted ? "" : ", ");
    }

    //Recursive time translation to seconds

    private long getTime(long number, TimeType type) {
        long time = number;
        switch (type) {
            case YEARS:
                time = getTime(time * 12, TimeType.MONTHS);
                break;
            case MONTHS:
                time = getTime(time * 30, TimeType.DAYS);
                break;
            case DAYS:
                time = getTime(time * 24, TimeType.HOURS);
                break;
            case HOURS:
                time = getTime(time * 60, TimeType.MINUTES);
                break;
            case MINUTES:
                time = getTime(time * 60, TimeType.SECONDS);
                break;
            case SECONDS:
                time = getTime(time * 1000, TimeType.MILLISECONDS);
            case MILLISECONDS:
            default:
                break;
        }
        return time;
    }
}