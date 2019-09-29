package com.example.task.viewModel;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.task.dataBase.tables.WeatherTable;
import com.example.task.repository.RepositortDataBase;

import java.util.List;

public class CheckDataViewModel extends ViewModel {

    public LiveData<List<WeatherTable>> getWeatherTableLiveData(Application application) {
        RepositortDataBase repositortDataBase = new RepositortDataBase(application);
        return repositortDataBase.listMutableLiveData;
    }
}
