package com.example.task.viewModel;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.task.dataBase.tables.ForecastTable;
import com.example.task.dataBase.tables.WeatherTable;
import com.example.task.repository.Repository;

import java.util.List;

public class CheckDataViewModel extends ViewModel {

    public LiveData<List<WeatherTable>> listLiveData;
    public LiveData<List<ForecastTable>> listForecasteLiveData;

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
}
