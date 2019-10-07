package com.example.task.viewModel;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.task.dataBase.tables.ForecastSingleTable;
import com.example.task.dataBase.tables.ForecastTable;
import com.example.task.dataBase.tables.WeatherTable;
import com.example.task.repository.Repository;

import java.util.List;

public class CheckDataViewModel extends ViewModel {

    public LiveData<List<WeatherTable>> listLiveData;
    public LiveData<List<ForecastTable>> listForecasteLiveData;
    public LiveData<List<ForecastSingleTable>> liveForecastSingleLiveData;

    /**
     * This is for taking the data of the weather from dataBase
     *
     * @param application
     */
    public void getWeatherTableLiveData(Application application) {
        listLiveData = Repository.getRepositoryDataBase(application).getListMutableLiveData();
    }

    public LiveData<List<WeatherTable>> sendLiveData() {
        return listLiveData;
    }

    /**
     * This is for taking the data of forcast from dataBase
     *
     * @param application
     */
    public void getForecastTableLiveData(Application application) {
        listForecasteLiveData = Repository.getRepositoryDataBase(application).getAllForecastTable();
    }

    public LiveData<List<ForecastTable>> sendForecastLiveData() {
        return listForecasteLiveData;
    }

    /**
     * This is for taking the forecast single data form the dataBase
     */
    public void getForecastSingleTableLiveData(Application application) {
        liveForecastSingleLiveData = Repository.getRepositoryDataBase(application).getAllForecastSingleTable();
    }

    public LiveData<List<ForecastSingleTable>> sendForecastSingleLiveData() {
        return liveForecastSingleLiveData;
    }
}
