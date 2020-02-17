package com.apogee.fleetsurvey.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class Operation {

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

    @NonNull
    @Override
    public String toString() {
        return name;
    }
}
