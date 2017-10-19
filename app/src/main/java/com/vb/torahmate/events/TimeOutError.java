package com.vb.torahmate.events;

/**
 * Created by Vbase on 1/18/2016.
 */
public class TimeOutError {
    private final String mMessage;
    private String mContextName;

    public TimeOutError(String mContextName, String message) {
        this.mContextName = mContextName;
        mMessage = message;
    }

    public String getMsg() {
        return mMessage;
    }

    public String getContext() {
        return mContextName;
    }
}
