package com.example.task.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class ForecastObejctsModel implements Serializable {

    @SerializedName("main")
    @Expose
    private ForcasteMainModel forcasteMainModel;

    @SerializedName("weather")
    @Expose
    private ArrayList<ForecastWeatherModel> weatherModels = new ArrayList<>();

    @SerializedName("dt_txt")
    @Expose
    private String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ArrayList<ForecastWeatherModel> getWeatherModels() {
        return weatherModels;
    }

    public void setWeatherModels(ArrayList<ForecastWeatherModel> weatherModels) {
        this.weatherModels = weatherModels;
    }

    public ForcasteMainModel getForcasteMainModel() {
        return forcasteMainModel;
    }

    public void setForcasteMainModel(ForcasteMainModel forcasteMainModel) {
        this.forcasteMainModel = forcasteMainModel;
    }

}
