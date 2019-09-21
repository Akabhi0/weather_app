package com.example.task.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class WeatherMain implements Serializable {

    @SerializedName("cod")
    @Expose
    private String cod;

    @SerializedName("weather")
    @Expose
    private ArrayList<WeatherModel> weatherModels = new ArrayList<>();

    @SerializedName("main")
    @Expose
    private WeatherClimateModel weatherClimateModel;

    @SerializedName("name")
    @Expose
    private String name;

    public ArrayList<WeatherModel> getWeatherModels() {
        return weatherModels;
    }

    public void setWeatherModels(ArrayList<WeatherModel> weatherModels) {
        this.weatherModels = weatherModels;
    }

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public WeatherClimateModel getWeatherClimateModel() {
        return weatherClimateModel;
    }

    public void setWeatherClimateModel(WeatherClimateModel weatherClimateModel) {
        this.weatherClimateModel = weatherClimateModel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}

