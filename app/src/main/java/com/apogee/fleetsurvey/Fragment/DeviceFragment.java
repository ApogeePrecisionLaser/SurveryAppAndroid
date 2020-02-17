package com.apogee.fleetsurvey.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.apogee.fleetsurvey.Connect;
import com.apogee.fleetsurvey.Generic.taskGeneric;
import com.apogee.fleetsurvey.GridAdapter;
import com.apogee.fleetsurvey.R;
import com.apogee.fleetsurvey.SetupActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class DeviceFragment extends Fragment {

    GridView grid;
    String[] web = {
            "Connect","Generic", "Work Mode", "Static Setting", "NMEA Output", "Device Info", "System Config", "About",


    } ;
    int[] imageId = {R.drawable.image_connection1,R.drawable.genericpng, R.drawable.image_workmode2, R.drawable.image_staticsetting3, R.drawable.image_nmea4, R.drawable.image_deviceinfo5, R.drawable.image_systemconfig6,
            R.drawable.image_about7


    };
    public DeviceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calls, container, false);


        GridAdapter adapter = new GridAdapter(getActivity().getApplicationContext(), web, imageId);
        grid = (GridView)view.findViewById(R.id.grid);
        grid.setAdapter(adapter);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                if(position==0) {
                    Toast.makeText(getActivity().getApplicationContext(), "You Clicked at " +web[+ position], Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getActivity().getApplicationContext(), Connect.class);
                    startActivity(intent);
                }
                else if(position==1)
                {
                    Toast.makeText(getActivity().getApplicationContext(), "You Clicked at " +web[+ position], Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getActivity().getApplicationContext(), taskGeneric.class);
                    startActivity(intent);

                }else if(position==2){
                    Toast.makeText(getActivity().getApplicationContext(), "You Clicked at " +web[+ position], Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getActivity().getApplicationContext(), SetupActivity.class);
                    startActivity(intent);
                }
                Toast.makeText(getActivity().getApplicationContext(), "You Clicked at " + web[+position], Toast.LENGTH_SHORT).show();

            }
        });

        return view;
    }

}
