package com.apogee.fleetsurvey;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.apogee.fleetsurvey.Fragments.Developer;
import com.apogee.fleetsurvey.Fragments.User;

public class Connect extends AppCompatActivity {
    Switch simpleSwitch1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);



        loadFragment(new User());
        // initiate view's
        simpleSwitch1 = (Switch) findViewById(R.id.simpleSwitch1);

        simpleSwitch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(simpleSwitch1.isChecked()){
                    loadFragment(new Developer());
                    simpleSwitch1.setText("For Developer");
                    Toast.makeText(Connect.this, "For Developer", Toast.LENGTH_SHORT).show();
                }else{
                    loadFragment(new User());
                    simpleSwitch1.setText("For User");
                    Toast.makeText(Connect.this, "For User", Toast.LENGTH_SHORT).show();
                }

            }
        });




    }

    private void loadFragment(Fragment fragment) {
        // create a FragmentManager
        FragmentManager fm = getSupportFragmentManager();
        // create a FragmentTransaction to begin the transaction and replace the Fragment
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        // replace the FrameLayout with new Fragment
        fragmentTransaction.replace(R.id.frameLayout,fragment);
        fragmentTransaction.commit(); // save the changes
    }

    @Override
    public void onBackPressed() {
       Intent intent = new Intent(Connect.this,HomeActivity.class);
       startActivity(intent);
    }
}

