package com.apogee.fleetsurvey.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.apogee.fleetsurvey.bean.BleBean;
import com.apogee.fleetsurvey.model.Operation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class DatabaseOperation {
    private static final String TAG = "DatabaseOperation.java";
    Context context;
    private DatabaseWrapper dbHelper;
    private SQLiteDatabase database;

    public DatabaseOperation(Context context) {
        dbHelper = new DatabaseWrapper(context);
        this.context = context;
    }

    public void getuserdetail() {
        String a = "";
        try {
            Cursor cursor = database.rawQuery("SELECT id FROM command_type ", null);
            cursor.moveToPosition(0);
            a = cursor.getString(0);


        } catch (Exception e) {
            Log.e(TAG, "getUserDtailerror: " + e);
        }
    }

    public void open() {
        // to get a db in writable mode
        try {

            database = dbHelper.getWritableDatabase();
        } catch (Exception e) {
            Log.d(TAG, "open db: " + e);
        }
    }

    public long insertmanufectureDetail(List<BleBean> list) {
        long result = 0;
        try {
            Iterator<BleBean> iterator = list.iterator();
            database.beginTransaction();
            while (iterator.hasNext() && result != -1) {
                ContentValues values = new ContentValues();
                BleBean bleBean = iterator.next();
                values.put("manufacturer_id", bleBean.getManufacturer_id());
                values.put("name", bleBean.getManufacturer_name());
                values.put("remark", bleBean.getRemark1());
//                values.put("remark2", bleBean.getRemark2());
                result = database.replace("manufacturer", null, values);
            }
            if (result > 0) {
                database.setTransactionSuccessful();
            }

        } catch (Exception e) {
            Log.e(TAG, "insertNodeDetail error: " + e);
        } finally {
            database.endTransaction();
        }
        return result;
    }

    public long insertcommand_typeDetail(List<BleBean> list) {
        long result = 0;
        try {
            Iterator<BleBean> iterator = list.iterator();
            database.beginTransaction();
            while (iterator.hasNext() && result != -1) {
                ContentValues values = new ContentValues();
                BleBean bleBean = iterator.next();
                values.put("id", bleBean.getCommand_type_id());
                values.put("command_name", bleBean.getCommand_type_name());
//                values.put("manufacturer_id", bleBean.getRemark1());
                values.put("remark", bleBean.getRemark());
                result = database.replace("command_type", null, values);
            }
            if (result > 0) {
                database.setTransactionSuccessful();
            }

        } catch (Exception e) {
            Log.e(TAG, "insertNodeDetail error: " + e);
        } finally {
            database.endTransaction();
        }
        return result;
    }

    public long insertmodel_typeDetail(List<BleBean> list) {
        long result = 0;
        try {
            Iterator<BleBean> iterator = list.iterator();
            database.beginTransaction();
            while (iterator.hasNext() && result != -1) {
                ContentValues values = new ContentValues();
                BleBean bleBean = iterator.next();
                values.put("id", bleBean.getModel_type_id());
                values.put("type", bleBean.getModel_type_name());
                values.put("remark", bleBean.getRemark());
//                values.put("remark2", bleBean.getRemark2());
                result = database.replace("model_type", null, values);
            }
            if (result > 0) {
                database.setTransactionSuccessful();
            }

        } catch (Exception e) {
            Log.e(TAG, "insertNodeDetail error: " + e);
        } finally {
            database.endTransaction();
        }
        return result;
    }

    public long insertmodelDetail(List<BleBean> list) {
        long result = 0;
        try {
            Iterator<BleBean> iterator = list.iterator();
            database.beginTransaction();
            while (iterator.hasNext() && result != -1) {
                ContentValues values = new ContentValues();
                BleBean bleBean = iterator.next();
                values.put("id", bleBean.getModel_id());
                values.put("device_name", bleBean.getModel_name());
                values.put("device_no", bleBean.getDevice_no());
                values.put("model_type_id", bleBean.getModel_type_id());
                values.put("warranty", bleBean.getWarranty());
                values.put("device_address", bleBean.getDevice_address());
                values.put("no_of_module", bleBean.getNo_of_module());
                values.put("remark", bleBean.getRemark());
//                values.put("remark2", bleBean.getRemark2());
                result = database.replace("model", null, values);
            }
            if (result > 0) {
                database.setTransactionSuccessful();
            }

        } catch (Exception e) {
            Log.e(TAG, "insertNodeDetail error: " + e);
        } finally {
            database.endTransaction();
        }
        return result;
    }

    public long insertdevice_typeDetail(List<BleBean> list) {
        long result = 0;
        try {
            Iterator<BleBean> iterator = list.iterator();
            database.beginTransaction();
            while (iterator.hasNext() && result != -1) {
                ContentValues values = new ContentValues();
                BleBean bleBean = iterator.next();
                values.put("id", bleBean.getDevice_type_id());
                values.put("type", bleBean.getDevice_type());
                values.put("remark", bleBean.getRemark());
//                values.put("remark2", bleBean.getRemark2());
                result = database.replace("device_type", null, values);
            }
            if (result > 0) {
                database.setTransactionSuccessful();
            }

        } catch (Exception e) {
            Log.e(TAG, "insertNodeDetail error: " + e);
        } finally {
            database.endTransaction();
        }
        return result;
    }

    public long insertdeviceDetail(List<BleBean> list) {
        long result = 0;
        try {
            Iterator<BleBean> iterator = list.iterator();
            database.beginTransaction();
            while (iterator.hasNext() && result != -1) {
                ContentValues values = new ContentValues();
                BleBean bleBean = iterator.next();
                values.put("id", bleBean.getDevice_id());
                values.put("manufacturer_id", bleBean.getManufacturer_id());
                values.put("device_type_id", bleBean.getDevice_type_id());
                values.put("model_id", bleBean.getModel_id());
                // values.put("warranty", bleBean.getWarranty());
                //values.put("remark", bleBean.getRemark());
                values.put("remark", bleBean.getRemark());
//                values.put("remark2", bleBean.getRemark2());
                result = database.replace("device", null, values);
            }
            if (result > 0) {
                database.setTransactionSuccessful();
            }

        } catch (Exception e) {
            Log.e(TAG, "insertNodeDetail error: " + e);
        } finally {
            database.endTransaction();
        }
        return result;
    }

    public long insertoperationDetail(List<BleBean> list) {
        long result = 0;
        try {
            Iterator<BleBean> iterator = list.iterator();
            database.beginTransaction();
            while (iterator.hasNext() && result != -1) {
                ContentValues values = new ContentValues();
                BleBean bleBean = iterator.next();
                values.put("id", bleBean.getOperation_id());
                values.put("operation_name", bleBean.getOperation_name());
                values.put("remark", bleBean.getRemark());
                values.put("parent_id", bleBean.getParent_id());
                values.put("is_super_child", bleBean.getIs_super_child());
//                values.put("remark2", bleBean.getRemark2());
                result = database.replace("operation", null, values);
            }
            if (result > 0) {
                database.setTransactionSuccessful();
            }

        } catch (Exception e) {
            Log.e(TAG, "insertNodeDetail error: " + e);
        } finally {
            database.endTransaction();
        }
        return result;
    }

    public long insertcommandDetail(List<BleBean> list) {
        long result = 0;
        try {
            Iterator<BleBean> iterator = list.iterator();
            database.beginTransaction();
            while (iterator.hasNext() && result != -1) {
                ContentValues values = new ContentValues();
                BleBean bleBean = iterator.next();
                values.put("command_id", bleBean.getCommand_id());
                values.put("command", bleBean.getCommand_name());
                values.put("command_type_id", bleBean.getCommand_type_id1());
                values.put("start_del", bleBean.getStart_del());
                values.put("end_del", bleBean.getEnd_del());
                values.put("remark", bleBean.getRemark());
                values.put("selection", bleBean.getSelection());
                values.put("input", bleBean.getInput());
//                values.put("remark2", bleBean.getRemark2());
                result = database.replace("command", null, values);
            }
            if (result > 0) {
                database.setTransactionSuccessful();
            }

        } catch (Exception e) {
            Log.e(TAG, "insertcommand error: " + e);
        } finally {
            database.endTransaction();
        }
        return result;
    }

    public boolean insertToTable(String title, String desc, String person, String timeline, String table_name) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("task_title", title);
        contentValues.put("task_person", desc);
        contentValues.put("task_description", person);
        contentValues.put("task_timeline", timeline);
        contentValues.put("table_name", table_name);
        database.insert("Task_table", null, contentValues);
        return true;
    }

    public boolean insertlocation(double lati, double longi, String status, double accuracy) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("latitude", lati);
        contentValues.put("longitude", longi);
        contentValues.put("status", status);
        contentValues.put("accuracy", accuracy);
        database.insert("location", null, contentValues);
        return true;
    }

    public boolean insertmainproject(String p_name, String oprtr, String cmnt, String ggaString) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("Project_name", p_name);
        contentValues.put("Operator", oprtr);
        contentValues.put("Comment", cmnt);
        contentValues.put("GGAString", ggaString);
        database.insert("location", null, contentValues);
        return true;
    }

    public long insertservicesDetail(List<BleBean> list) {
        long result = 0;
        try {
            Iterator<BleBean> iterator = list.iterator();
            database.beginTransaction();
            while (iterator.hasNext() && result != -1) {
                ContentValues values = new ContentValues();
                BleBean bleBean = iterator.next();
                values.put("id", bleBean.getServices_id());
                values.put("services_name", bleBean.getService_name());
                values.put("services_uuid", bleBean.getServise_uuid());
                values.put("device_id", bleBean.getDevice_id());
                values.put("remark", bleBean.getRemark());
//                values.put("remark2", bleBean.getRemark2());
                result = database.replace("services", null, values);
            }
            if (result > 0) {
                database.setTransactionSuccessful();
            }

        } catch (Exception e) {
            Log.e(TAG, "insertcommand error: " + e);
        } finally {
            database.endTransaction();
        }
        return result;
    }

    public long insertcharachtristicsDetail(List<BleBean> list) {
        long result = 0;
        try {
            Iterator<BleBean> iterator = list.iterator();
            database.beginTransaction();
            while (iterator.hasNext() && result != -1) {
                ContentValues values = new ContentValues();
                BleBean bleBean = iterator.next();
                values.put("id", bleBean.getCharachtristics_id());
                values.put("char_name", bleBean.getChar_name());
                values.put("char_uuid", bleBean.getChar_uuid());
                values.put("service_id", bleBean.getServices_id());
                values.put("remark", bleBean.getRemark());
//                values.put("remark2", bleBean.getRemark2());
                result = database.replace("charachtristics", null, values);
            }
            if (result > 0) {
                database.setTransactionSuccessful();
            }

        } catch (Exception e) {
            Log.e(TAG, "insertcommand error: " + e);
        } finally {
            database.endTransaction();
        }
        return result;
    }

    public long insertruleDetail(List<BleBean> list) {
        long result = 0;
        try {
            Iterator<BleBean> iterator = list.iterator();
            database.beginTransaction();
            while (iterator.hasNext() && result != -1) {
                ContentValues values = new ContentValues();
                BleBean bleBean = iterator.next();
                values.put("id", bleBean.getRule_id());
                values.put("command_id", bleBean.getCommand_id());
                values.put("remark1", bleBean.getRemark1());
                values.put("remark2", bleBean.getRemark2());
                result = database.replace("rules", null, values);
            }
            if (result > 0) {
                database.setTransactionSuccessful();
            }

        } catch (Exception e) {
            Log.e(TAG, "insertNodeDetail error: " + e);
        } finally {
            database.endTransaction();
        }
        return result;
    }

    public long insertdeviceregDetail(List<BleBean> list) {
        long result = 0;
        try {
            Iterator<BleBean> iterator = list.iterator();
            database.beginTransaction();
            while (iterator.hasNext() && result != -1) {
                ContentValues values = new ContentValues();
                BleBean bleBean = iterator.next();
                values.put("id", bleBean.getServices_id());
                values.put("device_id", bleBean.getDevice_id());
                values.put("manufactrure_date", bleBean.getManufactrerdate());
                values.put("date2", bleBean.getDate2());
                values.put("remark", bleBean.getRemark());
                result = database.replace("device_registration", null, values);
            }
            if (result > 0) {
                database.setTransactionSuccessful();
            }

        } catch (Exception e) {
            Log.e(TAG, "insertcommand error: " + e);
        } finally {
            database.endTransaction();
        }
        return result;
    }

    public long insertbleOperation(List<BleBean> list) {
        long result = 0;
        try {
            Iterator<BleBean> iterator = list.iterator();
            database.beginTransaction();
            while (iterator.hasNext() && result != -1) {
                ContentValues values = new ContentValues();
                BleBean bleBean = iterator.next();
                values.put("ble_operation_id", bleBean.getBle_operation_id());
                values.put("operation_name", bleBean.getBle_operationName());
                values.put("remark", bleBean.getRemark());
                result = database.replace("ble_operation", null, values);
            }
            if (result > 0) {
                database.setTransactionSuccessful();
            }

        } catch (Exception e) {
            Log.e(TAG, "insertcommand error: " + e);
        } finally {
            database.endTransaction();
        }
        return result;
    }

    public long insertDeviceMap(List<BleBean> list) {
        long result = 0;
        try {
            Iterator<BleBean> iterator = list.iterator();
            database.beginTransaction();
            while (iterator.hasNext() && result != -1) {
                ContentValues values = new ContentValues();
                BleBean bleBean = iterator.next();
                values.put("device_map_id", bleBean.getDevice_map_id());
                values.put("finished_device_id", bleBean.getFinished_device_id());
                values.put("ble_device_id", bleBean.getBle_device_id());
                values.put("module_device_id", bleBean.getModule_id());
                values.put("remark", bleBean.getRemark());
                result = database.replace("device_map", null, values);
            }
            if (result > 0) {
                database.setTransactionSuccessful();
            }

        } catch (Exception e) {
            Log.e(TAG, "insertcommand error: " + e);
        } finally {
            database.endTransaction();
        }
        return result;
    }

    public long insertinput(List<BleBean> list) {
        long result = 0;
        try {
            Iterator<BleBean> iterator = list.iterator();
            database.beginTransaction();
            while (iterator.hasNext() && result != -1) {
                ContentValues values = new ContentValues();
                BleBean bleBean = iterator.next();
                values.put("command_id", bleBean.getCommand_id());
                values.put("input_id", bleBean.getInput_id());
                values.put("remark", bleBean.getRemark3());
                values.put("parameter_id", bleBean.getParameter_id());
                result = database.replace("input", null, values);
            }
            if (result > 0) {
                database.setTransactionSuccessful();
            }

        } catch (Exception e) {
            Log.e(TAG, "insertcommand error: " + e);
        } finally {
            database.endTransaction();
        }
        return result;
    }

    public long insertselection(List<BleBean> list) {
        long result = 0;
        try {
            Iterator<BleBean> iterator = list.iterator();
            database.beginTransaction();
            while (iterator.hasNext() && result != -1) {
                ContentValues values = new ContentValues();
                BleBean bleBean = iterator.next();
                values.put("command_id", bleBean.getCommand_id());
                values.put("selection_id", bleBean.getSelection_id());
                values.put("parameter_id", bleBean.getParameter_id());
                values.put("selection_value_no", bleBean.getSelection_value_no());
                values.put("remark", bleBean.getRemark4());
                result = database.replace("selection", null, values);
            }
            if (result > 0) {
                database.setTransactionSuccessful();
            }

        } catch (Exception e) {
            Log.e(TAG, "insertcommand error: " + e);
        } finally {
            database.endTransaction();
        }
        return result;
    }

    public long insertparameter(List<BleBean> list) {
        long result = 0;
        try {
            Iterator<BleBean> iterator = list.iterator();
            database.beginTransaction();
            while (iterator.hasNext() && result != -1) {
                ContentValues values = new ContentValues();
                BleBean bleBean = iterator.next();
                values.put("parameter_name", bleBean.getParameter_name());
                values.put("parameter_id", bleBean.getParameter_id());
                values.put("parameter_type", bleBean.getParameter_type());
                values.put("remark", bleBean.getRemark5());
                result = database.replace("parameter", null, values);
            }
            if (result > 0) {
                database.setTransactionSuccessful();
            }

        } catch (Exception e) {
            Log.e(TAG, "insertcommand error: " + e);
        } finally {
            database.endTransaction();
        }
        return result;
    }

    public long insertselectionvalue(List<BleBean> list) {
        long result = 0;
        try {
            Iterator<BleBean> iterator = list.iterator();
            database.beginTransaction();
            while (iterator.hasNext() && result != -1) {
                ContentValues values = new ContentValues();
                BleBean bleBean = iterator.next();
                values.put("selection_value_id", bleBean.getSelection_value_id());
                values.put("display_value", bleBean.getDisplay_value());
                values.put("revision_no", bleBean.getRevision_no1());
                values.put("byte_value", bleBean.getByte_value());
                values.put("selection_id", bleBean.getSelection_id1());
                values.put("remark", bleBean.getRemark8());
                result = database.replace("selection_value", null, values);
            }
            if (result > 0) {
                database.setTransactionSuccessful();
            }

        } catch (Exception e) {
            Log.e(TAG, "insertcommand error: " + e);
        } finally {
            database.endTransaction();
        }
        return result;
    }

    public long insertcommanddevicemapvalue(List<BleBean> list) {


        database.rawQuery("DELETE FROM device_command_map", null);
//        database.delete("device_command_map",null,null);
        long result = 0;
        try {
            Iterator<BleBean> iterator = list.iterator();
            database.beginTransaction();
            while (iterator.hasNext() && result != -1) {
                ContentValues values = new ContentValues();
                BleBean bleBean = iterator.next();
                values.put("device_id", bleBean.getDevice_id2());
                values.put("command_id", bleBean.getCommand_id2());
                values.put("operation_id", bleBean.getOperation_id2());
                values.put("device_command_id", bleBean.getId());
                values.put("delay", bleBean.getDelay());
                values.put("order_no", bleBean.getOrder_no1());
                values.put("remark", bleBean.getRemark11());

                result = database.replace("device_command_map", null, values);
//                result = database.replace("device_command_map", null, values);
            }
            if (result > 0) {
                database.setTransactionSuccessful();
            }

        } catch (Exception e) {
            Log.e(TAG, "insertcommand error: " + e);
        } finally {
            database.endTransaction();
        }
        return result;
    }

    public long insertsubbytedivisionvalue(List<BleBean> list) {
        long result = 0;
        try {
            Iterator<BleBean> iterator = list.iterator();
            database.beginTransaction();
            while (iterator.hasNext() && result != -1) {
                ContentValues values = new ContentValues();
                BleBean bleBean = iterator.next();
                values.put("sub_byte_division_id", bleBean.getSub_byte_division_id());
                values.put("sub_division_no", bleBean.getSub_division_no());
                values.put("revision_no", bleBean.getRevision_no2());
                values.put("byte_id", bleBean.getByte_id());
                values.put("remark", bleBean.getRemark9());
                values.put("parameter_name", bleBean.getParameter_name1());
                values.put("start_pos", bleBean.getStart_pos());
                values.put("no_of_bit", bleBean.getNo_of_bit());
                result = database.replace("sub_byte_division", null, values);
            }
            if (result > 0) {
                database.setTransactionSuccessful();
            }

        } catch (Exception e) {
            Log.e(TAG, "insertcommand error: " + e);
        } finally {
            database.endTransaction();
        }
        return result;
    }

    public long insertbytedatavalue(List<BleBean> list) {
        long result = 0;
        try {
            Iterator<BleBean> iterator = list.iterator();
            database.beginTransaction();
            while (iterator.hasNext() && result != -1) {
                ContentValues values = new ContentValues();
                BleBean bleBean = iterator.next();
                values.put("byte_data_id", bleBean.getByte_data_id());
                values.put("sub_byte_division", bleBean.getSub_byte_division());
                values.put("command_id", bleBean.getCommand_id1());
                values.put("parameter_name", bleBean.getParameter_name2());
                values.put("remark", bleBean.getRemark10());
                values.put("revision_no", bleBean.getRevision_no3());
                result = database.replace("byte_data", null, values);
            }
            if (result > 0) {
                database.setTransactionSuccessful();
            }

        } catch (Exception e) {
            Log.e(TAG, "insertcommand error: " + e);
        } finally {
            database.endTransaction();
        }
        return result;
    }

    public long insertSubdivisionSelectionvalue(List<BleBean> list) {
        long result = 0;
        try {
            Iterator<BleBean> iterator = list.iterator();
            database.beginTransaction();
            while (iterator.hasNext() && result != -1) {
                ContentValues values = new ContentValues();
                BleBean bleBean = iterator.next();
                values.put("sub_division_selection_id", bleBean.getSub_division_selection_id());
                values.put("display_value", bleBean.getDisplayvalue());
                values.put("bit_value", bleBean.getBit_value());
                values.put("sub_byte_division_id", bleBean.getSub_bytedivision_id());
                result = database.replace("sub_division_selection", null, values);
            }
            if (result > 0) {
                database.setTransactionSuccessful();
            }

        } catch (Exception e) {
            Log.e(TAG, "insertcommand error: " + e);
        } finally {
            database.endTransaction();
        }
        return result;
    }

    public long insertcommand_crc_mapping(List<BleBean> list) {
        long result = 0;
        try {
            Iterator<BleBean> iterator = list.iterator();
            database.beginTransaction();
            while (iterator.hasNext() && result != -1) {
                ContentValues values = new ContentValues();
                BleBean bleBean = iterator.next();
                values.put("command_id", bleBean.getCommand_id());
                values.put("crc_type_id", bleBean.getCrc_type_id());
                values.put("command_crc_mapping_id", bleBean.getCommand_crc_mapping_id());
                values.put("remark", bleBean.getRemark());
                result = database.replace("command_crc_mapping", null, values);
            }
            if (result > 0) {
                database.setTransactionSuccessful();
            }

        } catch (Exception e) {
            Log.e(TAG, "insertcommand error: " + e);
        } finally {
            database.endTransaction();
        }
        return result;
    }

    public long insertcrc_type(List<BleBean> list) {
        long result = 0;
        try {
            Iterator<BleBean> iterator = list.iterator();
            database.beginTransaction();
            while (iterator.hasNext() && result != -1) {
                ContentValues values = new ContentValues();
                BleBean bleBean = iterator.next();
                values.put("crc_type_name", bleBean.getCrc_type());
                values.put("crc_type_id", bleBean.getCrc_type_id());
                values.put("remark", bleBean.getRemark());
                result = database.replace("crc_type", null, values);
            }
            if (result > 0) {
                database.setTransactionSuccessful();
            }

        } catch (Exception e) {
            Log.e(TAG, "insertcommand error: " + e);
        } finally {
            database.endTransaction();
        }
        return result;
    }

    public long insertbleOperationMap(List<BleBean> list) {
        long result = 0;
        try {
            Iterator<BleBean> iterator = list.iterator();
            database.beginTransaction();
            while (iterator.hasNext() && result != -1) {
                ContentValues values = new ContentValues();
                BleBean bleBean = iterator.next();
                values.put("ble_operation_mapping_id", bleBean.getBle_operationMapping_id());
                values.put("device_id", bleBean.getDevice_id());
                values.put("charachtristics_write_id", bleBean.getCharachtristics_id());
                values.put("charachtristics_read_id", bleBean.getCharachtristicsReadId());
                values.put("ble_operation_id", bleBean.getBle_operation_id());
                values.put("order_no", bleBean.getOrder_no());
                values.put("remark", bleBean.getRemark());
//                values.put("remark2", bleBean.getRemark2());
                result = database.replace("ble_operation_mapping", null, values);
            }
            if (result > 0) {
                database.setTransactionSuccessful();
            }

        } catch (Exception e) {
            Log.e(TAG, "insertcommand error: " + e);
        } finally {
            database.endTransaction();
        }
        return result;
    }

    public long insertDatumData(List<BleBean> list) {
        long result = 0;
        try {
            Iterator<BleBean> iterator = list.iterator();
            database.beginTransaction();
            while (iterator.hasNext() && result != -1) {
                ContentValues values = new ContentValues();
                BleBean bleBean = iterator.next();
                values.put("datum_id_server", bleBean.getDatum_id());
                values.put("name", bleBean.getName());
                values.put("major_axis", bleBean.getMajor_axis());
                values.put("flattening", bleBean.getFlattening());
                values.put("x_axis_shift", bleBean.getX_axis_shift());
                values.put("Y_axis_shift", bleBean.getY_axis_shift());
                values.put("Z_axis_shift", bleBean.getZ_axis_shift());
                values.put("rot_x_axis", bleBean.getRot_x_axis());
                values.put("rot_y_axis", bleBean.getRot_y_axis());
                values.put("rot_z_axis", bleBean.getRot_z_axis());
                values.put("scale", bleBean.getScale());
//                values.put("remark2", bleBean.getRemark2());
                result = database.replace("Datum", null, values);
            }
            if (result > 0) {
                database.setTransactionSuccessful();
            }

        } catch (Exception e) {
            Log.e(TAG, "insertcommand error: " + e);
        } finally {
            database.endTransaction();
        }
        return result;
    }

    public ArrayList<String> getoperation(int deviceid) {
        ArrayList<String> list = new ArrayList<String>();
        try {
            // Cursor cursor = database.rawQuery("SELECT operation_name FROM operation", null);
            //  Cursor cursor = database.rawQuery("SELECT operation_name FROM operation", null);
            Cursor cursor = database.rawQuery("SELECT op.operation_name FROM operation AS op,device_command_map as dcm WHERE dcm.device_id='" + deviceid + "' AND op.id=dcm.operation_id", null);
            int a = cursor.getCount();
            list.add("--select--");
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToPosition(i);
                if (!list.contains(cursor.getString(0))) {
                    list.add(cursor.getString(0));
                }
                // list.add(surveyBean);
            }
        } catch (Exception e) {
            Log.e(TAG, "getItemData error: " + e);
        }
        return list;
    }

    public ArrayList<String> getBleoperations() {
        ArrayList<String> list = new ArrayList<String>();
        try {
            Cursor cursor = database.rawQuery("SELECT operation_name FROM ble_operation", null);
            cursor.moveToPosition(0);
            int a = cursor.getCount();
            list.add("--select--");
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToPosition(i);
                list.add(cursor.getString(0));
                // list.add(surveyBean);
            }
        } catch (Exception e) {
            Log.e(TAG, "getItemData error: " + e);
        }
        return list;
    }

    public ArrayList<String> getManufactrur(String type) {
        ArrayList<String> list = new ArrayList<String>();
        try {
            // Cursor cursor = database.rawQuery("SELECT name FROM manufacturer", null);
            Cursor cursor = database.rawQuery("SELECT m.name FROM manufacturer as m ,device_type as dt,device as d where dt.type='" + type + "'" +
                    "and d.device_type_id=dt.id and m.manufacturer_id=d.manufacturer_id group by m.manufacturer_id ", null);
            cursor.moveToPosition(0);
            int a = cursor.getCount();
            list.add("--select--");
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToPosition(i);
                list.add(cursor.getString(0));
                // list.add(surveyBean);
            }
        } catch (Exception e) {
            Log.e(TAG, "getItemData error: " + e);
        }
        return list;
    }

    public ArrayList<String> getDevice_type() {
        ArrayList<String> list = new ArrayList<String>();
        try {
            Cursor cursor = database.rawQuery("SELECT type FROM device_type", null);
            cursor.moveToPosition(0);
            int a = cursor.getCount();
            list.add("--select--");
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToPosition(i);
                list.add(cursor.getString(0));
                // list.add(surveyBean);
            }
        } catch (Exception e) {
            Log.e(TAG, "getItemData error: " + e);
        }
        return list;
    }

    public ArrayList<String> getmodel_type() {
        ArrayList<String> list = new ArrayList<String>();
        try {
            Cursor cursor = database.rawQuery("SELECT type FROM model_type", null);
            cursor.moveToPosition(0);
            int a = cursor.getCount();
            list.add("--select--");
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToPosition(i);
                list.add(cursor.getString(0));
                // list.add(surveyBean);
            }
        } catch (Exception e) {
            Log.e(TAG, "getItemData error: " + e);
        }
        return list;
    }

    public ArrayList<String> getdatumlist() {
        ArrayList<String> list = new ArrayList<String>();
        try {
            Cursor cursor = database.rawQuery("SELECT DISTINCT name FROM datum", null);
            cursor.moveToPosition(0);
            int a = cursor.getCount();
            list.add("--select--");
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToPosition(i);
                list.add(cursor.getString(0));
                // list.add(surveyBean);
            }
        } catch (Exception e) {
            Log.e(TAG, "getItemData error: " + e);
        }
        return list;
    }

    public ArrayList<String> getmnypes(int id) {
        ArrayList<String> list = new ArrayList<String>();
        try {
            Cursor cursor = database.rawQuery("SELECT device_name FROM model where model_type_id=" + id + "", null);
            cursor.moveToPosition(0);
            int a = cursor.getCount();
            list.add("--select--");
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToPosition(i);
                list.add(cursor.getString(0));
                // list.add(surveyBean);
            }
        } catch (Exception e) {
            Log.e(TAG, "getItemData error: " + e);
        }
        return list;
    }

    public ArrayList<String> getmodel_typels1() {
        ArrayList<String> list = new ArrayList<String>();
        try {
            Cursor cursor = database.rawQuery("SELECT type FROM model_type", null);
            cursor.moveToPosition(0);
            int a = cursor.getCount();
            list.add("--select--");
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToPosition(i);
                list.add(cursor.getString(0));
                // list.add(surveyBean);
            }
        } catch (Exception e) {
            Log.e(TAG, "getItemData error: " + e);
        }
        return list;
    }

    public ArrayList<String> getmodel(int id) {
        ArrayList<String> list = new ArrayList<String>();
        try {
            Cursor cursor = database.rawQuery("SELECT device_name FROM model where model_type_id=" + id + " ", null);
            //Cursor cursor = database.rawQuery("SELECT m.device_name FROM device AS d, device_type as dt ,model AS m WHERE dt.type='"+devicetypename+"' AND d.device_type_id=dt.id AND m.id=d.model_id", null);

            cursor.moveToPosition(0);
            int a = cursor.getCount();
            list.add("--select--");
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToPosition(i);
                list.add(cursor.getString(0));
                // list.add(surveyBean);
            }
        } catch (Exception e) {
            Log.e(TAG, "getItemData error: " + e);
        }
        return list;
    }

    public ArrayList<String> getmodel(String devicetypename) {
        ArrayList<String> list = new ArrayList<String>();
        try {
            // Cursor cursor = database.rawQuery("SELECT device_name FROM model where model_type_id="+id+" ", null);
            Cursor cursor = database.rawQuery("SELECT m.device_name FROM device AS d, device_type as dt ,model AS m WHERE dt.type='" + devicetypename + "' AND d.device_type_id=dt.id AND m.id=d.model_id", null);
            cursor.moveToPosition(0);
            int a = cursor.getCount();
            list.add("--select--");
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToPosition(i);
                list.add(cursor.getString(0));
                // list.add(surveyBean);
            }
        } catch (Exception e) {
            Log.e(TAG, "getItemData error: " + e);
        }
        return list;
    }

    public ArrayList<String> getsuboperationname(int id) {
        ArrayList<String> list = new ArrayList<String>();
        try {
            Cursor cursor = database.rawQuery("SELECT operation_name FROM operation where parent_id=" + id + " ", null);
            cursor.moveToPosition(0);
            int a = cursor.getCount();
            list.add("--select--");
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToPosition(i);
                list.add(cursor.getString(0));
                // list.add(surveyBean);
            }
        } catch (Exception e) {
            Log.e(TAG, "getItemData error: " + e);
        }
        return list;
    }

    public int detopnameid(String name) {
        int a = 0;
        try {
            Cursor cursor = database.rawQuery("SELECT id FROM operation where operation_name='" + name + "' ", null);
            cursor.moveToPosition(0);
            a = cursor.getInt(0);
        } catch (Exception e) {
            Log.e(TAG, "getUserDtailerror: " + e);
        }
        return a;
    }

    public int datumopid(String name) {
        int a = 0;
        try {
            Cursor cursor = database.rawQuery("SELECT id FROM operation where operation_name='" + name + "' ", null);
            cursor.moveToPosition(0);
            a = cursor.getInt(0);
        } catch (Exception e) {
            Log.e(TAG, "getUserDtailerror: " + e);
        }
        return a;
    }

    public String getfinalcommand() {
        String a = "0";
        try {
            Cursor cursor = database.rawQuery("SELECT final_commandlist FROM  finalcommand_list ", null);
            cursor.moveToPosition(0);
            a = cursor.getString(0);
        } catch (Exception e) {
            Log.e(TAG, "getUserDtailerror: " + e);
        }
        return a;
    }

    public int manufactrur_id(String name) {
        int a = 0;
        try {
            Cursor cursor = database.rawQuery("SELECT manufacturer_id FROM manufacturer where name='" + name + "' ", null);
            cursor.moveToPosition(0);
            a = cursor.getInt(0);
        } catch (Exception e) {
            Log.e(TAG, "getUserDtailerror: " + e);
        }
        return a;
    }

    public int datumcommandid(int id) {
        int a = 0;
        try {
            Cursor cursor = database.rawQuery("SELECT command_id FROM device_command_map where operation_id = " + id + "", null);
            cursor.moveToPosition(0);
            a = cursor.getInt(0);
        } catch (Exception e) {
            Log.e(TAG, "getUserDtailerror: " + e);
        }
        return a;
    }

    public String datumcommand(int id) {
        String cmnd = "";
        try {
            Cursor cursor = database.rawQuery("SELECT distinct command FROM command where command_id = " + id + " ", null);
            cursor.moveToPosition(0);
            cmnd = cursor.getString(0);
        } catch (Exception e) {
            Log.e(TAG, "getUserDtailerror: " + e);
        }
        return cmnd;
    }

    public String getsubopid(String name) {
        String id = "";
        try {
            Cursor cursor = database.rawQuery("SELECT id FROM operation where operation_name = '" + name + "'; ", null);
            cursor.moveToPosition(0);
            id = cursor.getString(0);
        } catch (Exception e) {
            Log.e(TAG, "getUserDtailerror: " + e);
        }
        return id;
    }

    public int bleOperation_id(String name) {
        int a = 0;
        try {
            Cursor cursor = database.rawQuery("SELECT ble_operation_id FROM ble_operation where operation_name='" + name + "' ", null);
            cursor.moveToPosition(0);
            a = cursor.getInt(0);
        } catch (Exception e) {
            Log.e(TAG, "getUserDtailerror: " + e);
        }
        return a;
    }

    public int getDeviceType_id(String name) {
        int a = 0;
        try {
            Cursor cursor = database.rawQuery("SELECT id FROM device_type where type='" + name + "' ", null);
            cursor.moveToPosition(0);
            a = cursor.getInt(0);
        } catch (Exception e) {
            Log.e(TAG, "getUserDtailerror: " + e);
        }
        return a;
    }

    public int getModelType_id(String name) {
        int a = 0;
        try {
            Cursor cursor = database.rawQuery("SELECT id FROM model_type where type='" + name + "'", null);
            cursor.moveToPosition(0);
            a = cursor.getInt(0);
        } catch (Exception e) {
            Log.e(TAG, "getUserDtailerror: " + e);
        }
        return a;
    }

    public int getModelType_id2(String name) {
        int a = 0;
        try {
            Cursor cursor = database.rawQuery("SELECT id FROM model where device_name='" + name + "'", null);
            cursor.moveToPosition(0);
            a = cursor.getInt(0);
        } catch (Exception e) {
            Log.e(TAG, "getUserDtailerror: " + e);
        }
        return a;
    }

    public int getModelType_id1(int id) {
        int a = 0;
        try {
            Cursor cursor = database.rawQuery("SELECT id FROM device where model_id=" + id + " ", null);
            cursor.moveToPosition(0);
            a = cursor.getInt(0);
        } catch (Exception e) {
            Log.e(TAG, "getUserDtailerror: " + e);
        }
        return a;
    }

    public int getnoofmodule(int id) {
        int a = 0;
        try {
            Cursor cursor = database.rawQuery("SELECT no_of_module FROM model Where id=" + id + " ", null);
            cursor.moveToPosition(0);
            a = cursor.getInt(0);
        } catch (Exception e) {
            Log.e(TAG, "getUserDtailerror: " + e);
        }
        return a;
    }

    public String gebleModel(String name) {
        String namest = "";
        String address = "";
        try {
            Cursor cursor = database.rawQuery("SELECT device_name,device_address FROM model where id=" + name + " ", null);
            cursor.moveToPosition(0);
            namest = cursor.getString(0);
            address = cursor.getString(1);
        } catch (Exception e) {
            Log.e(TAG, "getUserDtailerror: " + e);
        }
        return namest + "," + address;
    }

    public int getmodel_id(String name) {
        int a = 0;
        try {
            Cursor cursor = database.rawQuery("SELECT id FROM model where device_name='" + name + "' ", null);
            cursor.moveToPosition(0);
            a = cursor.getInt(0);
        } catch (Exception e) {
            Log.e(TAG, "getUserDtailerror: " + e);
        }
        return a;
    }

    public int getDevice_id(int manufactur_id, int deviceType_id, int model_id) {
        int a = 0;
        try {
            Cursor cursor = database.rawQuery("SELECT id FROM device where manufacturer_id=" + manufactur_id + " and device_type_id=" + deviceType_id + " and model_id=" + model_id + " ", null);
            cursor.moveToPosition(0);
            a = cursor.getInt(0);
        } catch (Exception e) {
            Log.e(TAG, "getUserDtailerror: " + e);
        }
        return a;
    }


    public String delay(int name) {
        String delay = "";
        String cmd = "";
        try {
            Cursor cursor = database.rawQuery("SELECT delay,command FROM command where id=" + name + " ", null);
            cursor.moveToPosition(0);
            delay = cursor.getString(0);
            cmd = cursor.getString(1);
        } catch (Exception e) {
            Log.e(TAG, "getUserDtailerror: " + e);
        }
        return delay + "_" + cmd;
    }

    public Map<String, List<String>> parameterlist(String joined) {
        String parameter_name = "";
        String parameter_value = "";
        Map<String, List<String>> stringSetHashMap = new HashMap<>();
        List<String> intSet = new ArrayList<>();
        try {

            Cursor cursor = database.rawQuery("SELECT parameter.parameter_name,selection.parameter_value " +
                    "FROM selection INNER JOIN parameter ON parameter.parameter_id = selection.parameter_id " +
                    "Where selection.command_id IN (" + joined + "); ", null);
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToPosition(i);

                if (stringSetHashMap.containsKey(cursor.getString(0))) {
                    intSet = stringSetHashMap.get(cursor.getString(0));
                    if (!intSet.contains(cursor.getString(1))) {
                        intSet.add(cursor.getString(1));

                    }
                    stringSetHashMap.put(cursor.getString(0), intSet);
                } else {
                    intSet = new ArrayList<>();
                    intSet.add(cursor.getString(1));
                    stringSetHashMap.put(cursor.getString(0), intSet);
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "getUserDtailerror: " + e);
        }
        //return parameter_name + "_" + parameter_value;
        return stringSetHashMap;


    }

