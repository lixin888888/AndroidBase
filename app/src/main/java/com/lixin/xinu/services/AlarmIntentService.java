package com.lixin.xinu.services;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.util.Log;


public class AlarmIntentService extends IntentService {

    private final  String TAG = "AlarmIntentService";

    public AlarmIntentService() {
        super("AlarmIntentService");
    }



    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(TAG, "onHandleIntent: ");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: ");
    }

}
