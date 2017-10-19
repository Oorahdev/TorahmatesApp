package com.vb.torahmate.events;

import com.vb.torahmate.models.TmInfoModel;

import java.util.List;

/**
 * Created by Najia on 7/24/2015.
 */
public class CallTorahmateEvent extends Event {

    private final List<TmInfoModel> mTellsList;

    public CallTorahmateEvent(List<TmInfoModel> tellsList) {

        this.mTellsList = tellsList;
    }

    public List<TmInfoModel> getList() {
        return mTellsList;
    }
}