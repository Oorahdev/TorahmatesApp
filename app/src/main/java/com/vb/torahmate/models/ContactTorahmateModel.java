package com.vb.torahmate.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Column.ForeignKeyAction;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ContactTorahmateModel extends Model {

    @Expose
    @Column(name = "Coordinator", onUpdate = ForeignKeyAction.CASCADE, onDelete = ForeignKeyAction.CASCADE)
    @SerializedName("Coordinator")
    private
    String mCoordinator = "";

    @Expose
    @Column(name = "Extension", onUpdate = ForeignKeyAction.CASCADE, onDelete = ForeignKeyAction.CASCADE)
    @SerializedName("Extension")
    private
    String mExtension = "";

    @Expose
    @Column(name = "Email", onUpdate = ForeignKeyAction.CASCADE, onDelete = ForeignKeyAction.CASCADE)
    @SerializedName("Email")
    private
    String mEmail = "";

    public String getCoordinator() {
        return mCoordinator;
    }

    public void setCoordinator(String coordinator) {
        this.mCoordinator = coordinator;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        this.mEmail = email;
    }

    public String getExtension() {
        return mExtension;
    }

    public void setExtension(String extension) {
        this.mExtension = extension;
    }
}
