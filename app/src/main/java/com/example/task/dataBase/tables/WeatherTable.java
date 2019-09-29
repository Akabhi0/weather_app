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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public double getMintemp() {
        return mintemp;
    }

    public void setMintemp(double mintemp) {
        this.mintemp = mintemp;
    }

    public double getMaxtemp() {
        return maxtemp;
    }

    public void setMaxtemp(double maxtemp) {
        this.maxtemp = maxtemp;
    }

    public String getDiscriptions() {
        return discriptions;
    }

    public void setDiscriptions(String discriptions) {
        this.discriptions = discriptions;
    }
}