//    public List<Integer> commandididlist(String joined) {
//        List<Integer> list=new ArrayList<>();
//        try {
//            Cursor cursor = database.rawQuery("SELECT DISTINCT command_id FROM selection where command_id IN ("+joined+"); ", null);
//            Cursor cursor1 = database.rawQuery("SELECT DISTINCT command_id FROM input where command_id IN ("+joined+"); ", null);
//            for (int i = 0; i < cursor.getCount(); i++) {
//                cursor.moveToPosition(i);
//                list.add(cursor.getInt(0));
//                // list.add(surveyBean);
//            }
//            for (int i = 0; i < cursor1.getCount(); i++) {
//                cursor1.moveToPosition(i);
//                list.add(cursor1.getInt(0));
//                // list.add(surveyBean);
//            }
//            String command_id[] =joined.split (",");
//            for(int i=0;i<command_id.length;i++){
//                if(!list.contains(command_id[i])){
//                    list.add(Integer.parseInt(command_id[i]));
//                }
//            }
//        } catch (Exception e) {
//            Log.e(TAG, "getUserDtailerror: " + e);
//        }
//        return list;
//    }

    public List<String> getoperationid(String operation_name, int Device_id) {
        int op_id = 0;
        List<String> list = new ArrayList<>();
        try {
            Cursor cursor = database.rawQuery("SELECT id FROM operation where operation_name = '" + operation_name + "'; ", null);
            int val = cursor.getCount();
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToPosition(i);
                op_id = cursor.getInt(0);
            }
            if (op_id > 0) {
                list = getmorecommandlist(op_id, Device_id);
            }
        } catch (Exception e) {
            Log.e(TAG, "getUserDtailerror: " + e);
        }
        return list;
    }

    public List<String> Radiocommandid(String operation_name) {
        int op_id = 0;
        List<String> list = new ArrayList<>();
        try {
            Cursor cursor = database.rawQuery("SELECT id FROM operation where operation_name = '" + operation_name + "'; ", null);
            int val = cursor.getCount();
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToPosition(i);
                list.add(cursor.getString(0));
                op_id = cursor.getInt(0);
            }
            if (op_id > 0) {
//                list = getradiocommandlist(op_id);
            }

        } catch (Exception e) {
            Log.e(TAG, "getUserDtailerror: " + e);
        }
        return list;
    }

    public List<String> getradiocommandlist(int id) {
        List<String> list = new ArrayList<>();
        try {
            Cursor cursor = database.rawQuery("SELECT distinct command FROM command where command_id = " + id + " ; ", null);

            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToPosition(i);
                list.add(cursor.getString(0));
                // list.add(surveyBean);
            }
        } catch (Exception e) {
            Log.e(TAG, "getUserDtailerror: " + e);
        }
        return list;
    }


    public List<String> getmorecommandlist(int id, int Device_id) {
        List<String> list = new ArrayList<>();
        try {
            Cursor cursor = database.rawQuery("SELECT command_id FROM device_command_map where operation_id = " + id + " and device_id = " + Device_id + " order by order_no; ", null);

            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToPosition(i);
                list.add(cursor.getString(0));
                // list.add(surveyBean);
            }
        } catch (Exception e) {
            Log.e(TAG, "getUserDtailerror: " + e);
        }
        return list;
    }

    public List<String> getsomecommandlist(String joined) {
        List<String> list = new ArrayList<>();
        try {
            Cursor cursor = database.rawQuery("SELECT DISTINCT command FROM command where command_id IN (" + joined + ") ; ", null);
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToPosition(i);
                list.add(cursor.getString(0));
                // list.add(surveyBean);
            }
        } catch (Exception e) {
            Log.e(TAG, "getUserDtailerror: " + e);
        }
        return list;
    }

    public ArrayList<String> delaylist(String joined1, int id, int Device_id) {
        ArrayList<String> list = new ArrayList<>();
        try {
            Cursor cursor = database.rawQuery("SELECT delay FROM device_command_map where command_id IN (" + joined1 + ") AND operation_id = " + id + " and device_id = " + Device_id + " ORDER BY order_no ASC ; ", null);
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToPosition(i);
                list.add(cursor.getString(0));
                // list.add(surveyBean);
            }
        } catch (Exception e) {
            Log.e(TAG, "getUserDtailerror: " + e);
        }
        return list;
    }

    public ArrayList<String> delaylist2(String joined1, String id, int Device_id) {
        ArrayList<String> list = new ArrayList<>();
        try {
            Cursor cursor = database.rawQuery("SELECT delay FROM device_command_map where command_id IN (" + joined1 + ") AND operation_id = " + id + " and device_id = " + Device_id + " ; ", null);
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToPosition(i);
                list.add(cursor.getString(0));
                // list.add(surveyBean);
            }
        } catch (Exception e) {
            Log.e(TAG, "getUserDtailerror: " + e);
        }
        return list;
    }


    public List<String> commandforparsinglist(int Device_id, int operationid) {
        List<String> list = new ArrayList<>();
        try {
           // Cursor cursor = database.rawQuery("SELECT distinct command FROM command where command_id IN (" + joined1 + ") ; ", null);
            Cursor cursor = database.rawQuery("SELECT c.command FROM device_command_map as map , command as c WHERE map.device_id= "+Device_id+" AND map.operation_id="+operationid+" and map.command_id = c.command_id ORDER By order_no;", null);
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToPosition(i);
                //  list.add(cursor.getString(0).replaceAll("/",""));
                list.add(cursor.getString(0));
                // list.add(surveyBean);
            }
        } catch (Exception e) {
            Log.e(TAG, "getUserDtailerror: " + e);
        }
        return list;
    }

    public List<Integer> commandls(int id) {
        List<Integer> list = new ArrayList<>();
        try {
            Cursor cursor = database.rawQuery("SELECT id FROM command where operation_id=" + id + " ORDER BY order_no ASC ", null);
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToPosition(i);
                list.add(cursor.getInt(0));
                // list.add(surveyBean);
            }
        } catch (Exception e) {
            Log.e(TAG, "getUserDtailerror: " + e);
        }
        return list;
    }

    public List<Integer> commandidls1(int id1, int id2) {
        List<Integer> list = new ArrayList<>();
        try {
            Cursor cursor = database.rawQuery("SELECT command_id FROM device_command_map where operation_id=" + id1 + " AND device_id=" + id2 + " ORDER BY order_no ASC ", null);
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToPosition(i);
                list.add(cursor.getInt(0));
                // list.add(surveyBean);
            }
        } catch (Exception e) {
            Log.e(TAG, "getUserDtailerror: " + e);
        }
        return list;
    }

    public List<Integer> selectionidlist(String joined) {
        List<Integer> list = new ArrayList<>();
        try {
            Cursor cursor = database.rawQuery("SELECT selection_id FROM selection where command_id IN (" + joined + ") ; ", null);
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToPosition(i);
                list.add(cursor.getInt(0));
            }
        } catch (Exception e) {
            Log.e(TAG, "getUserDtailerror: " + e);
        }
        return list;
    }

    public List<Integer> inputlist(String joined) {
        List<Integer> list = new ArrayList<>();
        try {
            Cursor cursor = database.rawQuery("SELECT parameter_id FROM input Where command_id IN (" + joined + ") ; ", null);
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToPosition(i);
                list.add(cursor.getInt(0));
            }
        } catch (Exception e) {
            Log.e(TAG, "getUserDtailerror: " + e);
        }
        return list;
    }

    public List<String> inputparameterlist(String joined) {
        List<String> list = new ArrayList<>();
        try {
            Cursor cursor = database.rawQuery("SELECT parameter_name FROM parameter Where parameter_id IN (" + joined + ") ; ", null);
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToPosition(i);
                list.add(cursor.getString(0));
            }
        } catch (Exception e) {
            Log.e(TAG, "getUserDtailerror: " + e);
        }
        return list;
    }

    public String retrnfromtfromrmrk(String paramatername){

       String rtrnfrmrmk=null;
        try {
            Cursor cursor = database.rawQuery("SELECT remark FROM parameter Where parameter_name='" + paramatername +"'", null);


            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToPosition(i);
                rtrnfrmrmk=cursor.getString(0);
            }
        } catch (Exception e) {
            Log.e(TAG, "getUserDtailerror: " + e);
        }
        return rtrnfrmrmk;

    }
    public Map<String, Map<String, String>> displayvaluelist(String joined) {
        Map<String, Map<String, String>> selectionMap = new HashMap<>();
        Map<String, String> selectionValueMap = new HashMap<>();

        String parameter = "";
        try {
            Cursor cursor = database.rawQuery("SELECT distinct parameter_name,display_value,byte_value FROM selection, parameter, selection_value " +
                    "where selection.parameter_id = parameter.parameter_id and selection.selection_id = selection_value.selection_id " +
                    "and selection.selection_id IN (" + joined + ") ; ", null);
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToPosition(i);
                String para1 = cursor.getString(0);
                if (parameter.equals("")) {
                    selectionValueMap.put(cursor.getString(1), cursor.getString(2));
                    parameter = para1;
                } else if (parameter.equals(para1)) {
                    selectionValueMap.put(cursor.getString(1), cursor.getString(2));
                } else if (!parameter.equals(para1)) {
                    selectionMap.put(parameter, selectionValueMap);
                    parameter = para1;
                    selectionValueMap = new HashMap<>();
                    selectionValueMap.put(cursor.getString(1), cursor.getString(2));
                }

                if (i == cursor.getCount() - 1) {
                    selectionMap.put(parameter, selectionValueMap);
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "getUserDtailerror: " + e);
        }
        return selectionMap;
    }

    public int i;

    public Map<String, Map<String, Map<String, String>>> radiodata(String joined) {
        Map<String, Map<String, Map<String, String>>> byteDataMap = new HashMap<>();
        Map<String, Map<String, String>> subBytedataMap = new HashMap<>();
        Map<String, String> bitSelectionMap = new HashMap<>();
        String parameter = "";
        String parameter1 = "";
        try {
            Cursor cursor = database.rawQuery("SELECT DISTINCT b.parameter_name AS byte_para , sb.parameter_name" +
                    " AS sb_para, sd.display_value as dv, sd.bit_value as bv  FROM byte_data as b,sub_byte_division as sb,sub_division_selection as sd " +
                    "Where b.byte_data_id=sb.byte_id And sb.sub_byte_division_id=sd.sub_byte_division_id And b.command_id IN (" + joined + ") ; ", null);
            while (i < cursor.getCount()) {
                cursor.moveToPosition(i);
                String para1 = cursor.getString(0);
                subBytedataMap = mapFunction(parameter1, cursor, para1);
                byteDataMap.put(para1, subBytedataMap);

            }
        } catch (Exception e) {
            Log.e(TAG, "getUserDtailerror: " + e);
        }
        return byteDataMap;
    }

    public Map<String, Map<String, String>> mapFunction(String parameter, Cursor cursor, String parameter1) {
        Map<String, Map<String, String>> selectionMap = new HashMap<>();
        Map<String, String> selectionValueMap = new HashMap<>();
        int val = cursor.getCount();
        for (int k = i; k < cursor.getCount(); k++) {
            cursor.moveToPosition(k);
            String para1 = cursor.getString(1);
            if (parameter1.equals(cursor.getString(0))) {
                if (parameter.equals("")) {
                    selectionValueMap.put(cursor.getString(2), cursor.getString(3));
                    parameter = para1;
                } else if (parameter.equals(para1)) {
                    selectionValueMap.put(cursor.getString(2), cursor.getString(3));
                } else if (!parameter.equals(para1)) {
                    selectionMap.put(parameter, selectionValueMap);
                    parameter = para1;
                    selectionValueMap = new HashMap<>();
                    selectionValueMap.put(cursor.getString(2), cursor.getString(3));
                }

                if (k == cursor.getCount() - 1) {
                    i = k + 1;
                    selectionMap.put(parameter, selectionValueMap);
                    break;
                }


            } else {
                i = k;
                selectionMap.put(parameter, selectionValueMap);
                break;
            }

        }

        return selectionMap;
    }

    public List<Integer> parameteridlist(String joined) {
        List<Integer> list = new ArrayList<>();
        try {
            Cursor cursor = database.rawQuery("SELECT parameter_id FROM selection where command_id IN (" + joined + ") ; ", null);
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToPosition(i);
                list.add(cursor.getInt(0));
                // list.add(surveyBean);
            }
        } catch (Exception e) {
            Log.e(TAG, "getUserDtailerror: " + e);
        }
        return list;
    }

    public List<String> parameternamelist(String joined) {
        List<String> list = new ArrayList<>();
        try {
            Cursor cursor = database.rawQuery("SELECT parameter_name FROM parameter where parameter_id IN (" + joined + ") ; ", null);
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToPosition(i);
                list.add(cursor.getString(0));
                // list.add(surveyBean);
            }
        } catch (Exception e) {
            Log.e(TAG, "getUserDtailerror: " + e);
        }
        return list;
    }

    public String getServerIp() {
        String ip = "";
        String port = "";
        try {
            Cursor cursor = database.rawQuery("SELECT ip,port FROM server_ip ORDER BY server_ip_id DESC LIMIT 1 ", null);
            cursor.moveToPosition(0);
            ip = cursor.getString(0);
            port = cursor.getString(1);
        } catch (Exception e) {
            Log.e(TAG, "getServerIp error: " + e);
        }
        return ip + "_" + port;
    }

    public String getTcpIp() {
        String ip = "";
        String port = "";
        try {
            Cursor cursor = database.rawQuery("SELECT ip,port FROM tcp_ip ORDER BY tcp_ip_id DESC LIMIT 1 ", null);
            cursor.moveToPosition(0);
            ip = cursor.getString(0);
            port = cursor.getString(1);
        } catch (Exception e) {
            Log.e(TAG, "getServerIp error: " + e);
        }
        return ip + "_" + port;
    }

    public String getserviceChar(String type) {
        String service_uuid = "";
        String char_uuid = "";
        try {
//            Cursor cursor1 = database.rawQuery("SELECT id  FROM device_type where type='"+type+"' ", null);
//            cursor1.moveToPosition(0);
//            ip = cursor1.getString(0);
            Cursor cursor = database.rawQuery("SELECT dt.id,d.id,s.services_uuid,c.char_uuid FROM device_type as dt,device as d,services as s,charachtristics as c where dt.type='" + type + "' and  d.device_type_id=dt.id and  s.device_id=d.id and  c.service_id=s.id ", null);
            //cursor = database.rawQuery("SELECT vc.node_id,v.video FROM Videoco_ordinates as vc,Videodata as v where v.serial_no="+type"  ", null);
            cursor.moveToPosition(0);
            service_uuid = cursor.getString(2);
            char_uuid = cursor.getString(3);
        } catch (Exception e) {
            Log.e(TAG, "getServerIp error: " + e);
        }
        return service_uuid + "_" + char_uuid;
    }

    public String getserviceCharop12(int device_id) {
        String service_uuid = "";
        String char_uuid = "";
        try {
//            Cursor  cursor = database.rawQuery("SELECT m.device_name ,m.device_address,dm.ble_device_id FROM device as d" +
//                    ",model as m,device_map as dm  where dm.finished_device_id="+device_id+" and  dm.ble_device_id=d.id or  dm.module_device_id=d.id and " +
//                    "d.model_id=m.id  ", null);
            Cursor cursor = database.rawQuery("SELECT m.device_name,m.device_address ,m1.device_name,m1.device_address,d.id,d1.id FROM device  d," +
                    "device  d1" +
                    "                    ,model  m" +
                    "                     ,model  m1" +
                    "                    ,device_map  dm1," +
                    "                    device_map  dm" +
                    "                    where  dm.finished_device_id=+" + device_id + "" +
                    "                    and dm.module_device_id=d1.id" +
                    "                     and d.model_id = m.id\n" +
                    "                    and dm1.ble_device_id=d.id and" +
                    "                    d1.model_id=m1.id  order by m.model_id ", null);
            for (int i = 0; i < 1; i++) {
                cursor.moveToPosition(i);
                service_uuid = cursor.getString(0) + "-" + cursor.getString(1) + "-" + cursor.getString(4);
                char_uuid = cursor.getString(2) + "-" + cursor.getString(3) + "-" + cursor.getString(5);
            }
        } catch (Exception e) {
            Log.e(TAG, "getServerIp error: " + e);
        }
        return service_uuid + "," + char_uuid;
    }

    public String getserviceCharop(int device_id, int bleopid) {
        String service_uuid = "";
        String char_uuid = "";
        String read_uuid = "";
        try {
            Cursor cursor = database.rawQuery("SELECT s.services_uuid,c.char_uuid FROM device as d" +
                    ",ble_operation_mapping as bm,services as s,charachtristics as c  where bm.device_id=" + device_id + " and  bm.ble_operation_id=" + bleopid + " and " +
                    "c.id=bm.charachtristics_write_id and  c.service_id=s.id ", null);
            Cursor cursor2 = database.rawQuery("SELECT s.services_uuid,c.char_uuid FROM device as d" +
                    ",ble_operation_mapping as bm,services as s,charachtristics as c  where bm.device_id=" + device_id + " and  bm.ble_operation_id=" + bleopid + " and " +
                    "c.id=bm.charachtristics_read_id and  c.service_id=s.id ", null);
            cursor.moveToPosition(0);
            service_uuid = cursor.getString(0);
            char_uuid = cursor.getString(1);
            cursor2.moveToPosition(0);
            read_uuid = cursor2.getString(1);
        } catch (Exception e) {
            Log.e(TAG, "getServerIp error: " + e);
        }
        return service_uuid + "_" + char_uuid + "_" + read_uuid;
    }

    public String getdel(int id) {
        String stdel = "";
        String enddel = "";
        try {
            Cursor cursor = database.rawQuery("SELECT start_del,end_del FROM command where operation_id=" + id + " ", null);
            cursor.moveToPosition(0);
            stdel = cursor.getString(0);
            enddel = cursor.getString(1);
        } catch (Exception e) {
            Log.e(TAG, "getServerIp error: " + e);
        }
        return stdel + "_" + enddel;
    }

    public String getcahar(int id, int bleop) {
        String stdel = "";
        String enddel = "";
        try {
            Cursor cursor = database.rawQuery("SELECT charachtristics_write_id,charachtristics_write_id FROM ble_operation_mapping" +
                    " where device_id=" + id + " and ble_operation_id=" + bleop, null);
            cursor.moveToPosition(0);
            stdel = cursor.getString(0);
            enddel = cursor.getString(1);
        } catch (Exception e) {
            Log.e(TAG, "getServerIp error: " + e);
        }
        return stdel + "_" + enddel;
    }

    public String finished1(int id) {
        int stdel = 0;
        int enddel = 0;
        String bledevice = "";
        String module_id = "";
        try {
            Cursor cursor = database.rawQuery("SELECT ble_device_id,module_device_id FROM device_map where finished_device_id=" + id + " ", null);
            cursor.moveToPosition(0);
            stdel = cursor.getInt(0);
            enddel = cursor.getInt(1);
            Cursor cursor1 = database.rawQuery("SELECT model_id FROM device where id=" + stdel + " ", null);
            Cursor cursor3 = database.rawQuery("SELECT model_id FROM device where id=" + enddel + " ", null);
            cursor1.moveToPosition(0);
            cursor3.moveToPosition(0);
            bledevice = cursor1.getString(0);
            module_id = cursor3.getString(0);
        } catch (Exception e) {
            Log.e(TAG, "getServerIp error: " + e);
        }
        return bledevice + "_" + module_id + "_" + stdel + "_" + enddel;
    }

    public Map<String, Map<String, String>> finished(int id) {
        Map<String, Map<String, String>> map = new HashMap<>();
        Map<String, String> map1 = new HashMap<>();
        try {
            Cursor cursor = database.rawQuery("SELECT d1.id AS finished_device_id, dt1.type,m1.device_name, d2.id AS module_device_id,dt2.type,m2.device_name" +
                    " FROM device_map dm ,device d1,device d2,model m1,model m2,device_type dt1,device_type dt2" +
                    " WHERE dm.finished_device_id = " + id + " AND dm.finished_device_id = d1.id AND dm.module_device_id = d2.id AND d1.model_id=m1.id AND d2.model_id = m2.id" +
                    " AND d1.device_type_id = dt1.id AND d2.device_type_id = dt2.id", null);
            cursor.moveToPosition(0);
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToPosition(i);
                map1 = new HashMap<>();
                map1.put(cursor.getString(3), cursor.getString(5));
                map.put(cursor.getString(4), map1);
            }

        } catch (Exception e) {
            Log.e(TAG, "getServerIp error: " + e);
        }
        return map;
    }


    public String modeldetail(String id) {
        String stdel = "";
        String enddel = "";
        try {
            Cursor cursor = database.rawQuery("SELECT device_name FROM model where id=" + id + " ", null);
            cursor.moveToPosition(0);
            stdel = cursor.getString(0);
            // enddel = cursor.getString(1);
        } catch (Exception e) {
            Log.e(TAG, "getServerIp error: " + e);
        }
        return stdel;
    }

    public String deviceAdress(String id) {
        String stdel = "";
        String enddel = "";
        try {
            Cursor cursor = database.rawQuery("SELECT device_address FROM model where id=" + id + " ", null);
            cursor.moveToPosition(0);
            stdel = cursor.getString(0);
            // enddel = cursor.getString(1);
        } catch (Exception e) {
            Log.e(TAG, "getServerIp error: " + e);
        }
        return stdel;
    }

    public String getDevice(int id) {
        String stdel = "";
        String enddel = "";
        try {
            Cursor cursor = database.rawQuery("SELECT device_name,device_address FROM model where id=" + id + " ", null);
            cursor.moveToPosition(0);
            stdel = cursor.getString(0);
            enddel = cursor.getString(1);
        } catch (Exception e) {
            Log.e(TAG, "getServerIp error: " + e);
        }
        return stdel + "," + enddel;
    }

    public String getDevice1(int id) {
        String stdel = "";
        String enddel = "";
        try {
            Cursor cursor = database.rawQuery("SELECT device_name,device_address FROM model where id=" + id + " ", null);
            cursor.moveToPosition(0);
            stdel = cursor.getString(0);
            enddel = cursor.getString(1);
        } catch (Exception e) {
            Log.e(TAG, "getServerIp error: " + e);
        }
        return stdel + "," + enddel;
    }

    public String getDevice_id(int id) {
        String stdel = "";
        String enddel = "";
        try {
            Cursor cursor = database.rawQuery("SELECT model_id FROM device where id=" + id + " ", null);
            cursor.moveToPosition(0);
            stdel = cursor.getString(0);
        } catch (Exception e) {
            Log.e(TAG, "getServerIp error: " + e);
        }
        return stdel;
    }

    public String getDevice_id1(int id) {
        String stdel = "";
        String enddel = "";
        try {
            Cursor cursor = database.rawQuery("SELECT id FROM device where model_id=" + id + " ", null);
            cursor.moveToPosition(0);
            stdel = cursor.getString(0);
        } catch (Exception e) {
            Log.e(TAG, "getServerIp error: " + e);
        }
        return stdel;
    }

    public ArrayList<String> getdevice_detail(int id) {
        ArrayList<String> list = new ArrayList<>();
        list.add("--select--");
        try {
//            Cursor  cursor = database.rawQuery("SELECT mt.id, mt.type  FROM device_type as dt" +
//                    ",device as d,model as m,model_type as mt where  dt.type='DGPS' and d.device_type_id=dt.id and   d.model_id=m.id and " +
//                    "m.model_type_id=mt.id group by mt.type" , null);
            Cursor cursor = database.rawQuery("SELECT mt.id, mt.type  FROM device_type as dt" +
                    ",device as d,model as m,model_type as mt,manufacturer as mf where  mf.manufacturer_id=" + id + " and d.manufacturer_id=mf.manufacturer_id and   d.model_id=m.id and " +
                    "m.model_type_id=mt.id group by mt.type", null);
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToPosition(i);
                list.add(cursor.getString(1));

            }
        } catch (Exception e) {
            Log.e(TAG, "getServerIp error: " + e);
        }
        return list;
    }

    public ArrayList<String> getdevn() {
        ArrayList<String> list = new ArrayList<String>();
        try {
            Cursor cursor = database.rawQuery("SELECT device_id FROM command", null);
//            Cursor cursor = database.rawQuery("SELECT op.operation_name FROM operation as op,command as c,device as d where c.device_id=7 " +
//                    "and op.id=c.operation_id", null);
            int a = cursor.getCount();
            list.add("--select--");
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToPosition(i);
                if (!list.contains(cursor.getString(0))) {
                    list.add(cursor.getString(0));
                }
                // list.add(surveyBean);
            }
        } catch (Exception e) {
            Log.e(TAG, "getItemData error: " + e);
        }
        return list;
    }


    public void close() {
        dbHelper.close();
    }

    public long insertTCPIp(String ip, String port) {
        long result = 0;
        try {

            database.execSQL("INSERT OR REPLACE INTO tcp_ip(tcp_ip_id,ip,port,remark) values ("
                    + "1, '" + ip + "','" + port + "', (SELECT remark FROM server_ip WHERE server_ip_id = 1)) ");

            result = 1;
        } catch (Exception e) {
            Log.e(TAG, "Exception in insertEnclosure: " + e);
            result = 0;
        }
        return result;
    }


    public String returnCRCType(String command) {
        Cursor cursor = database.rawQuery("SELECT ct.crc_type_name FROM command_crc_mapping as map , crc_type as ct , command AS cd where cd.command='" + command + "' AND map.command_id = cd.command_id and map.crc_type_id = ct.crc_type_id"
                , null);

        int a = cursor.getCount();
        String str = "";
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            str = cursor.getString(cursor.getColumnIndex("crc_type_name"));
            // list.add(surveyBean);
        }

        return str;
    }


    public ArrayList<Operation> getoperationParent(int deviceid) {
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

                Operation operation = namesList.get(l);

                Cursor cursor = null;
                if (operation.getId() > 0) {
                    cursor = isParent(operation.getId());
                    int a = cursor.getCount();


                    for (int i = 0; i < cursor.getCount(); i++) {
                        cursor.moveToPosition(i);

                        listparent.add(new Operation(cursor.getInt(0), cursor.getString(2), cursor.getString(3)));

                    }

                }

            } catch (Exception e) {

            }
        }

        ArrayList<Operation> namesListparent = new ArrayList<>(listparent);
        namesListparent.add(0, new Operation(-15, "--select--", null));

        return namesListparent;
    }


    public List<Operation> getchild(int parentid) {
        HashSet<Operation> setchild = new HashSet<>();
        if (parentid > 0) {
            Cursor cursor = isChild(parentid);

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

        return listchild;
    }

    public Cursor isChild(int parentid) {
        Cursor cursorchild = null;
        Cursor cursor = database.rawQuery("SELECT id,operation_name,is_super_child FROM operation WHERE parent_id ='" + parentid + "'", null);
        int a = cursor.getCount();


        if(a!=0){
            cursor.moveToNext();
            if (cursor.getString(2).equalsIgnoreCase("on")) {
                return cursor;
            }
        }



        cursorchild = cursor;


        return cursorchild;
    }

    Cursor cursorhold = null;

    public Cursor isParent(int id) {

        Cursor cursor = database.rawQuery("SELECT id,parent_id,operation_name,is_super_child FROM operation  WHERE id='" + id + "' and parent_id!='null'", null);
        int a = cursor.getCount();
        cursor.moveToNext();
        if (a == 0 ) {
            return cursorhold;
        }

        int valId = cursor.getInt(1);


        cursorhold = cursor;


        return isParent(valId);
    }
}
//    SELECT c.crc_type
//        FROM command_crc_mapping as map , crc_type as c
//        where  map.command_id =  105 and map.crc_type_id = c.crc_type_id;