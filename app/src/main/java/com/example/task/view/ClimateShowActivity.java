package com.example.task.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.bumptech.glide.Glide;
import com.example.task.BasicUtality.BasicFunction;
import com.example.task.BasicUtality.Constant;
import com.example.task.R;
import com.example.task.databinding.ActivityClimateShowBinding;
import com.example.task.model.ForeCasteMain;
import com.example.task.model.WeatherMain;

public class ClimateShowActivity extends AppCompatActivity {

    private ActivityClimateShowBinding binding;
    private ForeCasteMain foreCasteMain;
    private WeatherMain weatherMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_climate_show);
        Intent intent = getIntent();
        foreCasteMain = (ForeCasteMain) intent.getSerializableExtra(Constant.ADDRESS_TO_CLIMATE_FORECAST_KEY);
        weatherMain = (WeatherMain) intent.getSerializableExtra(Constant.ADDRESS_TO_CLIMATE_WEATHER_KEY);

        binding.setForecastModel(foreCasteMain);
        binding.setWeatherModel(weatherMain);
        binding.setTemp(String.format(Constant.STRING_FORMATE,
                BasicFunction.getCelcius(weatherMain.getWeatherClimateModel().getTemp())) + (char) 0x00B0);

        binding.setTempMin(String.format(Constant.STRING_FORMATE,
                BasicFunction.getCelcius(weatherMain.getWeatherClimateModel().getTempMin())) + (char) 0x00B0);

        binding.setTempMax(String.format(Constant.STRING_FORMATE,
                BasicFunction.getCelcius(weatherMain.getWeatherClimateModel().getTempMax())) + (char) 0x00B0);

        Glide.with(this)
                .load(Constant.PIC + weatherMain.getWeatherModels().get(0).getIcon() + Constant.FORMATE)
                .placeholder(R.drawable.ic_cloud_computing).
                into(binding.acivWeatherIcon);
    }
}
