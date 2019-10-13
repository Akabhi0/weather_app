package com.example.task.services;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.example.task.BasicUtality.Constant;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class WeatherService extends JobService {
    private static final String TAG = "ExampleJobService";
    private boolean jobCancelled = false;

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.d(TAG, "Job started");
        doBackgroundWork(params);
        return true;
    }

    private void doBackgroundWork(final JobParameters params) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    if (jobCancelled) {
                        return;
                    }
                    /**
                     * This is the function which is used to retrun the livedata
                     */
                    doWorkTask();
                    try {
                        Thread.sleep(300000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
//                    Log.d(TAG, "Job finished");
                    jobFinished(params, true);
                }
            }
        }).start();
    }

    private void doWorkTask() {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Constant.UPDATELIVE.setValue(true);
//                Log.d(TAG, "Job chekc");
            }
        });
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.d(TAG, "Job cancelled before completion");
        jobCancelled = true;
        return true;
    }
}
