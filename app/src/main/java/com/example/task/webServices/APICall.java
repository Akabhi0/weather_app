package com.example.task.webServices;

import com.example.task.model.ForeCasteMain;
import com.example.task.model.WeatherMain;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APICall {

    @GET("weather?&APPID=89a9841f0eb1cc0c99fb9ca71191f116")
    Call<WeatherMain> getWeatherApp(@Query("q") String place);

    @GET("forecast?&APPID=89a9841f0eb1cc0c99fb9ca71191f116")
    Call<ForeCasteMain> getForcasteApp(@Query("q") String place);

}
