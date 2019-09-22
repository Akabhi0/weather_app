package com.example.task.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.example.task.BasicUtality.BasicFunction;
import com.example.task.BasicUtality.Constant;
import com.example.task.R;
import com.example.task.dataBase.tables.WeatherTable;
import com.example.task.databinding.ActivityClimateShowBinding;
import com.example.task.model.ForeCasteMain;
import com.example.task.model.WeatherMain;
import com.example.task.viewModel.ClimateViewModel;

public class ClimateShowActivity extends AppCompatActivity {

    private ActivityClimateShowBinding binding;
    private ForeCasteMain foreCasteMain;
    private WeatherMain weatherMain;
    private int ADDRESS_VALUE, START_VALUE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_climate_show);
        Intent intent = getIntent();
        ADDRESS_VALUE = intent.getIntExtra(Constant.INTENT_CLIMATE_ADDRESS_SCREEN, 0);
        START_VALUE = intent.getIntExtra(Constant.INTENT_CLIMATE_START_SCREEN, 0);
        START_VALUE = intent.getIntExtra(Constant.INTENT_CLIMATE_START_SCREEN, 0);

        if (ADDRESS_VALUE == Constant.INTENT_CLIMATE_ADDRESS_SCREEN_VALUE) {
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

            WeatherTable weatherTable = new WeatherTable();
            weatherTable.setDiscriptions(weatherMain.getWeatherModels().get(0).getDescription());
            weatherTable.setIcon(weatherMain.getWeatherModels().get(0).getIcon());
            weatherTable.setCity(weatherMain.getName());
            weatherTable.setTemp(weatherMain.getWeatherClimateModel().getTemp());
            weatherTable.setMaxtemp(weatherMain.getWeatherClimateModel().getTempMax());
            weatherTable.setMintemp(weatherMain.getWeatherClimateModel().getTempMin());

            ClimateViewModel viewModel = ViewModelProviders.of(this).get(ClimateViewModel.class);
            viewModel.insertWeatherData(weatherTable, this);


        } else if (START_VALUE == Constant.INTENT_CLIMATE_START_SCREEN_VALUE) {


        }
    }
}
