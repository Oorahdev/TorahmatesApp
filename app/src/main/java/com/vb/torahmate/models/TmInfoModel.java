package com.vb.torahmate.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Najia on 7/24/2015.
 */
public class TmInfoModel {

    @SerializedName("Name")
    private
    String mName;

    @SerializedName("RelatedTo")
    private
    String mRelatedTo;

    @SerializedName("RelatedId")
    private
    String mRelatedId;

    @SerializedName("Tels")
    private
    List<CallMyTorahmateInfoModel> mTelsList = new ArrayList<>();

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public String getRelatedTo() {
        return mRelatedTo;
    }

    public void setRelatedTo(String relatedTo) {
        this.mRelatedTo = relatedTo;
    }

    public String getRelatedId() {
        return mRelatedId;
    }

    public void setRelatedId(String relatedId) {
        this.mRelatedId = relatedId;
    }

    public List<CallMyTorahmateInfoModel> getTelsList() {
        return mTelsList;
    }

    public void setTelsList(List<CallMyTorahmateInfoModel> tellList) {
        this.mTelsList = tellList;
    }
}