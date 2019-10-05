package com.example.task.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class ForeCasteMain implements Serializable {

    @SerializedName("cod")
    @Expose
    private String cod;

    @SerializedName("list")
    @Expose
    private ArrayList<ForecastObejctsModel> forecastObejctsModels = new ArrayList<>();

    @SerializedName("city")
    @Expose
    private City city;

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public ArrayList<ForecastObejctsModel> getForecastObejctsModels() {
        return forecastObejctsModels;
    }

    public void setForecastObejctsModels(ArrayList<ForecastObejctsModel> forecastObejctsModels) {
        this.forecastObejctsModels = forecastObejctsModels;
    }


}
