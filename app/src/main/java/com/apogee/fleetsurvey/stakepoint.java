package com.apogee.fleetsurvey;

import android.app.AlertDialog;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.apogee.fleetsurvey.Generic.GPSTrack;
import com.apogee.fleetsurvey.utility.DeviceControlActivity;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.OnDataPointTapListener;
import com.jjoe64.graphview.series.PointsGraphSeries;
import com.jjoe64.graphview.series.Series;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class stakepoint extends AppCompatActivity implements SensorEventListener {
    private static final String TAG = "MainActivity";

    //add PointsGraphSeries of DataPoint type
    PointsGraphSeries<DataPoint> xySeries;
    LineGraphSeries<DataPoint> lineSeries;

    private Button btnAddPt;
    DeviceControlActivity dle = new DeviceControlActivity();

    int threadtime;
    private ImageView image;

    // record the compass picture angle turned
    private float currentDegree = 0f;

    // device sensor manager
    private SensorManager mSensorManager;

    TextView tvHeading;
    TextView distance;

    // private EditText mX,mY;

    GraphView mScatterPlot;
//    double x=1,y=2;


    //make xyValueArray global
    private ArrayList<XYValue> xyValueArray;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stakepoint);
        // our compass image
        image = (ImageView) findViewById(R.id.imageViewCompass);

        // TextView that will tell the user what degree is he heading
        tvHeading = (TextView) findViewById(R.id.tvHeading);
        distance = (TextView) findViewById(R.id.distance);

        // initialize your android device sensor capabilities
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        //declare variables in oncreate
        btnAddPt = (Button) findViewById(R.id.btnAddPt);
        // mX = (EditText) findViewById(R.id.numX);
        // mY = (EditText) findViewById(R.id.numY);
        mScatterPlot = (GraphView) findViewById(R.id.scatterPlot);
        mScatterPlot.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));

        xyValueArray = new ArrayList<>();

        mScatterPlot.getGridLabelRenderer().setGridStyle(GridLabelRenderer.GridStyle.NONE);
        mScatterPlot.getViewport().setDrawBorder(true);

        String lat_long = dle.lat_lang;

//         lineSeries = new LineGraphSeries<>(new DataPoint[] {
//
//
//                  new DataPoint(2, 3),
//
////                 new DataPoint(6, 7),
////                 new DataPoint(7, 8),
////                 new DataPoint(8, 9),
//
//        });
//        mScatterPlot.addSeries(lineSeries);
//        lineSeries.setColor(Color.MAGENTA);
//        lineSeries.setAnimated(true);
//        xyValueArray.add(new XYValue(50,90));
//        xyValueArray.add(new XYValue(40,80));
//        xyValueArray.add(new XYValue(60,80));
//        xyValueArray.add(new XYValue(70,40));
//        xyValueArray.add(new XYValue(20,30));
//        xyValueArray.add(new XYValue(34,30));
//        xyValueArray.add(new XYValue(78,20));
//        xyValueArray.add(new XYValue(23,30));
//        xyValueArray.add(new XYValue(54,70));
//        xyValueArray.add(new XYValue(87,88));
//        xyValueArray.add(new XYValue(90,22));
//        xyValueArray.add(new XYValue(78,34));

        PointsGraphSeries<DataPoint> series4 = new PointsGraphSeries<>(new DataPoint[] {
                new DataPoint(28.627236, 77.377047),

        });
        mScatterPlot.addSeries(series4);
        series4.setColor(Color.GREEN);
        series4.setCustomShape(new PointsGraphSeries.CustomShape() {
            @Override
            public void draw(Canvas canvas, Paint paint, float x, float y, DataPointInterface dataPoint) {
                paint.setStrokeWidth(10);
                canvas.drawLine(x-20, y-20, x+20, y+20, paint);
                canvas.drawLine(x+20, y-20, x-20, y+20, paint);
            }
        });

        PointsGraphSeries<DataPoint> series5 = new PointsGraphSeries<>(new DataPoint[] {
                new DataPoint(28.627860,77.377103)

        });
        mScatterPlot.addSeries(series5);
        series5.setColor(Color.RED);
        series5.setCustomShape(new PointsGraphSeries.CustomShape() {
            @Override
            public void draw(Canvas canvas, Paint paint, float x, float y, DataPointInterface dataPoint) {
                paint.setStrokeWidth(10);
                canvas.drawLine(x-20, y-20, x+20, y+20, paint);
                canvas.drawLine(x+20, y-20, x-20, y+20, paint);
            }
        });

        GPSTrack gpsTrack = new GPSTrack(stakepoint.this);
        double latitude = gpsTrack.getLatitude();
        double longitude= gpsTrack.getLongitude();
