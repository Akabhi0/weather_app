package com.example.task.viewModel;

import android.content.Context;

import androidx.lifecycle.ViewModel;

import com.example.task.dataBase.tables.WeatherTable;
import com.example.task.repository.RepositortDataBase;

public class ClimateViewModel extends ViewModel {

    public void insertWeatherData(WeatherTable weatherTable, Context context) {
        RepositortDataBase repositortDataBase = new RepositortDataBase(context);
        repositortDataBase.IntsertData(weatherTable);
    }
}
