package com.vb.torahmate.jobs;

import com.google.gson.JsonObject;
import com.path.android.jobqueue.Job;
import com.path.android.jobqueue.Params;
import com.vb.torahmate.R;
import com.vb.torahmate.events.ErrorEvent;
import com.vb.torahmate.events.LearningScheduleErrorEvent;
import com.vb.torahmate.events.LearningScheduleEvent;
import com.vb.torahmate.utils.AppManager;
import com.vb.torahmate.utils.AppURL;
import com.vb.torahmate.utils.Constants;
import com.vb.torahmate.utils.HttpRequest;
import com.vb.torahmate.utils.L;
import com.vb.torahmate.utils.SPAccountsManager;
import com.vb.torahmate.utils.Util;

import org.json.JSONException;

/**
 * Created by Najia on 7/28/2015.
 */
public class ReportLearningMinutesJob extends Job {

    private static String mStrMinutes;
    String mRelatedTo;
    String mRelatedId;
    String mDate;

    public ReportLearningMinutesJob(String strRelatedId, String strRelatedTo, String strMinutes, String strDate) {
        super(new Params(Constants.PRIORITY).requireNetwork().persist());
        this.mStrMinutes = strMinutes;
        this.mRelatedId = strRelatedId;
        this.mRelatedTo = strRelatedTo;
        this.mDate = strDate;
    }

    @Override
    public void onAdded() {
        // TODO Auto-generated method stub
    }

    @Override
    protected void onCancel() {
        // TODO Auto-generated method stub

    }

    @Override
    public void onRun() throws Throwable {
        // TODO Auto-generated method stub
        getData();
    }

    @Override
    protected boolean shouldReRunOnThrowable(Throwable arg0) {
        // TODO Auto-generated method stub
        return false;
    }

    private void getData() throws JSONException {
        HttpRequest request = HttpRequest.post(AppURL.urlMileageEnterSessionMobile);
        request.header(Constants.HEADER, SPAccountsManager.getToken(AppManager.getAppContext()));
        request.part(Constants.RELATED_TO, mRelatedTo);
        request.part(Constants.RELATED_ID, mRelatedId);
        request.part(Constants.MINUTES, mStrMinutes);
        request.part(Constants.DATE, mDate);
        L.m(request + "");
        request.connectTimeout(30000);
        if (request.ok()) {
            String responseStr = request.body();
            JsonObject obj = AppManager.getInstance().getGSON().fromJson(responseStr, JsonObject.class);
            String res = obj.get(Constants.TYPE).getAsString();
            if (res.equals(Constants.SUCCESS_RESPONSE)) {
                String msg = obj.get(Constants.RESULT_DATA).getAsString().toString();
                AppManager.getInstance().getEventBus().post(new LearningScheduleEvent(msg));
            } else if (res.equals(Constants.ERROR_RESPONSE)) {
                String msg = obj.get(Constants.MESSAGE).getAsString();
                AppManager.getInstance().getEventBus().post(new LearningScheduleErrorEvent(msg));
            }
        } else {
            Util.showToast(AppManager.getAppContext(), AppManager.getAppContext().getResources().getString(R.string.timeOut));
        }
    }
}