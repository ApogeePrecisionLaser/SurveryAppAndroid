package com.apogee.fleetsurvey.Generic;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.FileProvider;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.apogee.fleetsurvey.Database.DatabaseOperation;
import com.apogee.fleetsurvey.HomeActivity;
import com.apogee.fleetsurvey.R;
import com.apogee.fleetsurvey.model.BleModel;
import com.apogee.fleetsurvey.utility.DeviceControlActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class GenericActivity extends AppCompatActivity {
    String title;
    String desc;
    String time;
    String whoareyou;
    String ip;
    String port;
    DeviceControlActivity dle = new DeviceControlActivity();
    /*Image part*/
    ImageButton camButton, AudioButton, ipimgbtn;
    FloatingActionButton fab1, fab2, fab3;
    String img = "";
    LinearLayout imagelayout;
    File mediaFile;
    String imagePath = "";
    private Uri fileUri;
    public File f;
    String path = "";
    public static final int MEDIA_TYPE_IMAGE = 1;
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    private static final int CAMERA_CAPTURE_VIDEO_REQUEST_CODE = 200;
    public static final int RequestPermissionCode = 1;
    boolean mBound = false;
    ArrayList<String> imagelist = new ArrayList<String>();
    private static final String IMAGE_DIRECTORY_NAME = "Generic";
    public static final int MEDIA_TYPE_VIDEO = 2;
    int counter = 1;
    File dir;
    int imgCount = 0;
    Date date = new Date();
    String stringDate = DateFormat.getDateInstance(DateFormat.LONG).format(date);
    String[] items1 = stringDate.split(" ");
    String date1 = items1[0];
    String month = items1[1];
    String year = items1[2];
    String Peripheral = "Image=image,Audio=audio,Lattitude=latitude,Longitude=longitude,CurrentDateAndTime=created_at,Status=status,Altitude=altitude,Accuracy=accuracy";
    String[] prphrl = Peripheral.split(",");
    String image = prphrl[0].split("=")[1];
    // String audio=prphrl[1].split("=")[1];
    String audio = null;
    String lat = prphrl[2].split("=")[1];
    String long1 = prphrl[3].split("=")[1];
    String cdt = prphrl[4].split("=")[1];
    String status = prphrl[5].split("=")[1];
    String alti = prphrl[6].split("=")[1];
    String accu = prphrl[7].split("=")[1];


    //    String[] img1 = image.split("=");
//    String img1i=img1[0];
//    String img2i=img1[1];
//    String audio=prphrl[1];
//    String[] ad = audio.split("=");
//    String ad1=ad[0];
//    String ad2=ad[1];
    /*Audio Part*/
    final int BUFFER_SIZE = 4096;
    ImageButton imageButton;
    MediaRecorder recorder;
    File audiofile = null;
    String file = "";
    String doctype = "";
    BleModel dbmodel = new BleModel(GenericActivity.this);
    AlertDialog alertDialog;
    Button btnStop;
    boolean isrecordStop = false, isAudiopopup = false;
    double longitude = 0.0;
    double latitude = 0.0;
    double altitude = 0.0;
    double accuracy = 0.0;
    ContentValues values = new ContentValues();
    /*Text Part*/
    private LinearLayout parentLinearLayout;
    public String[] subStrings1 = new String[60];
    String[] resultvalue = new String[70];
    String[] editextarray = new String[70];
    List<EditText> allEds = new ArrayList<EditText>();
    List<TextView> alltxt = new ArrayList<TextView>();
    String TABLE_NAME = "resetWork";
    int result = 0;
    EditText e1, e2, e3;
    private SQLiteDatabase database;
    String value = "";
    BleModel model = new BleModel(GenericActivity.this);
    Map<String, String> peripheralMap = new HashMap<>();
    private final static int ALL_PERMISSIONS_RESULT = 101;
    GPSTrack locationTrack;
    JSONObject json5 = new JSONObject();

    @SuppressLint({"WrongConstant", "RestrictedApi"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generic);
        parentLinearLayout = (LinearLayout) findViewById(R.id.parent_linear_layout);
        final Dialog dialog = new Dialog(GenericActivity.this);
        dialog.setContentView(R.layout.newcustom_layout);
        dialog.setCancelable(false);
        dialog.show();
        String lat_long = dle.lat_lang;
        Toast.makeText(this, lat_long, Toast.LENGTH_SHORT).show();
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        Button gpsButton = (Button) dialog.findViewById(R.id.btngps);
        // if GPS button is clicked, it send gps location
        gpsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(GenericActivity.this, "GPS Selection", Toast.LENGTH_SHORT).show();
                gpslocation();
                // Close dialog
                dialog.dismiss();
            }
        });
        Button rtkButton = (Button) dialog.findViewById(R.id.btnrtk);
        // if RTK button is clicked, it send RTK location That means accurate lattitude and longitude via DGPS.
        rtkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(GenericActivity.this, "RTK Selection", Toast.LENGTH_SHORT).show();
                String lat_long = dle.latlongvalue;
                rtklocation(lat_long);
                // Close dialog
                dialog.dismiss();
            }
        });

        fab1 = (FloatingActionButton) findViewById(R.id.image);
        fab2 = (FloatingActionButton) findViewById(R.id.audio);
        fab3 = (FloatingActionButton) findViewById(R.id.newdata);
        // long result1 = model.requestDBdetail();
        /*Image part*/
        //camButton=(ImageButton) findViewById(R.id.camButton);
        // AudioButton = (ImageButton) findViewById(R.id.imageButton);
        ipimgbtn = (ImageButton) findViewById(R.id.ipimg);

        ipimgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ipPortdialog();
            }
        });
        //camButton.setVisibility(View.GONE);
        fab1.setVisibility(View.GONE);
        //AudioButton.setVisibility(View.GONE);
        fab2.setVisibility(View.GONE);
        imagelayout = (LinearLayout) findViewById(R.id.imgLayout);
        Intent intent = getIntent();

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                title = null;
                desc = null;
                time = null;
            } else {
                title = extras.getString("Title");
                desc = extras.getString("Description");
                time = extras.getString("Date");
                whoareyou = extras.getString("whoareyou");
                try {
                    json5.put("Title", title);
                    json5.put("Description", desc);
                    json5.put("Date", time);
                    json5.put("person", whoareyou);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } else {
            title = (String) savedInstanceState.getSerializable("Title");
            desc = (String) savedInstanceState.getSerializable("Description");
            time = (String) savedInstanceState.getSerializable("Date");
            whoareyou = (String) savedInstanceState.getSerializable("whoareyou");
        }
        String json = intent.getStringExtra("Array");
        /*Database and Text PArt*/
        try {
            database = openOrCreateDatabase("GenericDB", SQLiteDatabase.CREATE_IF_NECESSARY, null);
            TABLE_NAME = "H_Block_Survey_Test";
            // String value=" create table IF NOT EXISTS Pipeline(id INTEGER PRIMARY KEY  AUTOINCREMENT ,name String  ,address String  ,img1 String  ,audio String  ,latitude INTEGER,longitude INTEGER,created_at STRING,status STRING NOT NULL DEFAULT 'Y');";
            // String value="create table IF NOT EXISTS Device(id INTEGER PRIMARY KEY  AUTOINCREMENT ,Manufacturer_Name String  ,Device_Type String  ,Model String  ,image String  ,audio String ,latitude INTEGER,longitude INTEGER,created_at STRING,status STRING NOT NULL DEFAULT 'Y');";
            // String value= "  create table IF NOT EXISTS meter_detail(id INTEGER PRIMARY KEY AUTOINCREMENT ,meter_no STRING ,meter_type STRING ,meter_reading STRING ,x_current STRING ,y_current STRING ,z_current STRING ,image STRING ,latitude INTEGER,longitude INTEGER,created_at STRING,status STRING NOT NULL DEFAULT 'Y');";
            // String value=" create table IF NOT EXISTS traffic_signal_pole(id INTEGER PRIMARY KEY AUTOINCREMENT ,junction_name STRING ,side_name STRING ,side_no STRING ,pole_type STRING ,position STRING ,primary_horizontal_aspect_number STRING ,primary_vertical_aspect_number STRING ,secondary_horizontal_aspect_number STRING ,secondary_vertical_aspect_number STRING ,vehicle_detection STRING ,count_down STRING ,number_of_lane STRING ,RLPD STRING ,ANPR STRING ,PA_System STRING ,image STRING ,latitude INTEGER,longitude INTEGER,altitude STRING,created_at STRING,status STRING NOT NULL DEFAULT 'Y');";
            String value = "create table IF NOT EXISTS H_Block_Survey_Test(id INTEGER PRIMARY KEY AUTOINCREMENT ,area_name STRING ,point_name STRING ,image STRING ,latitude INTEGER,longitude INTEGER,altitude INTEGER,accuracy INTEGER,created_at STRING,status STRING NOT NULL DEFAULT 'Y');";
            // String value="create table IF NOT EXISTS TubeWell_Survey(id INTEGER PRIMARY KEY AUTOINCREMENT ,ivrs STRING ,r STRING ,y STRING ,b STRING ,image STRING ,latitude INTEGER,longitude INTEGER,altitude INTEGER,created_at STRING,status STRING NOT NULL DEFAULT 'Y');";
            database.execSQL(value);
            int j1 = value.indexOf('(');
            int j2 = value.indexOf(')');
            String substr = value.substring(j1 + 1, j2);
            String[] substr1 = substr.split(",");
            String[][] subStrings = new String[substr1.length][];
            for (int i = 0; i < substr1.length; i++) {
                subStrings[i] = substr1[i].split(" ");
            }
            System.out.println(subStrings.length);
            String[] subStrings1 = new String[substr1.length];
            for (int i = 1; i < substr1.length; i++) {
                subStrings1[i] = subStrings[i][0];
                result = subStrings1.length;
                resultvalue[i] = subStrings1[i];
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        addvalue();
//        peripheralMap.put(image.split("=")[1],image.split("=")[0]);
//        peripheralMap.put(audio.split("=")[1],audio.split("=")[0]);
        if (audio != null) {
            fab2.setVisibility(View.VISIBLE);
//            AudioButton.setVisibility(View.VISIBLE);
        } else {
        }
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customAlert();
            }
        });
        if (image != null) {
            fab1.setVisibility(View.VISIBLE);
//            camButton.setVisibility(View.VISIBLE);
        } else {

        }

        fab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread t = new Thread() {

                    @Override
                    public void run() {
                        try {
                            while (!isInterrupted()) {
                                Thread.sleep(2000);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        insertcontinuertk();
                                    }
                                });
                            }
                        } catch (InterruptedException e) {
                        }
                    }
                };

                t.start();

            }
        });


        inserttaskdata();
    }

    public void inserttaskdata() {
        DatabaseOperation dbTask = new DatabaseOperation(GenericActivity.this);
        dbTask.open();
        boolean result = dbTask.insertToTable(title, desc, whoareyou, time, TABLE_NAME);
        if (result == true) {
            System.out.println("Data inserted");
        } else {
            System.out.println("Insertion Problem");
        }
        dbTask.close();
    }

    public void insertcontinuertk() {
        String lat_long = dle.lat_lang;
        double latitide;
        double longitude;
        double accuracyy = 0;
        if (lat_long != null) {
            String lati = lat_long.split("_")[0];
            String longi = lat_long.split("_")[1];
            String status = lat_long.split("_")[2];
            String accuracy = lat_long.split("_")[3];
            String fix = lat_long.split("_")[4];
            if (fix.equalsIgnoreCase("0")) {

            } else if (fix.equalsIgnoreCase("4")) {
                accuracyy = Double.parseDouble(accuracy) * 2;
            } else if (fix.equalsIgnoreCase("5")) {
                accuracyy = Double.parseDouble(accuracy) * 20;
            } else {
                accuracyy = Double.parseDouble(accuracy) * 250;
            }

            String latitude1 = lati;
            String arr1[] = latitude1.split("\\.");
            String beforePoint = arr1[0];
            String firsthalf = beforePoint.substring(0, 2);
            String secondhalf = beforePoint.substring(2, 4);
            String afterPoint = arr1[1];
            String finalSubString = (secondhalf + afterPoint);
            int value = (Integer.parseInt(finalSubString)) / 60;
            String afterMultiply = Integer.toString(value);
            String finalString = firsthalf + "." + afterMultiply;

            String longitude1 = longi;
            String arr2[] = longitude1.split("\\.");
            String beforePoint2 = arr2[0];
            String firsthalf2 = beforePoint2.substring(0, 3);
            String secondhalf2 = beforePoint2.substring(3, 5);
            String afterPoint2 = arr2[1];
            String finalSubString2 = (secondhalf2 + afterPoint2);
            int value2 = (Integer.parseInt(finalSubString2)) / 60;
            String afterMultiply2 = Integer.toString(value2);
            String finalString2 = firsthalf2 + "." + afterMultiply2;


            latitide = Double.parseDouble(finalString);
            longitude = Double.parseDouble(finalString2);

            DatabaseOperation dbTask = new DatabaseOperation(GenericActivity.this);
            dbTask.open();
            boolean result = dbTask.insertlocation(latitide, longitude, status, accuracyy);
            if (result == true) {
                System.out.println("Data inserted");
                Toast.makeText(this, "Data inserted", Toast.LENGTH_SHORT).show();
            } else {
                System.out.println("Insertion Problem");
            }
            dbTask.close();
        }
    }

    public void rtklocation(String text) {
        if (text != null) {
            Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
            String lati = text.split(",")[0];
            String longi = text.split(",")[1];
            String alti = text.split(",")[2];
            String accurac = text.split(",")[3];
            String fix = text.split(",")[4];
            if (fix.equalsIgnoreCase("0")) {

            } else if (fix.equalsIgnoreCase("4")) {
                accuracy = Double.parseDouble(accurac) * 2;
            } else if (fix.equalsIgnoreCase("5")) {
                accuracy = Double.parseDouble(accurac) * 20;
            } else {
                accuracy = Double.parseDouble(accurac) * 250;
            }

            String latitude1 = lati;
            String arr1[] = latitude1.split("\\.");
            String beforePoint = arr1[0];
            String firsthalf = beforePoint.substring(0, 2);
            String secondhalf = beforePoint.substring(2, 4);
            String afterPoint = arr1[1];
            String finalSubString = (secondhalf + afterPoint);
            int value = (Integer.parseInt(finalSubString)) / 60;
            String afterMultiply = Integer.toString(value);
            String finalString = firsthalf + "." + afterMultiply;

            String longitude1 = longi;
            String arr2[] = longitude1.split("\\.");
            String beforePoint2 = arr2[0];
            String firsthalf2 = beforePoint2.substring(0, 3);
            String secondhalf2 = beforePoint2.substring(3, 5);
            String afterPoint2 = arr2[1];
            String finalSubString2 = (secondhalf2 + afterPoint2);
            int value2 = (Integer.parseInt(finalSubString2)) / 60;
            String afterMultiply2 = Integer.toString(value2);
            String finalString2 = firsthalf2 + "." + afterMultiply2;


            latitude = Double.parseDouble(finalString);
            longitude = Double.parseDouble(finalString2);
            altitude = Double.parseDouble(alti);
        } else {
            rtkdialog();
        }
    }

    public void gpslocation() {
        locationTrack = new GPSTrack(GenericActivity.this);
        if (locationTrack.canGetLocation()) {
            longitude = locationTrack.getLongitude();
            latitude = locationTrack.getLatitude();
            altitude = locationTrack.getAltitude();
            accuracy = locationTrack.getAccuracy();

            Toast.makeText(getApplicationContext(), "Longitude:" + Double.toString(longitude) + "\nLatitude:" + Double.toString(latitude)
                    + "\nAccuracy:" + Double.toString(accuracy) + "\nAltitude:" + Double.toString(altitude), Toast.LENGTH_SHORT).show();
        } else {

            Toast.makeText(GenericActivity.this, "Gps class didn't get Location", Toast.LENGTH_SHORT).show();
        }
    }

    public void rtkdialog() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(GenericActivity.this);
        builder1.setTitle("RTK NULL!");
        builder1.setMessage("Configure RTK First");
        builder1.setCancelable(false);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(GenericActivity.this, HomeActivity.class);
                        startActivity(intent);
                        dialog.cancel();
                    }
                });

        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    public void SubmitButton(View view) {

        Callingserver cs = new Callingserver();
        cs.execute();
        insertdata();

    }

    public void addvalue() {
        //        final TextView[] myTextViews = new TextView[result]; // create an empty array;
        final EditText[] myEditViews = new EditText[result]; // create an empty array;

        for (int i = 1; i < result; i++) {
            // create a new textview
            // final TextView rowTextView = new TextView(this);
            final EditText rowEditView = new EditText(this);
            // LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
            // LinearLayout.LayoutParams.WRAP_CONTENT);
            // layoutParams.gravity = Gravity.LEFT;
            // layoutParams.setMargins(10, 10, 10, 10); // (left, top, right, bottom)
            // rowTextView.setLayoutParams(layoutParams);
            LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams1.gravity = Gravity.RIGHT;
            layoutParams1.setMargins(10, 10, 10, 10); // (left, top, right, bottom)
            rowEditView.setLayoutParams(layoutParams1);
//            rowTextView.setId(i);
//            rowTextView.setTextSize(20);
            rowEditView.setId(i);
            rowEditView.setHintTextColor(Color.parseColor("#FFFFFF"));
            rowEditView.setTextColor(Color.parseColor("#FFFFFF"));
//            rowTextView.setPadding(20,20,20,20);
//            rowEditView.setPadding(20,20,20,20);
            // set some properties of rowTextView or something
//            rowTextView.setText(resultvalue[i]);
            if (resultvalue[i].equalsIgnoreCase(image) || resultvalue[i].equalsIgnoreCase(audio) || resultvalue[i].equalsIgnoreCase(lat) || resultvalue[i].equalsIgnoreCase(long1) || resultvalue[i].equalsIgnoreCase(cdt) || resultvalue[i].equalsIgnoreCase(status) || resultvalue[i].equalsIgnoreCase(alti) || resultvalue[i].equalsIgnoreCase(accu)) {
                System.out.println("in equal .. " + resultvalue[i] + "_" + image);
            } else {
                if (resultvalue[i].contains("_")) {
                    int index = resultvalue[i].indexOf('_');
                    editextarray[i] = resultvalue[i].substring(0, index)
                            + " "
                            + resultvalue[i].substring(index + 1);
                    rowEditView.setHint("Values of  " + editextarray[i]);
                } else {
                    editextarray[i] = resultvalue[i];
                    rowEditView.setHint("Values of  " + editextarray[i]);
                }

                allEds.add(rowEditView);
                parentLinearLayout.addView(rowEditView);
                System.out.println(resultvalue[i] + "_" + image);
            }
//            if (resultvalue[i].equalsIgnoreCase(img2i)){
//                 break;
//            }
            // allEds.add(rowEditView);
//            alltxt.add(rowTextView);
//            // add the textview to the linearlayout
//            parentLinearLayout.addView(rowTextView);
            // save a reference to the textview for later
//            myTextViews[i] = rowTextView;
            myEditViews[i] = rowEditView;
        }
    }

    public void ipPortdialog() {
        final AlertDialog dialogBuilder = new AlertDialog.Builder(this).create();
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.ip_port_dialog, null);
        final SharedPreferences mSharedPreferences;

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.getBaseContext());
        ip = mSharedPreferences.getString("ip", "DEFAULT");
        port = mSharedPreferences.getString("port", "DEFAULT");

        final EditText editText = (EditText) dialogView.findViewById(R.id.etip);
        final EditText editText1 = (EditText) dialogView.findViewById(R.id.etport);

        Button button1 = (Button) dialogView.findViewById(R.id.save);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = mSharedPreferences.edit();
                editor.putString("ip", editText.getText().toString());
                editor.putString("port", editText1.getText().toString());
                editor.commit();
                Toast.makeText(GenericActivity.this, ip + "_" + port, Toast.LENGTH_SHORT).show();
                dialogBuilder.cancel();
            }
        });

        dialogBuilder.setView(dialogView);
        dialogBuilder.show();
    }

    private class Callingserver extends AsyncTask<String, String, String> {
        ProgressDialog dialog;

        @SuppressLint("WrongThread")
        @Override
        protected String doInBackground(String... params) {
            String result = "";
            JSONObject json1 = new JSONObject();
            JSONObject json2 = new JSONObject();
            JSONObject json3 = new JSONObject();
            JSONObject json4 = new JSONObject();
            JSONObject combined = new JSONObject();
            try {
                FileInputStream inputStream = null;
                if (audiofile != null) {
                    file = audiofile.getAbsolutePath();
                    File uploadaudioFile = new File(file);
                    inputStream = new FileInputStream(uploadaudioFile);

                    byte[] buffer = new byte[BUFFER_SIZE];
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();

                    for (int readNum; (readNum = inputStream.read(buffer)) != -1; ) {
                        bos.write(buffer, 0, readNum);
                    }
                    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                            Locale.getDefault()).format(new Date());
                    byte[] bytes = bos.toByteArray();
                    String encodedString = Base64.encodeToString(bytes, Base64.DEFAULT);
                    json3.put("audio", encodedString);
                    json3.put("fileName", timeStamp + ".mp3");

                } else {
                    json3.put("audio", "");
                    json3.put("fileName", "");
                }
                //jsonObject.put("complaint_id", params[0]);

                if (inputStream != null)
                    inputStream.close();

                // jsonObject1.put("img", img);

                File mediaStorageDir;
                if (Build.VERSION.SDK_INT > 23) {
                    mediaStorageDir = dir;
                } else {
                    mediaStorageDir = new File(Environment.getExternalStorageDirectory().getPath(), IMAGE_DIRECTORY_NAME + "/" + year + "/" + month + "/" + date1 + "/" + TABLE_NAME);
                }
                String stringTime = DateFormat.getTimeInstance(DateFormat.LONG).format(date);
                String newStr = stringTime.substring(0, stringTime.indexOf("G"));
                json1.put("TableName", TABLE_NAME);
                json1.put("currentDate", stringDate);
                json1.put("currentTime", newStr);

                try {
                    for (int i = 0; i < allEds.size(); i++) {
                        value = (allEds.get(i).getText().toString());
//            Toast.makeText(this, value, Toast.LENGTH_SHORT).show();
                        json1.put(resultvalue[i + 1], value);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                // File mediaStorageDir = new File(Environment.getExternalStorageDirectory().getPath(), IMAGE_DIRECTORY_NAME);

//                    File childfile[] = mediaStorageDir.listFiles();
//                    for (File file2 : childfile) {
                for (int i = 0; i < imagelist.size(); i++) {
                    // path = mediaStorageDir + "/" + file2.getName();
                    path = imagelist.get(i);
                    int count = i + 1;
                    //  json2 = uploadImg(mediaStorageDir + "/" + file2.getName());
                    json2.put("totalImg", count);
                    //  for (int i = 1; i <= imagelist.size(); i++) {
                    //   String path = (String) imagelist.get(i-1);
                    Bitmap bitmap = BitmapFactory.decodeFile(path);
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 90, stream);
                    byte[] byte_arr = stream.toByteArray();
                    //String img = "IMG_" + i;
                    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                            Locale.getDefault()).format(new Date());
                    String imgname = "IMG_" + "," + timeStamp + ".jpg";
                    String encodedString = Base64.encodeToString(byte_arr, Base64.DEFAULT);
                    json2.put("byte_arr" + count, encodedString);
                    json2.put("imgname" + count, imgname);

//                            i=i+1;
                }


//                        File file1 = new File(path);
//                        file1.delete();

//                        if (!result.equals(0)) {
////                        File file = new File(mediaStorageDir + "/" + file2.getName());
////                        file.delete();
//                        }
//                    }
                try {
                    insertdata();
                } catch (Exception e) {
                    e.printStackTrace();
                }


                json4.put("lattitude", latitude);
                json4.put("longitude", longitude);
                json4.put("altitude", altitude);
                json4.put("accuracy", accuracy);
                combined.put("text", json1);
                combined.put("image", json2);
                combined.put("audio", json3);
                combined.put("Location", json4);
                combined.put("task", json5);
                BleModel dbmodel = new BleModel(GenericActivity.this);
                result = dbmodel.sendData(combined, ip, port);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
// execution of result of Long time consuming operation
            if (result.equals("success")) {
                dialog.dismiss();
                Toast.makeText(GenericActivity.this, "Data Sent Successfully", Toast.LENGTH_SHORT).show();
                finish();
                startActivity(getIntent());

            } else {
                dialog.dismiss();
                Toast.makeText(GenericActivity.this, "Data Sending Fail", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected void onPreExecute() {
            dialog = ProgressDialog.show(GenericActivity.this, "", "Proccessing....Please wait");
            dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            dialog.setProgress(0);
            dialog.setMax(100);
            dialog.show();
            dialog.show();
// Things to be done before execution of long running operation. For
// example showing ProgessDialog
        }

        @Override
        protected void onProgressUpdate(String... text) {
            //firstBar.
            // Things to be done while execution of long running operation is in
            // progress. For example updating ProgessDialog
        }
    }

    public void insertdata() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentDateTime = dateFormat.format(new Date()); // Find todays date
        try {

            for (int i = 0; i < allEds.size(); i++) {
                value = (allEds.get(i).getText().toString());
                Toast.makeText(this, value, Toast.LENGTH_SHORT).show();
                values.put(resultvalue[i + 1], value);

            }


            for (int i = 0; i < 1; i++) {
                if (image != null) {
                    values.put(image, path);
                }
                if (audio != null) {
                    values.put(audio, path);
                }
                values.put(lat, latitude);
                values.put(long1, longitude);
                values.put(alti, altitude);
                values.put(accu, accuracy);
                values.put(cdt, currentDateTime);

            }
//            values.put("currentDateandTime", currentDateTime);
            if ((database.insert(TABLE_NAME, null, values)) != -1) {

                Toast.makeText(GenericActivity.this, "record Successfully Inserted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(GenericActivity.this, "Insert Error", Toast.LENGTH_LONG).show();
            }
//            rowEditView.setText("");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void showdata(View view) {
        int i = 0;
        Cursor c = database.rawQuery("select * from " + TABLE_NAME, null);
        int count = c.getCount();
        if (count == 0) {
            showMessage("Error", "Nothing found");

        }
        StringBuffer buffer = new StringBuffer();
        while (c.moveToNext()) {
            buffer.append("id" + "\t" + c.getString(0) + "\n");
            for (int j = 1; j < result; j++) {
                buffer.append(resultvalue[j] + "\t" + c.getString(j) + "\n");
            }

        }

        showMessage("Data", buffer.toString());
    }

    public Cursor getAllData() {
        Cursor res = database.rawQuery("select * from " + TABLE_NAME, null);
        return res;
    }

    public void showMessage(String title, String Message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();

    }


    /*Image Part*/

    public void imgbtn(View v) {
        captureImage();
    }


    private void selectImage() {


        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};


        AlertDialog.Builder builder = new AlertDialog.Builder(GenericActivity.this);

        builder.setTitle("Add Photo!");

        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override

            public void onClick(DialogInterface dialog, int item) {

                if (options[item].equals("Take Photo")) {

                    captureImage();


                } else if (options[item].equals("Choose from Gallery")) {

                    Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                    startActivityForResult(intent, 2);


                } else if (options[item].equals("Cancel")) {

                    dialog.dismiss();

                }

            }

        });

        builder.show();

    }

    private void captureImage() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (Build.VERSION.SDK_INT > 23) {
            File file = getCameraFile();
            imagePath = file.getPath();
            // fileUri = FileProvider.getUriForFile(GenericActivity.this, getApplicationContext().getPackageName() + ".provider", file);
            fileUri = FileProvider.getUriForFile(GenericActivity.this, getApplicationContext().getPackageName() + ".provider", file);

        } else {
            fileUri = Uri.fromFile(getOutputMediaFile(MEDIA_TYPE_IMAGE));
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
    }

    public File getCameraFile() {
        dir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        //  dir = new File(Environment.getExternalStorageDirectory() + "/DirName");

        String timeStamp = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.getDefault()).format(new Date());

        return new File(dir, "IMG_" + "" + timeStamp + ".jpg");
    }

    private File getOutputMediaFile(int type) {
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory().getPath(), IMAGE_DIRECTORY_NAME + "/" + year + "/" + month + "/" + date1 + "/" + TABLE_NAME);
        // File  mediaStorageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        //File mediaStorageDir = new File(Environment.getExternalStorageDirectory().toString());
        f = mediaStorageDir;
        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(IMAGE_DIRECTORY_NAME, "Oops! Failed create "
                        + IMAGE_DIRECTORY_NAME + " directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.getDefault()).format(new Date());

        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_" + "," + timeStamp + ".jpg");
        } else if (type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator + "VID_" + timeStamp + ".mp4");
        } else {
            return null;
        }
        imagePath = mediaFile.getAbsolutePath();
        return mediaFile;
        //return mediaStorageDir;
    }


    @SuppressLint("LongLogTag")
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // if the result is capturing Image
        super.onActivityResult(requestCode, resultCode, data);
        final ImageView imgview = new ImageView(this);
        if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                //viewImage();
                //viewImage1();
                imagelayout.setVisibility(View.VISIBLE);

                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 7;
                Bitmap bitmap = BitmapFactory.decodeFile(imagePath, options);
                imgview.setImageBitmap(bitmap);
                // Toast.makeText(getApplicationContext(),""+bitmap, Toast.LENGTH_SHORT).show();
                Toast.makeText(this, "Image Captured Successfully..", Toast.LENGTH_SHORT).show();
                imgview.setImageBitmap(bitmap);
                imgview.requestFocus();
                imgview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                });
                imagelayout.addView(imgview);
                imagelist.add(imagePath);

            } else if (resultCode == RESULT_CANCELED) {

                Toast.makeText(getApplicationContext(), "User cancelled image capture", Toast.LENGTH_SHORT).show();
                //showAlert("Image Cancel");
            } else {
                // failed to capture image
                Toast.makeText(getApplicationContext(), "Sorry! Failed to capture image", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == 2) {
            Uri selectedImage = data.getData();
            // h=1;
            //imgui = selectedImage;
            String[] filePath = {MediaStore.Images.Media.DATA};

            Cursor c = getContentResolver().query(selectedImage, filePath, null, null, null);

            c.moveToFirst();

            int columnIndex = c.getColumnIndex(filePath[0]);

            path = c.getString(columnIndex);

            c.close();

            Bitmap thumbnail = (BitmapFactory.decodeFile(path));


            Log.w("path of image from gallery......******************.........", path + "");
            imagelayout.setVisibility(View.VISIBLE);
            imgview.setImageBitmap(thumbnail);
            imagelayout.addView(imgview);
            imagelist.add(imagePath);


        }
    }


    public JSONObject uploadImg(String path) {
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.put("totalImg", counter);


            //  for (int i = 1; i <= imagelist.size(); i++) {
            //   String path = (String) imagelist.get(i-1);
            Bitmap bitmap = BitmapFactory.decodeFile(path);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, stream);
            byte[] byte_arr = stream.toByteArray();
            //String img = "IMG_" + i;
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                    Locale.getDefault()).format(new Date());
            String imgname = "IMG_" + "," + timeStamp + ".jpg";
            String encodedString = Base64.encodeToString(byte_arr, Base64.DEFAULT);
            jsonObject.put("byte_arr" + counter, encodedString);
            jsonObject.put("imgname" + counter, imgname);

            counter++;
            File file = new File(path);
            file.delete();


        } catch (Exception e) {
            Log.e("", e.getLocalizedMessage(), e);
        }
        return jsonObject;
    }

    /*Audio Part*/

    private class UploadFile extends AsyncTask<String, Void, String> {
        private ProgressDialog dialog;

        @Override
        protected String doInBackground(String... params) {
            String response = "";
            try {
                //GenericModel genericModel = new GenericModel(context);

                JSONObject jsonObject1 = new JSONObject(params[0]);
                FileInputStream inputStream = null;
                if (audiofile != null) {
                    String file = audiofile.getAbsolutePath();
                    File uploadaudioFile = new File(file);
                    inputStream = new FileInputStream(uploadaudioFile);

                    byte[] buffer = new byte[BUFFER_SIZE];
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();

                    for (int readNum; (readNum = inputStream.read(buffer)) != -1; ) {
                        bos.write(buffer, 0, readNum);
                    }
                    byte[] bytes = bos.toByteArray();
                    String encodedString = Base64.encodeToString(bytes, Base64.DEFAULT);
                    jsonObject1.put("audio", encodedString);
                    jsonObject1.put("fileName", doctype + ".mp3");

                } else {
                    jsonObject1.put("audio", "");
                    jsonObject1.put("fileNa" +
                            "me", "");
                }
                //jsonObject.put("complaint_id", params[0]);

                if (inputStream != null)
                    inputStream.close();

                response = dbmodel.sendData(jsonObject1, ip, port);


            } catch (SocketTimeoutException e) {
                Log.e("Debug", "error: " + e.getMessage(), e);
            } catch (MalformedURLException ex) {
                Log.e("Debug", "error: " + ex.getMessage(), ex);
            } catch (IOException ioe) {
                Log.e("Debug", "error: " + ioe.getMessage(), ioe);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            try {
                dialog.dismiss();
                if (result.equals("success")) {
                    showAlert("audio uploaded successfully");
                } else {
                    showAlert("audio not uploaded successfully");
                }

            } catch (Exception e) {
                System.out.println("err in webservice postexecute HelpActivity" + e);
            }
        }

        @Override
        protected void onPreExecute() {
            dialog = ProgressDialog.show(GenericActivity.this, "", "Proccessing....Please wait");
            dialog.show();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }


    }

    private void showAlert(String msg) {

        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        // Setting Dialog Title
        alertDialog.setTitle("Message");
        // Setting Dialog Message
        alertDialog.setMessage(msg);
        alertDialog.setButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        // Showing Alert Message
        alertDialog.show();

    }

    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /* Checks if external storage is available to at least read */
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }

    public void customAlert() {
        LayoutInflater li = LayoutInflater.from(GenericActivity.this);
        View promptsView = li.inflate(R.layout.audiorecord_layout, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(GenericActivity.this);
        alertDialogBuilder.setCancelable(false);

        alertDialogBuilder.setView(promptsView);
        ImageButton btnspeak = (ImageButton) promptsView.findViewById(R.id.btnSpeak);
        final TextView Tm = (TextView) promptsView.findViewById(R.id.timer);
        final Button btnStop = (Button) promptsView.findViewById(R.id.btnStop);
        final Button btnClose = (Button) promptsView.findViewById(R.id.btnSpeak1);
        try {
            boolean isExtStorwrite = isExternalStorageWritable();
            boolean isExtStorread = isExternalStorageReadable();
            if (!isExtStorwrite || !isExtStorread) {
                Log.e("", "sdcard access error");
                Toast.makeText(GenericActivity.this, "SD Card not readable", Toast.LENGTH_LONG).show();
                return;
            }
            // File sampleDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC+"/"+year+"/"+month+"/"+date1+"/"+TABLE_NAME);
            File sampleDir = new File(Environment.getExternalStorageDirectory().getPath(), IMAGE_DIRECTORY_NAME + "/" + year + "/" + month + "/" + date1 + "/" + TABLE_NAME);

            try {
                if (!sampleDir.exists()) {
                    if (!sampleDir.mkdirs()) {
                        Toast.makeText(GenericActivity.this, "Cannot create folder in Sd card ", Toast.LENGTH_LONG).show();
                    }
                }
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                        Locale.getDefault()).format(new Date());
                audiofile = File.createTempFile(timeStamp, ".mp3", sampleDir);
            } catch (IOException e) {
                Log.e("", "sdcard access error");
                try {
                    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                            Locale.getDefault()).format(new Date());
                    sampleDir = getFilesDir();
                    audiofile = File.createTempFile(timeStamp, ".mp3", sampleDir);
                } catch (Exception ex) {
                    Log.e("", "sdcard access error" + ex);
                    return;
                }
                // Toast.makeText(context,"SD Card not readable",Toast.LENGTH_LONG).show();

            }
            recorder = new MediaRecorder();
            recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            recorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
            recorder.setOutputFile(audiofile.getAbsolutePath());

            recorder.prepare();

            alertDialog = alertDialogBuilder.create();
            btnspeak.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new CountDownTimer(300000, 1000) {
                        public void onTick(long millisUntilFinished) {
                            Tm.setText("" + String.format("%d min, %d sec",
                                    TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                                    TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
                        }

                        public void onFinish() {
                            Tm.setText("done!");
                        }
                    }.start();
                    btnStop.setVisibility(View.VISIBLE);
                    isAudiopopup = true;
                    btnStop.setEnabled(true);
                    try {
                        recorder.start();
                    } catch (Exception e) {
                        System.out.print("error in stating recorder" + e);
                    }
                    new Timer().schedule(new TimerTask() {

                        @Override
                        public void run() {
                            runOnUiThread(new Runnable() {

                                @Override
                                public void run() {
                                    try {
                                        if (!isrecordStop) { //if recording is already stopped by clicking on stop then no need to run this code
                                            //stopping recorder
                                            recorder.stop();
                                            recorder.release();

                                            //after stopping the recorder, create the sound file and add it to media library.
                                            addRecordingToMediaLibrary();
                                            isAudiopopup = false;
                                            alertDialog.cancel();
                                        } else {////if recording is already stopped by clicking on stop then again reinitalize the variable for new recording
                                            isrecordStop = false;
                                        }

                                    } catch (Exception e) {
                                        System.out.println("error" + e);
                                    }

                                }
                            });

                        }
                    }, 60000);
                }
            });

            btnClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    alertDialog.cancel();
                }
            });
            btnStop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        isrecordStop = true;
                        recorder.stop();
                        recorder.release();
                        addRecordingToMediaLibrary();
                        isAudiopopup = false;
                        alertDialog.cancel();
