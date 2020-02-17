package com.apogee.fleetsurvey;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class SetupActivity extends AppCompatActivity {

    Spinner s1,s2,s3,s4,s5,s6,s7,s8;
    EditText e1;
    TextView t1,t2,t3,t4,t5,t6,t7,t8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);

        s1=(Spinner) findViewById(R.id.mode);
        s2=(Spinner) findViewById(R.id.Bms);
        s3=(Spinner) findViewById(R.id.Ct);
        s4=(Spinner) findViewById(R.id.Cp);
        s5=(Spinner) findViewById(R.id.Br);
        s6=(Spinner) findViewById(R.id.Cm);
        s7=(Spinner) findViewById(R.id.Bp);
        s8=(Spinner) findViewById(R.id.Antna);
        e1=(EditText) findViewById(R.id.Bne1);
        t1=(TextView) findViewById(R.id.BM);
        t2=(TextView) findViewById(R.id.ctt);
        t3=(TextView) findViewById(R.id.cpp);
        t4=(TextView) findViewById(R.id.Brt);
        t5=(TextView) findViewById(R.id.crmt);
        t6=(TextView) findViewById(R.id.bpt);
        t7=(TextView) findViewById(R.id.bnmt);
        t8=(TextView) findViewById(R.id.antnat);

        /*First we Put All Spinners in Invisible Mode*/
        s2.setVisibility(View.INVISIBLE);
        t1.setVisibility(View.INVISIBLE);
        t2.setVisibility(View.INVISIBLE);
        t3.setVisibility(View.INVISIBLE);
        t4.setVisibility(View.INVISIBLE);
        t5.setVisibility(View.INVISIBLE);
        t6.setVisibility(View.INVISIBLE);
        t7.setVisibility(View.INVISIBLE);
        t8.setVisibility(View.INVISIBLE);
        e1.setVisibility(View.INVISIBLE);
        s3.setVisibility(View.INVISIBLE);
        s4.setVisibility(View.INVISIBLE);
        s5.setVisibility(View.INVISIBLE);
        s6.setVisibility(View.INVISIBLE);
        s7.setVisibility(View.INVISIBLE);
        s8.setVisibility(View.INVISIBLE);


        ArrayList<String> modeList = new ArrayList<>();
        modeList.add("..Select>>");
        modeList.add("BASE");
        modeList.add("ROVER");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, modeList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s1.setAdapter(arrayAdapter);
        s1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String SurveyMode = parent.getItemAtPosition(position).toString();
                Toast.makeText(parent.getContext(), "Selected: " + SurveyMode,Toast.LENGTH_LONG).show();
                if(SurveyMode.equalsIgnoreCase("ROVER")){
                    Intent intent = new Intent(SetupActivity.this,Connect.class);
                    startActivity(intent);
                }else if(SurveyMode.equalsIgnoreCase("BASE")){
                    s2.setVisibility(View.VISIBLE);
                    t1.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView <?> parent) {
            }
        });



        ArrayList<String> BMList = new ArrayList<>();
        BMList.add("..Select>>");
        BMList.add("Survey Mode");
        BMList.add("Fix Mode");
        ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, BMList);
        arrayAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s2.setAdapter(arrayAdapter1);
        s2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String SurveyMode1 = parent.getItemAtPosition(position).toString();
                Toast.makeText(parent.getContext(), "Selected: " + SurveyMode1,Toast.LENGTH_LONG).show();
                if(SurveyMode1.equalsIgnoreCase("Survey Mode")){
                    s3.setVisibility(View.VISIBLE);
                    t2.setVisibility(View.VISIBLE);
//                    timedialog();
                }else if(SurveyMode1.equalsIgnoreCase("Fix Mode")) {
                    s3.setVisibility(View.VISIBLE);
                    t2.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView <?> parent) {
            }
        });


        ArrayList<String> CTList = new ArrayList<>();
        CTList.add("..Select>>");
        CTList.add("Bluetooth");
        CTList.add("4G");
        CTList.add("LORA");
        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, CTList);
        arrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s3.setAdapter(arrayAdapter2);
        s3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String SurveyMode2 = parent.getItemAtPosition(position).toString();
                Toast.makeText(parent.getContext(), "Selected: " + SurveyMode2,Toast.LENGTH_LONG).show();
                if(SurveyMode2.equalsIgnoreCase("Bluetooth")){
                    s4.setVisibility(View.VISIBLE);
                    t3.setVisibility(View.VISIBLE);
                }else if(SurveyMode2.equalsIgnoreCase("4G")) {
                    s4.setVisibility(View.VISIBLE);
                    t3.setVisibility(View.VISIBLE);
                }else if(SurveyMode2.equalsIgnoreCase("LORA")) {
                    s4.setVisibility(View.VISIBLE);
                    t3.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView <?> parent) {
            }
        });

        ArrayList<String> CPList = new ArrayList<>();
        CPList.add("..Select>>");
        CPList.add("Port1");
        CPList.add("Port2");
        ArrayAdapter<String> arrayAdapter3 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, CPList);
        arrayAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s4.setAdapter(arrayAdapter3);
        s4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String SurveyMode2 = parent.getItemAtPosition(position).toString();
                Toast.makeText(parent.getContext(), "Selected: " + SurveyMode2,Toast.LENGTH_LONG).show();
                if(SurveyMode2.equalsIgnoreCase("Port1")){
                    s5.setVisibility(View.VISIBLE);
                    t4.setVisibility(View.VISIBLE);
                }else if(SurveyMode2.equalsIgnoreCase("Port2")) {
                    s5.setVisibility(View.VISIBLE);
                    t4.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView <?> parent) {
            }
        });

        ArrayList<String> BRList = new ArrayList<>();
        BRList.add("..Select>>");
        BRList.add("1200");
        BRList.add("2400");
        BRList.add("4800");
        BRList.add("9600");
        BRList.add("19200");
        BRList.add("38400");
        BRList.add("57600");
        BRList.add("115200");
        BRList.add("230400");
        BRList.add("460800");
        BRList.add("921600");
        ArrayAdapter<String> arrayAdapter4 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, BRList);
        arrayAdapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s5.setAdapter(arrayAdapter4);
        s5.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String SurveyMode2 = parent.getItemAtPosition(position).toString();
                Toast.makeText(parent.getContext(), "Selected: " + SurveyMode2,Toast.LENGTH_LONG).show();
                if(SurveyMode2.equalsIgnoreCase("1200")){
                    s6.setVisibility(View.VISIBLE);
                    t5.setVisibility(View.VISIBLE);
                }else if(SurveyMode2.equalsIgnoreCase("2400")) {
                    s6.setVisibility(View.VISIBLE);
                    t5.setVisibility(View.VISIBLE);

                }
            }


            @Override
            public void onNothingSelected(AdapterView <?> parent) {
            }
        });

        ArrayList<String> CMList = new ArrayList<>();
        CMList.add("..Select>>");
        CMList.add("RTCM3X");
        CMList.add("RTCM2X");
        ArrayAdapter<String> arrayAdapter5 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, CMList);
        arrayAdapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s6.setAdapter(arrayAdapter5);
        s6.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String SurveyMode2 = parent.getItemAtPosition(position).toString();
                Toast.makeText(parent.getContext(), "Selected: " + SurveyMode2,Toast.LENGTH_LONG).show();
                if(SurveyMode2.equalsIgnoreCase("RTCM3X")){
                    s7.setVisibility(View.VISIBLE);
                    t6.setVisibility(View.VISIBLE);
                }else if(SurveyMode2.equalsIgnoreCase("RTCM2X")) {
                    s7.setVisibility(View.VISIBLE);
                    t6.setVisibility(View.VISIBLE);

                }
            }


            @Override
            public void onNothingSelected(AdapterView <?> parent) {
            }
        });

         ArrayList<String> BPList = new ArrayList<>();
         BPList.add ("..Select>>");
         BPList.add ("Lat/Long/Height");
        ArrayAdapter<String> arrayAdapter6 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, BPList);
        arrayAdapter6.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s7.setAdapter(arrayAdapter6);
        s7.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String SurveyMode2 = parent.getItemAtPosition(position).toString();
                Toast.makeText(parent.getContext(), "Selected: " + SurveyMode2,Toast.LENGTH_LONG).show();
                if(SurveyMode2.equalsIgnoreCase("Lat/Long/Height")) {
                    basepositiondialog();
                    s8.setVisibility(View.VISIBLE);
                    t8.setVisibility(View.VISIBLE);

                }
            }


            @Override
            public void onNothingSelected(AdapterView <?> parent) {
            }
        });

        ArrayList<String> AntenaList = new ArrayList<>();
        AntenaList.add ("..Select>>");
        AntenaList.add ("Default");
        AntenaList.add ("Add Antenna");
        ArrayAdapter<String> arrayAdapter7 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, AntenaList);
        arrayAdapter7.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s8.setAdapter(arrayAdapter7);
        s8.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String SurveyMode2 = parent.getItemAtPosition(position).toString();
                Toast.makeText(parent.getContext(), "Selected: " + SurveyMode2,Toast.LENGTH_LONG).show();
                if(SurveyMode2.equalsIgnoreCase("Default")) {
                    e1.setVisibility(View.VISIBLE);
                    t7.setVisibility(View.VISIBLE);
                }else if(SurveyMode2.equalsIgnoreCase("Add Antenna")){
                    dialogantenna();
                    e1.setVisibility(View.VISIBLE);
                    t7.setVisibility(View.VISIBLE);
                }
            }


            @Override
            public void onNothingSelected(AdapterView <?> parent) {
            }
        });








    }

    public void timedialog(){
        final AlertDialog dialogBuilder = new AlertDialog.Builder(this).create();
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialogtime, null);

        final EditText editText = (EditText) dialogView.findViewById(R.id.et1);
        Button button1 = (Button) dialogView.findViewById(R.id.timebt);



        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // DO SOMETHINGS
                dialogBuilder.dismiss();
            }
        });

        dialogBuilder.setView(dialogView);
        dialogBuilder.show();
    }

    public void basepositiondialog(){
        final AlertDialog dialogBuilder = new AlertDialog.Builder(this).create();
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.basepositiondialog, null);

        final EditText editText = (EditText) dialogView.findViewById(R.id.lat);
        final EditText editText1 = (EditText) dialogView.findViewById(R.id.lng);
        final EditText editText2 = (EditText) dialogView.findViewById(R.id.hgt);
        Button button1 = (Button) dialogView.findViewById(R.id.llhbtn);



        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // DO SOMETHINGS
                dialogBuilder.dismiss();
            }
        });

        dialogBuilder.setView(dialogView);
        dialogBuilder.show();
    }

    public void dialogantenna(){
        final AlertDialog dialogBuilder = new AlertDialog.Builder(this).create();
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialogantenna, null);

        final EditText editText = (EditText) dialogView.findViewById(R.id.nm);
        final EditText editText1 = (EditText) dialogView.findViewById(R.id.rds);
        final EditText editText2 = (EditText) dialogView.findViewById(R.id.mdl);
        final EditText editText3 = (EditText) dialogView.findViewById(R.id.btm);
        final ImageView imgvw = (ImageView) dialogView.findViewById(R.id.imgv);
        Button button1 = (Button) dialogView.findViewById(R.id.antnabtn);



        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // DO SOMETHINGS
                dialogBuilder.dismiss();
            }
        });

        dialogBuilder.setView(dialogView);
        dialogBuilder.show();
    }
}
