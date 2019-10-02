package com.example.task.view;

import android.app.Activity;
import android.content.Intent;
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
import com.example.task.dataBase.tables.WeatherTable;
import com.example.task.databinding.ActivityNewAddressBinding;
import com.example.task.model.ForeCasteMain;
import com.example.task.model.Places;
import com.example.task.model.WeatherMain;
import com.example.task.viewModel.AddressScreenViewModel;
import com.example.task.viewModel.AddressScreenViewModelFactory;
import com.example.task.viewModel.ClimateViewModel;

public class NewAddressActivity extends AppCompatActivity {

    private ActivityNewAddressBinding binding;
    private AddressScreenViewModel viewModel;
    private AddressScreenViewModelFactory addressScreenViewModelFactory;
    private boolean networkCheck;
    private Places places;
    private String placeName;

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

                                                WeatherTable weatherTable = new WeatherTable(weatherMain.getName(),
                                                        weatherMain.getWeatherModels().get(0).getIcon(),
                                                        weatherMain.getWeatherClimateModel().getTemp(),
                                                        weatherMain.getWeatherClimateModel().getTempMin(),
                                                        weatherMain.getWeatherClimateModel().getTempMax(),
                                                        weatherMain.getWeatherModels().get(0).getDescription());

                                                ClimateViewModel viewModel = ViewModelProviders.of(NewAddressActivity.this).get(ClimateViewModel.class);
                                                viewModel.insertWeatherData(getApplication());
                                                viewModel.insertWeatherData(weatherTable);

                                                /**
                                                 * This is the code for setting the result and get back to the previous screen
                                                 */
                                                Intent returnIntent = new Intent();
                                                returnIntent.putExtra(Constant.INTENT_RETURN, weatherTable);
                                                setResult(Activity.RESULT_OK, returnIntent);
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
