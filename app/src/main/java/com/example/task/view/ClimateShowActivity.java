package com.example.task.view;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.example.task.BasicUtality.BasicFunction;
import com.example.task.BasicUtality.Constant;
import com.example.task.R;
import com.example.task.adapter.ForecastAdapter;
import com.example.task.adapter.ForecastSingleAdapter;
import com.example.task.dataBase.tables.ForecastArrayTable;
import com.example.task.dataBase.tables.ForecastSingleArrayTable;
import com.example.task.dataBase.tables.ForecastSingleTable;
import com.example.task.dataBase.tables.ForecastTable;
import com.example.task.dataBase.tables.WeatherTable;
import com.example.task.databinding.ActivityClimateShowBinding;
import com.example.task.model.ForeCasteMain;
import com.example.task.model.Places;
import com.example.task.model.WeatherClimateModel;
import com.example.task.model.WeatherMain;
import com.example.task.model.WeatherModel;
import com.example.task.services.WeatherService;
import com.example.task.viewModel.AddressScreenViewModel;
import com.example.task.viewModel.AddressScreenViewModelFactory;
import com.example.task.viewModel.CheckDataViewModel;
import com.example.task.viewModel.ClimateViewModel;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class ClimateShowActivity extends AppCompatActivity {

    private ActivityClimateShowBinding binding;
    private ForeCasteMain foreCasteMain;
    private WeatherMain weatherMain;
    private String date, day, time, cityNameCurrent;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_climate_show);
        intent = getIntent();
        Constant.ADDRESS_VALUE = intent.getIntExtra(Constant.INTENT_CLIMATE_ADDRESS_SCREEN, 0);
        Constant.START_VALUE = intent.getIntExtra(Constant.INTENT_CLIMATE_START_SCREEN, 0);
    }

    @Override
    protected void onResume() {
        super.onResume();

        /**
         * This is for the 1st time when data is not present in the dataBase
         */
        if (Constant.ADDRESS_VALUE == Constant.INTENT_CLIMATE_ADDRESS_SCREEN_VALUE) {
            foreCasteMain = (ForeCasteMain) intent.getSerializableExtra(Constant.ADDRESS_TO_CLIMATE_FORECAST_KEY);
            weatherMain = (WeatherMain) intent.getSerializableExtra(Constant.ADDRESS_TO_CLIMATE_WEATHER_KEY);

            /**
             * All forecast data into dataBase
             */
            ArrayList<ForecastArrayTable> forecastArrayTables = new ArrayList<>();
            for (int i = 0; i < foreCasteMain.getForecastObejctsModels().size(); i++) {
                try {
                    date = BasicFunction.getDateFromDateTime(foreCasteMain.getForecastObejctsModels().get(0).getDate());
                    day = BasicFunction.getDayFromDate(foreCasteMain.getForecastObejctsModels().get(0).getDate());
                    time = BasicFunction.getTimeFromDateTime(foreCasteMain.getForecastObejctsModels().get(0).getDate());

                    ForecastArrayTable forecastArrayTable = new ForecastArrayTable();
                    /**
                     * 1st taking the data from weather list
                     */
                    forecastArrayTable.setDescription(foreCasteMain.getForecastObejctsModels().get(i).getWeatherModels().get(0).getDescription());
                    forecastArrayTable.setIcon(foreCasteMain.getForecastObejctsModels().get(i).getWeatherModels().get(0).getIcon());

                    /**
                     * Taking the data from from main object list
                     */
                    forecastArrayTable.setTemp(foreCasteMain.getForecastObejctsModels().get(i).getForcasteMainModel().getTemp());
                    forecastArrayTable.setTempMax(foreCasteMain.getForecastObejctsModels().get(i).getForcasteMainModel().getTempMax());
                    forecastArrayTable.setTempMin(foreCasteMain.getForecastObejctsModels().get(i).getForcasteMainModel().getTempMin());

                    forecastArrayTable.setTime(BasicFunction.getTimeFromDateTime(foreCasteMain.getForecastObejctsModels().get(i).getDate()));
                    forecastArrayTable.setDay(BasicFunction.getDayFromDate(foreCasteMain.getForecastObejctsModels().get(i).getDate()));
                    forecastArrayTable.setCity(foreCasteMain.getCity().getName());
                    forecastArrayTable.setDate(BasicFunction.getDateFromDateTime(foreCasteMain.getForecastObejctsModels().get(i).getDate()));
                    forecastArrayTables.add(forecastArrayTable);

                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            ForecastTable forecastTable = new ForecastTable();
            forecastTable.setForecastArrayTables(forecastArrayTables);

            /**
             * Checking for the forecast of eack 1st index
             */
            ArrayList<ForecastSingleArrayTable> forecastSingleArrayTables = new ArrayList<>();
            for (int days = 0; days < forecastTable.getForecastArrayTables().size(); days++) {
                try {
                    if (!forecastTable.getForecastArrayTables().get(days).getDay().
                            equals(forecastTable.getForecastArrayTables().get(days + 1).getDay())) {

                        ForecastSingleArrayTable forecastSingleArrayTable = new ForecastSingleArrayTable();
                        forecastSingleArrayTable.setDate(forecastTable.getForecastArrayTables().get(days).getDate());
                        forecastSingleArrayTable.setDay(forecastTable.getForecastArrayTables().get(days).getDay());
                        forecastSingleArrayTable.setIcon(forecastTable.getForecastArrayTables().get(days).getIcon());
                        forecastSingleArrayTable.setTempMax(forecastTable.getForecastArrayTables().get(days).getTempMax());
                        forecastSingleArrayTable.setTempMin(forecastTable.getForecastArrayTables().get(days).getTempMin());
                        forecastSingleArrayTables.add(forecastSingleArrayTable);
                    }
                } catch (IndexOutOfBoundsException i) {
                }
            }

            ForecastSingleTable forecastSingleTable = new ForecastSingleTable();
            forecastSingleTable.setSingleArrayTables(forecastSingleArrayTables);
            List<ForecastSingleTable> forecastSingleTables = new ArrayList<>();
            forecastSingleTables.add(forecastSingleTable);

            ForecastSingleAdapter forecastSingleAdapter = new ForecastSingleAdapter(forecastSingleTables);
            binding.recyclerSingleView.setAdapter(forecastSingleAdapter);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ClimateShowActivity.this, RecyclerView.HORIZONTAL, false);
            binding.recyclerSingleView.setLayoutManager(linearLayoutManager);

            final WeatherTable weatherTable = new WeatherTable(weatherMain.getName(),
                    weatherMain.getWeatherModels().get(0).getIcon(),
                    weatherMain.getWeatherClimateModel().getTemp(),
                    weatherMain.getWeatherClimateModel().getTempMin(),
                    weatherMain.getWeatherClimateModel().getTempMax(),
                    weatherMain.getWeatherModels().get(0).getDescription(),
                    day, date, time
            );


            /**
             * This is for setting the adapter
             */
            ForecastAdapter adapter = new ForecastAdapter(this, forecastArrayTables);
            binding.recyclerView.setAdapter(adapter);
            LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
            binding.recyclerView.setLayoutManager(linearLayoutManager1);

            ClimateViewModel viewModel = ViewModelProviders.of(this).get(ClimateViewModel.class);
            viewModel.insertWeatherData(getApplication());
            viewModel.insertWeatherData(weatherTable);
            viewModel.insertForcastData(forecastTable);
            viewModel.insertForecastSingleData(forecastSingleTable);

            /**
             * This is setting for setting the data into xml
             */
            binding.setForecastModel(foreCasteMain);
            binding.setWeatherModel(weatherMain);
            binding.setDate(time);
            binding.setDay(day);

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

            /**
             * On click of the forward arrow
             */
            binding.acivForward.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String city = binding.actvCity.getText().toString();
                    Intent intentResult = new Intent(ClimateShowActivity.this, NewAddressActivity.class);
                    intentResult.putExtra(Constant.INTENT_CITY_NAME, city);
                    startActivityForResult(intentResult, Constant.INTENT_RESULT_CODE);
                }
            });

            /**
             * onSwipe
             */
            binding.swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    weatherRefreshTask(binding.actvCity.getText().toString());
                }
            });

            /**
             * Take the data from database
             */
            CheckDataViewModel viewModelCheck = ViewModelProviders.of(this).get(CheckDataViewModel.class);
            viewModelCheck.getWeatherTableLiveData(getApplication());
            viewModelCheck.listLiveData.observe(this, new Observer<List<WeatherTable>>() {
                @Override
                public void onChanged(final List<WeatherTable> weatherTables) {
                    if (weatherTable != null) {
                        /**
                         * This is for background services
                         */
                        weatherContinoue();
                        Constant.UPDATELIVE.observe(ClimateShowActivity.this, new Observer<Boolean>() {
                            @Override
                            public void onChanged(Boolean aBoolean) {
                                if (aBoolean) {
                                    weatherRefreshTask(weatherTables.get(0).getCity());
                                }
                            }
                        });

                    }
                }
            });
        }

        /**
         * This is for the 2nd times when data is present in the dataBase
         */
        else if (Constant.START_VALUE == Constant.INTENT_CLIMATE_START_SCREEN_VALUE) {
            CheckDataViewModel viewModel = ViewModelProviders.of(this).get(CheckDataViewModel.class);
            viewModel.getWeatherTableLiveData(getApplication());
            viewModel.getForecastTableLiveData(getApplication());
            viewModel.getForecastSingleTableLiveData(getApplication());

            /**
             * This is observer is used for checking the weather table
             */
            viewModel.sendLiveData().observe(this, new Observer<List<WeatherTable>>() {
                @Override
                public void onChanged(List<WeatherTable> weatherTables) {
                    if (weatherTables != null) {

                        String cityname = weatherTables.get(0).getCity();
                        final WeatherMain weatherMain = new WeatherMain();
                        weatherMain.setName(weatherTables.get(0).getCity());

                        WeatherModel weatherModel = new WeatherModel();
                        weatherModel.setDescription(weatherTables.get(0).getDiscriptions());

                        WeatherClimateModel weatherClimateModel = new WeatherClimateModel();
                        weatherClimateModel.setTemp(weatherTables.get(0).getTemp());
                        weatherClimateModel.setTempMax(weatherTables.get(0).getMaxtemp());
                        weatherClimateModel.setTempMin(weatherTables.get(0).getMintemp());

                        binding.setWeatherModel(weatherMain);
                        binding.setWeather(weatherModel);
                        binding.setDay(weatherTables.get(0).getDay());
                        binding.setDate(weatherTables.get(0).getDate());

                        binding.setTemp(String.format(Constant.STRING_FORMATE,
                                BasicFunction.getCelcius(weatherClimateModel.getTemp())) + (char) 0x00B0);

                        binding.setTempMin(String.format(Constant.STRING_FORMATE,
                                BasicFunction.getCelcius(weatherClimateModel.getTempMin())) + (char) 0x00B0);

                        binding.setTempMax(String.format(Constant.STRING_FORMATE,
                                BasicFunction.getCelcius(weatherClimateModel.getTempMax())) + (char) 0x00B0);


                        Glide.with(ClimateShowActivity.this)
                                .load(Constant.PIC + weatherTables.get(0).getIcon() + Constant.FORMATE)
                                .placeholder(R.drawable.ic_cloud_computing).
                                into(binding.acivWeatherIcon);

                        /**
                         * On click of the forward arrow
                         */
                        binding.acivForward.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String cityName = binding.actvCity.getText().toString();
                                Intent newIntent = new Intent(ClimateShowActivity.this, NewAddressActivity.class);
                                newIntent.putExtra(Constant.INTENT_CITY_NAME, cityName);
                                startActivityForResult(newIntent, Constant.INTENT_RESULT_CODE);
                            }
                        });

                        /**
                         * This is for auto refresh
                         */
                        weatherContinoue();
                        Constant.UPDATELIVE.observe(ClimateShowActivity.this, new Observer<Boolean>() {
                            @Override
                            public void onChanged(Boolean aBoolean) {
                                if (aBoolean) {
                                    weatherRefreshTask(weatherMain.getName());
                                }
                            }
                        });
                    }
                }
            });

            /**
             * This is observer is used for checking in forecast table
             */
            viewModel.sendForecastLiveData().observe(this, new Observer<List<ForecastTable>>() {
                @Override
                public void onChanged(List<ForecastTable> forecastTables) {
                    if (forecastTables != null) {
                        ForecastAdapter adapter = new ForecastAdapter(ClimateShowActivity.this, (ArrayList<ForecastArrayTable>) forecastTables.get(0).getForecastArrayTables());
                        binding.recyclerView.setAdapter(adapter);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ClimateShowActivity.this, LinearLayoutManager.HORIZONTAL, false);
                        binding.recyclerView.setLayoutManager(linearLayoutManager);
                    }
                }
            });

            /**
             * This is the observer is used for checking the forecastsingle tables
             */
            viewModel.sendForecastSingleLiveData().observe(this, new Observer<List<ForecastSingleTable>>() {
                @Override
                public void onChanged(List<ForecastSingleTable> forecastSingleTables) {
                    if (forecastSingleTables != null) {
                        ForecastSingleAdapter forecastSingleAdapter = new ForecastSingleAdapter(forecastSingleTables);
                        binding.recyclerSingleView.setAdapter(forecastSingleAdapter);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ClimateShowActivity.this, RecyclerView.HORIZONTAL, false);
                        binding.recyclerSingleView.setLayoutManager(linearLayoutManager);
                    }
                }
            });

            binding.swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    weatherRefreshTask(binding.actvCity.getText().toString());
                }
            });
        }
    }

    /**
     * This is the function is used for refreshing the weather data continoue for every five min
     */
    private void weatherContinoue() {
        /**
         * Set job info
         */
        final ComponentName componentName = new ComponentName(this, WeatherService.class);
        JobInfo jobInfo = new JobInfo.Builder(Constant.JOB_NUMBER, componentName)
//                .setRequiresCharging(false)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                .setPersisted(true)
                .setPeriodic(15 * 60 * 1000)
                .build();

        /**
         * Now schedule the job according the task
         */
        JobScheduler scheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        int resultCode = scheduler.schedule(jobInfo);
        if (resultCode == JobScheduler.RESULT_SUCCESS) {
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        JobScheduler scheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        scheduler.cancel(Constant.JOB_NUMBER);
        Log.d("finshed", "finished");
    }

    /**
     * THis is the function is used to refresh the data of weather only on swipe
     */
    private void weatherRefreshTask(String cityName) {
        Places places = new Places();
        places.setPlace(cityName);
        AddressScreenViewModelFactory addressScreenViewModelFactory =
                new AddressScreenViewModelFactory(ClimateShowActivity.this, places);

        AddressScreenViewModel addressScreenViewModel = ViewModelProviders.of(this, addressScreenViewModelFactory).get(AddressScreenViewModel.class);
        addressScreenViewModel.getweather(cityName);
        addressScreenViewModel.getWeatherData().observe(this, new Observer<WeatherMain>() {
            @Override
            public void onChanged(WeatherMain weatherMain) {
                if (weatherMain != null) {
                    binding.swipeRefresh.setRefreshing(false);

                    binding.setWeatherModel(weatherMain);
                    binding.setWeather(weatherMain.getWeatherModels().get(0));

                    binding.setTemp(String.format(Constant.STRING_FORMATE,
                            BasicFunction.getCelcius(weatherMain.getWeatherClimateModel().getTemp())) + (char) 0x00B0);

                    binding.setTempMin(String.format(Constant.STRING_FORMATE,
                            BasicFunction.getCelcius(weatherMain.getWeatherClimateModel().getTempMin())) + (char) 0x00B0);

                    binding.setTempMax(String.format(Constant.STRING_FORMATE,
                            BasicFunction.getCelcius(weatherMain.getWeatherClimateModel().getTempMax())) + (char) 0x00B0);

                    Glide.with(ClimateShowActivity.this)
                            .load(Constant.PIC + weatherMain.getWeatherModels().get(0).getIcon() + Constant.FORMATE)
                            .placeholder(R.drawable.ic_cloud_computing).
                            into(binding.acivWeatherIcon);

                    Toast.makeText(ClimateShowActivity.this, "Weather data is refresh", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}
