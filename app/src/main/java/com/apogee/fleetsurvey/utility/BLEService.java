package com.apogee.fleetsurvey.utility;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.annotation.Nullable;

import com.apogee.fleetsurvey.Database.DatabaseOperation;
import com.apogee.fleetsurvey.model.BleModel;

public class BLEService extends IntentService {
    Context context;
    DatabaseOperation dbTask;

    public BLEService() {
        super("CableService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        dbTask = new DatabaseOperation(BLEService.this);
        dbTask.open();
        BleModel model = new BleModel(BLEService.this);
        String serverip = dbTask.getServerIp();
        model.setServer_ip(serverip.split("_")[0]);
        model.setPort(serverip.split("_")[1]);
        dbTask.close();
        long result = model.requestBleDetail();
        final ResultReceiver receiver = intent.getParcelableExtra("receiver");

        new Handler().postAtTime(new Runnable() {
            @Override
            public void run() {
                Bundle bundle = new Bundle();
                receiver.send(123,bundle);
            }
        },5000);

    }

    @Override
    public void onCreate() {
        super.onCreate(); // if you override onCreate(), make sure to call super().
        // If a Context object is needed, call getApplicationContext() here.
        context = getApplicationContext();

    }

}
