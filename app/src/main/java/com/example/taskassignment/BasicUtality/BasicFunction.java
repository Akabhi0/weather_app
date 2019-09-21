package com.example.taskassignment.BasicUtality;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskassignment.model.UserModel;

import java.util.Calendar;

public class BasicFunction {
    private static DatePickerDialog datePickerDialog;

    /**
     * This is for the getting the date picker
     *
     * @param context
     * @param mDateSetlistener
     * @return
     */
    public static DatePickerDialog getDialoge(Context context, DatePickerDialog.OnDateSetListener mDateSetlistener) {
        Calendar myCalendar = Calendar.getInstance();
        int year = myCalendar.get(Calendar.YEAR);
        int month = myCalendar.get(Calendar.MONTH);
        int day = myCalendar.get(Calendar.DAY_OF_MONTH);

        datePickerDialog = new DatePickerDialog(
                context,
                AlertDialog.THEME_HOLO_LIGHT,
                mDateSetlistener,
                year, month, day);

        return datePickerDialog;
    }

    /**
     * Universal toast message
     *
     * @param messageToast
     * @param context
     */
    public static void getToastMessage(String messageToast, Context context) {
        Toast.makeText(context, messageToast, Toast.LENGTH_SHORT).show();
    }

    /**
     * This is the function is used for passing the object class data into another class
     *
     * @param activity
     * @param clas
     * @param valueName
     * @return
     */
    public static Intent passValueObjectIntent(Activity activity, Class clas, String valueName, UserModel userModel) {
        Intent intent = new Intent(activity, clas);
        intent.putExtra(valueName, userModel);
        return intent;
    }

    /**
     * This is the function is used to get the data from the object class
     *
     * @param activity
     * @param valueName
     * @return
     */
    public static UserModel getPassValueObjectIntnet(Activity activity, String valueName) {
        Intent intent = activity.getIntent();
        Bundle bundle = intent.getExtras();
        try {
            UserModel userModel = (UserModel) bundle.getSerializable(valueName);
            return userModel;
        } catch (NullPointerException e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * This is the function is used for opening the new activity
     *
     * @param activty
     * @param clas
     * @return
     */
    public static Intent useIntent(Activity activty, Class clas) {
        Intent intent = new Intent(activty, clas);
        return intent;
    }

    /**
     * This is used for setting the linear layout manager
     *
     * @param context
     * @return
     */
    public static LinearLayoutManager getLinearLayoutManagerVertical(Context context) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
        return linearLayoutManager;
    }

    /**
     * This is the function is used for the using intent with value
     *
     * @param activty
     * @param clas
     * @param valueInt
     * @param valueString
     * @return
     */
    public static Intent passValueIntent(Activity activty, Class clas, int valueInt, String valueString) {
        Intent intent = new Intent(activty, clas);
        intent.putExtra(valueString, valueInt);
        return intent;
    }

    /**
     * This is for getting the string value from intent
     *
     * @param valueString
     * @param activity
     * @return
     */
    public static int getPassValueIntent(String valueString, Activity activity) {
        Intent intent = activity.getIntent();
        return intent.getIntExtra(valueString, 0);
    }

}
