package com.example.taskassignment.view;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.taskassignment.BasicUtality.BasicFunction;
import com.example.taskassignment.BasicUtality.Constant;
import com.example.taskassignment.R;
import com.example.taskassignment.databinding.ActivityMainBinding;
import com.example.taskassignment.model.UserModel;
import com.example.taskassignment.viewModel.MainActivtyViewModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private MainActivtyViewModel viewModel;
    private DatabaseReference mFirebaseDatabase;
    private String otp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        viewModel = ViewModelProviders.of(this).get(MainActivtyViewModel.class);
        binding.setViewModel(viewModel);

        try {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 101);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        /**
         * On clicking on registration button
         */
        viewModel.getOnClickRegisterButton().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    DialogFragment dialogFragment = new DialogFragment();
                    dialogFragment.onRegister(getResources().getString(R.string.register_title));
                    dialogFragment.show(getSupportFragmentManager(), Constant.REGISTER_FORM);
                }
            }
        });


        /**
         * On clicking on the login button
         */
        viewModel.getOnClickLogin().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    otp = binding.acetOtpOne.getText().toString() + binding.acetOtpTwo.getText().toString() + binding.acetOtpThree.getText().toString() + binding.acetOtpFour.getText().toString();

                    if (TextUtils.isEmpty(binding.getMobileNumber())) {
                        BasicFunction.getToastMessage("Enter mobile number", MainActivity.this);
                    } else if (binding.getMobileNumber().length() != 10) {
                        BasicFunction.getToastMessage("Enter valid mobile number", MainActivity.this);
                    } else if (TextUtils.isEmpty(binding.acetOtpOne.getText().toString())) {
                        BasicFunction.getToastMessage("Enter otp", MainActivity.this);
                    } else if (TextUtils.isEmpty(binding.acetOtpTwo.getText().toString())) {
                        BasicFunction.getToastMessage("Enter otp", MainActivity.this);
                    } else if (TextUtils.isEmpty(binding.acetOtpThree.getText().toString())) {
                        BasicFunction.getToastMessage("Enter otp", MainActivity.this);
                    } else if (TextUtils.isEmpty(binding.acetOtpFour.getText().toString())) {
                        BasicFunction.getToastMessage("Enter otp", MainActivity.this);
                    } else {
                        mFirebaseDatabase = FirebaseDatabase.getInstance().getReference().child("users");
                        mFirebaseDatabase.orderByChild("mobileNumber").equalTo(binding.getMobileNumber()).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                        UserModel userModel = new UserModel();
                                        userModel.setAddress(dataSnapshot1.child("address").getValue().toString());
                                        userModel.setDateOfBirth(dataSnapshot1.child("dateOfBirth").getValue().toString());
                                        userModel.setDateOfBirth(dataSnapshot1.child("mobileNumber").getValue().toString());
                                        userModel.setName(dataSnapshot1.child("name").getValue().toString());
                                        userModel.setEmail(dataSnapshot1.child("email").getValue().toString());
                                        startActivity(BasicFunction.passValueObjectIntent(MainActivity.this, DashboardActivity.class, Constant.PASS_INTENT_OBJECT_VALUE, userModel));
                                    }
                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                }
            }
        });
    }
}
