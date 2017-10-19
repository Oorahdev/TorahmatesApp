package com.vb.torahmate.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Najia on 10/6/2015.
 */
public class GetTorahmatesModel  extends Model {

    @Expose
    @Column(name="Name" , onUpdate = Column.ForeignKeyAction.CASCADE, onDelete = Column.ForeignKeyAction.CASCADE)
    @SerializedName("Name")
    private
    String mTorahmates;

    @Expose
    @Column(name="RelatedTo" , onUpdate = Column.ForeignKeyAction.CASCADE, onDelete = Column.ForeignKeyAction.CASCADE)
    @SerializedName("RelatedTo")
    private
    String mRelatedTo;

    @Expose
    @Column(name="RelatedId" , onUpdate = Column.ForeignKeyAction.CASCADE, onDelete = Column.ForeignKeyAction.CASCADE)
    @SerializedName("RelatedId")
    private
    String mRelatedId;

    public String getTorahmates() {
        return mTorahmates;
    }

    public void setTorahmates(String torahmates) {
        this.mTorahmates = torahmates;
    }

    public String getRelatedTo() {
        return mRelatedTo;
    }

    public void setRelatedTo(String mRelatedTo) {
        this.mRelatedTo = mRelatedTo;
    }

    public String getRelatedId() {
        return mRelatedId;
    }

    public void setRelatedId(String mRelatedId) {
        this.mRelatedId = mRelatedId;
    }
}
