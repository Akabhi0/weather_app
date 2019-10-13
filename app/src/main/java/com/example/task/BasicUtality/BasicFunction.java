package com.example.task.BasicUtality;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class BasicFunction {

    /**
     * This is used for converting the celcuis
     *
     * @param kalvin
     * @return
     */
    public static double getCelcius(double kalvin) {
        return (kalvin - 273.15);
    }

    /**
     * This function is used for checking the network in the app
     *
     * @param activity
     * @return
     */
    public static boolean isNetworkOnline(Activity activity) {
        boolean status = false;
        try {
            ConnectivityManager cm = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getNetworkInfo(0);
            if (netInfo != null && netInfo.getState() == NetworkInfo.State.CONNECTED) {
                status = true;
            } else {
                netInfo = cm.getNetworkInfo(1);
                if (netInfo != null && netInfo.getState() == NetworkInfo.State.CONNECTED)
                    status = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return status;
    }

    /**
     * This function is used for getting the day form date
     *
     * @param apiDate
     * @return
     * @throws ParseException
     */
    public static String getDayFromDate(String apiDate) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = formatter.parse(apiDate);
        return String.valueOf(date).substring(0, 3);
    }

    /**
     * This is used for getting the date from date and time
     *
     * @param dateTime
     * @return
     * @throws ParseException
     */
    public static String getDateFromDateTime(String dateTime) throws ParseException {
        SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date date = dt.parse(dateTime);
        SimpleDateFormat dt1 = new SimpleDateFormat("MMM dd,yyyy", Locale.ENGLISH);
        return String.valueOf(dt1.format(date));
    }

    /**
     * This is the function is used to get the time from the date time in java
     *
     * @param dateTime
     * @return
     * @throws ParseException
     */
    public static String getTimeFromDateTime(String dateTime) throws ParseException {
        SimpleDateFormat dt = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        Date date = dt.parse(dateTime);
        SimpleDateFormat dt1 = new SimpleDateFormat("hh:mm a", Locale.ENGLISH);
        return String.valueOf(dt1.format(date));
    }
}
