package com.example.task.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.task.dataBase.DataAsscessObjects;
import com.example.task.dataBase.DatabaseClimate;
import com.example.task.dataBase.tables.ForecastTable;
import com.example.task.dataBase.tables.WeatherTable;
import com.example.task.model.ForeCasteMain;
import com.example.task.model.WeatherMain;
import com.example.task.webServices.RetroFitConnectionClass;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repository {
    private static Repository repository = null;

    //================NETWORK REFERENCE VARIABLES===================================================
    private MutableLiveData<ForeCasteMain> foreCasteMutableLiveData;
    private MutableLiveData<WeatherMain> weatherModelMutableLiveData;

    //===============DATABASE REFERENCE VARIABLES===================================================
    public LiveData<List<WeatherTable>> listMutableLiveData;
    private static DataAsscessObjects dataAsscessObjects;
    private static DatabaseClimate databaseClimate;

    private Repository() {
    }

    public static Repository getRepository() {
        if (repository == null)
            repository = new Repository();
        return repository;
    }

    public static Repository getRepositoryDataBase(Application application) {
        if (repository == null) {
            repository = new Repository();
            databaseClimate = DatabaseClimate.getInstance(application);
            dataAsscessObjects = databaseClimate.getDataAsscessObjects();
        }
        return repository;
    }

    //========================NETWORK CALL OPERATIONS===============================================

    /**
     * @param places
     * @return
     */
    public MutableLiveData<ForeCasteMain> getForecasteData(String places) {
        foreCasteMutableLiveData = new MutableLiveData<>();
        RetroFitConnectionClass.getApiCall().getForcasteApp(places).enqueue(new Callback<ForeCasteMain>() {
            @Override
            public void onResponse(Call<ForeCasteMain> call, Response<ForeCasteMain> response) {
                foreCasteMutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<ForeCasteMain> call, Throwable t) {
            }
        });
        return foreCasteMutableLiveData;
    }

    public MutableLiveData<WeatherMain> getWeatherData(String places) {
        weatherModelMutableLiveData = new MutableLiveData<>();

        RetroFitConnectionClass.getApiCall().getWeatherApp(places).enqueue(new Callback<WeatherMain>() {
            @Override
            public void onResponse(Call<WeatherMain> call, Response<WeatherMain> response) {
                weatherModelMutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<WeatherMain> call, Throwable t) {
                int i = 0;
            }
        });
        return weatherModelMutableLiveData;
    }

    //========================DATABASE OPERATIONS===================================================
    public LiveData<List<WeatherTable>> getListMutableLiveData() {
        return listMutableLiveData = dataAsscessObjects.getAllWeatherData();
    }

    public void InsertWeatherData(WeatherTable weatherTable) {
        new Repository.InsertWeatherAsyncTask(dataAsscessObjects).execute(weatherTable);
    }

    public void InsertForecastData(ForecastTable forecastTable) {
        new Repository.InsertForecastAsyncTask(dataAsscessObjects).execute(forecastTable);
    }


    //=======================ROOM DATABASE IS RUN ON BACKGROUND THREAD==============================

    /**
     * this is class should be static because it should be created its own memory and have not connected
     * to this repo
     */
    //INSERT THE DATABASE

    /***
     * This class is for inserting the weather into dataBase
     */
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

    /**
     * This is the class for storing the forecast into dataBase
     */
    public static class InsertForecastAsyncTask extends AsyncTask<ForecastTable, Void, Void> {
        private DataAsscessObjects dao;

        public InsertForecastAsyncTask(DataAsscessObjects asscessObjects) {
            this.dao = asscessObjects;
        }

        @Override
        protected Void doInBackground(ForecastTable... forecastTables) {
            dao.insertForeCast(forecastTables[0]);
            return null;
        }
    }

}
