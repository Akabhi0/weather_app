
package com.example.task.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.task.R;
import com.example.task.databinding.ActivityStartingBinding;

public class StartingActivity extends AppCompatActivity {

    private ActivityStartingBinding binding;
    private AddressFragment addressFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_starting);
        addressFragment = new AddressFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.container, addressFragment).commit();
    }
}
