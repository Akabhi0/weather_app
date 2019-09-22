package com.example.task.viewModel;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.task.model.ForeCasteMain;
import com.example.task.model.Places;
import com.example.task.model.WeatherMain;
import com.example.task.repository.RepositoryAPI;

public class AddressScreenViewModel extends ViewModel {

    private MutableLiveData<Boolean> isChecked = new MutableLiveData<>();
    private MutableLiveData<WeatherMain> weatherModelMutableLiveData;
    private MutableLiveData<ForeCasteMain> foreCasteMutableLiveData;
    private Context context;
    private Places places;

    public MutableLiveData<Boolean> getIsChecked() {
        return isChecked;
    }

    public void setIsChecked(Boolean isChecked) {
        this.isChecked.setValue(isChecked);
    }

    public AddressScreenViewModel(Places places, Context context) {
        this.places = places;
        this.context = context;
    }

    public void onPlaceCheck() {
        if (TextUtils.isEmpty(places.getPlace())) {
            Toast.makeText(context, "Enter Place", Toast.LENGTH_SHORT).show();
        } else {
            setIsChecked(true);
        }
    }

    /**
     * This is used for send the data the server
     *
     * @return
     */
    public MutableLiveData<WeatherMain> getWeatherData() {
        return weatherModelMutableLiveData;
    }

    public void getweather(String places) {
        weatherModelMutableLiveData = RepositoryAPI.getRepositoryAPI().getWeatherData(places);
    }

    public MutableLiveData<ForeCasteMain> getForcasteData() {
        return foreCasteMutableLiveData;
    }

    /**
     * This is used for send the data the server
     *
     * @return
     */
    public void getForecaste(String places) {
        foreCasteMutableLiveData = RepositoryAPI.getRepositoryAPI().getForecasteData(places);
    }
}

