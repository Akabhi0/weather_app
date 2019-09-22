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
}
