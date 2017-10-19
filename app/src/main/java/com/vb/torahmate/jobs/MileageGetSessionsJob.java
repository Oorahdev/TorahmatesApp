package com.vb.torahmate.jobs;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.path.android.jobqueue.Job;
import com.path.android.jobqueue.Params;
import com.vb.torahmate.R;
import com.vb.torahmate.events.ErrorEvent;
import com.vb.torahmate.events.MileageGetSessionEvent;
import com.vb.torahmate.models.MileageLogModel;
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

/**
 * Created by Najia on 10/2/2015.
 */
public class MileageGetSessionsJob extends Job {

    public MileageGetSessionsJob() {
        super(new Params(Constants.PRIORITY).requireNetwork().persist());
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
        HttpRequest request = HttpRequest.get(AppURL.urlMileageGetSessions);
        request.header(Constants.HEADER, SPAccountsManager.getToken(AppManager.getAppContext()));
        L.m(request + "");
        request.connectTimeout(30000);
        if (request.ok()) {
            String responseStr = request.body();
            JsonObject obj = AppManager.getInstance().getGSON().fromJson(responseStr, JsonObject.class);
            String resultStr = obj.get(Constants.TYPE).getAsString();
            if (resultStr.equals(Constants.SUCCESS_RESPONSE)) {
                String totalMiles = obj.get(Constants.TOTAL_MILES).getAsString();
                String time = obj.get(Constants.MILES_UPDATE_TIME).getAsString();
                String minutes = obj.get(Constants.MILES_UPDATE_MINUTES).getAsString();
                String miles = obj.get(Constants.MILES).getAsString();
//                SPAccountsManager.setTotalMiles(AppManager.getAppContext(), totalMiles);
                SPAccountsManager.setMilesUpdateTime(AppManager.getAppContext(), time);
                SPAccountsManager.setMilesUpdateMinutes(AppManager.getAppContext(), minutes);
                SPAccountsManager.setMiles(AppManager.getAppContext(), miles);
                AppManager.getInstance().getEventBus().post(new MileageGetSessionEvent(Constants.SUCCESS_RESPONSE));
            } else if (resultStr.equals(Constants.ERROR_RESPONSE)) {
                String msg = obj.get(Constants.MESSAGE).getAsString();
                AppManager.getInstance().getEventBus().post(new ErrorEvent(msg));
            }
        } else {
            Util.showToast(AppManager.getAppContext(), AppManager.getAppContext().getResources().getString(R.string.timeOut));
        }
    }

    private List<MileageLogModel> parseJson(String str) {
        List<MileageLogModel> list;
        JsonObject jsonObject = new JsonParser().parse(str).getAsJsonObject();
        JsonArray jsonArray = jsonObject.getAsJsonArray(Constants.RESULT_DATA);
        Type type = new TypeToken<ArrayList<MileageLogModel>>() {
        }.getType();
        list = AppManager.getInstance().getGSON().fromJson(jsonArray, type);
        return list;
    }
}
