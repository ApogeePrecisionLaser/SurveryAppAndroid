package com.apogee.fleetsurvey.Fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.cardview.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.apogee.fleetsurvey.Connect;
import com.apogee.fleetsurvey.Database.DatabaseOperation;
import com.apogee.fleetsurvey.R;
import com.apogee.fleetsurvey.model.Operation;
import com.apogee.fleetsurvey.utility.DeviceScanActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * A simple {@link Fragment} subclass.
 */
public class User extends Fragment {
    View view;
    /*Model Detail*/
    Spinner modeltype, modelname;
    ArrayList<String> modeltypeList, mnList;
    DatabaseOperation dbTask;
    TextView mnText, ble, dgps;
    CardView Card1;
    int model_type_id = 0;
    ////    Button connectBtn;
    String dgps_id, device_id;
    String bleDetail = "", bleDeviceModeule = "", ble_detail = "", dgps_detail = "";
    String device_id2;
    Button connectBtn2;
    Map<String, String> selectionValue1 = new HashMap<String, String>();
    Map<String, String> selectionValue2 = new HashMap<String, String>();
    Map<String, String> selectionValue3 = new HashMap<String, String>();
    Map<String, String> selectionValue4 = new HashMap<String, String>();

    String ble_device_name;
    String dgps_device_name;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_user, container, false);
        final Context context = inflater.getContext();

        /*Model Detail*/
        dbTask = new DatabaseOperation(context);
        modeltype = (Spinner) view.findViewById(R.id.mt);
        mnText = (TextView) view.findViewById(R.id.mntxt);
        modelname = (Spinner) view.findViewById(R.id.mn);
        ble = (TextView) view.findViewById(R.id.bledevicetxt);
        dgps = (TextView) view.findViewById(R.id.dgpstxt);

        connectBtn2 = (Button) view.findViewById(R.id.connect1);
        Card1 = (CardView) view.findViewById(R.id.card_view);


//        connectBtn=(Button)findViewById(R.id.connect);
        mnList = new ArrayList<>();
        modeltypeList = new ArrayList<>();
        dbTask.open();
        //  modeltypeList = dbTask.getdevn();
        modeltypeList = dbTask.getmodel_type();
        modelname.setVisibility(View.INVISIBLE);

        connectBtn2.setVisibility(View.INVISIBLE);
        ble.setVisibility(View.INVISIBLE);
        dgps.setVisibility(View.INVISIBLE);


        final ArrayAdapter<String> model_typeAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, modeltypeList);
        model_typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        modeltype.setAdapter(model_typeAdapter);

        modeltype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                if (item.equals("Finished")) {
                    modelname.setVisibility(View.VISIBLE);
                    connectBtn2.setVisibility(View.VISIBLE);
                    ble.setVisibility(View.VISIBLE);
                    dgps.setVisibility(View.VISIBLE);
                    dbTask.open();
                    model_type_id = dbTask.getModelType_id(item);
                    mnList = dbTask.getmnypes(model_type_id);
                    ArrayAdapter<String> mnAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, mnList);
                    mnAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    modelname.setAdapter(mnAdapter);
                } else {
                    //Toast.makeText(context, "Wrong Selection", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        modelname.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                if (!item.equals("--select--")) {
                    dbTask.open();
                    model_type_id = dbTask.getModelType_id2(item);
                    int device_id1 = dbTask.getModelType_id1(model_type_id);
                    int no_of_module = dbTask.getnoofmodule(model_type_id);
                    Map<String, Map<String, String>> mapdetail = dbTask.finished(device_id1);
                    Set<String> devicetype = mapdetail.keySet();
                    for (String param : devicetype) {
                        if (param.equalsIgnoreCase("DGPS")) {
                            selectionValue1 = mapdetail.get(param);
                            Set<String> moduledeviceid = selectionValue1.keySet();
                            Collection<String> devicename = selectionValue1.values();
                            String strNew = devicename.toString().replace("[", "");
                            String strNew1 = strNew.replace("]", "");
                            dgps_device_name = strNew1;
                            System.out.println(strNew1);
                            String strNew11 = moduledeviceid.toString().replace("[", "");
                            String strNew111 = strNew11.replace("]", "");
                            dgps_id = strNew111;
                            System.out.println("Initial values : " + strNew1);
                            System.out.println("Initial values : " + strNew111);
                        } else if (param.equalsIgnoreCase("BLE")) {
                            selectionValue2 = mapdetail.get(param);
                            Set<String> moduledeviceid = selectionValue2.keySet();
                            Collection<String> devicename = selectionValue2.values();
                            String strNew = devicename.toString().replace("[", "");
                            String strNew1 = strNew.replace("]", "");
                            ble_device_name = strNew1;
                            System.out.println(strNew1);
                            String strNew11 = moduledeviceid.toString().replace("[", "");
                            String strNew111 = strNew11.replace("]", "");
                            device_id = strNew111;
                            System.out.println("Initial values : " + devicename);
                            System.out.println("Initial values : " + moduledeviceid);
                        } else if (param.equalsIgnoreCase("Sensor")) {
                            selectionValue3 = mapdetail.get(param);
                            Set<String> moduledeviceid = selectionValue3.keySet();
                            Collection<String> devicename = selectionValue3.values();
                            System.out.println("Initial values : " + devicename);
                            System.out.println("Initial values : " + moduledeviceid);
                        } else if (param.equalsIgnoreCase("2G")) {
                            selectionValue4 = mapdetail.get(param);
                            Set<String> moduledeviceid = selectionValue4.keySet();
                            Collection<String> devicename = selectionValue4.values();
                            System.out.println("Initial values : " + devicename);
                            System.out.println("Initial values : " + moduledeviceid);
                        }
                    }

                    ble.setText("BLE Device: " + ble_device_name);
                    dgps.setText("DGPS Module: " + dgps_device_name);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        connectBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  dbTask.open();
                dbTask.open();
                String device_name = dbTask.modeldetail(device_id);
                String device_address = dbTask.deviceAdress(device_id);
                Intent intent = new Intent(context, DeviceScanActivity.class);
                intent.putExtra("device_name", device_name);
                intent.putExtra("device_address", device_address);
                intent.putExtra("device_id", device_id);
                intent.putExtra("dgps_device_id", dgps_id);

                Operation operation = new Operation();

                operation.setDeviceid(model_type_id);
                operation.setDevicename(device_name);
                operation.setBleid(Integer.parseInt(device_id));
                operation.setBlename(ble_device_name);
                operation.setDgpsid(Integer.parseInt(dgps_id));
                operation.setDgpsname(dgps_device_name);
                operation.setDevice_address(device_address);
                List<Operation> savedevicelist = new ArrayList<>();
                Gson gson = new Gson();
                if (((Connect) getActivity()).sharedPreferences.getString("SAVEDLIST", null) != null) {

                    String json = ((Connect) getActivity()).sharedPreferences.getString("SAVEDLIST", null);



                    Type type = new TypeToken<List<Operation>>(){}.getType();
                    List<Operation> operationList = gson.fromJson(json, type);
                    savedevicelist.addAll(operationList);
                }

                savedevicelist.add(operation);
                Set<Operation> stringSet = new HashSet<Operation>();
                stringSet.addAll(savedevicelist);

                String json = gson.toJson(savedevicelist);
                ((Connect) getActivity()).editor.putString("SAVEDLIST", json);
                ((Connect) getActivity()).editor.apply();

                startActivity(intent);
            }
        });


        return view;
    }

}
