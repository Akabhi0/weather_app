package com.example.task.repository;

import androidx.lifecycle.MutableLiveData;

import com.example.task.model.ForeCasteMain;
import com.example.task.model.WeatherMain;
import com.example.task.webServices.RetroFitConnectionClass;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repository {
    private static Repository repository = null;
    private MutableLiveData<ForeCasteMain> foreCasteMutableLiveData;
    private MutableLiveData<WeatherMain> weatherModelMutableLiveData;

    private Repository() {
    }

    public static Repository getRepository() {
        if (repository == null)
            repository = new Repository();
        return repository;
    }

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
}
