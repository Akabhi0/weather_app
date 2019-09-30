package com.example.task.dataBase.tables;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "weatherTable")
public class WeatherTable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String city;

    private String icon;

    private double temp;

    private double mintemp;

    private double maxtemp;

    private String discriptions;

    public WeatherTable(String city, String icon, double temp, double mintemp, double maxtemp, String discriptions) {
        this.city = city;
        this.icon = icon;
        this.temp = temp;
        this.mintemp = mintemp;
        this.maxtemp = maxtemp;
        this.discriptions = discriptions;
    }


    public int getId() {
        return id;
    }

    public String getCity() {
        return city;
    }

    public String getIcon() {
        return icon;
    }

    public double getTemp() {
        return temp;
    }

    public double getMintemp() {
        return mintemp;
    }

    public double getMaxtemp() {
        return maxtemp;
    }

    public String getDiscriptions() {
        return discriptions;
    }

    public void setId(int id) {
        this.id = id;
    }

}
