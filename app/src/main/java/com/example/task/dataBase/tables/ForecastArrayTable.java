package com.example.task.dataBase.tables;

import java.util.ArrayList;

public class ForecastArrayTable {

    private ArrayList<ForecastDateTable> forecastDateTables;

    private String date;

    public ArrayList<ForecastDateTable> getForecastDateTables() {
        return forecastDateTables;
    }

    public void setForecastDateTables(ArrayList<ForecastDateTable> forecastDateTables) {
        this.forecastDateTables = forecastDateTables;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
