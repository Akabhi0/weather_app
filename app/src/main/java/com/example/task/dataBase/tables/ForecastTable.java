package com.example.task.dataBase.tables;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.task.dataBase.TypeConverter;

import java.util.List;

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

    @TypeConverters(TypeConverter.class)
    private List<ForecastArrayTable> forecastArrayTables;

    public List<ForecastArrayTable> getForecastArrayTables() {
        return forecastArrayTables;
    }

    public void setForecastArrayTables(List<ForecastArrayTable> forecastArrayTables) {
        this.forecastArrayTables = forecastArrayTables;
    }
}
