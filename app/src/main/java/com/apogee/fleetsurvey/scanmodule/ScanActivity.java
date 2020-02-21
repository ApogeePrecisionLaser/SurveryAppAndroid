package com.apogee.fleetsurvey.scanmodule;

import android.app.Activity;
import android.app.PendingIntent;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.apogee.fleetsurvey.Connect;
import com.apogee.fleetsurvey.R;
import com.apogee.fleetsurvey.utility.DeviceControlActivity;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidParameterSpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class ScanActivity extends Activity {

    IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
    private TextView nfcval, scanval;
    private EditText manualdata;
    //qr code scanner object
    private IntentIntegrator qrScan;

    /*NFC tag Read*/
    public static final String ERROR_DETECTED = "No NFC tag detected!";
    NfcAdapter nfcAdapter;
    PendingIntent pendingIntent;
    IntentFilter writeTagFilters[];


    Tag myTag;
    Context context;
    String text = "";


    BluetoothAdapter mBluetoothAdapter;
    public static int REQUEST_BLUETOOTH = 1;
    String devicename = "D_6";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        context = this;

        mBluetoothAdapter= BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            new AlertDialog.Builder(this)
                    .setTitle("Not compatible")
                    .setMessage("Your phone does not support Bluetooth")
                    .setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            System.exit(0);
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }

        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableBT = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBT, REQUEST_BLUETOOTH);
        }
        nfcval = findViewById(R.id.nfcval);
        scanval = findViewById(R.id.svtxt);
        manualdata = findViewById(R.id.mdetext);
        //intializing scan object
        qrScan = new IntentIntegrator(this);


        nfcAdapter = NfcAdapter.getDefaultAdapter(this);

        if (nfcAdapter == null) {
            // Stop here, we definitely need NFC
            Toast.makeText(this, ERROR_DETECTED, Toast.LENGTH_LONG).show();
        }
        readFromIntent(getIntent());

        pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
        IntentFilter tagDetected = new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);
        tagDetected.addCategory(Intent.CATEGORY_DEFAULT);
        writeTagFilters = new IntentFilter[]{tagDetected};

    }


    /******************************************************************************
     **********************************Read From NFC Tag***************************
     ******************************************************************************/
    private void readFromIntent(Intent intent) {

        String action = intent.getAction();
        if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(action)
                || NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)
                || NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)) {
            Parcelable[] rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);

            NdefMessage[] msgs;
//            if (rawMsgs != null) {

            msgs = new NdefMessage[rawMsgs.length];
            for (int i = 0; i < rawMsgs.length; i++) {
                msgs[i] = (NdefMessage) rawMsgs[i];
            }

            buildTagViews(msgs);
        }
    }

    private void buildTagViews(NdefMessage[] msgs) {
        if (msgs == null || msgs.length == 0) return;

//        text=itm.contactList.toString();


//        String tagId = new String(msgs[0].getRecords()[0].getType());
        byte[] payload = msgs[0].getRecords()[0].getPayload();
        String textEncoding = ((payload[0] & 128) == 0) ? "UTF-8" : "UTF-16"; // Get the Text Encoding
        int languageCodeLength = payload[0] & 0063; // Get the Language Code, e.g. "en"
        // String languageCode = new String(payload, 1, languageCodeLength, "US-ASCII");

        try {
            text = SecurityUtils.decryptMsg(payload);

            nfcval.setText(text);
            startActivityfromScan(text);
            // text = new String(payload, languageCodeLength + 1, payload.length - languageCodeLength - 1, textEncoding);


            AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
            builder1.setTitle("Your Data.");
            builder1.setMessage(text);
            builder1.setCancelable(true);
            myTag = (Tag) getIntent().getParcelableExtra(NfcAdapter.EXTRA_TAG);
            String nFCID = myTag.getId().toString();
            Toast.makeText(getApplicationContext(), "NFC id is: " + nFCID, Toast.LENGTH_SHORT).show();
            final String finalText = text;
            builder1.setPositiveButton(
                    "Yes",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

            builder1.setNegativeButton(
                    "No",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });


            AlertDialog alert11 = builder1.create();
            alert11.show();

            // Get the Text
            //    text = new String(payload, languageCodeLength + 1, payload.length - languageCodeLength - 1, textEncoding);
        } catch (UnsupportedEncodingException e) {
            Log.e("UnsupportedEncoding", e.toString());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidParameterSpecException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }

//        FireBean fireBean = new FireBean();
//        fireBean.setNFCvehicleno(text);


//        tvNFCContent.setText("NFC Content: " + text);
    }


    //Getting the scan results
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            //if qrcode has nothing in it
            if (result.getContents() == null) {
                Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
            } else {
                scanval.setText(result.getContents());
                startActivityfromScan(result.getContents());
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void sendmanual(View view) {
        //initiating the qr code scan
        String manualval = manualdata.getText().toString();
        Toast.makeText(this, manualval, Toast.LENGTH_LONG).show();
        manualdata.getText().clear();
        startActivityfromScan(manualval);
    }

    public void qrcodescan(View view) {
        //initiating the qr code scan
        new IntentIntegrator(this).initiateScan();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(bReciever);
    }

    public void startActivityfromScan(String finalValue) {
        devicename=finalValue;
        registerReceiver(bReciever, filter);


    }


    private final BroadcastReceiver bReciever = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intentboradcast) {
            String action = intentboradcast.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                Log.d("DEVICELIST", "Bluetooth device found\n");
                BluetoothDevice device = intentboradcast.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                String name = device.getName();
                if (devicename.equals(name)) {
                    String address = device.getAddress();
                    Intent intent = new Intent(ScanActivity.this, DeviceControlActivity.class);
                    intent.putExtra(DeviceControlActivity.EXTRAS_DEVICE_NAME, device.getName()
                    );
                    intent.putExtra(DeviceControlActivity.EXTRAS_DEVICE_ADDRESS, device.getAddress());
                    intent.putExtra("device_id", devicename);
                    intent.putExtra("dgps_device_id", devicename);
                    startActivity(intent);

                }
            }
        }
    };


}
