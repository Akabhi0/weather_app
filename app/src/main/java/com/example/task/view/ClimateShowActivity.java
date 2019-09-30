package com.example.task.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.example.task.BasicUtality.BasicFunction;
import com.example.task.BasicUtality.Constant;
import com.example.task.R;
import com.example.task.dataBase.tables.WeatherTable;
import com.example.task.databinding.ActivityClimateShowBinding;
import com.example.task.model.ForeCasteMain;
import com.example.task.model.WeatherClimateModel;
import com.example.task.model.WeatherMain;
import com.example.task.model.WeatherModel;
import com.example.task.viewModel.CheckDataViewModel;
import com.example.task.viewModel.ClimateViewModel;

import java.util.List;

public class ClimateShowActivity extends AppCompatActivity {

    private ActivityClimateShowBinding binding;
    private ForeCasteMain foreCasteMain;
    private WeatherMain weatherMain;
    private int ADDRESS_VALUE, START_VALUE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_climate_show);
        final Intent intent = getIntent();
        ADDRESS_VALUE = intent.getIntExtra(Constant.INTENT_CLIMATE_ADDRESS_SCREEN, 0);
        START_VALUE = intent.getIntExtra(Constant.INTENT_CLIMATE_START_SCREEN, 0);

        /**
         * This is for the 1st time when data is not present in the dataBase
         */
        if (ADDRESS_VALUE == Constant.INTENT_CLIMATE_ADDRESS_SCREEN_VALUE) {
            foreCasteMain = (ForeCasteMain) intent.getSerializableExtra(Constant.ADDRESS_TO_CLIMATE_FORECAST_KEY);
            weatherMain = (WeatherMain) intent.getSerializableExtra(Constant.ADDRESS_TO_CLIMATE_WEATHER_KEY);

            binding.setForecastModel(foreCasteMain);
            binding.setWeatherModel(weatherMain);

            binding.setTemp(String.format(Constant.STRING_FORMATE,
                    BasicFunction.getCelcius(weatherMain.getWeatherClimateModel().getTemp())) + (char) 0x00B0);
            binding.setWeather(weatherMain.getWeatherModels().get(0));

            binding.setTempMin(String.format(Constant.STRING_FORMATE,
                    BasicFunction.getCelcius(weatherMain.getWeatherClimateModel().getTempMin())) + (char) 0x00B0);

            binding.setTempMax(String.format(Constant.STRING_FORMATE,
                    BasicFunction.getCelcius(weatherMain.getWeatherClimateModel().getTempMax())) + (char) 0x00B0);

            Glide.with(this)
                    .load(Constant.PIC + weatherMain.getWeatherModels().get(0).getIcon() + Constant.FORMATE)
                    .placeholder(R.drawable.ic_cloud_computing).
                    into(binding.acivWeatherIcon);

            WeatherTable weatherTable = new WeatherTable(weatherMain.getName(),
                    weatherMain.getWeatherModels().get(0).getIcon(),
                    weatherMain.getWeatherClimateModel().getTemp(),
                    weatherMain.getWeatherClimateModel().getTempMin(),
                    weatherMain.getWeatherClimateModel().getTempMax(),
                    weatherMain.getWeatherModels().get(0).getDescription());

            ClimateViewModel viewModel = ViewModelProviders.of(this).get(ClimateViewModel.class);
            viewModel.insertWeatherData(getApplication());
            viewModel.insert(weatherTable);

            /**
             * On click of the forward arrow
             */
            binding.acivForward.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intentResult = new Intent(ClimateShowActivity.this, NewAddressActivity.class);
                    intentResult.putExtra(binding.actvCity.getText().toString(), Constant.INTENT_CITY_NAME);
                    startActivityForResult(intentResult, Constant.INTENT_RESULT_CODE);
                }
            });
        }

        /**
         * This is for the 2nd times when data is present in the dataBase
         */
        else if (START_VALUE == Constant.INTENT_CLIMATE_START_SCREEN_VALUE) {
            CheckDataViewModel viewModel = ViewModelProviders.of(this).get(CheckDataViewModel.class);
            viewModel.getWeatherTableLiveData(getApplication());
            viewModel.sendLiveData().observe(this, new Observer<List<WeatherTable>>() {
                @Override
                public void onChanged(List<WeatherTable> weatherTables) {
                    if (weatherTables != null) {
                        final WeatherMain weatherMain = new WeatherMain();
                        weatherMain.setName(weatherTables.get(weatherTables.size() - 1).getCity());

                        WeatherModel weatherModel = new WeatherModel();
                        weatherModel.setDescription(weatherTables.get(weatherTables.size() - 1).getDiscriptions());

                        WeatherClimateModel weatherClimateModel = new WeatherClimateModel();
                        weatherClimateModel.setTemp(weatherTables.get(weatherTables.size() - 1).getTemp());
                        weatherClimateModel.setTempMax(weatherTables.get(weatherTables.size() - 1).getMaxtemp());
                        weatherClimateModel.setTempMin(weatherTables.get(weatherTables.size() - 1).getMintemp());

                        binding.setWeatherModel(weatherMain);
                        binding.setWeather(weatherModel);

                        binding.setTemp(String.format(Constant.STRING_FORMATE,
                                BasicFunction.getCelcius(weatherClimateModel.getTemp())) + (char) 0x00B0);

                        binding.setTempMin(String.format(Constant.STRING_FORMATE,
                                BasicFunction.getCelcius(weatherClimateModel.getTempMin())) + (char) 0x00B0);

                        binding.setTempMax(String.format(Constant.STRING_FORMATE,
                                BasicFunction.getCelcius(weatherClimateModel.getTempMax())) + (char) 0x00B0);


                        Glide.with(ClimateShowActivity.this)
                                .load(Constant.PIC + weatherTables.get(weatherTables.size() - 1).getIcon() + Constant.FORMATE)
                                .placeholder(R.drawable.ic_cloud_computing).
                                into(binding.acivWeatherIcon);

                        /**
                         * On click of the forward arrow
                         */
                        binding.acivForward.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String cityName = binding.actvCity.getText().toString();
                                Intent intentResult = new Intent(ClimateShowActivity.this, NewAddressActivity.class);
                                intentResult.putExtra(cityName, Constant.INTENT_CITY_NAME);
                                startActivityForResult(intentResult, Constant.INTENT_RESULT_CODE);
                            }
                        });

                    }
                }
            });


        }
    }
}
