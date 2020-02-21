package com.apogee.fleetsurvey;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import com.apogee.fleetsurvey.Fragments.Developer;
import com.apogee.fleetsurvey.Fragments.OperationListFragment;
import com.apogee.fleetsurvey.Fragments.User;
import com.apogee.fleetsurvey.model.Operation;
import com.apogee.fleetsurvey.utility.DeviceScanActivity;

public class Connect extends AppCompatActivity implements OperationListFragment.OnListFragmentInteractionListener {
    Switch simpleSwitch1;


    public SharedPreferences sharedPreferences;
    public String SHAREDPREFCONSTANT = "DeviceBleInfo";
    public SharedPreferences.Editor editor;
    public static String FRAGMENTREFERENCE = "fragmentref";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);
        sharedPreferences = getSharedPreferences(SHAREDPREFCONSTANT, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        // initiate view's
        simpleSwitch1 = (Switch) findViewById(R.id.simpleSwitch1);
     //   showDialog();
        int reference = getIntent().getIntExtra(FRAGMENTREFERENCE, 0);
        if (reference == 1) {
            loadFragment(new User());
        } else if (reference == 2) {
            simpleSwitch1.setVisibility(View.GONE);
            loadFragment(new OperationListFragment());

        }



        simpleSwitch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (simpleSwitch1.isChecked()) {
                    loadFragment(new Developer());
                    simpleSwitch1.setText("For Developer");
                    Toast.makeText(Connect.this, "For Developer", Toast.LENGTH_SHORT).show();
                } else {
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
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit(); // save the changes
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    void showDialog() {
        final Dialog dialog = new Dialog(Connect.this);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_connect);

        ImageView iv_createnew = dialog.findViewById(R.id.iv_createanew);
        iv_createnew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        ImageView iv_chooseoption = dialog.findViewById(R.id.iv_chooseoption);
        iv_chooseoption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simpleSwitch1.setVisibility(View.GONE);
                loadFragment(new OperationListFragment());
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    public void onListFragmentInteraction(Operation operation) {
        Intent intent = new Intent(Connect.this, DeviceScanActivity.class);
        intent.putExtra("device_name", operation.getDevicename());
        intent.putExtra("device_address", operation.getDevice_address());
        intent.putExtra("device_id", String.valueOf(operation.getBleid()));
        intent.putExtra("dgps_device_id", String.valueOf(operation.getDgpsid()));
        startActivity(intent);
    }
}

