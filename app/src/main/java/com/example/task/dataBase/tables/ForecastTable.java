package com.example.task.dataBase.tables;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "forecastTable")
public class ForecastTable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private double temp;

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }
}
