package com.apogee.fleetsurvey.Generic;

import android.content.Intent;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.apogee.fleetsurvey.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class taskGeneric extends AppCompatActivity {

    TextView titlepage, addtitle, adddesc, adddate;
    EditText titledoes, descdoes, datedoes,aboutyourself;
    Button btnSaveTask, btnCancel;
    Integer doesNum = new Random().nextInt();
    String keydoes = Integer.toString(doesNum);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_generic);
        ActionBar actionBar=getSupportActionBar();
        actionBar.hide();

        titlepage = findViewById(R.id.titlepage);

        addtitle = findViewById(R.id.addtitle);
        adddesc = findViewById(R.id.adddesc);
        adddate = findViewById(R.id.adddate);

        titledoes = findViewById(R.id.titledoes);
        descdoes = findViewById(R.id.descdoes);
        datedoes = findViewById(R.id.datedoes);
        aboutyourself = findViewById(R.id.aboutyourself);

        final int[] ids = new int[]
                {
                        R.id.titledoes,
                        R.id.descdoes,
                        R.id.aboutyourself
                };


        final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentDateTime = dateFormat.format(new Date()); // Find todays date
        datedoes.setText(currentDateTime);
        btnSaveTask = findViewById(R.id.btnSaveTask);
        btnCancel = findViewById(R.id.btnCancel);

        btnSaveTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title =   titledoes.getText().toString();
                String desc =    descdoes.getText().toString();
                String whoareyou =    aboutyourself.getText().toString();
                String date =    datedoes.getText().toString();

                if(!validateEditText(ids))
                {
                    //if not empty do something
                    Intent i = new Intent(taskGeneric.this, GenericActivity.class);
                    String strName = null;
                    i.putExtra("Title", title);
                    i.putExtra("Description", desc);
                    i.putExtra("Date", date);
                    i.putExtra("whoareyou", whoareyou);

                    startActivity(i);
                }else{
                    //if empty do somethingelse
                    Toast.makeText(taskGeneric.this, "Select All fields First", Toast.LENGTH_SHORT).show();
                }



            }
        });
    }
    public boolean validateEditText(int[] ids)
    {
        boolean isEmpty = false;

        for(int id: ids)
        {
            EditText et = (EditText)findViewById(id);

            if(TextUtils.isEmpty(et.getText().toString()))
            {
                et.setError("Must enter Value");
                isEmpty = true;
            }
        }

        return isEmpty;
    }
}