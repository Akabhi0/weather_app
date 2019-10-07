package com.example.task.dataBase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.task.dataBase.tables.ForecastSingleTable;
import com.example.task.dataBase.tables.ForecastTable;
import com.example.task.dataBase.tables.WeatherTable;

import java.util.List;

@Dao
public interface DataAsscessObjects {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertWeather(WeatherTable weatherTable);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertForeCast(ForecastTable forecastTable);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertForecastArray(ForecastSingleTable forecastSingleTable);

    @Query("SELECT * FROM weatherTable ORDER BY id DESC")
    LiveData<List<WeatherTable>> getAllWeatherData();

    @Query("SELECT * FROM forecastTable ORDER BY id DESC")
    LiveData<List<ForecastTable>> getAllForecastTable();

    @Query("SELECT * FROM forecastSingleTable ORDER BY id DESC")
    LiveData<List<ForecastSingleTable>> getAllForecastSingleTable();
}
