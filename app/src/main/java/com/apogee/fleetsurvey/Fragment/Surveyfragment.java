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

import com.apogee.fleetsurvey.GridAdapter;
import com.apogee.fleetsurvey.R;
import com.apogee.fleetsurvey.stakepoint;


/**
 * A simple {@link Fragment} subclass.
 */
public class Surveyfragment extends Fragment {
    GridView grid;
    String[] web = {
            "Topo Survey", "Auto Survey", "Stake Point", "Stake Line", "PPK", "Area Survey", "Static",


    } ;
    int[] imageId = {R.drawable.topo, R.drawable.auto, R.drawable.stakepoint, R.drawable.stakeline, R.drawable.ppk, R.drawable.area,
            R.drawable.image_staticsetting3


    };

    public Surveyfragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
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
                    Intent intent = new Intent(getActivity().getApplicationContext(), stakepoint.class);
                    startActivity(intent);
                }
                else if(position==1)
                {
//                    Toast.makeText(MainActivity.this, "You Clicked at " +web[+ position], Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(MainActivity.this, GenericActivity.class);
//                    startActivity(intent);

                }else if(position==2){
//                    Toast.makeText(MainActivity.this, "You Clicked at " +web[+ position], Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(MainActivity.this, SetupActivity.class);
//                    startActivity(intent);
                }
                Toast.makeText(getActivity().getApplicationContext(), "You Clicked at " + web[+position], Toast.LENGTH_SHORT).show();

            }
        });

        return view;
    }

}
