package com.apogee.fleetsurvey.multiview;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.apogee.fleetsurvey.R;

import java.util.List;

public class RecycerlViewAdapter extends RecyclerView.Adapter {

    List<ItemType> itemTypeList;
    OnItemValueListener onItemValueListener;

    public RecycerlViewAdapter(List<ItemType> itemTypeList,OnItemValueListener onItemValueListener) {
        this.itemTypeList = itemTypeList;
        this.onItemValueListener = onItemValueListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        if (viewType == 0) {

            View itemdropview = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_row_dropdown, parent, false);
            return new ItemDropword(itemdropview);
        } else if (viewType == 1) {

            View iteminputview = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_row_input, parent, false);
            return new ItemInput(iteminputview,onItemValueListener);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ItemType itemType = itemTypeList.get(position);
        switch (itemType.type) {
            case ItemType.DROPDOWNTYPE:
                ItemDropword itemDropword = (ItemDropword) holder;
                itemDropword.txtdrop.setText(itemType.title);
               // itemDropword.setDropdown(itemType.getStringList(),onItemValueListener);
                itemDropword.setDropdown(itemType.getStringStringMapdrop(),onItemValueListener);
                break;
            case ItemType.INPUTTYPE:
                ItemInput itemInput = (ItemInput) holder;
                itemInput.txtinput.setText(itemType.title);
                break;
        }

    }

    @Override
    public int getItemCount() {
        return itemTypeList.size();
    }

    @Override
    public int getItemViewType(int position) {

        if (itemTypeList.get(position).getType() == 0) {
            return 0;
        } else {
            return 1;
        }


    }
}
