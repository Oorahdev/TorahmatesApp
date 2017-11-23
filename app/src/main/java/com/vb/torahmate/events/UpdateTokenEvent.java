package com.vb.torahmate.events;

import com.vb.torahmate.models.UpdateTokenModel;

import java.util.List;

/**
 * Created by twender on 11/9/2017.
 */

public class UpdateTokenEvent extends Event{

    private final String mMessage;

    public UpdateTokenEvent(String message) {
        mMessage = message;
    }

    public String getMsg() {
        return mMessage;
    }
}
