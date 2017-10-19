package com.vb.torahmate.jobs;

import com.activeandroid.query.Delete;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.path.android.jobqueue.Job;
import com.path.android.jobqueue.Params;
import com.vb.torahmate.R;
import com.vb.torahmate.events.CallTorahmateEvent;
import com.vb.torahmate.events.ErrorEvent;
import com.vb.torahmate.events.TimeOutError;
import com.vb.torahmate.models.TmInfoModel;
import com.vb.torahmate.models.MilesReportLearningModel;
import com.vb.torahmate.utils.AppManager;
import com.vb.torahmate.utils.AppURL;
import com.vb.torahmate.utils.Constants;
import com.vb.torahmate.utils.HttpRequest;
import com.vb.torahmate.utils.L;
import com.vb.torahmate.utils.SPAccountsManager;

import org.json.JSONException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class CallTorahmateJob extends Job {

    private String mContextName;

    public CallTorahmateJob(String mContextName) {
        super(new Params(Constants.PRIORITY).requireNetwork().persist());
        this.mContextName = mContextName;
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
        HttpRequest request = HttpRequest.get(AppURL.urlMyTorahmates);
        request.header(Constants.HEADER, SPAccountsManager.getToken(AppManager.getAppContext()));
        L.m(request + "");
        request.connectTimeout(30000);
        if (request.ok()) {
            String responseStr = request.body();
            JsonObject obj = AppManager.getGSON().fromJson(responseStr, JsonObject.class);
            String resultStr = obj.get(Constants.TYPE).getAsString();
            if (resultStr.equals(Constants.SUCCESS_RESPONSE)) {
                saveData(responseStr);
                List<TmInfoModel> list = parseJson(responseStr);
                AppManager.getEventBus().post(new CallTorahmateEvent(list));

            } else if (resultStr.equals(Constants.ERROR_RESPONSE)) {
                String msg;
                if (obj.get(Constants.MESSAGE) != null)
                    msg = obj.get(Constants.MESSAGE).getAsString();
                else
                    msg = obj.get(Constants.RESULT_DATA).getAsString();
                AppManager.getEventBus().post(new ErrorEvent(msg));
            }
        } else {
            AppManager.getEventBus().post(new TimeOutError(mContextName, AppManager.getAppContext().getResources().getString(R.string.timeOut)));
        }
    }

    private void saveData(String responseStr) {
        new Delete().from(MilesReportLearningModel.class).executeSingle();
        MilesReportLearningModel milesReportLearningModel = new MilesReportLearningModel();
        milesReportLearningModel.setResponseString(responseStr);
        milesReportLearningModel.save();
    }

    private List<TmInfoModel> parseJson(String str) {
        List<TmInfoModel> list;
        JsonObject jsonObject = new JsonParser().parse(str).getAsJsonObject();
        JsonArray jsonArray = jsonObject.getAsJsonArray(Constants.RESULT_DATA);
        Type type = new TypeToken<ArrayList<TmInfoModel>>() {
        }.getType();
        list = AppManager.getGSON().fromJson(jsonArray, type);
        return list;
    }
}