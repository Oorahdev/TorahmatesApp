package com.vb.torahmate.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Column.ForeignKeyAction;
import com.activeandroid.annotation.Table;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Najia on 7/27/2015.
 */
@Table(name = "MileageLogModel")
public class MileageLogModel extends Model {

    @Expose
    @Column(name="SessionId" , onUpdate = ForeignKeyAction.CASCADE, onDelete = ForeignKeyAction.CASCADE)
    @SerializedName("SessionId")
    private
    String mSessionId;

    @Expose
    @Column(name="Date" , onUpdate = ForeignKeyAction.CASCADE, onDelete = ForeignKeyAction.CASCADE)
    @SerializedName("Date")
    private
    String mDate;

    @Expose
    @Column(name="Minutes" , onUpdate = ForeignKeyAction.CASCADE, onDelete = ForeignKeyAction.CASCADE)
    @SerializedName("Minutes")
    private
    String mMinutes;

    @Expose
    @Column(name="RelatedTo" , onUpdate = ForeignKeyAction.CASCADE, onDelete = ForeignKeyAction.CASCADE)
    @SerializedName("RelatedTo")
    private
    String mRelatedTo;

    @Expose
    @Column(name="RelatedId" , onUpdate = ForeignKeyAction.CASCADE, onDelete = ForeignKeyAction.CASCADE)
    @SerializedName("RelatedId")
    private
    String mRelatedId;

    @Expose
    @Column(name="Verified" , onUpdate = ForeignKeyAction.CASCADE, onDelete = ForeignKeyAction.CASCADE)
    @SerializedName("Verified")
    private
    String mVerified;

    @Expose
    @Column(name="Miles" , onUpdate = ForeignKeyAction.CASCADE, onDelete = ForeignKeyAction.CASCADE)
    @SerializedName("Miles")
    private
    String mMiles;

    @Expose
    @Column(name="Name2" , onUpdate = ForeignKeyAction.CASCADE, onDelete = ForeignKeyAction.CASCADE)
    @SerializedName("Name2")
    private
    String mName2;

    public String getSessionId() {
        return mSessionId;
    }

    public void setSessionId(String sessionId) {
        this.mSessionId = sessionId;
    }

    public String getMinutes() {
        return mMinutes;
    }

    public void setMinutes(String minutes) {
        this.mMinutes = minutes;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        this.mDate = date;
    }

    public String getRelatedTo() {
        return mRelatedTo;
    }

    public void setmRelatedTo(String relatedTo) {
        this.mRelatedTo = relatedTo;
    }

    public String getRelatedId() {
        return mRelatedId;
    }

    public void setRelatedId(String relatedId) {
        this.mRelatedId = relatedId;
    }

    public String getVerified() {
        return mVerified;
    }

    public void setVerified(String verified) {
        this.mVerified = verified;
    }

    public String getMiles() {
        return mMiles;
    }

    public void setMiles(String miles) {
        this.mMiles = miles;
    }

    public String getName2() {
        return mName2;
    }

    public void setName2(String name2) {
        this.mName2 = name2;
    }
}