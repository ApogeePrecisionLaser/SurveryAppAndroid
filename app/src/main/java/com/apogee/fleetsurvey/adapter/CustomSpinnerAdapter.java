package com.apogee.fleetsurvey.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.apogee.fleetsurvey.model.Operation;

import java.util.ArrayList;

public class CustomSpinnerAdapter extends ArrayAdapter {

    ArrayList<Operation> operationArrayList;
    public CustomSpinnerAdapter(@NonNull Context context, int resource, ArrayList<Operation> operationArrayList) {
        super(context, resource);
        this.operationArrayList = operationArrayList;
    }

    @Override
    public int getCount() {
        return operationArrayList.size();
    }


    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return super.getDropDownView(position, convertView, parent);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return super.getView(position, convertView, parent);
    }
}
