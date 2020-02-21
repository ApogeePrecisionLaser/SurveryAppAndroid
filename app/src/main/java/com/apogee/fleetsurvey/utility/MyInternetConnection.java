package com.apogee.fleetsurvey.utility;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;


public class MyInternetConnection extends Application {
    private static MyInternetConnection mInstance;
    //    public  SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("SurveyApp", 0);
//    private SharedPreferences.Editor editor;
//    public static String KEY="locationdata";

    public static final String FILTER_ACTION_KEY = MyInternetConnection.class.getSimpleName()+"localreciever";
    public static final String BROADCASTMESSAGE = "";

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;

    }

    public static synchronized MyInternetConnection getInstance() {
        return mInstance;
    }



    @Override
    public void unregisterReceiver(BroadcastReceiver receiver) {
        super.unregisterReceiver(receiver);

    }



}
