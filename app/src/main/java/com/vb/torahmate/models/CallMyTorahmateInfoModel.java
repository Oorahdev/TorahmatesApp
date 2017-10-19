package com.vb.torahmate.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Najia on 8/10/2015.
 */
public class CallMyTorahmateInfoModel {

    @SerializedName("TEL_Type")
    private
    String mTelType;

    @SerializedName("TEL_Display")
    private
    String mTelDisplay;

    @SerializedName("Tel_Data")
    private
    String mTelData;

    @SerializedName("PersonalPhone")
    private
    String mPersonalPhone;

    public String getPersonalPhone() {
        return mPersonalPhone;
    }

    public void setPersonalPhone(String personalPhone) {
        this.mPersonalPhone = personalPhone;
    }

    public String getTelType() {
        return mTelType;
    }

    public void setTelType(String telType) {
        this.mTelType = telType;
    }

    public String getTelDisplay() {
        return mTelDisplay;
    }

    public void setTelDisplay(String telDisplay) {
        this.mTelDisplay = telDisplay;
    }

    public String getTelData() {
        return mTelData;
    }

    public void setTelData(String telData) {
        this.mTelData = telData;
    }
}
