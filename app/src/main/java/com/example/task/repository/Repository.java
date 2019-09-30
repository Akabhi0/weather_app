package com.example.task.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.task.dataBase.DataAsscessObjects;
import com.example.task.dataBase.DatabaseClimate;
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

    //========================NETWORK CALL OPERATIONS==============================================

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

            }
        });
        return weatherModelMutableLiveData;
    }

    //========================DATABASE OPERATIONS===================================================
    public LiveData<List<WeatherTable>> getListMutableLiveData() {
        return listMutableLiveData = dataAsscessObjects.getAllWeatherData();
    }

    public void IntsertData(WeatherTable weatherTable) {
        new Repository.InsertWeatherAsyncTask(dataAsscessObjects).execute(weatherTable);
    }

    public void UpdateData(WeatherTable weatherTable) {
//        new RepositortDataBase.UpdateWeatherAsyncTask(dataAsscessObjects).execute(weatherTable);
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
