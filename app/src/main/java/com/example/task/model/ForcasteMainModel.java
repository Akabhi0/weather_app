package com.example.task.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ForcasteMainModel implements Serializable {

    @SerializedName("temp")
    @Expose
    private Double temp;
    @SerializedName("temp_min")
    @Expose
    private Double tempMin;
    @SerializedName("temp_max")
    @Expose
    private Double tempMax;

    public Double getTemp() {
        return temp;
    }

    public Double getTempMin() {
        return tempMin;
    }

    public Double getTempMax() {
        return tempMax;
    }
}