//        String val=editText.getText().toString();
//        if(val!=null){
//            threadtime=Integer.parseInt(val)*1000;
//        }
//
//      xyValueArray.add(new XYValue(latitude, longitude));
        xyValueArray.add(new XYValue(28.62709,77.37765));
        xyValueArray.add(new XYValue(28.62708,77.37766));
        xyValueArray.add(new XYValue(28.62708,77.37768));
        xyValueArray.add(new XYValue(28.62708,77.37771));
        xyValueArray.add(new XYValue(28.62707,77.37773));
        xyValueArray.add(new XYValue(28.62707,77.37775));
        xyValueArray.add(new XYValue(28.62707,77.37778));
        xyValueArray.add(new XYValue(28.62707,77.3778	));
        xyValueArray.add(new XYValue(28.62707,77.37782));
        xyValueArray.add(new XYValue(28.62707,77.37784));
        xyValueArray.add(new XYValue(28.62706,77.37787));
        xyValueArray.add(new XYValue(28.62706,77.37789));
        xyValueArray.add(new XYValue(28.62706,77.37791));
        xyValueArray.add(new XYValue(28.62706,77.37794));
        xyValueArray.add(new XYValue(28.62706,77.37796));
        xyValueArray.add(new XYValue(28.62705,77.37799));
        xyValueArray.add(new XYValue(28.62705,77.37801));
        xyValueArray.add(new XYValue(28.62705,77.37804));
        xyValueArray.add(new XYValue(28.62705,77.37806));
        xyValueArray.add(new XYValue(28.62705,77.37808));
        xyValueArray.add(new XYValue(28.62705,77.37811));
        xyValueArray.add(new XYValue(28.62704,77.37813));
        xyValueArray.add(new XYValue(28.62704,77.37816));
        xyValueArray.add(new XYValue(28.62704,77.37818));
        xyValueArray.add(new XYValue(28.62704,77.37821));
        xyValueArray.add(new XYValue(28.62704,77.37823));
        xyValueArray.add(new XYValue(28.62704,77.37826));
        xyValueArray.add(new XYValue(28.62705,77.37828));
        xyValueArray.add(new XYValue(28.62707,77.37828));
        xyValueArray.add(new XYValue(28.6271	,77.37828));
        xyValueArray.add(new XYValue(28.62712,77.37829));
        xyValueArray.add(new XYValue(28.62714,77.37829));
        xyValueArray.add(new XYValue(28.62716,77.37829));
        xyValueArray.add(new XYValue(28.62718,77.37829));
        xyValueArray.add(new XYValue(28.62721,77.37829));
        xyValueArray.add(new XYValue(28.62723,77.3783	));
        xyValueArray.add(new XYValue(28.62724,77.3783	));
        xyValueArray.add(new XYValue(28.62726,77.3783	));
        xyValueArray.add(new XYValue(28.62728,77.37829));
        xyValueArray.add(new XYValue(28.6273	,77.3783	));
        xyValueArray.add(new XYValue(28.62732,77.3783	));
        xyValueArray.add(new XYValue(28.62734,77.37831));
        xyValueArray.add(new XYValue(28.62736,77.37831));
        xyValueArray.add(new XYValue(28.62738,77.37831));
        xyValueArray.add(new XYValue(28.6274	,77.37831));
        xyValueArray.add(new XYValue(28.62742,77.37832));
        xyValueArray.add(new XYValue(28.62744,77.37832));
        xyValueArray.add(new XYValue(28.62747,77.37832));
        xyValueArray.add(new XYValue(28.62749,77.37832));
        xyValueArray.add(new XYValue(28.62751,77.37832));
        xyValueArray.add(new XYValue(28.62754,77.37833));
        xyValueArray.add(new XYValue(28.62756,77.37833));
        xyValueArray.add(new XYValue(28.62758,77.37833));
        xyValueArray.add(new XYValue(28.6276	,77.37833));
        xyValueArray.add(new XYValue(28.62763,77.37834));
        xyValueArray.add(new XYValue(28.62765,77.37834));
        xyValueArray.add(new XYValue(28.62767,77.37834));
        xyValueArray.add(new XYValue(28.6277	,77.37834));
        xyValueArray.add(new XYValue(28.62772,77.37835));
        xyValueArray.add(new XYValue(28.62774,77.37835));
        xyValueArray.add(new XYValue(28.62777,77.37835));
        xyValueArray.add(new XYValue(28.62779,77.37835));
        xyValueArray.add(new XYValue(28.62781,77.37835));
        xyValueArray.add(new XYValue(28.62783,77.37835));
        xyValueArray.add(new XYValue(28.62785,77.37836));
        xyValueArray.add(new XYValue(28.62787,77.37835));
        xyValueArray.add(new XYValue(28.62789,77.37835));
        xyValueArray.add(new XYValue(28.62791,77.37833));
        xyValueArray.add(new XYValue(28.62791,77.37831));
        xyValueArray.add(new XYValue(28.62791,77.37828));
        xyValueArray.add(new XYValue(28.62792,77.37825));
        xyValueArray.add(new XYValue(28.62792,77.37822));
        xyValueArray.add(new XYValue(28.62792,77.3782	));
        xyValueArray.add(new XYValue(28.62792,77.37817));
        xyValueArray.add(new XYValue(28.62793,77.37815));
        xyValueArray.add(new XYValue(28.62793,77.37812));
        xyValueArray.add(new XYValue(28.62793,77.3781	));
        xyValueArray.add(new XYValue(28.62793,77.37807));
        xyValueArray.add(new XYValue(28.62793,77.37804));
        xyValueArray.add(new XYValue(28.62794,77.37802));
        xyValueArray.add(new XYValue(28.62794,77.37799));
        xyValueArray.add(new XYValue(28.62794,77.37797));
        xyValueArray.add(new XYValue(28.62794,77.37794));
        xyValueArray.add(new XYValue(28.62794,77.37791));
        xyValueArray.add(new XYValue(28.62794,77.37789));
        xyValueArray.add(new XYValue(28.62795,77.37786));
        xyValueArray.add(new XYValue(28.62795,77.37783));
        xyValueArray.add(new XYValue(28.62795,77.37781));
        xyValueArray.add(new XYValue(28.62795,77.37778));
        xyValueArray.add(new XYValue(28.62795,77.37775));
        xyValueArray.add(new XYValue(28.62796,77.37772));
        xyValueArray.add(new XYValue(28.62796,77.3777	));
        xyValueArray.add(new XYValue(28.62796,77.37767));
        xyValueArray.add(new XYValue(28.62796,77.37764));
        xyValueArray.add(new XYValue(28.62796,77.37762));
        xyValueArray.add(new XYValue(28.62796,77.37759));
        xyValueArray.add(new XYValue(28.62797,77.37757));
        xyValueArray.add(new XYValue(28.62797,77.37754));
        xyValueArray.add(new XYValue(28.62797,77.37751));
        xyValueArray.add(new XYValue(28.62797,77.37749));
        xyValueArray.add(new XYValue(28.62797,77.37746));
        xyValueArray.add(new XYValue(28.62798,77.37743));
        xyValueArray.add(new XYValue(28.62798,77.37741));
        xyValueArray.add(new XYValue(28.62798,77.37738));
        xyValueArray.add(new XYValue(28.62798,77.37736));
        xyValueArray.add(new XYValue(28.62799,77.37733));
        xyValueArray.add(new XYValue(28.62799,77.3773	));
        xyValueArray.add(new XYValue(28.62799,77.37728));
        xyValueArray.add(new XYValue(28.62799,77.37726));
        xyValueArray.add(new XYValue(28.62799,77.37723));
        xyValueArray.add(new XYValue(28.62799,77.37721));
        xyValueArray.add(new XYValue(28.62796,77.3772	));
        xyValueArray.add(new XYValue(28.62794,77.37719));
        xyValueArray.add(new XYValue(28.62792,77.37719));
        xyValueArray.add(new XYValue(28.62791,77.37717));
        xyValueArray.add(new XYValue(28.6279	,77.37715));
        xyValueArray.add(new XYValue(28.6279	,77.37712));
        xyValueArray.add(new XYValue(28.6279	,77.3771	));
        xyValueArray.add(new XYValue(28.62788,77.37709));
        xyValueArray.add(new XYValue(28.62786,77.37708));
        xyValueArray.add(new XYValue(28.62783,77.37708));
        xyValueArray.add(new XYValue(28.62781,77.37708));
        xyValueArray.add(new XYValue(28.62779,77.37708));
        xyValueArray.add(new XYValue(28.62776,77.37708));
        xyValueArray.add(new XYValue(28.62774,77.37708));
        xyValueArray.add(new XYValue(28.62772,77.37708));
        xyValueArray.add(new XYValue(28.6277	,77.37707));
        xyValueArray.add(new XYValue(28.62767,77.37707));
        xyValueArray.add(new XYValue(28.62765,77.37707));
        xyValueArray.add(new XYValue(28.62763,77.37707));
        xyValueArray.add(new XYValue(28.6276	,77.37706));
        xyValueArray.add(new XYValue(28.62758,77.37706));
        xyValueArray.add(new XYValue(28.62756,77.37706));
        xyValueArray.add(new XYValue(28.62753,77.37706));
        xyValueArray.add(new XYValue(28.62751,77.37705));
        xyValueArray.add(new XYValue(28.62749,77.37705));
        xyValueArray.add(new XYValue(28.62747,77.37705));
        xyValueArray.add(new XYValue(28.62745,77.37705));
        xyValueArray.add(new XYValue(28.62743,77.37704));
        xyValueArray.add(new XYValue(28.62741,77.37704));
        xyValueArray.add(new XYValue(28.62739,77.37704));
        xyValueArray.add(new XYValue(28.62738,77.37704));
        xyValueArray.add(new XYValue(28.62738,77.37704));
        xyValueArray.add(new XYValue(28.62736,77.37704));
        xyValueArray.add(new XYValue(28.62734,77.37704));
        xyValueArray.add(new XYValue(28.62732,77.37704));
        xyValueArray.add(new XYValue(28.6273	,77.37703));
        xyValueArray.add(new XYValue(28.62728,77.37703));
        xyValueArray.add(new XYValue(28.62726,77.37704));
        xyValueArray.add(new XYValue(28.62725,77.37706));
        xyValueArray.add(new XYValue(28.62724,77.37708));
        xyValueArray.add(new XYValue(28.62724,77.3771));
        xyValueArray.add(new XYValue(28.62723,77.37713));
        xyValueArray.add(new XYValue(28.62722,77.37714));
        xyValueArray.add(new XYValue(28.62721,77.37715));
        xyValueArray.add(new XYValue(28.62719,77.37715));
        xyValueArray.add(new XYValue(28.62717,77.37715));
        xyValueArray.add(new XYValue(28.62714,77.37715));
        xyValueArray.add(new XYValue(28.62713,77.37716));
        xyValueArray.add(new XYValue(28.62712,77.37719));
        xyValueArray.add(new XYValue(28.62712,77.37721));
        xyValueArray.add(new XYValue(28.62711,77.37723));
        xyValueArray.add(new XYValue(28.62711,77.37726));
        xyValueArray.add(new XYValue(28.62711,77.37728));
        xyValueArray.add(new XYValue(28.62711,77.37731));
        xyValueArray.add(new XYValue(28.62711,77.37733));
        xyValueArray.add(new XYValue(28.62711,77.37736));
        xyValueArray.add(new XYValue(28.62711,77.37738));
        xyValueArray.add(new XYValue(28.62711,77.3774	));
        xyValueArray.add(new XYValue(28.6271,77.37743	));
        xyValueArray.add(new XYValue(28.6271	,77.37745));
        xyValueArray.add(new XYValue(28.6271,77.37748	));
        xyValueArray.add(new XYValue(28.6271,77.3775	));
        xyValueArray.add(new XYValue(28.6271	,77.37753));
        xyValueArray.add(new XYValue(28.62709,77.37755));
        xyValueArray.add(new XYValue(28.62709,77.37758));
        xyValueArray.add(new XYValue(28.62709,77.3776	));
        xyValueArray.add(new XYValue(28.62709,77.37763));
        xyValueArray.add(new XYValue(28.62709,77.37765));
        xyValueArray.add(new XYValue(28.62709,77.37766));
        xyValueArray.add(new XYValue(28.62709,77.37766));



