package com.vb.torahmate.events;

/**
 * Created by Najia on 7/28/2015.
 */
public class LearningScheduleErrorEvent extends Event {

    private final String mMessage;

    public LearningScheduleErrorEvent(String message) {
        mMessage = message;
    }

    public String getMsg() {
        return mMessage;
    }
}
