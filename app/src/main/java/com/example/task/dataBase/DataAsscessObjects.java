package com.example.task.dataBase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.task.dataBase.tables.WeatherTable;

import java.util.List;

@Dao
public interface DataAsscessObjects {

    @Insert
    void insertWeather(WeatherTable weatherTable);

    @Update
    void updateWeather(WeatherTable weatherTable);

    @Query("SELECT * FROM weatherTable ORDER BY id DESC")
    LiveData<List<WeatherTable>> getAllWeatherData();
}
