package com.apogee.fleetsurvey.utility;

import android.app.Application;

public class MyInternetConnection extends Application {
    private static MyInternetConnection mInstance;
//    public  SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("SurveyApp", 0);
//    private SharedPreferences.Editor editor;
//    public static String KEY="locationdata";

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
//        editor = sharedPreferences.edit();
    }

    public static synchronized MyInternetConnection getInstance() {
        return mInstance;
    }


//    public void setString(String value) {
//        editor.putString(KEY,value);
//        editor.commit();
//    }
//
//
//    public String getString(){
//       return sharedPreferences.getString(KEY,"");
//    }
    public void setConnectivityListener(ConnectivityReciever.ConnectivityReceiverListener listener) {
        ConnectivityReciever.connectivityReceiverListener = listener;
    }
}
