package com.example.task.viewModel;

import android.app.Application;

import androidx.lifecycle.ViewModel;

import com.example.task.dataBase.tables.WeatherTable;
import com.example.task.repository.RepositortDataBase;

public class ClimateViewModel extends ViewModel {

    public void insertWeatherData(WeatherTable weatherTable, Application application) {
        RepositortDataBase repositortDataBase = new RepositortDataBase(application);
        repositortDataBase.IntsertData(weatherTable);
    }
}
