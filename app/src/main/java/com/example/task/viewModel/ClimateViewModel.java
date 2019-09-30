package com.example.task.viewModel;

import android.app.Application;

import androidx.lifecycle.ViewModel;

import com.example.task.dataBase.tables.WeatherTable;
import com.example.task.repository.Repository;

public class ClimateViewModel extends ViewModel {
    private Repository repositortDataBase;

    public void insertWeatherData(Application application) {
        repositortDataBase = Repository.getRepositoryDataBase(application);
    }

    public void insert(WeatherTable weatherTable) {
        repositortDataBase.IntsertData(weatherTable);
    }
}
