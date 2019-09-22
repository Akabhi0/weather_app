
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
    private AddressFragment addressFragment;
    private CheckDataViewModel checkDataViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_starting);

        checkDataViewModel = ViewModelProviders.of(this).get(CheckDataViewModel.class);
        checkDataViewModel.getWeatherTableLiveData(getApplication()).observe(this, new Observer<List<WeatherTable>>() {
            @Override
            public void onChanged(List<WeatherTable> weatherTables) {
                switch (weatherTables.size()) {
                    case 0:
                        addressFragment = new AddressFragment();
                        Bundle bundle = new Bundle();
                        bundle.putInt(Constant.INTENT_STARTING_SCREEN, Constant.INTENT_STARTING_SCREEN_VALUE);
                        addressFragment.setArguments(bundle);
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, addressFragment).commit();
                        break;
                    default:
                        Intent intent = new Intent(StartingActivity.this, ClimateShowActivity.class);
                        intent.putExtra(Constant.INTENT_CLIMATE_START_SCREEN, Constant.INTENT_CLIMATE_START_SCREEN_VALUE);
                        startActivity(intent);
                }
            }
        });
    }
}
