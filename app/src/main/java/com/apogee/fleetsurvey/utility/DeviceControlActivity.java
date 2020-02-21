package com.apogee.fleetsurvey.utility;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothGattCharacteristic;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.apogee.fleetsurvey.Connect;
import com.apogee.fleetsurvey.Database.DatabaseOperation;
import com.apogee.fleetsurvey.R;
import com.apogee.fleetsurvey.bean.BleBean;
import com.apogee.fleetsurvey.model.BleModel;
import com.apogee.fleetsurvey.model.Operation;
import com.apogee.fleetsurvey.multiview.ItemType;
import com.apogee.fleetsurvey.multiview.OnItemValueListener;
import com.apogee.fleetsurvey.multiview.RecycerlViewAdapter;
import com.apogee.fleetsurvey.newproject;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class DeviceControlActivity extends AppCompatActivity implements BluetoothLeService.OnShowDailogListener, OnItemValueListener {
    private final static String TAG = DeviceControlActivity.class.getSimpleName();
    public static final String EXTRAS_DEVICE_NAME = "DEVICE_NAME";
    public static final String EXTRAS_DEVICE_ADDRESS = "DEVICE_ADDRESS";
    Spinner mode, Baudratespnr, comport, DYNmodel, maskangle, communicationmethod, radiobaudrate, radioAirdata, radioPower, radioChannel;
    Spinner config, suboperation;
    List<String> models;
    List<String> configls;
    List<String> suboprtnlist;
    List<String> parameterlist;
    List<Integer> commandls1;
    List<Integer> commandidlist;
    List<String> commandsfromlist;
    List<String> delaylist;
    List<Integer> parameterlst;
    private TextView mConnectionState;
    private TextView mDataField;
    Button btn1, btn2, btn3;
    private String mDeviceName, device_type = "";
    boolean sendst = true;
    ImageButton connect, refresh, tcpConnect, end;
    String item = "";
    String subitem = "";
    public static String textview;
    ListView deviceListView;
    private String mDeviceAddress;
    public static String lat_lang;
    public static String latlongvalue;
    public static String StatusData;
    String[] Parameter = null;
    public byte[] data = null;
    private ArrayList<String> list;
    private ArrayList<String> list1;
    private ArrayList<String> list2;
    private ArrayList<String> list3;
    private List<Operation> operationArrayList;
    ArrayList<String> baudratevaluelist;
    ArrayList<String> airDatavaluelist;
    ArrayList<String> powerValuelist;
    ArrayList<String> channelControlValuelist;
    private ArrayAdapter<String> arrayAdapter;
    private ArrayAdapter<Operation> arrayAdaptersuboperation;
    private ArrayAdapter<String> arrayAdapter4;
    String latitude = "";
    String longitude = "";
    String altitude = "";
    String rovervalue = "";
    String baservalue = "";

    /*tAKING VARIABLE FOR SPINNER TEXT*/

    String comporttxt = null;
    String cmnmthdtxt = null;
    String Radiobaudratetxt = null;
    String RadioAirdatatxt = null;
    String Radiopowertxt = null;
    String Radiochanneltxt = null;
    //    String accuracyvalue=null;
//    String surveytimevalue=null;
    public List<String> newCommandList = new ArrayList<String>();
    public List<String> finalCommandList = new ArrayList<>();
    public List<String> newRadioCommandList = new ArrayList<>();

    Map<String, String> map = new HashMap<String, String>();
    Map<String, String> map1 = new HashMap<String, String>();
    Map<String, String> map3 = new HashMap<String, String>();
    Map<String, String> selectionValue1 = new HashMap<String, String>();
    Map<String, String> selectionValue2 = new HashMap<String, String>();
    Map<String, String> selectionValue3 = new HashMap<String, String>();
    Map<String, String> selectionValue4 = new HashMap<String, String>();
    Map<String, String> selectionValue5 = new HashMap<String, String>();
    Map<String, String> selectionValue6 = new HashMap<String, String>();
    Map<String, String> baudrateRadio = new HashMap<String, String>();
    Map<String, String> airDataRadio = new HashMap<String, String>();
    Map<String, String> parityRadio = new HashMap<String, String>();
    Map<String, String> packetSizeRadio = new HashMap<String, String>();
    Map<String, String> powerRadio = new HashMap<String, String>();
    Map<String, String> reservedRadio = new HashMap<String, String>();
    Map<String, String> channelControlRadio = new HashMap<String, String>();

    ArrayList<HashMap<String, String>> contactList;

    //private ExpandableListView mGattServicesList;
    private BluetoothLeService mBluetoothLeService;
    private ArrayList<ArrayList<BluetoothGattCharacteristic>> mGattCharacteristics =
            new ArrayList<ArrayList<BluetoothGattCharacteristic>>();
    private boolean mConnected = false;
    private BluetoothGattCharacteristic mNotifyCharacteristic;
    ArrayAdapter<String> listAdapter;
    ArrayList<String> deviceList;
    DatabaseOperation dbTask = new DatabaseOperation(this);
    int ble_operation_id = 0, device_id = 0, opid = 0, dgps_id = 0;
    public static boolean tcpconnect = true;
    public static String finalResponse;

    /*BROADCAST RECIEVER USED FOR CONNECTION OF BLE */


    ConcurrentHashMap<String, String> hashmapforremark = new ConcurrentHashMap<>();
    private final BroadcastReceiver mGattUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            if (BluetoothLeService.ACTION_GATT_CONNECTED.equals(action)) {
                mConnected = true;
                //connect.setText("Disconnect");
                connect.setImageResource(R.drawable.connected7);
                updateConnectionState(R.string.connected);
                Toast.makeText(context, "Your connection request sucessfully", Toast.LENGTH_SHORT).show();
                invalidateOptionsMenu();
//
            } else if (BluetoothLeService.ACTION_GATT_DISCONNECTED.equals(action)) {
                mConnected = false;
                //connect.setText("Connect");
                connect.setImageResource(R.drawable.disconnected7);
                updateConnectionState(R.string.disconnected);
                Toast.makeText(context, "Your connection request fail ! Retry again click on connect button", Toast.LENGTH_SHORT).show();
                //Toast.makeText(context, "SD Card not readable", Toast.LENGTH_LONG).show();
                invalidateOptionsMenu();
                clearUI();
            } else if (BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED.equals(action)) {
                Toast.makeText(DeviceControlActivity.this, "GATT SERVICE DISCOVERED", Toast.LENGTH_SHORT).show();
                // Show all the supported services and characteristics on the user interface.
                //displayGattServices(mBluetoothLeService.getSupportedGattServices());
            } else if (BluetoothLeService.ACTION_DATA_AVAILABLE.equals(action)) {
                displayData(intent.getStringExtra(BluetoothLeService.EXTRA_DATA));
            }
        }
    };

    private final ExpandableListView.OnChildClickListener servicesListClickListner =
            new ExpandableListView.OnChildClickListener() {
                @Override
                public boolean onChildClick(ExpandableListView parent, View v, int groupPosition,
                                            int childPosition, long id) {
                    if (mGattCharacteristics != null) {
                        final BluetoothGattCharacteristic characteristic =
                                mGattCharacteristics.get(groupPosition).get(childPosition);
                        final int charaProp = characteristic.getProperties();
                        if ((charaProp | BluetoothGattCharacteristic.PROPERTY_READ) > 0) {
                            // If there is an active notification on a characteristic, clear
                            // it first so it doesn't update the data field on the user interface.
                            if (mNotifyCharacteristic != null) {
                                mBluetoothLeService.setCharacteristicNotification(
                                        mNotifyCharacteristic, false);
                                mNotifyCharacteristic = null;
                            }
                            mBluetoothLeService.readCharacteristic(characteristic);
                        }
                        if ((charaProp | BluetoothGattCharacteristic.PROPERTY_NOTIFY) > 0) {
                            mNotifyCharacteristic = characteristic;
                            mBluetoothLeService.setCharacteristicNotification(
                                    characteristic, true);
                        }
                        return true;
                    }
                    return false;
                }
            };

    private void clearUI() {
        //mGattServicesList.setAdapter((SimpleExpandableListAdapter) null);
        // mDataField.setText(R.string.no_data);

    }

    RecycerlViewAdapter recycerlViewAdapter;
    RecyclerView recylcerview;
    List<ItemType> itemTypeList = new ArrayList<>();
    Callingserver1 callingserver1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gatt_services_characteristics);
        recylcerview = findViewById(R.id.recylcerview);
        recycerlViewAdapter = new RecycerlViewAdapter(itemTypeList, this);
        recylcerview.setAdapter(recycerlViewAdapter);
        final Intent intent = getIntent();
        mDeviceName = intent.getStringExtra(EXTRAS_DEVICE_NAME);
        mDeviceAddress = intent.getStringExtra(EXTRAS_DEVICE_ADDRESS);
        String d_id = intent.getStringExtra("device_id");
        device_id = Integer.parseInt(d_id);
        String dgpsid = intent.getStringExtra("dgps_device_id");
        dgps_id = Integer.parseInt(dgpsid);
        deviceListView = (ListView) findViewById(R.id.deviceListView);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        mode = (Spinner) findViewById(R.id.type);

        Baudratespnr = (Spinner) findViewById(R.id.baudratespnr);
        config = (Spinner) findViewById(R.id.config);
        suboperation = (Spinner) findViewById(R.id.suboprtnspnr);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        ((TextView) findViewById(R.id.device_address)).setText(mDeviceAddress);
        mConnectionState = (TextView) findViewById(R.id.connection_state);
        connect = (ImageButton) findViewById(R.id.conect);
        refresh = (ImageButton) findViewById(R.id.img1);
        tcpConnect = (ImageButton) findViewById(R.id.tcp);
        end = (ImageButton) findViewById(R.id.end);
        dbTask = new DatabaseOperation(DeviceControlActivity.this);
        // mDataField = (TextView) findViewById(R.id.data_value);
        listAdapter = new ArrayAdapter<>(this,
                R.layout.list_textview);
        deviceList = new ArrayList<>();
        deviceListView.setAdapter(listAdapter);
        deviceListView.setOnItemClickListener(mDeviceClickListener);
        list = new ArrayList<>();
        list1 = new ArrayList<>();
        list2 = new ArrayList<>();
        list3 = new ArrayList<>();

        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        operationArrayList = new ArrayList<>();
        arrayAdaptersuboperation = new ArrayAdapter<>(this, android.R.layout.simple_gallery_item, operationArrayList);
        arrayAdapter4 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list3);
        list.add("--Select--");
        list1.add("--Select--");
        list2.add("--Select--");
        list3.add("--Select--");

        datumcommand();


        //editval=(EditText)findViewById(R.id.val);
        connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  mBluetoothLeService.connect(mDeviceAddress,DeviceControlActivity.this,1);
                if (mConnected) {
                    mBluetoothLeService.disconnect();
                    connect.setImageResource(R.drawable.connected7);
                } else {
                    mBluetoothLeService.connect(mDeviceAddress, DeviceControlActivity.this, device_id, opid);
                    connect.setImageResource(R.drawable.disconnected7);
                }
            }
        });
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Callingserver callingserver = new Callingserver();
                callingserver.execute();
            }
        });


        /*ALL THE SPINNER DEFINE BELOW WITH THEIR DATA*/
        dbTask.open();
        models = dbTask.getBleoperations();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, models);
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        mode.setAdapter(dataAdapter);
        mode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                if (!item.equals("--select--")) {
                    dbTask.open();
                    int bleOperation_id = dbTask.bleOperation_id(item);
                    mBluetoothLeService.conectToService(device_id, bleOperation_id);
                    mBluetoothLeService.connectTcp();

                }
                Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        configls = new ArrayList<>();
        configls.add("--select--");
        dbTask.open();
        //  configls = dbTask.getoperation(dgps_id);
        List<Operation> configls = dbTask.getoperationParent(dgps_id);

        ArrayAdapter<Operation> configAdapter = new ArrayAdapter<Operation>(this, android.R.layout.simple_spinner_item, configls);
        configAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        config.setAdapter(configAdapter);
        config.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Operation operation = (Operation) parent.getSelectedItem();

                //  item = parent.getItemAtPosition(position).toString();
                if (!operation.getName().equals("--select--")) {
                    itemTypeList.clear();
                    recycerlViewAdapter.notifyDataSetChanged();
                    // Operation operation = (Operation) parent.getItemAtPosition(position);
                    // operationArrayList.clear();
                    operationArrayList = dbTask.getchild(operation.getId());
                    updateAdapter4(null);
                    arrayAdaptersuboperation.notifyDataSetChanged();
                    //   arrayAdaptersuboperation = new ArrayAdapter<Operation>(DeviceControlActivity.this, android.R.layout.simple_spinner_item,listchilds );

                    getcommandid(operation.getName());
                    getcommandforparsing();
                    opid = dbTask.detopnameid(operation.getName());
                    if (tcpconnect) {
                        opid = dbTask.detopnameid(operation.getName());
                        // mBluetoothLeService.send(item, DeviceControlActivity.this, false, true);

                    } else {
                        Toast.makeText(DeviceControlActivity.this, "please select Tcp ip port", Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        suboperation.setAdapter(arrayAdaptersuboperation);
        suboperation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Operation operation = (Operation) parent.getSelectedItem();

                if (!operation.getName().equals("--select--")) {

                    if (!operation.getissupechild().equalsIgnoreCase("on")) {
                        operationArrayList.clear();
                        operationArrayList = dbTask.getchild(operation.getId());
                        updateAdapter4(null);
                    } else {
                        itemTypeList.clear();
                        getcommandid(operation.getName());
                        getcommandforparsing();
                        opid = dbTask.detopnameid(operation.getName());
                    }

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    public void configsetting(View view) {
        alertdialog();
    }

    /*ALERTDIALOG VIEW FOR RADIO CONNECTIVITY */

    public void Radiobasedialog() {
        final androidx.appcompat.app.AlertDialog dialogBuilder = new androidx.appcompat.app.AlertDialog.Builder(this).create();
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialogradiobase, null);
        dialogBuilder.setTitle("Radio Base");
        final Spinner spnr1 = (Spinner) dialogView.findViewById(R.id.baudratespnr);
        final Spinner spnr2 = (Spinner) dialogView.findViewById(R.id.Airdataratespnr);
        final Spinner spnr3 = (Spinner) dialogView.findViewById(R.id.powerspnr);
        final Spinner spnr4 = (Spinner) dialogView.findViewById(R.id.channelspnr);
        Button button1 = (Button) dialogView.findViewById(R.id.btnbase);
        ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, baudratevaluelist);
        arrayAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnr1.setAdapter(arrayAdapter1);
        spnr1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Radiobaudratetxt = parent.getItemAtPosition(position).toString();
                Toast.makeText(parent.getContext(), "Selected: " + Radiobaudratetxt, Toast.LENGTH_LONG).show();

            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, airDatavaluelist);
        arrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnr2.setAdapter(arrayAdapter2);
        spnr2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                RadioAirdatatxt = parent.getItemAtPosition(position).toString();
                Toast.makeText(parent.getContext(), "Selected: " + RadioAirdatatxt, Toast.LENGTH_LONG).show();

            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        ArrayAdapter<String> arrayAdapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, powerValuelist);
        arrayAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnr3.setAdapter(arrayAdapter3);
        spnr3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Radiopowertxt = parent.getItemAtPosition(position).toString();
                Toast.makeText(parent.getContext(), "Selected: " + Radiopowertxt, Toast.LENGTH_LONG).show();

            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        ArrayAdapter<String> arrayAdapter4 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, channelControlValuelist);
        arrayAdapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnr4.setAdapter(arrayAdapter4);
        spnr4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Radiochanneltxt = parent.getItemAtPosition(position).toString();
                Toast.makeText(parent.getContext(), "Selected: " + Radiochanneltxt, Toast.LENGTH_LONG).show();

            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<String> commandls = gettingcommandforRAdio(cmnmthdtxt);
                dialogBuilder.dismiss();
            }
        });

        dialogBuilder.setView(dialogView);
        dialogBuilder.show();
    }





    /*THIS BUTTON IS USED FOR FINAL COMMAND OUT*/

    public void Finaldone(View view) {

        View current = getCurrentFocus();
        if (current != null) current.clearFocus();
        callingserver1 = new Callingserver1(SENDALL);
        callingserver1.execute();

//        mBluetoothLeService.setOnShowDialogListner(this);


    }


    public void previewsubmission(View view) throws InterruptedException {
        View current = getCurrentFocus();
        if (current != null) current.clearFocus();
        newCommandList.clear();
        callingserver1 = new Callingserver1(SENDONEBYDIALOG);
        callingserver1.execute();

    }


    /*IN THIS METHOD WE FETCH ALL THE RELATABLE COMMAND OF BASE AND ROVER LIKE GPS , BIODU , GLONASSS*/

    public List<String> gettingmorecommand(String item1, String comporttxt, int Device_id) {
        List<String> list = new ArrayList<>();
        List<String> list1 = new ArrayList<>();
        List<String> list2 = new ArrayList<>();
        ArrayList<String> list3 = new ArrayList<>();
        ;
        if (item1.equalsIgnoreCase(item)) {
            String operation_name = subitem;
            list1 = dbTask.getoperationid(operation_name, Device_id);
            String subop_id = dbTask.getsubopid(operation_name);
            String joined1 = TextUtils.join(", ", list1);
            list2 = dbTask.getsomecommandlist(joined1);
            list3 = dbTask.delaylist2(joined1, subop_id, dgps_id);

//            String operation_name1 = "Save all command Rover";
//            list2 = dbTask.getoperationid(operation_name1,Device_id);
            list.addAll(list2);
            delaylist.addAll(list3);

//            list.addAll(list2);
        }

        return list;

    }

    public List<String> gettingcommandforRAdio(String item) {
        List<String> list = new ArrayList<>();
        List<String> list1 = new ArrayList<>();
        if (item.contains("LORA")) {
            String operation_name = "M_LoRa_Configuration";
            list1 = dbTask.getradiocommandlist(32);
            editRadioCommand(list1);
        }


        return list;
    }

    /*IN THIS METHOD ALL THE DATA CONVERTED AND ASSEMBLE FOR FINAL COMMAND FORMATION*/

    public void dataconversion(int sentype) {
        boolean iscontain = false;

        for (String commmand : commandsfromlist) {
            if (commmand.contains("CRC")) {
                iscontain = true;
                break;
            }

        }

        if (iscontain) {


//            String value = "";
//            Set<String> bytevalue = map1.keySet();
//            for (String param : bytevalue) {
//                String disval = map1.get(param);
//                value = selectionValue2.get(disval);
//                if (value == null) {
//                    int value1 = Integer.parseInt(disval);
//                    value = bytesToHex(intToLittleEndian1(value1)).toUpperCase();
//                }
//                System.out.println(value);
//                map.put(param, value);
//
//            }
            editCommand(commandsfromlist, sentype);
        } else {
            newCommandList.addAll(commandsfromlist);
            if (sentype == 1) {
                mBluetoothLeService.send(item, DeviceControlActivity.this,
                        false, false, newCommandList, newRadioCommandList, delaylist);
            }


        }


    }

    private static byte[] intToLittleEndian1(long numero) {
        byte[] b = new byte[4];
        b[0] = (byte) (numero & 0xFF);
        b[1] = (byte) ((numero >> 8) & 0xFF);
        b[2] = (byte) ((numero >> 16) & 0xFF);
        b[3] = (byte) ((numero >> 24) & 0xFF);
        return b;
    }

    public static String bytesToHex(byte[] in) {
        final StringBuilder builder = new StringBuilder();
        for (byte b : in) {
            builder.append(String.format("%02x", b));
        }
        return builder.toString();
    }

    public void datumcommand() {
        dbTask.open();
        newproject np = new newproject();
        String datumname = np.datumitem;
        int datumopid = dbTask.datumopid(datumname);
        int datumcmndid = dbTask.datumcommandid(datumopid);
        String datumcommand = dbTask.datumcommand(datumcmndid);

    }

