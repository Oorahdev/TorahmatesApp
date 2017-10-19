package com.vb.torahmate.jobs;

import com.google.gson.JsonObject;
import com.path.android.jobqueue.Job;
import com.path.android.jobqueue.Params;
import com.vb.torahmate.R;
import com.vb.torahmate.events.DashboardEvent;
import com.vb.torahmate.events.ErrorEvent;
import com.vb.torahmate.events.LoginInfoEvent;
import com.vb.torahmate.utils.AppManager;
import com.vb.torahmate.utils.AppURL;
import com.vb.torahmate.utils.Constants;
import com.vb.torahmate.utils.HttpRequest;
import com.vb.torahmate.utils.L;
import com.vb.torahmate.utils.SPAccountsManager;
import com.vb.torahmate.utils.Util;

import org.json.JSONException;

public class DashboardJob extends Job {

    public DashboardJob() {
        super(new Params(Constants.PRIORITY).requireNetwork().persist());
    }

    @Override
    public void onAdded() {

    }

    @Override
    public void onRun() throws Throwable {
        getData();
    }

    @Override
    protected void onCancel() {

    }

    @Override
    protected boolean shouldReRunOnThrowable(Throwable arg0) {
        // TODO Auto-generated method stub
        return false;
    }

    private void getData() throws JSONException {
        HttpRequest request = HttpRequest.get(AppURL.urlMileageDashboard);
        request.header(Constants.HEADER, SPAccountsManager.getToken(AppManager.getAppContext()));
        L.m(request + "");
        request.connectTimeout(30000);
        if (request.ok()) {
            String responseStr = request.body();
            JsonObject obj = AppManager.getInstance().getGSON().fromJson(responseStr, JsonObject.class);
            String resultStr = obj.get(Constants.TYPE).getAsString();
            if (resultStr.equals(Constants.SUCCESS_RESPONSE)) {
                String minutesUnverified = obj.get(Constants.MinutesUnverified).getAsString();
                String minutesVerified = obj.get(Constants.MinutesVerified).getAsString();
                String minutesTotal = obj.get(Constants.MinutesTotal).getAsString();
                String mileageAvailable = obj.get(Constants.MileageAvailable).getAsString();
                String milesUsed = obj.get(Constants.MilesUsed).getAsString();
                String milesTotal = obj.get(Constants.MilesTotal).getAsString();
                SPAccountsManager.setMinutesUnverified(AppManager.getAppContext(), minutesUnverified);
                SPAccountsManager.setMinutesVerified(AppManager.getAppContext(), minutesVerified);
                SPAccountsManager.setMinutesTotal(AppManager.getAppContext(), minutesTotal);
                SPAccountsManager.setMileageAvailable(AppManager.getAppContext(), mileageAvailable);
                SPAccountsManager.setMilesUsed(AppManager.getAppContext(), milesUsed);
                SPAccountsManager.setMilesTotal(AppManager.getAppContext(), milesTotal);
                AppManager.getInstance().getEventBus().post(new DashboardEvent(Constants.SUCCESS_RESPONSE));
            } else if (resultStr.equals(Constants.ERROR_RESPONSE)) {
                String msg = obj.get(Constants.MESSAGE).getAsString();
                AppManager.getInstance().getEventBus().post(new ErrorEvent(msg));
            }
        }else {
            Util.showToast(AppManager.getAppContext(), AppManager.getAppContext().getResources().getString(R.string.timeOut));
        }
    }
}