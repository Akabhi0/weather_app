package com.example.task.dataBase;

import com.example.task.dataBase.tables.ForecastSingleArrayTable;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class TypeConverterArray {
    @androidx.room.TypeConverter
    public String fromForecastSingleTable(List<ForecastSingleArrayTable> forecastSingleArray) {
        if (forecastSingleArray == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<ForecastSingleArrayTable>>() {
        }.getType();
        String json = gson.toJson(forecastSingleArray, type);
        return json;
    }

    @androidx.room.TypeConverter // note this annotation
    public List<ForecastSingleArrayTable> toForecastSingleTable(String forecastSingleArrayTables) {
        if (forecastSingleArrayTables == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<ForecastSingleArrayTable>>() {
        }.getType();
        List<ForecastSingleArrayTable> forecastSingleArrayTables1 = gson.fromJson(forecastSingleArrayTables, type);
        return forecastSingleArrayTables1;
    }

}
