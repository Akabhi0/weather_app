package com.example.task.dataBase.tables;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "weatherTable")
public class WeatherTable implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String city;

    private String icon;

    private double temp;

    private double mintemp;

    private double maxtemp;

    private String discriptions;

    private String day;

    private String date;

    private String time;

    public String getTime() {
        return time;
    }

    public WeatherTable(String city, String icon, double temp, double mintemp, double maxtemp, String discriptions, String day, String date, String time) {
        this.city = city;
        this.icon = icon;
        this.temp = temp;
        this.mintemp = mintemp;
        this.maxtemp = maxtemp;
        this.discriptions = discriptions;
        this.date = date;
        this.day = day;
        this.time = time;
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

    public String getDay() {
        return day;
    }

    public String getDate() {
        return date;
    }


}
