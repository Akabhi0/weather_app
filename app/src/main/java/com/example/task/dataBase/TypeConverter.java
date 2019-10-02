package com.example.task.dataBase;

import com.example.task.dataBase.tables.ForecastArrayTable;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class TypeConverter {
    @androidx.room.TypeConverter
    public String fromForecastTable(List<ForecastArrayTable> forecastArrayTables) {
        if (forecastArrayTables == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<ForecastArrayTable>>() {
        }.getType();
        String json = gson.toJson(forecastArrayTables, type);
        return json;
    }

    @androidx.room.TypeConverter // note this annotation
    public List<ForecastArrayTable> toForecastTable(String forcasteTable) {
        if (forcasteTable == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<ForecastArrayTable>>() {
        }.getType();
        List<ForecastArrayTable> productCategoriesList = gson.fromJson(forcasteTable, type);
        return productCategoriesList;
    }
}