//                        JSONObject jsonObject1 = new JSONObject();
//                        try {
//                            String stringDate = DateFormat.getDateInstance(DateFormat.LONG).format(date);
//
//                            jsonObject1.put("TableName","Abhi");
//                            jsonObject1.put("currentDate",stringDate);
//
//
//
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                        MainActivity.UploadFile comWebservice = new MainActivity.UploadFile();
//                        comWebservice.execute(jsonObject1.toString());

                    } catch (Exception e) {
                        System.out.println("error in btnStop listner" + e);
                    }
                }
            });

            alertDialog.show();
        } catch (Exception e) {
            System.out.print("err in recording view " + e);
        }
    }

    protected void addRecordingToMediaLibrary() {
        try {
            //creating content values of size 4
            ContentValues values = new ContentValues(4);
            long current = System.currentTimeMillis();
            values.put(MediaStore.Audio.Media.TITLE, "audio" + audiofile.getName());
            // values.put(MediaStore.Audio.Media.DATE_ADDED, (int) (current / 1000));
            values.put(MediaStore.Audio.Media.MIME_TYPE, "audio/mp3");
            values.put(MediaStore.Audio.Media.DATA, audiofile.getAbsolutePath());

            //creating content resolver and storing it in the external content uri
            ContentResolver contentResolver = getContentResolver();
            Uri base = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
            Uri newUri = contentResolver.insert(base, values);

            //sending broadcast message to scan the media file so that it can be available
            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, newUri));
            Toast.makeText(this, "Audio Recorded " + audiofile.getName(), Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            System.out.println("error in addRecordingToMediaLibrary" + e);
        }
    }


    @Override
    public void onBackPressed() {
        if (!isAudiopopup) {
            super.onBackPressed();
        } else {
            Toast.makeText(getApplicationContext(), "Audio is recording.Please click on stop recording", Toast.LENGTH_SHORT).show();
        }
    }

