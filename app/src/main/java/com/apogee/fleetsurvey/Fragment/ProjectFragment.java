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

import com.apogee.fleetsurvey.CRS_sattelite;
import com.apogee.fleetsurvey.GridAdapter;
import com.apogee.fleetsurvey.R;
import com.apogee.fleetsurvey.newproject;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProjectFragment extends Fragment {
    GridView grid;
    String[] web = {
            "Projects", "CRS", "Import", "Export", "Reports", "Base Map", "Points", "Lines", "Features", "Cloud", "CodeList",


    };
    int[] imageId = {R.drawable.image_project1, R.drawable.image_crs2, R.drawable.image_import3, R.drawable.image_export4, R.drawable.image_reports5, R.drawable.image_basemap6,
            R.drawable.image_points7, R.drawable.image_lines8, R.drawable.image_features9, R.drawable.image_cloud10, R.drawable.image_codelist11


    };

    public ProjectFragment() {
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
                    Intent intent = new Intent(getActivity().getApplicationContext(), newproject.class);
                    startActivity(intent);
                }
                else if(position==1)
                {
                    Toast.makeText(getActivity().getApplicationContext(), "You Clicked at " +web[+ position], Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getActivity().getApplicationContext(), CRS_sattelite.class);
                    startActivity(intent);

                }else if(position==2){
//                    Toast.makeText(MainActivity.this, "You Clicked at " +web[+ position], Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(MainActivity.this, SetupActivity.class);
//                    startActivity(intent);
                }
                Toast.makeText(getActivity().getApplicationContext(), "You Clicked at " +web[+ position], Toast.LENGTH_SHORT).show();

            }
        });

        return view;
}

}
