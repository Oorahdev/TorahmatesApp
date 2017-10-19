package com.vb.torahmate.events;

public class ErrorEvent {

    private final String mMessage;

    public ErrorEvent(String message) {
        mMessage = message;
    }

    public String getMsg() {
        return mMessage;
    }
}
