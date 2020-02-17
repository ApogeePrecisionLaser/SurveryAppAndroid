package com.apogee.fleetsurvey.multiview;

import java.util.List;
import java.util.Map;

public class ItemType {


//    public ItemType(int type, String title, List<OnDropdownlist> onDropdownlistList) {
//        this.type = type;
//        this.title = title;
//        this.onDropdownlistList = onDropdownlistList;
//    }

    public static final int DROPDOWNTYPE=0;
    public static final int INPUTTYPE=1;

    public int type;
    public String title;
    List<String> stringList;

    public Map<String, String> getStringStringMapdrop() {
        return stringStringMapdrop;
    }

    Map<String,String> stringStringMapdrop;

    public ItemType(int type, String title, Map<String, String> stringStringMapdrop) {
        this.type = type;
        this.title = title;
        this.stringStringMapdrop = stringStringMapdrop;
    }

    public ItemType(int type, String title) {
        this.type = type;
        this.title = title;
    }

    public List<String> getStringList() {
        return stringList;
    }

    public int getType() {
        return type;
    }

    public ItemType(int type, String title, List<String> stringList) {
        this.type = type;
        this.title = title;
        this.stringList = stringList;


    }




    class OnDropdownlist{
        public String dropdownvalue;
        public String dropdownvalueId;

        public OnDropdownlist(String dropdownvalue, String dropdownvalueId) {
            this.dropdownvalue = dropdownvalue;
            this.dropdownvalueId = dropdownvalueId;
        }
    }


}
