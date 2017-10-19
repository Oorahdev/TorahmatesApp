package com.vb.torahmate.events;

import com.vb.torahmate.models.ContactTorahmateModel;
import com.vb.torahmate.models.LoginInfoModel;

import java.util.List;

/**
 * Created by Najia on 7/24/2015.
 */
public class LoginInfoEvent extends Event {

    private final String mMessage;

    public LoginInfoEvent(String message) {
        mMessage = message;
    }

    public String getMsg() {
        return mMessage;
    }
}
