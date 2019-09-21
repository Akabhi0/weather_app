package com.example.taskassignment.view.extraClass;

import android.os.CountDownTimer;

import androidx.lifecycle.MutableLiveData;

public class CounterClass {
    private CountDownTimer countDownTimer;
    private long timeLeftInMiliSecond = 300000;
    public MutableLiveData<String> timeUpdate = new MutableLiveData<>();

    public MutableLiveData<String> getTimeUpdate() {
        return timeUpdate;
    }

    public void setTimeUpdate(String timeUpdate) {
        this.timeUpdate.setValue(timeUpdate);
    }


    public void startTimer() {
        countDownTimer = new CountDownTimer(timeLeftInMiliSecond, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMiliSecond = millisUntilFinished;
                updateTimeText();
            }

            @Override
            public void onFinish() {

            }
        }.start();
    }

    public void updateTimeText() {
        int min = (int) (timeLeftInMiliSecond / 60000);
        int sec = (int) timeLeftInMiliSecond % 60000 / 1000;

        String timeLeft;
        timeLeft = " " + min;
        timeLeft += ":";
        if (sec < 10)
            timeLeft += 0;
        timeLeft += sec;
        setTimeUpdate(timeLeft);

    }

}

