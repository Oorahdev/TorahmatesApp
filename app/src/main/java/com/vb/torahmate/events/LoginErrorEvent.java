package com.vb.torahmate.events;

/**
 * Created by Najia on 9/17/2015.
 */
public class LoginErrorEvent {

    private final String mMessage;

    public LoginErrorEvent(String message) {
        mMessage = message;
    }

    public String getMsg() {
        return mMessage;
    }

}
