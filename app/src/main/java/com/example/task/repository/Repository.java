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
    private MutableLiveData<ForeCasteMain> foreCasteMutableLiveData;
    private MutableLiveData<WeatherMain> weatherModelMutableLiveData;
    private LiveData<List<WeatherTable>> listMutableLiveData;

    private Repository() {
    }

    public static Repository getRepository() {
        if (repository == null)
            repository = new Repository();
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

            }
        });
        return weatherModelMutableLiveData;
    }

    //========================DATABASE OPERATIONS===================================================
    public void IntsertData(WeatherTable weatherTable, Application application) {
        new InsertWeatherAsyncTask(DatabaseClimate.getInstance(application).getDataAsscessObjects()).doInBackground(weatherTable);
    }

    public void UpdateData(WeatherTable weatherTable, Application application) {
        new UpdateWeatherAsyncTask(DatabaseClimate.getInstance(application).getDataAsscessObjects()).doInBackground(weatherTable);
    }

    public LiveData<List<WeatherTable>> getWeatherTableData(Application application) {
        listMutableLiveData = new MutableLiveData<>();
        listMutableLiveData = DatabaseClimate.getInstance(application).getDataAsscessObjects().getAllWeatherData();
        return listMutableLiveData;
    }
    //=======================ROOM DATABASE IS RUN ON BACKGROUND THREAD==============================

    /**
     * this is class should be static because it should be created its own memory and have not connected
     * to this repo
     */
    //INSERT THE DATABASE
    public static class InsertWeatherAsyncTask extends AsyncTask<WeatherTable, Void, Void> {
        private DataAsscessObjects dataAsscessObjects;

        private InsertWeatherAsyncTask(DataAsscessObjects dataAsscessObjects) {
            this.dataAsscessObjects = dataAsscessObjects;
        }

        @Override
        protected Void doInBackground(WeatherTable... weatherTables) {
            dataAsscessObjects.insertWeather(weatherTables[0]);
            return null;
        }
    }

    //UPDATING THE DATABASE
    public static class UpdateWeatherAsyncTask extends AsyncTask<WeatherTable, Void, Void> {
        private DataAsscessObjects dataAsscessObjects;

        private UpdateWeatherAsyncTask(DataAsscessObjects dataAsscessObjects) {
            this.dataAsscessObjects = dataAsscessObjects;
        }

        @Override
        protected Void doInBackground(WeatherTable... weatherTables) {
            dataAsscessObjects.updateWeather(weatherTables[0]);
            return null;
        }
    }
}
