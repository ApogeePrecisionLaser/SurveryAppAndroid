package com.apogee.fleetsurvey.multiview;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


import com.apogee.fleetsurvey.Database.DatabaseOperation;
import com.apogee.fleetsurvey.R;
import com.apogee.fleetsurvey.utility.DeviceControlActivity;

import java.math.BigInteger;


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
            if (!hasFocus) {


                DatabaseOperation databaseOperation = new DatabaseOperation(itemView.getContext());
                databaseOperation.open();

                String rmkvalue = databaseOperation.retrnfromtfromrmrk(title);
                if (rmkvalue != null) {
                    if (rmkvalue.equals("1")) {
                        try {
                            int value1 = Integer.parseInt(finaltext);
                            finaltext = bytesToHex(intToLittleEndian1(value1)).toUpperCase();
                            onItemValueListener.returnValue(title, finaltext);
                            edinput.clearFocus();
                            databaseOperation.close();
                        } catch (Exception e) {

                        }
                    }
                    else
                    {
                        if (finaltext != null) {
                            onItemValueListener.returnValue(title, hexString(finaltext));
                            edinput.clearFocus();
                            databaseOperation.close();
                        }
                    }
                }



            }

        }
    }

    public static String bytesToHex(byte[] in) {
        final StringBuilder builder = new StringBuilder();
        for (byte b : in) {
            builder.append(String.format("%02x", b));
        }
        return builder.toString();
    }

    private static byte[] intToLittleEndian1(long numero) {
        byte[] b = new byte[4];
        b[0] = (byte) (numero & 0xFF);
        b[1] = (byte) ((numero >> 8) & 0xFF);
        b[2] = (byte) ((numero >> 16) & 0xFF);
        b[3] = (byte) ((numero >> 24) & 0xFF);
        return b;
    }

    private String hexString(String input) {
        char[] charinput = input.toCharArray();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < charinput.length; i++) {
            stringBuilder.append(Integer.toHexString(charinput[i]));
        }
        return stringBuilder.toString();
    }
}
