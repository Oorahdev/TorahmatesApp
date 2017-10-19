package com.vb.torahmate.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by Vbase on 12/2/2015.
 */

@Table(name = "MilesReportLearningModel")
public class MilesReportLearningModel extends Model {

    @Column(name = "responseString")
    private String responseString;

    public String getResponseString() {
        return responseString;
    }

    public void setResponseString(String responseString) {
        this.responseString= responseString;
    }

    public MilesReportLearningModel(){
        super();
    }

    public MilesReportLearningModel(String responseString){
        super();
        this.responseString=responseString;
    }
}
