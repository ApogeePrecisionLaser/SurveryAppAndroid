package com.apogee.fleetsurvey.Fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.ActionBar;
import androidx.cardview.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.apogee.fleetsurvey.Connect;
import com.apogee.fleetsurvey.Database.DatabaseOperation;
import com.apogee.fleetsurvey.R;
import com.apogee.fleetsurvey.model.Operation;
import com.apogee.fleetsurvey.utility.DeviceScanActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**DeviceControlActivity
 * A simple {@link Fragment} subclass.
 */
public class Developer extends Fragment {
    View view;
    private ActionBar toolbar;
    /*Model Detail*/
    Spinner modeltype,devicetype;
    ArrayList<String> modeltypeList,mnList,devicetypeList;
    DatabaseOperation dbTask;
    CardView Card1,card2;
    int model_type_id=0;
    String bleDetail="", bleDeviceModeule="", ble_detail="",dgps_detail="";

    /*Device Detail*/
    Spinner maufactrur, model_type, model;
    Spinner dgmaufactrur,  dgmodel_type, dgmodel;
    ArrayList<String> maufactrurList,  model_TypeList, modelList;
    ArrayList<String> dgmaufactrurList, dgmodelList;
    int manufactrur_id, model_id, model_ytpe_id,dgpsmodel_id;
    TextView maufactrurtxt,model_typetxt,modeltxt,dgmaufactrurtxt,dgmodel_typetxt,dgmodeltxt,bledevicedetail,dgpsdevicedetail;
    String mode_type, device,device_dgps;
    Button connectBtn1;
    ArrayAdapter<String> modelAdapter,dgmodelAdapter;
    String devicetypeval;
    ArrayAdapter<String> dgmauufactrurAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_developer, container, false);
        final Context context = inflater.getContext();
        /*Model Detail*/
        dbTask=new DatabaseOperation(context);
        modeltype=(Spinner)view.findViewById(R.id.mt);
        devicetype=view.findViewById(R.id.dt);
        maufactrurtxt=(TextView)view.findViewById(R.id.manufactrurtxt) ;
        model_typetxt=(TextView)view.findViewById(R.id.model_typetxt) ;
        modeltxt=(TextView)view.findViewById(R.id.modeltxt) ;
        dgmaufactrurtxt=(TextView)view.findViewById(R.id.manufactrur1txt) ;
        dgmodel_typetxt=(TextView)view.findViewById(R.id.model_type1txt) ;
        dgmodeltxt=(TextView)view.findViewById(R.id.model1txt) ;
        bledevicedetail=(TextView)view.findViewById(R.id.bledevicedetail) ;
        dgpsdevicedetail=(TextView)view.findViewById(R.id.dgpsdevicedetail) ;
        maufactrur = (Spinner) view.findViewById(R.id.manufactrur);
        model_type = (Spinner)view. findViewById(R.id.model_type);
        model = (Spinner)view. findViewById(R.id.model);
        dgmaufactrur = (Spinner) view.findViewById(R.id.manufactrur1);
        dgmodel_type = (Spinner)view. findViewById(R.id.model_type1);
        dgmodel = (Spinner) view.findViewById(R.id.model1);
        connectBtn1 = (Button)view. findViewById(R.id.connect);
        Card1 = (CardView)view. findViewById(R.id.card_view1);
        card2 = (CardView)view. findViewById(R.id.card_view2);
        mnList=new ArrayList<>();
        modeltypeList=new ArrayList<>();
        dbTask.open();
        modeltypeList = dbTask.getmodel_type();
        devicetypeList=new ArrayList<>();
        dbTask.open();
        devicetypeList=dbTask.getDevice_type();
        modeltxt.setVisibility(View.INVISIBLE);
        model_typetxt.setVisibility(View.INVISIBLE);
        maufactrurtxt.setVisibility(View.INVISIBLE);
        dgmaufactrurtxt.setVisibility(View.INVISIBLE);
        dgmodel_typetxt.setVisibility(View.INVISIBLE);
        dgmodeltxt.setVisibility(View.INVISIBLE);
        bledevicedetail.setVisibility(View.INVISIBLE);
        dgpsdevicedetail.setVisibility(View.INVISIBLE);
        maufactrur.setVisibility(View.INVISIBLE);
        model_type.setVisibility(View.INVISIBLE);
        model.setVisibility(View.INVISIBLE);
        dgmaufactrur.setVisibility(View.INVISIBLE);
        dgmodel_type.setVisibility(View.INVISIBLE);
        dgmodel.setVisibility(View.INVISIBLE);
        Card1.setVisibility(View.INVISIBLE);
        card2.setVisibility(View.INVISIBLE);
        connectBtn1.setVisibility(View.INVISIBLE);
        final ArrayAdapter<String> model_typeAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, modeltypeList);
        model_typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        modeltype.setAdapter(model_typeAdapter);

        modeltype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();

                if(!item.equals("--select--"))
                {
                    maufactrurtxt.setVisibility(View.VISIBLE);
                    model_typetxt.setVisibility(View.VISIBLE);
                    modeltxt.setVisibility(View.VISIBLE);
                    dgmaufactrurtxt.setVisibility(View.VISIBLE);
                    dgmodel_typetxt.setVisibility(View.VISIBLE);
                    dgmodeltxt.setVisibility(View.VISIBLE);
                    bledevicedetail.setVisibility(View.VISIBLE);
                    dgpsdevicedetail.setVisibility(View.VISIBLE);
                    maufactrur.setVisibility(View.VISIBLE);
                    model_type.setVisibility(View.VISIBLE);
                    model.setVisibility(View.VISIBLE);
                    dgmaufactrur.setVisibility(View.VISIBLE);
                    dgmodel_type.setVisibility(View.VISIBLE);
                    dgmodel.setVisibility(View.VISIBLE);
                    Card1.setVisibility(View.VISIBLE);
                    card2.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        final ArrayAdapter<String> device_typeAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, devicetypeList);
        device_typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        devicetype.setAdapter(device_typeAdapter);

        devicetype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                 devicetypeval = parent.getItemAtPosition(position).toString();

                if(!devicetypeval.equals("--select--"))
                {
                    maufactrurtxt.setVisibility(View.VISIBLE);
                    model_typetxt.setVisibility(View.VISIBLE);
                    modeltxt.setVisibility(View.VISIBLE);
                    dgmaufactrurtxt.setVisibility(View.VISIBLE);
                    dgmodel_typetxt.setVisibility(View.VISIBLE);
                    dgmodeltxt.setVisibility(View.VISIBLE);
                    bledevicedetail.setVisibility(View.VISIBLE);
                    dgpsdevicedetail.setVisibility(View.VISIBLE);
                    maufactrur.setVisibility(View.VISIBLE);
                    model_type.setVisibility(View.VISIBLE);
                    model.setVisibility(View.VISIBLE);
                    dgmaufactrur.setVisibility(View.VISIBLE);
                    dgmodel_type.setVisibility(View.VISIBLE);
                    dgmodel.setVisibility(View.VISIBLE);
                    Card1.setVisibility(View.VISIBLE);
                    card2.setVisibility(View.VISIBLE);
                    update();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        /*Device Detail*/


        dbTask = new DatabaseOperation(context);
        dbTask.open();
        // ArrayList<String>   lst= dbTask.getdevice_detail(1);
        maufactrurList = dbTask.getManufactrur("BLE");
        dgmaufactrurList = dbTask.getManufactrur(devicetypeval);
        //device_typeList = dbTask.getDevice_type();
        //  model_TypeList = dbTask.getdevice_detail();
        // model_TypeList = dbTask.getmodel_type();
//        Model_TypeList1 = dbTask.getmodel_typels1();
        ArrayAdapter<String> mauufactrurAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, maufactrurList);
        mauufactrurAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        maufactrur.setAdapter(mauufactrurAdapter);
        dgmauufactrurAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, dgmaufactrurList);
        mauufactrurAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dgmaufactrur.setAdapter(dgmauufactrurAdapter);
        maufactrur.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                if (!item.equals("--select--")) {
                    dbTask.open();
                    manufactrur_id = dbTask.manufactrur_id(item);
                    model_TypeList = dbTask.getdevice_detail(manufactrur_id);
                    ArrayAdapter<String> model_typeAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, model_TypeList);
                    model_typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    model_type.setAdapter(model_typeAdapter);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        model_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mode_type = parent.getItemAtPosition(position).toString();
                if (!mode_type.equals("--select--")) {
                    dbTask.open();
                    model_ytpe_id = dbTask.getModelType_id(mode_type);
                    modelList = new ArrayList<>();
                    //modelList = dbTask.getmodel(model_ytpe_id);
                    modelList = dbTask.getmodel(model_ytpe_id);
                    if (model_TypeList.size() > 0) {
                        model.setVisibility(View.VISIBLE);
                    }
                    //modelList=dbTask.getmodel("BLE_MODULE");
                    modelAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, modelList);
                    modelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    model.setAdapter(modelAdapter);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        model.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    device = parent.getItemAtPosition(position).toString();
                    if  (!device.equals("--select--")) {
                        dbTask.open();
                        model_id = dbTask.getmodel_id(device);
                        connectBtn1.setVisibility(View.VISIBLE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        dgmaufactrur.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                if (!item.equals("--select--")) {
                    dbTask.open();
                    manufactrur_id = dbTask.manufactrur_id(item);
                    model_TypeList = dbTask.getdevice_detail(manufactrur_id);
                    ArrayAdapter<String> model_typeAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, model_TypeList);
                    model_typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    dgmodel_type.setAdapter(model_typeAdapter);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        dgmodel_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mode_type = parent.getItemAtPosition(position).toString();
                if (!mode_type.equals("--select--")) {
                    dbTask.open();
                    int dgmodel_ytpe_id = dbTask.getModelType_id(mode_type);
                    dgmodelList = new ArrayList<>();
                    dgmodelList = dbTask.getmodel(devicetypeval);
                    if (dgmodelList.size() > 0) {
                        dgmodel.setVisibility(View.VISIBLE);
                    }
                    //modelList=dbTask.getmodel("BLE_MODULE");
                    dgmodelAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, dgmodelList);
                    dgmodelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    dgmodel.setAdapter(dgmodelAdapter);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        dgmodel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    device_dgps = parent.getItemAtPosition(position).toString();
                    if (!device.equals("--select--")) {
                        dbTask.open();
                        dgpsmodel_id = dbTask.getmodel_id(device_dgps);
                        connectBtn1.setVisibility(View.VISIBLE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        connectBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbTask.open();
                String device_id = dbTask.getDevice_id1(model_id);
                String device_detail = dbTask.getDevice(model_id);
                String dgps_device_id =dbTask.getDevice_id1(dgpsmodel_id);
                String device_name = device_detail.split(",")[0];
                String device_address = device_detail.split(",")[1];
                Intent intent = new Intent(context, DeviceScanActivity.class);
                intent.putExtra("device_name", device_name);
                intent.putExtra("device_address", device_address);
                intent.putExtra("device_id", ""+device_id);
                intent.putExtra("dgps_device_id", dgps_device_id);



                Operation operation = new Operation();
                operation.setDeviceid(model_type_id);
                operation.setDevicename(device_name);
                operation.setBleid(Integer.parseInt(device_id));
                operation.setBlename(device);
                operation.setDgpsid(Integer.parseInt(dgps_device_id));
                operation.setDgpsname(device_dgps);
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


        // Inflate the layout for this fragment
        return view;
    }

    public  void update(){
        dgmaufactrurList = dbTask.getManufactrur(devicetypeval);
        updateAdapter(dgmaufactrurList);

    }
    public void updateAdapter(List<String> singleAddressw) {
        dgmauufactrurAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, singleAddressw);
        dgmaufactrur.setAdapter(dgmauufactrurAdapter);
    }

}
