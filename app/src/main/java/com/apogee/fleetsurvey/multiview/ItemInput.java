package com.apogee.fleetsurvey.multiview;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


import com.apogee.fleetsurvey.R;


public class ItemInput extends RecyclerView.ViewHolder {

    TextView txtinput;
    EditText edinput;


    String finaltext;
    String title;

    public ItemInput(@NonNull View itemView, final OnItemValueListener onItemValueListener) {
        super(itemView);
        txtinput = itemView.findViewById(R.id.txtinput);
        edinput = itemView.findViewById(R.id.edinput);



        View.OnFocusChangeListener focusListener = new MyFoucuslitenerImpl(onItemValueListener);
        edinput.setOnFocusChangeListener(focusListener);
        edinput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                finaltext = s.toString();
                title = txtinput.getText().toString();


            }
        });

    }


    private class MyFoucuslitenerImpl implements View.OnFocusChangeListener {

        OnItemValueListener onItemValueListener;

        public MyFoucuslitenerImpl(OnItemValueListener onItemValueListener) {
            this.onItemValueListener = onItemValueListener;
        }

        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (!hasFocus)
            {
                onItemValueListener.returnValue(title,finaltext);
                edinput.clearFocus();
            }

        }
    }


}