//        if(threadtime>0){
//        Thread t = new Thread() {
//
//            @Override
//            public void run() {
//                try {
//                    while (!isInterrupted()) {
//                        Thread.sleep(threadtime);
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                GPSTrack gpsTrack = new GPSTrack(stakepoint.this);
//                                double latitude = gpsTrack.getLatitude();
//                                double longitude= gpsTrack.getLongitude();
//                                xyValueArray.add(new XYValue(latitude, longitude));
//                            }
//                        });
//                    }
//                } catch (InterruptedException e) {
//                }
//            }
//        };
//
//        t.start();
//        }

//        for(int a=1;a<=50;a++)
//        {
//            x++;
//            y++;
//            xyValueArray.add(new XYValue(x,y));
//        }


        init();
       String check = CalculationByDistance();
       System.out.println(check);
       distance.setText(check);
    }

    public String CalculationByDistance() {
        int Radius = 6378;// radius of earth in Km
        double lat1 = 28.627236;
        double lat2 = 28.627860;
        double lon1 = 77.377047;
        double lon2 = 77.377103;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1))
                * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2)
                * Math.sin(dLon / 2);
        double c = 2 * Math.asin(Math.sqrt(a));
        double valueResult = Radius * c;
        double km = valueResult / 1;
        DecimalFormat newFormat = new DecimalFormat("####");
        int kmInDec = Integer.valueOf(newFormat.format(km));
        double meter = valueResult * 1000;
        int meterInDec = Integer.valueOf(newFormat.format(meter));
        Log.i("Radius Value", "" + valueResult + "   KM  " + kmInDec
                + " Meter   " + meterInDec);

       // Toast.makeText(this, "Radius Value"+ "" + valueResult + "   KM  " + kmInDec + " Meter   " + meterInDec, Toast.LENGTH_SHORT).show();
        String distance =  "   KM  " + kmInDec + " Meter  " + meterInDec;
        return distance;
    }

    public void showMessage(String title, String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();

    }

    @Override
    protected void onResume() {
        super.onResume();
        // for the system's orientation sensor registered listeners
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onPause() {
        super.onPause();

        // to stop the listener and save battery
        mSensorManager.unregisterListener(this);
    }
    // Method of SensorEventListner
    @Override
    public void onSensorChanged(SensorEvent event) {

        // get the angle around the z-axis rotated
        float degree = Math.round(event.values[0]);

        tvHeading.setText("Heading: " + Float.toString(degree) + " degrees");

        // create a rotation animation (reverse turn degree degrees)
        RotateAnimation ra = new RotateAnimation(
                currentDegree,
                -degree,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF,
                0.5f);

        // how long the animation will take place
        ra.setDuration(210);

        // set the animation after the end of the reservation status
        ra.setFillAfter(true);

        // Start the animation
        image.startAnimation(ra);
        currentDegree = -degree;

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // THis method is not in use.....
    }

    private void init(){
        //declare the xySeries Object
        xySeries = new PointsGraphSeries<>();
        lineSeries = new LineGraphSeries<>();

        btnAddPt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // if(!mX.getText().toString().equals("") && !mY.getText().toString().equals("") ){
//                   double x1 = Double.parseDouble(mX.getText().toString());
//                    double y1= Double.parseDouble(mY.getText().toString());
//                    Log.d(TAG, "onClick: Adding a new point. (x,y): (" + x1 + "," + y1 + ")" );
//                    xyValueArray.add(new XYValue(x1,y1));

//                x=x+5;
//                y=y+5;
//
//                x++;
//                y++;
//                xyValueArray.add(new XYValue(x,y));


//                    for(int i = 0 ; i <xyValueArray.size() ; i++ ) {
//
//                        if (i >= 1) {
//
//                            for (int j = i-1; j < i; j++) {
//
//                                double x = xyValueArray.get(i).getX();
//                                double y = xyValueArray.get(i).getY();
//                                double x1 = xyValueArray.get(j).getX();
//                                double y1 = xyValueArray.get(j).getY();

//                                lineSeries = new LineGraphSeries<>(new DataPoint[]{
//                                        new DataPoint(2,3),
////                                        new DataPoint(x1, y1),
//
//                                });
//                                mScatterPlot.addSeries(lineSeries);
//                                lineSeries.setColor(Color.MAGENTA);
//                                lineSeries.setAnimated(true);


                GPSTrack gpsTrack = new GPSTrack(stakepoint.this);
                double latitude = gpsTrack.getLatitude();
                double longitude= gpsTrack.getLongitude();


                xyValueArray.add(new XYValue(latitude, longitude));


                init();
//                }else {
//                    toastMessage("You must fill out both fields!");
//                }
//try {


//}catch (Exception e)
//{
//    e.printStackTrace();
//}

            }
        });

        //little bit of exception handling for if there is no data.
        if(xyValueArray.size() != 0){
            createScatterPlot();

            for(int i = 0 ; i <xyValueArray.size() ; i++ ) {
//
                if (i >= 1) {

                    for (int j = i-1; j < i; j++) {

                        double x = xyValueArray.get(i).getX();
                        double y = xyValueArray.get(i).getY();
                        int value = (int)  x;
                        int value1 = (int)  y;

                        double x1 = xyValueArray.get(j).getX();
                        double y1 = xyValueArray.get(j).getY();
                        int value2 = (int)  x1;
                        int value3 = (int)  y1;

//                        lineSeries = new LineGraphSeries<>(new DataPoint[]{
////
//                                new DataPoint(value2, value3),
//                                new DataPoint(value, value1),
//
//                        });
//                        mScatterPlot.addSeries(lineSeries);
//                        lineSeries.setColor(Color.MAGENTA);
                        //  lineSeries.setAnimated(true);

                    }}}

        }else{
            Log.d(TAG, "onCreate: No data to plot.");
        }
    }


    private void createScatterPlot() {
        Log.d(TAG, "createScatterPlot: Creating scatter plot.");

        //sort the array of xy values
        xyValueArray = sortArray(xyValueArray);

        //add the data to the series
        for(int i = 0;i <xyValueArray.size(); i++){
            try{
                // double x1 = 0.0;
                // double y1 = 0.0;
                double x = xyValueArray.get(i).getX();
                double y = xyValueArray.get(i).getY();
//                if(i==0){
//                    x1 = xyValueArray.get(i).getX();
//                    y1 = xyValueArray.get(i).getY();
//                }
                xySeries.appendData(new DataPoint(x,y),true, 1000);
//                if (i > 1){
//
//                    LineGraphSeries<DataPoint> lineSeries1 = new LineGraphSeries<>(new DataPoint[] {
//                            new DataPoint(x1, y1),
//                            new DataPoint(x, y)
//                    });
//                }

            }catch (IllegalArgumentException e){
                Log.e(TAG, "createScatterPlot: IllegalArgumentException: " + e.getMessage() );
            }
        }

        //set some properties
        xySeries.setColor(Color.BLACK);
        xySeries.setSize(5f);

        // Used of draw your own shape i.e CustomShape
        xySeries.setCustomShape(new PointsGraphSeries.CustomShape() {

            @Override
            public void draw(Canvas canvas, Paint paint, float x, float y, DataPointInterface dataPoint) {
                paint.setStrokeWidth(10);
                canvas.drawLine(x-2,y,x,y-2,paint);
                canvas.drawLine(x,y-2,x+2,y,paint);
                canvas.drawLine(x+2,y,x,y+2,paint);
                canvas.drawLine(x-2,y,x,y+2,paint);
            }


        });
        // USed for show the X and Y axis through Toast , Just Tap on the mark...
        xySeries.setOnDataPointTapListener(new OnDataPointTapListener() {
            @Override
            public void onTap(Series series, DataPointInterface dataPoint) {
                String msg = "x: " + dataPoint.getX() + "\n y: " + dataPoint.getY();
                Toast.makeText(stakepoint.this, msg, Toast.LENGTH_LONG).show();
            }
        });

        //set Scrollable and Scaleable
        mScatterPlot.getViewport().setScalable(true);
        mScatterPlot.getViewport().setScalableY(true);
        mScatterPlot.getViewport().setScrollable(true);
        mScatterPlot.getViewport().setScrollableY(true);


        //set manual x bounds
        mScatterPlot.getViewport().setYAxisBoundsManual(true);
        mScatterPlot.getViewport().setMinX(27);
        mScatterPlot.getViewport().setMaxX(29);



        // mScatterPlot.getViewport().setMinY(-150);

        //set manual y bounds
        mScatterPlot.getViewport().setXAxisBoundsManual(true);
        mScatterPlot.getViewport().setMinY(75);
        mScatterPlot.getViewport().setMaxY(80);
        //  mScatterPlot.getViewport().setMinX(-150);

        mScatterPlot.addSeries(xySeries);
    }

    /**
     * Sorts an ArrayList<XYValue> with respect to the x values.
     * @param array
     * @return
     */
    private ArrayList<XYValue> sortArray(ArrayList<XYValue> array){
        /*
        //Sorts the xyValues in Ascending order to prepare them for the PointsGraphSeries<DataSet>
         */
        int factor = Integer.parseInt(String.valueOf(Math.round(Math.pow(array.size(),2))));
        int m = array.size() - 1;
        int count = 0;
        Log.d(TAG, "sortArray: Sorting the XYArray.");


        while (true) {
            m--;
            if (m <= 0) {
                m = array.size() - 1;
            }
            Log.d(TAG, "sortArray: m = " + m);
            try {
                //print out the y entrys so we know what the order looks like
                //Log.d(TAG, "sortArray: Order:");
                //for(int n = 0;n < array.size();n++){
                //Log.d(TAG, "sortArray: " + array.get(n).getY());
                //}
                double tempY = array.get(m - 1).getY();
                double tempX = array.get(m - 1).getX();
                if (tempX > array.get(m).getX()) {
                    array.get(m - 1).setY(array.get(m).getY());
                    array.get(m).setY(tempY);
                    array.get(m - 1).setX(array.get(m).getX());
                    array.get(m).setX(tempX);
                } else if (tempX == array.get(m).getX()) {
                    count++;
                    Log.d(TAG, "sortArray: count = " + count);
                } else if (array.get(m).getX() > array.get(m - 1).getX()) {
                    count++;
                    Log.d(TAG, "sortArray: count = " + count);
                }
                //break when factorial is done
                if (count == factor) {
                    break;
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                Log.e(TAG, "sortArray: ArrayIndexOutOfBoundsException. Need more than 1 data point to create Plot." +
                        e.getMessage());
                break;
            }
        }
        return array;
    }

    /**
     * customizable toast
     * @param message
     */
    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }

}

