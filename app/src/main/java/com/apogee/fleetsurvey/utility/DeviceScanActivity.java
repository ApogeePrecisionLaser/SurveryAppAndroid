package com.apogee.fleetsurvey.utility;
import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.bluetooth.le.BluetoothLeScanner;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.apogee.fleetsurvey.Database.DatabaseOperation;
import com.apogee.fleetsurvey.R;
import java.util.ArrayList;
import java.util.List;
public class DeviceScanActivity extends AppCompatActivity {
    private BluetoothAdapter mBluetoothAdapter;
    private boolean mScanning;
    private Handler mHandler;
    DatabaseOperation dbTask;
    private BluetoothGatt mGatt;
    public static final int RequestPermissionCode = 1;
    private ArrayAdapter<String> mNewDevicesArrayAdapter;
    private static final long SCAN_PERIOD = 10000;
    BluetoothLeScanner bluetoothLeScanner;
    public static String EXTRA_DEVICE_ADDRESS = "device_address";
    List<String> ls = new ArrayList<>();
    TextView textView;
    private BluetoothAdapter mBtAdapter;
    BluetoothAdapter bluetoothAdapter;
    BluetoothManager bluetoothManager;
    String device_name1="",address="";
    private final static int REQUEST_ENABLE_BT = 1;
    boolean is_deviceAvailable=false;
    boolean reSearch=true;
    String device_id="",dgps_device_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_device_scan);
        dbTask = new DatabaseOperation(DeviceScanActivity.this);
        dbTask.open();
        dbTask.getuserdetail();
        mHandler = new Handler();
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        mNewDevicesArrayAdapter = new ArrayAdapter<String>(this, R.layout.device_name);
        ListView newDevicesListView = (ListView) findViewById(R.id.new_devices);
        newDevicesListView.setAdapter(mNewDevicesArrayAdapter);
        newDevicesListView.setOnItemClickListener(mDeviceClickListener);
        Intent intent=getIntent();
        device_name1=intent.getStringExtra("device_name");
        address=intent.getStringExtra("device_address");
        device_id=intent.getStringExtra("device_id");
        dgps_device_id=intent.getStringExtra("dgps_device_id");
//        Intent intent=new Intent(DeviceScanActivity.this,BLEService.class);
//        startService(intent);
        initialiseBluetooth();
        if (bluetoothAdapter != null && !bluetoothAdapter.isEnabled()) {
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
            scanLeDevice(true);
        }
        if (!checkPermission()) {
            requestPermission();
        } else {
            scanLeDevice(true);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        scanLeDevice(true);
    }

    private void initialiseBluetooth() {
        bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        bluetoothAdapter = bluetoothManager.getAdapter();
        bluetoothLeScanner = bluetoothAdapter.getBluetoothLeScanner();
    }

    public void scanclk(View view) {
        mNewDevicesArrayAdapter.clear();
        scanLeDevice(true);
    }

    public boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION);
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION);
        int result2 = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.BLUETOOTH);
        int result3 = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.BLUETOOTH_ADMIN);
        return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED && result2 == PackageManager.PERMISSION_GRANTED
                && result3 == PackageManager.PERMISSION_GRANTED;
    }
    private void requestPermission() {
        ActivityCompat.requestPermissions(DeviceScanActivity.this, new
                String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.BLUETOOTH, Manifest.permission.BLUETOOTH_ADMIN}, RequestPermissionCode);
    }
    private void scanLeDevice(final boolean enable) {
        if (enable) {
            // Stops scanning after a pre-defined scan period.
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mScanning = false;
//                    if (!is_deviceAvailable ) {
//                        showAlert("your device not available. Go back and select another device");
//                    }
//                    int a=10;
                    mBluetoothAdapter.stopLeScan(mLeScanCallback);
                }
            }, SCAN_PERIOD);

                mScanning = true;
                mBluetoothAdapter.startLeScan(mLeScanCallback);


        } else {
            mScanning = false;
            mBluetoothAdapter.stopLeScan(mLeScanCallback);
        }
    }

    private BluetoothAdapter.LeScanCallback mLeScanCallback =
            new BluetoothAdapter.LeScanCallback() {
                @Override
                public void onLeScan(final BluetoothDevice device, int rssi,
                                     byte[] scanRecord) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(!ls.contains(""+device))
                            {
//                                    if (device.getAddress().equalsIgnoreCase(address))
                                    //{
                                        is_deviceAvailable = true;
                                        ls.add("" + device);
                                        mNewDevicesArrayAdapter.add(device.getName() + "\n" + device.getAddress());
                                    //}
                            }

                        }
                    });
                }
            };

    public void connectToDevice(String name) {
        // if (mGatt == null) {
        BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(name);
        mGatt = device.connectGatt(this, false, gattCallback);
        scanLeDevice(false);// will stop after first device detection
        // }
    }

    private final BluetoothGattCallback gattCallback = new BluetoothGattCallback() {
        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            Log.i("onConnectionStateChange", "Status: " + status);
            switch (newState) {
                case BluetoothProfile.STATE_CONNECTED:
                    Log.i("gattCallback", "STATE_CONNECTED");
                    // textView.setText("connected to");
                    gatt.discoverServices();
                    break;
                case BluetoothProfile.STATE_DISCONNECTED:
                    Log.e("gattCallback", "STATE_DISCONNECTED");
                    break;
                default:
                    Log.e("gattCallback", "STATE_OTHER");
            }

        }

    };
    private AdapterView.OnItemClickListener mDeviceClickListener
            = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView<?> av, View v, int arg2, long arg3) {
            // Cancel discovery because it's costly and we're about to connect
            // mBtAdapter.cancelDiscovery();
            // Get the device MAC address, which is the last 17 chars in the View
            String info = ((TextView) v).getText().toString();
            String address = info.substring(info.length() - 17);
            BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
            final Intent intent = new Intent(DeviceScanActivity.this, DeviceControlActivity.class);
            intent.putExtra(DeviceControlActivity.EXTRAS_DEVICE_NAME, device.getName());
            intent.putExtra(DeviceControlActivity.EXTRAS_DEVICE_ADDRESS, device.getAddress());
            intent.putExtra("device_id",device_id);
            intent.putExtra("dgps_device_id",dgps_device_id);
            startActivity(intent);
        }
    };
//    private void showAlert(String msg) {
//
//        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(DeviceScanActivity.this);
//        alertDialogBuilder.setTitle("Message");
//        alertDialogBuilder.setMessage(msg);
//        alertDialogBuilder
//                .setCancelable(false)
//                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        Intent intent=new Intent(DeviceScanActivity.this,Device_detail.class);
//                        startActivity(intent);
//                    }
//                });
//
//        AlertDialog alertDialog = alertDialogBuilder.create();
//        alertDialog.show();
//
//    }

}
