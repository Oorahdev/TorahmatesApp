package com.vb.torahmate.events;

import com.vb.torahmate.models.UpdateTokenModel;

import java.util.List;

/**
 * Created by twender on 11/9/2017.
 */

public class UpdateTokenEvent extends Event{

    /*private final List<UpdateTokenModel> updateToken;

    public UpdateTokenEvent(List<UpdateTokenModel> updateTokenList) {
        this.updateToken = updateTokenList;
    }

    public List<UpdateTokenModel> getList() {return updateToken;}*/

    private final String mMessage;

    public UpdateTokenEvent(String message) {
        mMessage = message;
    }

    public String getMsg() {
        return mMessage;
    }
}