/*IN THIS METHOD ALL THE DATABASE WORK PERFORMED LIKE GET OPERATION ID , SELECTION ID ,  COMMAND ID ETC
ON THE BASIS OF THESE WE GET SELECTION VALUE , PARAMETER NAME AND ADD THE VALUES IN UPDATEADAPTER
* */

    public void getcommandid(String operationname) {
        dbTask.open();
        opid = dbTask.detopnameid(operationname);
        commandls1 = new ArrayList<>();
        parameterlist = new ArrayList<>();
        commandls1 = dbTask.commandidls1(opid, dgps_id);


        suboprtnlist = new ArrayList<>();
        suboprtnlist = dbTask.getsuboperationname(opid);
        //updateAdapter4(suboprtnlist);
        String joined = TextUtils.join(", ", commandls1);
        ArrayList<Integer> parameteridlist = new ArrayList<>();
        parameteridlist = (ArrayList<Integer>) dbTask.parameteridlist(joined);
        String joined1 = TextUtils.join(", ", parameteridlist);
        Map<String, Map<String, Map<String, String>>> radiodatamap = new HashMap<>();
        radiodatamap = dbTask.radiodata(joined);
        Set<String> radiodata = radiodatamap.keySet();
        for (String param : radiodata) {
            if (param.equalsIgnoreCase("REG0")) {
                Map<String, Map<String, String>> selectionValue1 = new HashMap<>();
                selectionValue1 = radiodatamap.get(param);
                Set<String> REG0key = selectionValue1.keySet();
                for (String param1 : REG0key) {
                    if (param1.equalsIgnoreCase("BaudRate")) {
                        baudrateRadio = selectionValue1.get(param1);
                        Set<String> baudrateKeyRadio = baudrateRadio.keySet();
                        Collection<String> baudratevalueRadio = baudrateRadio.values();
                        baudratevaluelist = new ArrayList<String>(baudrateKeyRadio);
//                      list4.add(baudratevaluelist);
//                      updateAdapter(baudratevaluelist);
                        System.out.println("Initial values : " + param1);
                    } else if (param1.equalsIgnoreCase("AirData")) {
                        airDataRadio = selectionValue1.get(param1);
                        Set<String> airDataKeyRadio = airDataRadio.keySet();
                        Collection<String> airDataValueRadio = airDataRadio.values();
                        airDatavaluelist = new ArrayList<String>(airDataKeyRadio);

                        System.out.println("Initial values : " + param1);
                    } else if (param1.equalsIgnoreCase("parity")) {
                        parityRadio = selectionValue1.get(param1);
                        Set<String> parityKeyRadio = parityRadio.keySet();
                        Collection<String> parityvalueRadio = parityRadio.values();
                        List<String> parityvaluelist = new ArrayList<String>(parityKeyRadio);
                        System.out.println("Initial values : " + param1);
                    }

//
                }
            } else if (param.equalsIgnoreCase("REG1")) {
                Map<String, Map<String, String>> selectionValue2 = new HashMap<>();
                selectionValue2 = radiodatamap.get(param);
                Set<String> REG1key = selectionValue2.keySet();
                for (String param1 : REG1key) {
                    if (param1.equalsIgnoreCase("Packet Size")) {
                        packetSizeRadio = selectionValue2.get(param1);
                        Set<String> packetSizeKeyRadio = packetSizeRadio.keySet();
                        Collection<String> packetSizevalueRadio = packetSizeRadio.values();
                        List<String> packetSizeValuelist = new ArrayList<String>(packetSizeKeyRadio);
//                      updateAdapter(packetSizeValuelist);
                        System.out.println("Initial values : " + param1);
                    } else if (param1.equalsIgnoreCase("Power")) {
                        powerRadio = selectionValue2.get(param1);
                        Set<String> powerKeyRadio = powerRadio.keySet();
                        Collection<String> powerValueRadio = powerRadio.values();
                        powerValuelist = new ArrayList<String>(powerKeyRadio);
                        System.out.println("Initial values : " + param1);
                    } else if (param1.equalsIgnoreCase("Reserved")) {
                        reservedRadio = selectionValue2.get(param1);
                        Set<String> reservedKeyRadio = reservedRadio.keySet();
                        Collection<String> reservedValueRadio = reservedRadio.values();
                        List<String> reservedValuelist = new ArrayList<String>(reservedKeyRadio);
                        System.out.println("Initial values : " + param1);
                    }
                }
            } else if (param.equalsIgnoreCase("REG2")) {
                Map<String, Map<String, String>> selectionValue3 = new HashMap<>();
                selectionValue3 = radiodatamap.get(param);
                Set<String> REG2key = selectionValue3.keySet();
                for (String param1 : REG2key) {
                    if (param1.equalsIgnoreCase("Channel Control")) {
                        channelControlRadio = selectionValue3.get(param1);
                        Set<String> channelcontrolKeyRadio = channelControlRadio.keySet();
                        Collection<String> channelControlValueRadio = channelControlRadio.values();
                        channelControlValuelist = new ArrayList<String>(channelcontrolKeyRadio);
                        System.out.println("Initial values : " + param1);
                    }
                }
            }
        }
        ArrayList<String> parameter_namelist = new ArrayList<>();
        parameter_namelist = (ArrayList<String>) dbTask.parameternamelist(joined1);

        ArrayList<Integer> selectionidlist = new ArrayList<>();
        selectionidlist = (ArrayList<Integer>) dbTask.selectionidlist(joined);
        String joined2 = TextUtils.join(", ", selectionidlist);

        ArrayList<Integer> inputlist = new ArrayList<>();
        inputlist = (ArrayList<Integer>) dbTask.inputlist(joined);
        String joined3 = TextUtils.join(", ", inputlist);

        Map<String, Map<String, String>> selectionList = new HashMap<>();
        selectionList = dbTask.displayvaluelist(joined2);
        Set<String> selectionParameter = selectionList.keySet();

        for (String param : selectionParameter) {
            selectionValue1 = selectionList.get(param);
            Set<String> baudratekey = selectionValue1.keySet();
            Collection<String> baudratevalue = selectionValue1.values();
            List<String> baudratevaluelist = new ArrayList<String>(baudratekey);
            baudratevaluelist.add(0, "--select--");


//            itemTypeList.add(new ItemType(ItemType.DROPDOWNTYPE, param, baudratevaluelist));
            itemTypeList.add(new ItemType(ItemType.DROPDOWNTYPE, param, selectionValue1));
            recycerlViewAdapter.notifyDataSetChanged();
            selectionValue2.putAll(selectionValue1);
            System.out.println("Initial values : " + baudratevalue);//
        }

        ArrayList<String> inputparameterlist = new ArrayList<>();
        inputparameterlist = (ArrayList<String>) dbTask.inputparameterlist(joined3);
        for (String inputparam : inputparameterlist) {
//            itemTypeList.add(new ItemType(ItemType.INPUTTYPE, inputparam,null));
            itemTypeList.add(new ItemType(ItemType.INPUTTYPE, inputparam));
            recycerlViewAdapter.notifyDataSetChanged();
        }

        int lsSize1 = commandls1.size();
        dbTask.close();
    }

    /*THESE ADAPTER IS USED FOR UPDATE THE SPINNER ADAPTER */


    public void updateAdapter4(List<String> singleAddressy) {

        int visiblity = (operationArrayList.size() > 1) ? View.VISIBLE : View.GONE;
        ((LinearLayout) findViewById(R.id.sublayout)).setVisibility(visiblity);

        arrayAdaptersuboperation = new ArrayAdapter<Operation>(this, android.R.layout.simple_dropdown_item_1line, operationArrayList);
        suboperation.setAdapter(arrayAdaptersuboperation);

    }


    public void getcommandforparsing() {
        String joined = TextUtils.join(", ", commandls1);
        commandidlist = new ArrayList<>();
        commandsfromlist = new ArrayList<String>();
        delaylist = new ArrayList<>();
        dbTask.open();
//        commandidlist = dbTask.commandididlist(joined);
//        String joined1 = TextUtils.join(", ", commandidlist);
        delaylist = dbTask.delaylist(joined, opid, dgps_id);

        hashmapforremark = dbTask.commandforparsinglist(dgps_id, opid);
        Collection<String> values = hashmapforremark.values();
        commandsfromlist.addAll(values);


    }


    /*THIS METHOD IS USED FOR EDIT RADIO COMMANDS ON THE BASIS OF REG0,REG1,REG2*/

    public void editRadioCommand(List<String> radiocommand) {
        int i = 1;
        int[] index = new int[3];
        for (String command : radiocommand) {
            int index1 = command.indexOf('/');
            while (index1 >= 0) {
                System.out.println(index1);
                index[i] = index1;
                index1 = command.indexOf('/', index1 + 1);


                if (i == 2) {
                    String key = command.substring(index[1] + 1, index[2]);
                    if (key.equals("REG0")) {
                        String reg0binary = baudrateRadio.get(Radiobaudratetxt) + "00" + airDataRadio.get(RadioAirdatatxt);
                        int reg0 = Integer.parseInt(reg0binary, 2);
                        String reg0Hex = Integer.toHexString(reg0);
                        String actualKey = command.substring(index[1], index[2] + 1);
                        command = command.replace(actualKey, reg0Hex.toUpperCase());

                    } else if (key.equals("REG1")) {
                        String reg1binary = "00" + "00" + powerRadio.get(Radiopowertxt);
                        int reg1 = Integer.parseInt(reg1binary, 2);
                        String reg1Hex = Integer.toHexString(reg1);
                        if (reg1Hex.length() == 1) {
                            reg1Hex = "0" + reg1Hex;
                        }
                        String actualKey = command.substring(index[1], index[2] + 1);
                        command = command.replace(actualKey, reg1Hex.toUpperCase());
                    } else if (key.equals("REG2")) {
                        String reg2binary = channelControlRadio.get(Radiochanneltxt);
                        int reg2 = Integer.parseInt(reg2binary, 2);
                        String reg2Hex = Integer.toHexString(reg2);
                        String actualKey = command.substring(index[1], index[2] + 1);
                        command = command.replace(actualKey, reg2Hex);
                    }


                    i = 1;
                    index = new int[3];
                    index1 = command.indexOf('/');
                } else {
                    i++;
                }


            }
            newRadioCommandList.add(command);

        }

    }
    /**/
    /* THIS METHOD IS USED FOR MAKING FINAL COMMAND FOR BASE AND ROVER */

    public void editCommand(List<String> commandsfromlist, int sendtype) {
        int i = 1;
        int[] index = new int[3];
        for (String command : commandsfromlist) {
            String crctypename = dbTask.returnCRCType(command);
            //  crctypename = crctypename.replaceAll("\\s", "");
            String splitstr[] = command.split("/");
            String final_command = "";


            for (int j = 0; j < splitstr.length; j++) {

                if (map1.containsKey(splitstr[j])) {
                    splitstr[j] = map1.get(splitstr[j]);

                }

                final_command = final_command.concat(splitstr[j]).replaceAll("\\s+", "");

                if (final_command.contains("CRC")) {
                    if (MODBUSCRC16.modbuscrcclassname.equals(crctypename)) {
                        final_command = final_command.replace("CRC", new MODBUSCRC16().returnCRCHexstring((final_command.substring(0, final_command.indexOf("CRC")))));

                    } else if (FLETCHERALGORITHM.fletcheralgoname.equals(crctypename)) {
                        final_command = final_command.replace("CRC", new FLETCHERALGORITHM().checksum((final_command.substring(4, final_command.indexOf("CRC")))));

                    }
//                    else {
//                        final_command = final_command.replace("CRC", new FLETCHERALGORITHM().checksum((final_command.substring(4, final_command.indexOf("CRC")))));
//
//                    }


//                    else if (FLETCHERALGORITHM.fletcheralgoname.contains(crctypename)) {
//                        final_command = final_command.replace("CRC", new FLETCHERALGORITHM().checksum((final_command.substring(0, final_command.indexOf("CRC")))));
//
//                    }
                }
            }


            newCommandList.add(final_command.concat("0D0A"));

            for (Map.Entry entry : hashmapforremark.entrySet()) {
                String key = (String) entry.getKey();
                hashmapforremark.replace(key, command, final_command);

            }
            if (sendtype == 1)

                mBluetoothLeService.send(item, DeviceControlActivity.this, false, false, newCommandList, newRadioCommandList, delaylist);

        }

//        StringBuilder stringBuilder = new StringBuilder();
//        stringBuilder.append("param with values:\n\n");
//
//
//        for (Map.Entry m : map1.entrySet()) {
//            stringBuilder.append(m.getKey() + " = " + m.getValue()+"\n");
//        }
//        stringBuilder.append("\n\ncommands here\n\n");
//        for(String cmd:newCommandList){
//            stringBuilder.append(cmd+"\n\n\n");
//        }
//        stringBuilder.append("\n\nAll folks");
//
//        Intent email = new Intent(Intent.ACTION_SEND);
//        email.putExtra(Intent.EXTRA_EMAIL, new String[]{"shwetajpss@gmail.com"});
//        email.putExtra(Intent.EXTRA_SUBJECT, "Command to check");
//        email.putExtra(Intent.EXTRA_TEXT, stringBuilder.toString());
//
////need this to prompts email client only
//        email.setType("message/rfc822");
//
//        startActivity(Intent.createChooser(email, "Choose an Email client :"));
    }

    String devicetype;
    String communicationtype;

    public void timedialog() {
        final androidx.appcompat.app.AlertDialog dialogBuilder = new androidx.appcompat.app.AlertDialog.Builder(this).create();
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialogtime, null);
        dialogBuilder.setTitle("4G Configuration");
        final Spinner spnr1 = (Spinner) dialogView.findViewById(R.id.op);
        final Spinner spnr2 = (Spinner) dialogView.findViewById(R.id.cmnctn);
        final EditText editText1 = (EditText) dialogView.findViewById(R.id.et1);
        final EditText editText2 = (EditText) dialogView.findViewById(R.id.et2);
        final EditText editText3 = (EditText) dialogView.findViewById(R.id.et3);
        final EditText editText4 = (EditText) dialogView.findViewById(R.id.et4);
        Button button1 = (Button) dialogView.findViewById(R.id.timebt);
        map3.put("Base", "1");
        map3.put("Rover", "2");
        map3.put("4G", "6");
        map3.put("Radio/LoRa", "5");
        ArrayList<String> devicetypekeyvalue = new ArrayList<String>();
        ArrayList<String> communicationmethod = new ArrayList<String>();
        devicetypekeyvalue.add("Base");
        devicetypekeyvalue.add("rover");
        ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, devicetypekeyvalue);
        arrayAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnr1.setAdapter(arrayAdapter1);
        spnr1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                devicetype = parent.getItemAtPosition(position).toString();
                Toast.makeText(parent.getContext(), "Selected: " + devicetype, Toast.LENGTH_LONG).show();

            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        communicationmethod.add("4G");
        communicationmethod.add("Radio/LoRa");
        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, communicationmethod);
        arrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnr2.setAdapter(arrayAdapter2);
        spnr2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                communicationtype = parent.getItemAtPosition(position).toString();
                Toast.makeText(parent.getContext(), "Selected: " + communicationtype, Toast.LENGTH_LONG).show();

            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String devicevalll = map3.get(devicetype);
                String comval = map3.get(communicationtype);
                String mount_name = editText1.getText().toString();
                String password = editText2.getText().toString();
                String Ip = editText3.getText().toString();
                String port = editText4.getText().toString();
                String finalstring = "$$$$,02,00001,06,0" + comval + "," + password + "," + mount_name + "," + Ip + "," + port + ",0000,####";
                Toast.makeText(DeviceControlActivity.this, finalstring, Toast.LENGTH_SHORT).show();
            }
        });

        dialogBuilder.setView(dialogView);
        dialogBuilder.show();
    }

    /*This dialog box shows the alert about Device Configuration*/
    public void alertdialog() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(DeviceControlActivity.this);
        builder1.setMessage("Configuration for");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Lora/Radio",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
