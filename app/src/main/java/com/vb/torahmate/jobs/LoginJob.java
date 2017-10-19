package com.vb.torahmate.jobs;

import com.google.gson.JsonObject;
import com.path.android.jobqueue.Job;
import com.path.android.jobqueue.Params;
import com.vb.torahmate.R;
import com.vb.torahmate.events.LoginErrorEvent;
import com.vb.torahmate.events.SuccessEvent;
import com.vb.torahmate.utils.AppManager;
import com.vb.torahmate.utils.AppURL;
import com.vb.torahmate.utils.Constants;
import com.vb.torahmate.utils.HttpRequest;
import com.vb.torahmate.utils.L;
import com.vb.torahmate.utils.SPAccountsManager;
import com.vb.torahmate.utils.Util;

import org.json.JSONException;

public class LoginJob extends Job {

    private String mEmail, mPassword;

    public LoginJob(String email, String password) {
        super(new Params(Constants.PRIORITY).requireNetwork().persist());
        this.mEmail = email;
        this.mPassword = password;
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
        HttpRequest request = HttpRequest.post(AppURL.urlLogin);
        request.part(Constants.USER_NAME, mEmail);
        request.part(Constants.PASSWORD, mPassword);
        request.part(Constants.IP_ADDRESS, Util.getIPAddress());
        request.part(Constants.API_KEY, AppURL.API_KEY);
        request.part(Constants.IMEI_NUMBER, Util.getDeviceIMEINumber(AppManager.getAppContext()));
        L.m(request + "");
        request.connectTimeout(30000);
        if (request.ok()) {
            String responseStr = request.body();
            JsonObject obj = AppManager.getInstance().getGSON().fromJson(responseStr, JsonObject.class);
            String resultMessage = obj.get(Constants.TYPE).getAsString();
            if (resultMessage.equals(Constants.SUCCESS_RESPONSE)) {
                String token = obj.get(Constants.HEADER).getAsString();
                SPAccountsManager.setToken(AppManager.getAppContext(), token);
                String loginToken = obj.get(Constants.AUTH_LOG_IN_TOKEk).getAsString();
                SPAccountsManager.setAuthLogInToken(AppManager.getAppContext(), loginToken);
                SPAccountsManager.setCheckLogin(AppManager.getAppContext(), true);
                AppManager.getInstance().getEventBus().post(new SuccessEvent(Constants.SUCCESS_RESPONSE));
            } else if (resultMessage.equals(Constants.ERROR_RESPONSE)) {
                String msg = obj.get(Constants.RESULT_DATA).getAsString();
                AppManager.getInstance().getEventBus().post(new LoginErrorEvent(msg));
            }
        } else {
            Util.showToast(AppManager.getAppContext(), AppManager.getAppContext().getResources().getString(R.string.timeOut));
        }
    }
}