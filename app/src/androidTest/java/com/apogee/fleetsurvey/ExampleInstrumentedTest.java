package com.apogee.fleetsurvey;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import androidx.annotation.Nullable;
import androidx.test.ext.junit.runners.AndroidJUnit4;


import com.apogee.fleetsurvey.Database.DatabaseOperation;
import com.apogee.fleetsurvey.Database.DatabaseWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;


import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    private SQLiteDatabase database;
    private DatabaseWrapper dbHelper;


    @Test
    public void useAppContext() {
        // Context of the app under test.
      //String str =  String.format("%040x", new BigInteger(1, "01".getBytes(/*YOUR_CHARSET?*/)));
      String str =  Integer.toHexString('2');

        String jsonexample = "{\n" +
                "  \"id\": 123,\n" +
                "  \"name\": \"Pankaj\",\n" +
                "  \"permanent\": true,\n" +
                "  \"address\": {\n" +
                "    \"street\": \"Albany Dr\",\n" +
                "    \"city\": \"San Jose\",\n" +
                "    \"zipcode\": 95129\n" +
                "  },\n" +
                "  \"phoneNumbers\": [\n" +
                "    123456,\n" +
                "    987654\n" +
                "  ],\n" +
                "  \"role\": \"Manager\",\n" +
                "  \"cities\": [\n" +
                "    \"Los Angeles\",\n" +
                "    \"New York\"\n" +
                "  ],\n" +
                "  \"properties\": {\n" +
                "    \"age\": \"29 years\",\n" +
                "    \"salary\": \"1000 USD\"\n" +
                "  }\n" +
                "}";

        ObjectMapper objectMapper = new ObjectMapper();

        try {
            Employee employee = objectMapper.readValue(jsonexample, Employee.class);

            System.out.println("Employee Object\n"+employee);
        } catch (IOException e) {
            e.printStackTrace();
        }


//        Map<String, Map<String, String>> selectionList = new HashMap<>();
//
//        dbHelper = new DatabaseWrapper(MyInternetConnection.getInstance());
//
//        database = dbHelper.getReadableDatabase();
//
//        getoperation(23);
////        getoperation(38);
//        Context appContext = InstrumentationRegistry.getTargetContext();
//        int i = 1;
//        int[] index = new int[3];
//
//        HashMap<String, String> stringStringHashMap = new HashMap<>();
//
//        stringStringHashMap.put("SET_DEVICE_ID", "01");
//        stringStringHashMap.put("LIQUID_TYPE", "00 01");
//
//        String command = "0106001600/SET_DEVICE_ID/CRC/";
//        // String command = "/SET_DEVICE_ID/060012/LIQUID_TYPE/CRC/";
//
//
//        String splitstr[] = command.split("/");
//        String final_command = "";
//        for (int j = 0; j < splitstr.length; j++) {
//
//            if (stringStringHashMap.containsKey(splitstr[j])) {
//                splitstr[j] = stringStringHashMap.get(splitstr[j]);
//
//            }
//
//            final_command = final_command.concat(splitstr[j]);
//
//            if (final_command.contains("CRC")) {
//                final_command = final_command.replace("CRC", new MODBUSCRC16().returnCRCHexstring((final_command.substring(0, final_command.indexOf("CRC")).replaceAll("\\s+", ""))));
//            }
//        }
//
//
//        System.out.println(final_command);


    }

    public ArrayList<Operation> getoperation(int deviceid) {
        HashSet<Operation> setchild = new HashSet<Operation>();
        try {
            // Cursor cursor = database.rawQuery("SELECT operation_name FROM operation", null);
            //  Cursor cursor = database.rawQuery("SELECT operation_name FROM operation", null);
            Cursor cursor = database.rawQuery("SELECT op.id,op.operation_name,op.is_super_child FROM operation AS op," +
                    "device_command_map as dcm WHERE dcm.device_id='" + deviceid + "' AND op.id=dcm.operation_id", null);
            int a = cursor.getCount();


            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToPosition(i);


                setchild.add(new Operation(cursor.getInt(0), cursor.getString(1), cursor.getString(2)));
//                if (!list.contains(cursor.getString(cursor.getColumnIndex("operation_name")))) {
//
//
//                    list.add(cursor.getString(0));
//                }
//                 list.add(surveyBean);
            }
        } catch (Exception e) {

        }

        ArrayList<Operation> namesList = new ArrayList<>(setchild);
        namesList.add(0, new Operation(-15, "--select--", null));
        HashSet<Operation> listparent = new HashSet<Operation>();

        for (int l = 0; l < namesList.size(); l++) {
            try {
                // Cursor cursor = database.rawQuery("SELECT operation_name FROM operation", null);
                //  Cursor cursor = database.rawQuery("SELECT operation_name FROM operation", null);

                Operation operation = namesList.get(l);

                Cursor cursor = null;
                // Cursor cursor = database.rawQuery("SELECT * FROM operation  WHERE id=" + operation.getId() + " and parent_id!=1", null);
                if (operation.getId() > 0) {
                    cursor = isParent(operation.getId());
                    int a = cursor.getCount();


                    for (int i = 0; i < cursor.getCount(); i++) {
                        cursor.moveToPosition(i);

                        listparent.add(new Operation(cursor.getInt(0), cursor.getString(2), cursor.getString(3)));
//                if (!list.contains(cursor.getString(cursor.getColumnIndex("operation_name")))) {
//
//
//                    list.add(cursor.getString(0));
//                }
//                 list.add(surveyBean);
                    }

                }

            } catch (Exception e) {

            }
        }

        ArrayList<Operation> namesListparent = new ArrayList<>(listparent);
        namesListparent.add(0, new Operation(-15, "--select--", null));


        for (int postion = 0; postion < namesListparent.size(); postion++) {

            Operation operation = namesListparent.get(postion);

            if (operation.getId() > 0) {
                Cursor cursor = isChild(operation.getId());
                setchild.clear();
                for (int i = 0; i < cursor.getCount(); i++) {
                    cursor.moveToPosition(i);


                    setchild.add(new Operation(cursor.getInt(0), cursor.getString(1), cursor.getString(2)));
//                if (!list.contains(cursor.getString(cursor.getColumnIndex("operation_name")))) {
//
//
//                    list.add(cursor.getString(0));
//                }
//                 list.add(surveyBean);
                }
            }


            ArrayList<Operation> listchild = new ArrayList<>(setchild);
            listchild.add(0, new Operation(-15, "--select--", null));
        }

        return namesList;
    }

    Cursor cursorhold = null;

    public Cursor isParent(int id) {

        Cursor cursor = database.rawQuery("SELECT id,parent_id,operation_name,is_super_child FROM operation  WHERE id='" + id + "' and parent_id!='null'", null);
        int a = cursor.getCount();

        if (a == 0 || cursor.getString(3).equalsIgnoreCase("on")) {
            return cursorhold;
        }
        cursor.moveToNext();
        int valId = cursor.getInt(1);


        cursorhold = cursor;


        return isParent(valId);
    }


    public Cursor isChild(int parentid) {
        Cursor cursorchild = null;
        Cursor cursor = database.rawQuery("SELECT id,operation_name,is_super_child FROM operation WHERE parent_id ='" + parentid + "'", null);
        int a = cursor.getCount();

        cursor.moveToNext();
        if (cursor.getString(2).equalsIgnoreCase("on")) {
            return cursor;
        }


        cursorchild = cursor;


        return cursorchild;
    }

    private static int crc16(final byte[] buffer) {
        /* Note the change here */
        int crc = 0x1D0F;
        for (int j = 0; j < buffer.length; j++) {
            crc = ((crc >>> 8) | (crc << 8)) & 0xffff;
            crc ^= (buffer[j] & 0xff);//byte to int, trunc sign
            crc ^= ((crc & 0xff) >> 4);
            crc ^= (crc << 12) & 0xffff;
            crc ^= ((crc & 0xFF) << 5) & 0xffff;
        }
        crc &= 0xffff;
        return crc;
    }


    class Operation {
        int id;
        String name;
        String issupechild;

        public Operation(int id, String name, String issupechild) {
            this.id = id;
            this.name = name;
            this.issupechild = issupechild;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getissupechild() {
            return issupechild;
        }


        @Override
        public int hashCode() {
            return name.hashCode();
        }

        @Override
        public boolean equals(@Nullable Object obj) {
            Operation operation = (Operation) obj;
            if (this.name.equals(operation.name)) {
                return true;
            }

            return false;
        }
    }
}
