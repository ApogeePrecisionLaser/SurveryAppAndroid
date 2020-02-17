package com.apogee.fleetsurvey;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.apogee.fleetsurvey.Database.DatabaseOperation;
import com.apogee.fleetsurvey.bean.BleBean;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class newproject extends AppCompatActivity {

    private ActionBar toolbar;
    EditText ep1,ep2,ep3;
    Button btn;
    ViewFlipper vfp;
//    String item;
    Spinner datumspinner;
    ArrayList<String> datumlist;
    DatabaseOperation dbTask;
    public static String datumitem;
    String[] country = {"India", "China", "Russia", "America", "Nepal", "Bhutan", "Iran", "Iraq"};

    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String Project_name = "project_nameKey";
    public static final String comment = "commentKey";
    public static final String operator = "operatorKey";

    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newproject);
        toolbar = getSupportActionBar();
        toolbar.setTitle("Project");
        dbTask=new DatabaseOperation(newproject.this);
        ep1 = findViewById(R.id.ep1);
        ep2 = findViewById(R.id.ep2);
        ep3 = findViewById(R.id.ep3);
        btn = findViewById(R.id.okbutton);
        final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentDateTime = dateFormat.format(new Date()); // Find todays date
        ep1.setText(currentDateTime);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        datumspinner = findViewById(R.id.sp1);
        vfp = findViewById(R.id.vfp);
        int images[]= {R.drawable.genericpng,R.drawable.bleimg,R.drawable.rtk_logo,R.drawable.survey};
        //Creating the instance of ArrayAdapter containing list of fruit names
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this, android.R.layout.select_dialog_item, country);
        //Getting the instance of AutoCompleteTextView
        AutoCompleteTextView actv = (AutoCompleteTextView) findViewById(R.id.rp1);
        actv.setThreshold(1);//will start working from first character
        actv.setAdapter(adapter);//setting the adapter data into the AutoCompleteTextView
        actv.setTextColor(Color.RED);

        datumlist=new ArrayList<>();
        dbTask.open();
        datumlist = dbTask.getdatumlist();

        final ArrayAdapter<String> model_typeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, datumlist);
        model_typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        datumspinner.setAdapter(model_typeAdapter);

        datumspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                datumitem = parent.getItemAtPosition(position).toString();
                BleBean bleBean = new BleBean();
                bleBean.setDatumselection(datumitem);
                if (datumitem.equals("")) {

                }else{

                }

            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        for(int image : images){
            viewflipper(image);
        }

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedpreferences.edit();

                String p_name  = ep1.getText().toString();
                String cmnt  = ep2.getText().toString();
                String oprtr  = ep3.getText().toString();

                editor.putString(Project_name, p_name);
                editor.putString(comment, cmnt);
                editor.putString(operator, oprtr);
                editor.commit();
                Toast.makeText(newproject.this,"Thanks",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(newproject.this,HomeActivity.class);
                startActivity(intent);
            }
        });


    }

    public void viewflipper(int images){
        ImageView imageView=new ImageView(this);
        imageView.setBackgroundResource(images);

        vfp.addView(imageView);
        vfp.setFlipInterval(2000);
        vfp.setAutoStart(true);


        /*Animation*/
        vfp.setInAnimation(this,android.R.anim.slide_in_left);
        vfp.setOutAnimation(this,android.R.anim.slide_out_right);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
