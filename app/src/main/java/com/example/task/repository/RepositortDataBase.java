package com.example.task.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.task.dataBase.DataAsscessObjects;
import com.example.task.dataBase.DatabaseClimate;
import com.example.task.dataBase.tables.WeatherTable;

import java.util.List;

public class RepositortDataBase {
    public LiveData<List<WeatherTable>> listMutableLiveData;
    private DataAsscessObjects dataAsscessObjects;

    public RepositortDataBase(Application application) {
        dataAsscessObjects = DatabaseClimate.getInstance(application).getDataAsscessObjects();
        listMutableLiveData = dataAsscessObjects.getAllWeatherData();
    }

    //========================DATABASE OPERATIONS===================================================
    public void IntsertData(WeatherTable weatherTable) {
        new InsertWeatherAsyncTask(dataAsscessObjects).execute(weatherTable);
    }

    public void UpdateData(WeatherTable weatherTable) {
        new UpdateWeatherAsyncTask(dataAsscessObjects).execute(weatherTable);
    }

    //=======================ROOM DATABASE IS RUN ON BACKGROUND THREAD==============================

    /**
     * this is class should be static because it should be created its own memory and have not connected
     * to this repo
     */
    //INSERT THE DATABASE
    public static class InsertWeatherAsyncTask extends AsyncTask<WeatherTable, Void, Void> {
        private DataAsscessObjects dao;

        public InsertWeatherAsyncTask(DataAsscessObjects asscessObjects) {
            this.dao = asscessObjects;
        }

        @Override
        protected Void doInBackground(WeatherTable... weatherTables) {
            dao.insertWeather(weatherTables[0]);
            return null;
        }
    }

    //UPDATING THE DATABASE
    public static class UpdateWeatherAsyncTask extends AsyncTask<WeatherTable, Void, Void> {
        private DataAsscessObjects dao;

        private UpdateWeatherAsyncTask(DataAsscessObjects asscessObjects) {
            this.dao = asscessObjects;
        }

        @Override
        protected Void doInBackground(WeatherTable... weatherTables) {
            dao.updateWeather(weatherTables[0]);
            return null;
        }
    }
}
