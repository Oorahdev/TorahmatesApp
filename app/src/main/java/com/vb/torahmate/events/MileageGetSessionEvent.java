package com.vb.torahmate.events;

/**
 * Created by Najia on 10/2/2015.
 */
public class MileageGetSessionEvent extends Event {

    private final String mMessage;

    public MileageGetSessionEvent (String message) {
        mMessage = message;
    }

    public String getMsg() {
        return mMessage;
    }
}
