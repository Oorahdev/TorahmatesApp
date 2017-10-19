package com.vb.torahmate.models;

import com.activeandroid.Model;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Najia on 7/24/2015.
 */
public class LoginInfoModel extends Model {

    @SerializedName("FirstName")
    private
    String mFirstName;

    @SerializedName("LastName")
    private
    String mLastName;


    public String getFirstName() {
        return mFirstName;
    }

    public void setFirstName(String firstName) {
        this.mFirstName = firstName;
    }

    public String getLastName() {
        return mLastName;
    }

    public void setLastName(String lastName) {
        this.mLastName = lastName;
    }

    public String getFullName() {
        return mFirstName + " " + mLastName;
    }
}