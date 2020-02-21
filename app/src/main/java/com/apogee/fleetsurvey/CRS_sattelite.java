package com.apogee.fleetsurvey;

import android.graphics.Color;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class CRS_sattelite extends AppCompatActivity {

    com.github.mikephil.charting.charts.RadarChart RadarChart;
    RadarData radarData;
    RadarDataSet radarDataSet;
    ArrayList radarEntries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crs_sattelite);
        RadarChart = findViewById(R.id.RadarChart);
        getEntries();
        radarDataSet = new RadarDataSet(radarEntries, "Survey App");
        radarData = new RadarData(radarDataSet);
        RadarChart.setData(radarData);
        radarDataSet.setColors(ColorTemplate.JOYFUL_COLORS);
        radarDataSet.setValueTextColor(Color.BLACK);
        radarDataSet.setValueTextSize(18f);


    }
    private void getEntries() {
        radarEntries = new ArrayList<>();
        radarEntries.add(new RadarEntry(0, 6.21f));
        radarEntries.add(new RadarEntry(30, 15.12f));
        radarEntries.add(new RadarEntry(25, 14.20f));
        radarEntries.add(new RadarEntry(28, 56.52f));
        radarEntries.add(new RadarEntry(43, 17.29f));
        radarEntries.add(new RadarEntry(49, 21.62f));
    }
}