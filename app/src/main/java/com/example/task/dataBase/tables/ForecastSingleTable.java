package com.example.task.dataBase.tables;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.task.dataBase.TypeConverterArray;

import java.util.List;

@Entity(tableName = "forecastSingleTable")
public class ForecastSingleTable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @TypeConverters(TypeConverterArray.class)
    private List<ForecastSingleArrayTable> singleArrayTables;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<ForecastSingleArrayTable> getSingleArrayTables() {
        return singleArrayTables;
    }

    public void setSingleArrayTables(List<ForecastSingleArrayTable> singleArrayTables) {
        this.singleArrayTables = singleArrayTables;
    }

}
