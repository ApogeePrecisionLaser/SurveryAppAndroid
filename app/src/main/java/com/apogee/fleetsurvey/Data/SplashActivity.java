package com.apogee.fleetsurvey.Data;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.apogee.fleetsurvey.Connect;
import com.apogee.fleetsurvey.R;
import com.apogee.fleetsurvey.model.BleModel;
import com.apogee.fleetsurvey.utility.BLEService;

import dmax.dialog.SpotsDialog;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.ACCESS_NETWORK_STATE;
import static android.Manifest.permission.CALL_PHONE;
import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.READ_PHONE_STATE;
import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class SplashActivity extends Activity {
    public static final int RequestPermissionCode = 7;
    Button btn;
    long result1;
    AlertDialog spotsDialog;
    ResultReceiver resultReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spalsh);
        spotsDialog = new SpotsDialog.Builder().setContext(this).build();
      //  spotsDialog.show();
        btn = (Button) findViewById(R.id.btn_letstart);
        final BleModel model = new BleModel(SplashActivity.this);

//        AsyncTask.execute(new Runnable() {
//            @Override
//            public void run() {
//                //  result1 = model.requestDatum();
//
//            }
//        });

        Intent intentService = new Intent(SplashActivity.this, BLEService.class);
        resultReceiver=new MyResultReceiver(new Handler());
        intentService.putExtra("receiver", resultReceiver);
        startService(intentService);
//        ActionBar actionBar = getSupportActionBar();
//        actionBar.hide();
        RequestMultiplePermission();
        CheckingPermissionIsEnabledOrNot();
////        BleModel model = new BleModel(SplashActivity.this);
////        long result = model.requestBleDetail();
        isNetworkConnectionAvailable();
//

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(SplashActivity.this, Connect.class);
                startActivity(i);
                finish();
            }
        });

    }

    public void checkNetworkConnection() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.noconnection);
        builder.setTitle("No internet Connection");
        builder.setMessage("Please turn on internet connection to continue");
        builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public boolean isNetworkConnectionAvailable() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnected();
        if (isConnected) {
            Log.d("Network", "Connected");
            return true;
        } else {
            checkNetworkConnection();
            Log.d("Network", "Not Connected");
            return false;
        }
    }

    //Permission function starts from here
    private void RequestMultiplePermission() {

        // Creating String Array with Permissions.
        ActivityCompat.requestPermissions(SplashActivity.this, new String[]
                {
                        READ_PHONE_STATE,
                        ACCESS_FINE_LOCATION,
                        READ_EXTERNAL_STORAGE,
                        WRITE_EXTERNAL_STORAGE,
                        CALL_PHONE,
                        ACCESS_COARSE_LOCATION,
                        CAMERA,
                        ACCESS_NETWORK_STATE,
                        RECORD_AUDIO

                }, RequestPermissionCode);

    }

    // Calling override method.
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {

            case RequestPermissionCode:

                if (grantResults.length > 0) {

                    boolean PhoneStatePermission = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean LocationPermission = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    boolean RExternalStoragePermission = grantResults[2] == PackageManager.PERMISSION_GRANTED;
                    boolean WExternalStoragePermission = grantResults[3] == PackageManager.PERMISSION_GRANTED;
                    boolean PhoneCallPermission = grantResults[4] == PackageManager.PERMISSION_GRANTED;
                    boolean Location2Permission = grantResults[5] == PackageManager.PERMISSION_GRANTED;
                    boolean CameraPermission = grantResults[6] == PackageManager.PERMISSION_GRANTED;
                    boolean ntwrkstatePermission = grantResults[7] == PackageManager.PERMISSION_GRANTED;
                    boolean recordaudioPermission = grantResults[8] == PackageManager.PERMISSION_GRANTED;

                    if (PhoneStatePermission && LocationPermission && RExternalStoragePermission && WExternalStoragePermission && PhoneCallPermission && Location2Permission && CameraPermission && ntwrkstatePermission && recordaudioPermission) {
                        Toast.makeText(SplashActivity.this, "Permission Granted", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(SplashActivity.this, "Permission Denied", Toast.LENGTH_LONG).show();

                    }
                }

                break;
        }
    }

    //  Checking permission is enabled or not using function starts from here.
    public boolean CheckingPermissionIsEnabledOrNot() {

        int FirstPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), READ_PHONE_STATE);
        int SecondPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), ACCESS_FINE_LOCATION);
        int ThirdPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE);
        int ForthPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        int FifthPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), CALL_PHONE);
        int SixthPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), ACCESS_COARSE_LOCATION);
        int SeventhPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), CAMERA);
        int eightPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), ACCESS_NETWORK_STATE);
        int ninePermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), RECORD_AUDIO);

        return FirstPermissionResult == PackageManager.PERMISSION_GRANTED &&
                SecondPermissionResult == PackageManager.PERMISSION_GRANTED &&
                ThirdPermissionResult == PackageManager.PERMISSION_GRANTED &&
                ForthPermissionResult == PackageManager.PERMISSION_GRANTED &&
                FifthPermissionResult == PackageManager.PERMISSION_GRANTED &&
                SixthPermissionResult == PackageManager.PERMISSION_GRANTED &&
                SeventhPermissionResult == PackageManager.PERMISSION_GRANTED &&
                eightPermissionResult == PackageManager.PERMISSION_GRANTED &&
                ninePermissionResult == PackageManager.PERMISSION_GRANTED;
    }



    private class MyResultReceiver extends ResultReceiver {

        /**
         * Create a new ResultReceive to receive results.  Your
         * {@link #onReceiveResult} method will be called from the thread running
         * <var>handler</var> if given, or from an arbitrary thread if null.
         *
         * @param handler
         */
        public MyResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            super.onReceiveResult(resultCode, resultData);

            spotsDialog.dismiss();
        }
    }
//    @Override
//    public void OnLoadDataFinish() {
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                spotsDialog.dismiss();
//                Toast.makeText(getBaseContext(),"Completed",Toast.LENGTH_LONG).show();
//            }
//        });
//    }
}
