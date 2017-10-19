package com.vb.torahmate.events;

import com.vb.torahmate.models.MileageLogModel;

import java.util.List;

/**
 * Created by Najia on 7/27/2015.
 */
public class MileageLogEvent extends Event {

    private final List<MileageLogModel> mileageLog;

    public MileageLogEvent(List<MileageLogModel> mileageLogList) {
        this.mileageLog = mileageLogList;
    }

    public List<MileageLogModel> getList() {
        return mileageLog;
    }
}
