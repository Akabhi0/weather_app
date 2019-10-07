package com.example.task.view;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.task.BasicUtality.BasicFunction;
import com.example.task.BasicUtality.Constant;
import com.example.task.R;
import com.example.task.dataBase.tables.ForecastArrayTable;
import com.example.task.dataBase.tables.ForecastTable;
import com.example.task.dataBase.tables.WeatherTable;
import com.example.task.databinding.ActivityNewAddressBinding;
import com.example.task.model.ForeCasteMain;
import com.example.task.model.Places;
import com.example.task.model.WeatherMain;
import com.example.task.viewModel.AddressScreenViewModel;
import com.example.task.viewModel.AddressScreenViewModelFactory;
import com.example.task.viewModel.ClimateViewModel;

import java.text.ParseException;
import java.util.ArrayList;

public class NewAddressActivity extends AppCompatActivity {

    private ActivityNewAddressBinding binding;
    private AddressScreenViewModel viewModel;
    private AddressScreenViewModelFactory addressScreenViewModelFactory;
    private boolean networkCheck;
    private Places places;
    private String placeName, date, day, time;
    private WeatherTable weatherTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_new_address);

        placeName = getIntent().getStringExtra(Constant.INTENT_CITY_NAME);
        places = new Places();
        places.setPlace(placeName);
        binding.setPlaces(places);

        addressScreenViewModelFactory = new AddressScreenViewModelFactory(this, places);
        viewModel = ViewModelProviders.of(this, addressScreenViewModelFactory).get(AddressScreenViewModel.class);
        binding.setViewModel(viewModel);

        /**
         * This is the observer is used for checking the internet and the insertWeatherData the data into database
         */
        viewModel.getIsChecked().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    networkCheck = BasicFunction.isNetworkOnline(NewAddressActivity.this);

                    if (networkCheck) {
                        viewModel.getweather(places.getPlace());
                        viewModel.getWeatherData().observe(NewAddressActivity.this, new Observer<WeatherMain>() {
                            @Override
                            public void onChanged(final WeatherMain weatherMain) {
                                if (weatherMain != null) {
                                    viewModel.getForecaste(places.getPlace());

                                    /**
                                     * This is the observer for inserting the weather data into database
                                     */
                                    viewModel.getForcasteData().observe(NewAddressActivity.this, new Observer<ForeCasteMain>() {
                                        @Override
                                        public void onChanged(ForeCasteMain foreCaste) {
                                            if (foreCaste != null) {
                                                ArrayList<ForecastArrayTable> forecastArrayTables = new ArrayList<>();
                                                for (int i = 0; i < foreCaste.getForecastObejctsModels().size(); i++) {
                                                    try {
                                                        day = BasicFunction.getDayFromDate(foreCaste.getForecastObejctsModels().get(0).getDate());
                                                        date = BasicFunction.getDateFromDateTime(foreCaste.getForecastObejctsModels().get(0).getDate());
                                                        time = BasicFunction.getTimeFromDateTime(foreCaste.getForecastObejctsModels().get(0).getDate());

                                                        String days = BasicFunction.getDayFromDate(foreCaste.getForecastObejctsModels().get(i).getDate());

                                                        ForecastArrayTable forecastArrayTable = new ForecastArrayTable();
                                                        /**
                                                         * 1st taking the data from weather list
                                                         */
                                                        forecastArrayTable.setDescription(foreCaste.getForecastObejctsModels().get(i).getWeatherModels().get(0).getDescription());
                                                        forecastArrayTable.setIcon(foreCaste.getForecastObejctsModels().get(i).getWeatherModels().get(0).getIcon());

                                                        /**
                                                         * Taking the data from from main object list
                                                         */
                                                        forecastArrayTable.setTemp(foreCaste.getForecastObejctsModels().get(i).getForcasteMainModel().getTemp());
                                                        forecastArrayTable.setTempMax(foreCaste.getForecastObejctsModels().get(i).getForcasteMainModel().getTempMax());
                                                        forecastArrayTable.setTempMin(foreCaste.getForecastObejctsModels().get(i).getForcasteMainModel().getTempMin());

                                                        forecastArrayTable.setDay(days);
                                                        forecastArrayTable.setCity(foreCaste.getCity().getName());
                                                        forecastArrayTable.setDate(BasicFunction.getDateFromDateTime(foreCaste.getForecastObejctsModels().get(i).getDate()));
                                                        forecastArrayTable.setTime(BasicFunction.getTimeFromDateTime(foreCaste.getForecastObejctsModels().get(i).getDate()));
                                                        forecastArrayTables.add(forecastArrayTable);
                                                    } catch (ParseException e) {
                                                        e.printStackTrace();
                                                    }
                                                }
                                                ForecastTable forecastTable = new ForecastTable();
                                                forecastTable.setForecastArrayTables(forecastArrayTables);

                                                /**
                                                 * This is for inserting the data into weather table
                                                 */
                                                weatherTable = new WeatherTable(weatherMain.getName(),
                                                        weatherMain.getWeatherModels().get(0).getIcon(),
                                                        weatherMain.getWeatherClimateModel().getTemp(),
                                                        weatherMain.getWeatherClimateModel().getTempMin(),
                                                        weatherMain.getWeatherClimateModel().getTempMax(),
                                                        weatherMain.getWeatherModels().get(0).getDescription(),
                                                        day, date, time
                                                );

                                                ClimateViewModel viewModel = ViewModelProviders.of(NewAddressActivity.this).get(ClimateViewModel.class);
                                                viewModel.insertWeatherData(getApplication());
                                                viewModel.insertWeatherData(weatherTable);
                                                viewModel.insertForcastData(forecastTable);
                                                finish();
                                            }
                                        }
                                    });
                                }
                            }
                        });
                    } else {
                        /**
                         * if network is not present
                         */
                        DialogPopup dialogPopup = new DialogPopup(NewAddressActivity.this,
                                getResources().getString(R.string.network_message));
                        dialogPopup.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialogPopup.setCancelable(true);
                        dialogPopup.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                        dialogPopup.show();
                    }
                }
            }
        });

        /**
         * This is for the pressing the back button
         */
        binding.acivBackword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
