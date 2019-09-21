package com.example.taskassignment.view;

import android.os.Bundle;
import android.widget.CompoundButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;

import com.example.taskassignment.BasicUtality.BasicFunction;
import com.example.taskassignment.BasicUtality.Constant;
import com.example.taskassignment.R;
import com.example.taskassignment.databinding.ActivityQuizActivtyBinding;
import com.example.taskassignment.databinding.ActivityQuizAndroidActivtyBinding;
import com.example.taskassignment.databinding.ActivityQuizJavaActivtyBinding;
import com.example.taskassignment.view.extraClass.CounterClass;

public class QuizActivty extends AppCompatActivity {

    private int QUIZE_ACTIVTY;
    private ActivityQuizActivtyBinding binding;
    private ActivityQuizAndroidActivtyBinding activityQuizAndroidActivtyBinding;
    private ActivityQuizJavaActivtyBinding activityQuizJavaActivtyBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        QUIZE_ACTIVTY = BasicFunction.getPassValueIntent(Constant.QUIZE_ACTIVTY, QuizActivty.this);
        switch (QUIZE_ACTIVTY) {
            case 0:
                binding = DataBindingUtil.setContentView(this, R.layout.activity_quiz_activty);
                setToolBar("Maths Quiz");

                binding.acbTimer.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {

                            binding.acbTimer.setTextOn("Cancel");
                            CounterClass counterClass = new CounterClass();
                            counterClass.startTimer();
                            counterClass.getTimeUpdate().observe(QuizActivty.this, new Observer<String>() {
                                @Override
                                public void onChanged(String s) {
                                    binding.setTimeUpdate(s);
                                }
                            });
                        } else {

                        }
                    }
                });
                break;
            case 1:
                activityQuizJavaActivtyBinding = DataBindingUtil.setContentView(this, R.layout.activity_quiz_java_activty);
                setToolBar("Java Quiz");
                activityQuizJavaActivtyBinding.acbTimer.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            activityQuizJavaActivtyBinding.acbTimer.setTextOn("Cancel");
                            CounterClass counterClass = new CounterClass();
                            counterClass.startTimer();
                            counterClass.getTimeUpdate().observe(QuizActivty.this, new Observer<String>() {
                                @Override
                                public void onChanged(String s) {
                                    activityQuizJavaActivtyBinding.setTimeUpdate(s);
                                }
                            });
                        } else {

                        }
                    }
                });
                break;
            case 2:
                activityQuizAndroidActivtyBinding = DataBindingUtil.setContentView(this, R.layout.activity_quiz_android_activty);
                setToolBar("Android Quiz");
                activityQuizAndroidActivtyBinding.acbTimer.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            activityQuizAndroidActivtyBinding.acbTimer.setTextOn("Cancel");
                            CounterClass counterClass = new CounterClass();
                            counterClass.startTimer();
                            counterClass.getTimeUpdate().observe(QuizActivty.this, new Observer<String>() {
                                @Override
                                public void onChanged(String s) {
                                    activityQuizAndroidActivtyBinding.setTimeUpdate(s);
                                }
                            });
                        } else {

                        }
                    }
                });
                break;
        }

    }

    public void setToolBar(String title) {
        switch (QUIZE_ACTIVTY) {
            case 0:
                binding.toolbar.setTitle(title);
                setSupportActionBar(binding.toolbar);
                break;
            case 1:
                activityQuizJavaActivtyBinding.toolbar.setTitle(title);
                setSupportActionBar(activityQuizJavaActivtyBinding.toolbar);
                break;
            case 2:
                activityQuizAndroidActivtyBinding.toolbar.setTitle(title);
                setSupportActionBar(activityQuizAndroidActivtyBinding.toolbar);
                break;
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
