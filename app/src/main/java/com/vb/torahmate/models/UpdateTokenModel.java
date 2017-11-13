package com.vb.torahmate.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by twender on 11/9/2017.
 */

@Table(name = "UpdateTokenModel")
public class UpdateTokenModel extends Model{

    @Expose
    @Column(name ="KVMLO_ID" , onUpdate = Column.ForeignKeyAction.CASCADE, onDelete = Column.ForeignKeyAction.CASCADE)
            @SerializedName("KVMLO_ID")
    private
    String mID;

    @Expose
    @Column(name="RegistrationToken", onUpdate = Column.ForeignKeyAction.CASCADE, onDelete = Column.ForeignKeyAction.CASCADE)
    @SerializedName("RegistrationToken")
    private
    String mRegistrationToken;

    public String getID() {return mID;}

    public void setID(String id) {this.mID = id;}

    public String getRegistrationToken() {return mRegistrationToken;}

    public void setRegistrationToken(String registrationToken) {this.mRegistrationToken = registrationToken;}
}
