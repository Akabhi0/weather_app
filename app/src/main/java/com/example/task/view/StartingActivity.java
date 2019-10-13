
package com.example.task.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.task.BasicUtality.Constant;
import com.example.task.R;
import com.example.task.dataBase.tables.WeatherTable;
import com.example.task.databinding.ActivityStartingBinding;
import com.example.task.viewModel.CheckDataViewModel;

import java.util.List;

public class StartingActivity extends AppCompatActivity {

    private ActivityStartingBinding binding;
    private CheckDataViewModel checkDataViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_starting);

        checkDataViewModel = ViewModelProviders.of(this).get(CheckDataViewModel.class);
        checkDataViewModel.getWeatherTableLiveData(getApplication());
        checkDataViewModel.sendLiveData().observe(this, new Observer<List<WeatherTable>>() {
            @Override
            public void onChanged(List<WeatherTable> weatherTables) {
                if (weatherTables.size() == 0) {
                    checkDataViewModel.sendLiveData().removeObserver(this);
                    getSupportFragmentManager()
                            .beginTransaction().replace(R.id.container, new AddressFragment())
                            .addToBackStack(null)
                            .commit();
                } else if (weatherTables.size() > 0) {
                    checkDataViewModel.sendLiveData().removeObserver(this);
                    Intent intent = new Intent(StartingActivity.this, ClimateShowActivity.class);
                    intent.putExtra(Constant.INTENT_CLIMATE_START_SCREEN, Constant.INTENT_CLIMATE_START_SCREEN_VALUE);
                    startActivity(intent);
                    finish();
                }
            }
        });

    }
}
