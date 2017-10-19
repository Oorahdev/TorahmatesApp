package com.vb.torahmate.events;

/**
 * Created by Najia on 7/22/2015.
 */
public class SuccessEvent {

    private final String mMessage;

    public SuccessEvent(String message) {
        mMessage = message;
    }

    public String getMsg() {
        return mMessage;
    }
}