//    public void SubmitButton(View view){
//        String nm = e1.getText().toString();
//        String plc = e2.getText().toString();
//        String lttude = e3.getText().toString();
//
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String currentDateTime = dateFormat.format(new Date()); // Find todays date
//
//        ContentValues values=new ContentValues();
//        values.put("name", nm);
//        values.put("place", plc);
//        values.put("city", lttude);
//        values.put("currentDateandTime", currentDateTime);
//        if((database.insert("student", null, values))!=-1)
//        {
//
//            Toast.makeText(MainActivity.this, "record Successfully Inserted", Toast.LENGTH_SHORT).show();
//        }
//        else
//        {
//            Toast.makeText(MainActivity.this, "Insert Error", Toast.LENGTH_LONG).show();
//        }
//        e1.setText("");
//        e2.setText("");
//        e3.setText("");
//
//        Cursor c=database.rawQuery("SELECT * FROM student",null);
//        c.moveToFirst();
//        while(!c.isAfterLast())
//        {
//            Toast.makeText(MainActivity.this,c.getString(0)+ " " +
//                    ""+c.getString(1)+
//                    ""+c.getString(2)+
//                    ""+c.getString(3)+
//                    ""+c.getString(7)
//                    ,Toast.LENGTH_LONG).show();
//            c.moveToNext();
//        }
//        c.close();
//
//     }


}


