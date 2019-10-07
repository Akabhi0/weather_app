package com.example.task.viewModel;

import android.app.Application;

import androidx.lifecycle.ViewModel;

import com.example.task.dataBase.tables.ForecastSingleTable;
import com.example.task.dataBase.tables.ForecastTable;
import com.example.task.dataBase.tables.WeatherTable;
import com.example.task.repository.Repository;

public class ClimateViewModel extends ViewModel {
    private Repository repositortDataBase;

    public void insertWeatherData(Application application) {
        repositortDataBase = Repository.getRepositoryDataBase(application);
    }

    public void insertWeatherData(WeatherTable weatherTable) {
        repositortDataBase.InsertWeatherData(weatherTable);
    }

    public void insertForcastData(ForecastTable forecastTable) {
        repositortDataBase.InsertForecastData(forecastTable);
    }

    public void insertForecastSingleData(ForecastSingleTable forecastSingleTable) {
        repositortDataBase.InsertForecastSingleData(forecastSingleTable);
    }

}
