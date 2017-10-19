package com.vb.torahmate.jobs;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.path.android.jobqueue.Job;
import com.path.android.jobqueue.Params;
import com.vb.torahmate.R;
import com.vb.torahmate.events.ErrorEvent;
import com.vb.torahmate.events.LoginErrorEvent;
import com.vb.torahmate.events.LoginInfoEvent;
import com.vb.torahmate.events.SuccessEvent;
import com.vb.torahmate.models.LoginInfoModel;
import com.vb.torahmate.utils.AppManager;
import com.vb.torahmate.utils.AppURL;
import com.vb.torahmate.utils.Constants;
import com.vb.torahmate.utils.HttpRequest;
import com.vb.torahmate.utils.L;
import com.vb.torahmate.utils.SPAccountsManager;
import com.vb.torahmate.utils.Util;

import org.json.JSONException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class LoginInfoJob extends Job {

    public LoginInfoJob() {
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
        HttpRequest request = HttpRequest.get(AppURL.urlMileageLoginInfo);
        request.header(Constants.HEADER, SPAccountsManager.getToken(AppManager.getAppContext()));
        L.m(request + "");
        request.connectTimeout(30000);
        if (request.ok()) {
            String responseStr = request.body();
            JsonObject obj = AppManager.getInstance().getGSON().fromJson(responseStr, JsonObject.class);
            String resultStr = obj.get(Constants.TYPE).getAsString();
            if (resultStr.equals(Constants.SUCCESS_RESPONSE)) {
                String firstName = obj.get(Constants.FIRST_NAME).getAsString();
                String lastName = obj.get(Constants.LAST_NAME).getAsString();
                SPAccountsManager.setFirstName(AppManager.getAppContext(), firstName);
                SPAccountsManager.setLastName(AppManager.getAppContext(), lastName);
                AppManager.getInstance().getEventBus().post(new LoginInfoEvent(Constants.SUCCESS_RESPONSE));
            } else if (resultStr.equals(Constants.ERROR_RESPONSE)) {
                String msg = obj.get(Constants.MESSAGE).getAsString();
                AppManager.getInstance().getEventBus().post(new ErrorEvent(msg));
            }
        }else {
            Util.showToast(AppManager.getAppContext(), AppManager.getAppContext().getResources().getString(R.string.timeOut));
        }
    }
}