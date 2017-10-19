package com.vb.torahmate.events;

/**
 * Created by Najia on 7/28/2015.
 */
public class LearningScheduleEvent extends Event {

    private final String mMessage;

    public LearningScheduleEvent(String message) {
        mMessage = message;
    }

    public String getMsg() {
        return mMessage;
    }
}
