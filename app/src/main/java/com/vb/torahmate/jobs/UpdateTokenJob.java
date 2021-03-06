package com.vb.torahmate.jobs;

import android.util.Log;

import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonObject;
import com.path.android.jobqueue.Job;
import com.path.android.jobqueue.Params;
import com.vb.torahmate.R;
import com.vb.torahmate.events.ErrorEvent;
import com.vb.torahmate.events.UpdateTokenEvent;
import com.vb.torahmate.utils.AppManager;
import com.vb.torahmate.utils.AppURL;
import com.vb.torahmate.utils.Constants;
import com.vb.torahmate.utils.HttpRequest;
import com.vb.torahmate.utils.L;
import com.vb.torahmate.utils.SPAccountsManager;
import com.vb.torahmate.utils.Util;

import org.json.JSONException;

import java.util.Map;

/**
 * Created by twender on 11/13/2017.
 */

public class UpdateTokenJob extends Job {

        private static String mToken;
        private static String TAG = UpdateTokenJob.class.getCanonicalName();

        public UpdateTokenJob(String token) {
            super(new Params(Constants.PRIORITY).requireNetwork().persist());
            this.mToken = token;
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
            HttpRequest request = HttpRequest.post(AppURL.urlUpdateToken);
            Map<String, String> headers = ImmutableMap.of(
                    Constants.HEADER, SPAccountsManager.getToken(AppManager.getAppContext()),
                    Constants.RegistrationToken, mToken
            );
            request.headers(headers);
            L.m(request + "");
            request.connectTimeout(30000);
            if (request.ok()) {
                String responseStr = request.body();
                JsonObject obj = AppManager.getInstance().getGSON().fromJson(responseStr, JsonObject.class);
                String resultStr = obj.get(Constants.TYPE).getAsString();
                if (resultStr.equals(Constants.SUCCESS_RESPONSE)) {

                    AppManager.getInstance().getEventBus().post(new UpdateTokenEvent(Constants.SUCCESS_RESPONSE));
                } else if (resultStr.equals(Constants.ERROR_RESPONSE)) {
                    String msg = obj.get(Constants.MESSAGE).getAsString();
                    AppManager.getInstance().getEventBus().post(new ErrorEvent(msg));
                }
            }else {
                Util.showToast(AppManager.getAppContext(), AppManager.getAppContext().getResources().getString(R.string.timeOut));
            }
        }

}