//                        Radiobasedialog();
                        dialog.cancel();
                    }
                });

        builder1.setNegativeButton(
                "4G",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        timedialog();
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    /*THIS METHOD IS USED FOR CHECKSUM VALUE OF COMMAND*/
    public String checksum(String command) {
        String[] commandPair = new String[(command.length() / 2) + 1];  //new String[5+ 1]: RESULT commandPair[0]=01
        int j = 0;                                      // j=0
        int size = command.length();//10
        for (int i = 0; i < size; i += 2) {                 //int i = 0; i < 10; i += 2(2)
            commandPair[j] = command.substring(i, i + 2);   //commandPair[0] = command.substring(0, 2):RESULT 01
            j++;                                            // j=1
        }
        String ch_A = "0";
        String ch_B = "0";
        int length = commandPair.length - 1;
        for (int i = 0; i < length; i++) {
            ch_A = addCheckSum(ch_A, commandPair[i]);
            ch_B = addCheckSum(ch_B, ch_A);
        }
        ch_A = Integer.toHexString(Integer.parseInt(ch_A, 16) & 0xFF).toUpperCase();
        ch_B = Integer.toHexString(Integer.parseInt(ch_B, 16) & 0xFF).toUpperCase();
        if (ch_A.length() == 1) {
            ch_A = "0" + ch_A;
        }
        if (ch_B.length() == 1) {
            ch_B = "0" + ch_B;
        }
        return ch_A + ch_B;
    }

    public String addCheckSum(String ch_A, String ch_B) {
        int A = Integer.parseInt(ch_A, 16);
        int B = Integer.parseInt(ch_B, 16);
        int sum = A + B;
        return Integer.toHexString(sum);

    }

    @Override
    public void returnValue(String title, String finalvalue) {
        if (!finalvalue.equalsIgnoreCase("--select--")) {
            String titl = title;
            String returnname = finalvalue;
            map1.put(titl, finalvalue);

        }

    }

    public static int SENDALL = 1;
    public static int SENDONEBYDIALOG = 2;

    public class Callingserver1 extends AsyncTask<String, String, Integer> {
        // ProgressDialog dialog;

        int sendtype;

        public Callingserver1(int sendtype) {
            this.sendtype = sendtype;
        }

        @SuppressLint("WrongThread")
        @Override
        protected Integer doInBackground(String... params) {
            String result = "";

            try {

                if (newCommandList.size() == 0) {
                    dataconversion(sendtype);

                } else {
                    sendst = true;

                    if (sendtype == 1) {
                        mBluetoothLeService.send(item, DeviceControlActivity.this, false, false,
                                newCommandList, newRadioCommandList, null);
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return sendtype;
        }

        @Override
        protected void onPostExecute(Integer result) {
// execution of result of Long time consuming operation
            // dialog.dismiss();
            if (result.equals("success")) {
                //  dialog.dismiss();


            } else {
                //

            }

            if (result == 2) {
                setCommandidialog();
            }

            cancel(true);
            callingserver1 = null;
        }

        @Override
        protected void onPreExecute() {
//            dialog = ProgressDialog.show(DeviceControlActivity.this, "Command Execution..", "Proccessing....Please wait");
//            dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
//            dialog.setProgress(0);
//            dialog.setMax(100);
//            dialog.show();

// Things to be done before execution of long running operation. For
// example showing ProgessDialog
        }

        @Override
        protected void onProgressUpdate(String... text) {
            //firstBar.
            // Things to be done while execution of long running operation is in
            // progress. For example updating ProgessDialog
        }
    }

    ArrayList<String> tempcomnd = new ArrayList<>();

    public void setCommandidialog() {
        final Dialog dialog = new Dialog(DeviceControlActivity.this);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_showcommands);
        ImageView buttoncancel = dialog.findViewById(R.id.iv_cancel);
        buttoncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        final ListView listView = dialog.findViewById(R.id.dialog_listview);
        tempcomnd.clear();


        for (Map.Entry entry : hashmapforremark.entrySet()) {
            tempcomnd.add(entry.getKey() + " = " + entry.getValue());
        }

        //   tempcomnd.addAll(newCommandList);


        listView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, tempcomnd));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String commandlocal = parent.getItemAtPosition(position).toString();
                List<String> listlocal = new ArrayList();
                listlocal.add(commandlocal);

                mBluetoothLeService.send(item, DeviceControlActivity.this, false, false,
                        listlocal, newRadioCommandList, null);

            }
        });
        dialog.show();
    }

    /**
     * customizable toast
     *
     * @param message
     */
    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private AdapterView.OnItemClickListener mDeviceClickListener
            = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView<?> av, View v, int arg2, long arg3) {
        }
    };

    protected void onStart() {
        super.onStart();
        Intent gattServiceIntent = new Intent(this, BluetoothLeService.class);
        bindService(gattServiceIntent, mServiceConnection, BIND_AUTO_CREATE);
    }

    public void sendclick(View view) {
        sendst = true;
        mBluetoothLeService.send(item, DeviceControlActivity.this, false, false, newCommandList, newRadioCommandList, null);
    }


    public void resendclick(View view) {
        sendst = false;
        mBluetoothLeService.send(item, DeviceControlActivity.this, true, false, newCommandList, newRadioCommandList, null);
    }

    public void clearclick(View view) {
        try {
            listAdapter.clear();
        } catch (Exception e) {

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // if the result is capturing Image
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {


            try {
                String ip = data.getStringExtra("ip");
                String port = data.getStringExtra("port");
                mBluetoothLeService.connectTcp();
            } catch (Exception e) {
                System.out.println("err" + e);
            }
        }


    }


    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            mBluetoothLeService = ((BluetoothLeService.LocalBinder) service).getService();
            if (!mBluetoothLeService.initialize()) {
                Log.e(TAG, "Unable to initialize Bluetooth");
                finish();
            }
            // Automatically connects to the device upon successful start-up initialization.
            mBluetoothLeService.connect(mDeviceAddress, DeviceControlActivity.this, device_id, opid);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mBluetoothLeService = null;
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(mGattUpdateReceiver, makeGattUpdateIntentFilter());
        if (mBluetoothLeService != null) {
            final boolean result = mBluetoothLeService.connect(mDeviceAddress, DeviceControlActivity.this, device_id, opid);
            Log.d(TAG, "Connect request result=" + result);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mServiceConnection);
        mBluetoothLeService = null;
    }

    private void updateConnectionState(final int resourceId) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mConnectionState.setText(resourceId);
            }
        });
    }

    /*HERE WE GET FINAL RESPONSE AND STRING FROM BASE AND ROVER MAINLY ROVER'S $GNGGA STRING*/

    private void displayData(String data) {
        if (data != null) {

//            MyInternetConnection.getInstance().setString(data);
            if (deviceList.size() > 10) {
                listAdapter.clear();
                deviceList.clear();
            }
            if (data.contains("$GNGGA")) {
                @SuppressLint("WrongConstant") SharedPreferences sh = getSharedPreferences("MyPrefs", MODE_APPEND);
                String p_nme = sh.getString("project_nameKey", "");
                String comment = sh.getString("commentKey", "");
                String oprtr = sh.getString("operatorKey", "");


                String fixTime = data.split(",")[1];
                latitude = data.split(",")[2];
                longitude = data.split(",")[4];
                altitude = data.split(",")[9];
                String accuracy = data.split(",")[8];
                String fix = data.split(",")[6];
                latlongvalue = latitude + "," + longitude + "," + altitude + "," + accuracy + "," + fix;

                BleBean bleBean = new BleBean();
                bleBean.setLatitude(latitude);
                bleBean.setLongitude(longitude);

//                 GenericActivity Ga = new GenericActivity();
//                 Ga.rtklocation(Text);


                //  BluetoothLeService ble = new BluetoothLeService();
                //ble.sendRequestToServer(latitude+"_"+longitude);


                StatusData = "";
                if (fix.equalsIgnoreCase("0")) {
                    StatusData = "invalid";
                    listAdapter.add("invalid");
                    deviceList.add(data);
                } else if (fix.equalsIgnoreCase("1")) {
                    StatusData = "GPS fix";
                    listAdapter.add("GPS fix");
                    deviceList.add("GPS fix");
                } else if (fix.equalsIgnoreCase("2")) {
                    StatusData = "DGPS fix";
                    listAdapter.add("DGPS fix");
                    deviceList.add("DGPS fix");
                } else if (fix.equalsIgnoreCase("3")) {
                    StatusData = "PPS fix";
                    listAdapter.add("PPS fix");
                    deviceList.add("PPS fix");
                } else if (fix.equalsIgnoreCase("4")) {
                    StatusData = "Real time kinematic";
                    listAdapter.add("Real time kinematic");
                    deviceList.add("Real time kinematic");
                } else if (fix.equalsIgnoreCase("5")) {
                    StatusData = "Float RTK";
                    listAdapter.add("Float RTK");
                    deviceList.add("Float RTK");
                } else if (fix.equalsIgnoreCase("6")) {
                    StatusData = "estimated";
                    listAdapter.add("estimated");
                    deviceList.add("estimated");
                } else if (fix.equalsIgnoreCase("7")) {
                    StatusData = "manual input mode";
                    listAdapter.add("manual input mode");
                    deviceList.add("manual input mode");
                } else {
                    StatusData = "simulation mode";
                    listAdapter.add("simulation mode");
                    deviceList.add("simulation mode");
                }
                lat_lang = latitude + "_" + longitude + "_" + StatusData + "_" + accuracy + "_" + fix;

            }


            listAdapter.add(data);
            deviceList.add(data);


            int milliesec = (((EditText) findViewById(R.id.ed_inputnum)).getText().toString().isEmpty()) ? 5000 :
                    Integer.parseInt(((EditText) findViewById(R.id.ed_inputnum)).getText().toString() + "000");
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (callingserver1 != null)
                        callingserver1.execute();

                }
            }, milliesec);
        }

    }

    private static IntentFilter makeGattUpdateIntentFilter() {
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_CONNECTED);
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_DISCONNECTED);
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED);
        intentFilter.addAction(BluetoothLeService.ACTION_DATA_AVAILABLE);
        return intentFilter;
    }

    ProgressDialog proDialog;

    @Override
    public void showDailog(Context context) {
        proDialog = new ProgressDialog(context);
        proDialog = ProgressDialog.show(context, "Command Loading", "please wait...");
    }

    @Override
    public void hideDialog(Context context) {
        proDialog = new ProgressDialog(context);
        proDialog.dismiss();
    }


    @Override
    public void onResendCommand(Context con, boolean resendStatus, boolean isnewtask,
                                List<String> newCommandList, List<String> newRadioCommandlist, List<String> delayList) {

        //  new Handler().postDelayed(, Integer.parseInt(((EditText)findViewById(R.id.ed_inputnum)).getText().toString()));


    }


    private class Callingserver extends AsyncTask<String, String, Long> {
        ProgressDialog dialog;

        @Override
        protected Long doInBackground(String... params) {
            dbTask.open();
            BleModel model = new BleModel(DeviceControlActivity.this);
            String serverip = dbTask.getServerIp();
            model.setServer_ip(serverip.split("_")[0]);
            model.setPort(serverip.split("_")[1]);
            dbTask.close();
            long result = model.requestBleDetail();
            return result;

        }

        @Override
        protected void onPostExecute(Long result) {
            dialog.dismiss();
            if (result > 0) {
                Toast.makeText(DeviceControlActivity.this, "Data successfully recieved", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(DeviceControlActivity.this, " no updation  Found", Toast.LENGTH_LONG).show();
            }
        }

        @Override
        protected void onPreExecute() {
            dialog = ProgressDialog.show(DeviceControlActivity.this, "", "Proccessing....Please wait");
            dialog.show();
            // Things to be done before execution of long running operation. For
            // example showing ProgessDialog
        }

        @Override
        protected void onProgressUpdate(String... text) {
            //firstBar.
            // Things to be done while execution of long running operation is in
            // progress. For example updating ProgessDialog
        }
    }
//    private boolean checkConnection() {
//        boolean isConnected = ConnectivityReciever.isConnected();
//        return isConnected;
//    }

    @Override
    public void onBackPressed() {
//        mBluetoothLeService.serverDisconnect();
        Intent intent = new Intent(DeviceControlActivity.this, Connect.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        finish();
        startActivity(intent);
    }


}
