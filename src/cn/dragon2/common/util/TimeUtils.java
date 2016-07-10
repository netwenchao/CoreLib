package cn.dragon2.common.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import android.content.Context;

/**
 * TimeUtils
 * 
 * @author <a href="http://www.trinea.cn" target="_blank">Trinea</a> 2013-8-24
 */
public class TimeUtils {

    public static final SimpleDateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final SimpleDateFormat DEFAULT_DATE_FORMAT12 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");
    public static final SimpleDateFormat DATE_FORMAT_DATE    = new SimpleDateFormat("yyyy-MM-dd");
    public static final SimpleDateFormat DATE_FORMAT_MONTH_DAY    = new SimpleDateFormat("MM/dd");
    public static final SimpleDateFormat DATE_FORMAT_TIME    = new SimpleDateFormat("HH:mm");
    public static final SimpleDateFormat DATE_FORMAT_TIME12    = new SimpleDateFormat("hh:mm a");

    public static Date getDate(long timeInMillis){
    	return new Date(timeInMillis);
    }
    
    public static String getNoTime(Date dt){
    	return DATE_FORMAT_DATE.format(dt);
    }
    
    public static String getOnlyTime(Date dt){
    	return  DATE_FORMAT_TIME.format(dt);
    }
    
    public static String getWeek(Context ctx,long timeInMillis,String[] WeekDescs){    	
    	Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timeInMillis);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)w = 0;
    	return WeekDescs[w];
    }
    
    public static String getMonthDay(Context ctx,long timeInMillis){
    	return DATE_FORMAT_MONTH_DAY.format(new Date(timeInMillis));
    }
    
    /**
     * long time to string
     * 
     * @param timeInMillis
     * @param dateFormat
     * @return
     */
    public static String getTime(long timeInMillis, SimpleDateFormat dateFormat) {
        return dateFormat.format(new Date(timeInMillis));
    }

    /**
     * long time to string, format is {@link #DEFAULT_DATE_FORMAT}
     * 
     * @param timeInMillis
     * @return
     */
    public static String getTime(long timeInMillis) {
        return getTime(timeInMillis, DEFAULT_DATE_FORMAT);
    }

    /**
     * get current time in milliseconds
     * 
     * @return
     */
    public static long getCurrentTimeInLong() {
        return System.currentTimeMillis();
    }

    /**
     * get current time in milliseconds, format is {@link #DEFAULT_DATE_FORMAT}
     * 
     * @return
     */
    public static String getCurrentTimeInString() {
        return getTime(getCurrentTimeInLong());
    }

    /**
     * get current time in milliseconds
     * 
     * @return
     */
    public static String getCurrentTimeInString(SimpleDateFormat dateFormat) {
        return getTime(getCurrentTimeInLong(), dateFormat);
    }
    
    public static Long getStartTime(long timeInMillis){
    	Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timeInMillis);
        cal.set(Calendar.HOUR_OF_DAY,0);
        cal.set(Calendar.MINUTE,0);
        cal.set(Calendar.SECOND,0);
        cal.set(Calendar.MILLISECOND,0);
        return cal.getTimeInMillis();
    }
    
    public static Long getEndTime(long timeInMillis){
    	Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timeInMillis);
        cal.set(Calendar.HOUR_OF_DAY,23);
        cal.set(Calendar.MINUTE,59);
        cal.set(Calendar.SECOND,59);
        cal.set(Calendar.MILLISECOND,0);
        return cal.getTimeInMillis();
    }
}
