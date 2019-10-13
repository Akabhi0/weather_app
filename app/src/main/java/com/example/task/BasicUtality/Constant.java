package com.example.task.BasicUtality;

import androidx.lifecycle.MutableLiveData;

public class Constant {

    public static String BASE_URL = "http://api.openweathermap.org/data/2.5/";
    public static String PIC = "http://openweathermap.org/img/w/";
    public static String FORMATE = ".png";
    public static String STRING_FORMATE = "%.2f";
    public static String ADDRESS_TO_CLIMATE_WEATHER_KEY = "address_to_climate_weather_key";
    public static String ADDRESS_TO_CLIMATE_FORECAST_KEY = "address_to_climate_forecast_key";

    public static String INTENT_CITY_NAME = "city_name";
    public static String INTENT_CLIMATE_START_SCREEN = "intent_climate_start_screen";
    public static String INTENT_CLIMATE_ADDRESS_SCREEN = "intent_climate_address_screen";

    public static int INTENT_CLIMATE_START_SCREEN_VALUE = 12;
    public static int INTENT_CLIMATE_ADDRESS_SCREEN_VALUE = 13;
    public static int INTENT_RESULT_CODE = 1;
    public static int JOB_NUMBER = 1;
    public static int START_VALUE = 0;
    public static int ADDRESS_VALUE = 0;

    public static MutableLiveData<Boolean> UPDATELIVE = new MutableLiveData<>();

    public static MutableLiveData<Boolean> getBooleanMutableLiveData() {
        return UPDATELIVE;
    }

    public static void setBooleanMutableLiveData(Boolean booleanMutableLiveData) {
        Constant.UPDATELIVE.setValue(booleanMutableLiveData);
    }
}
