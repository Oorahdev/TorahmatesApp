package com.vb.torahmate.jobs;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.google.common.collect.Lists;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.path.android.jobqueue.Job;
import com.path.android.jobqueue.Params;
import com.vb.torahmate.R;
import com.vb.torahmate.events.ErrorEvent;
import com.vb.torahmate.events.MileageLogEvent;
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
 * Created by Najia on 7/27/2015.
 */
public class MileageLogJob extends Job {

    public MileageLogJob() {
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
        HttpRequest request = HttpRequest.get(AppURL.urlMileageLog);
        request.header(Constants.HEADER, SPAccountsManager.getToken(AppManager.getAppContext()));
        L.m(request + "");
        request.connectTimeout(30000);
        if (request.ok()) {
            String responseStr = request.body();
            JsonObject obj = AppManager.getInstance().getGSON().fromJson(responseStr, JsonObject.class);
            String resultStr = obj.get(Constants.TYPE).getAsString();
            if (resultStr.equals(Constants.SUCCESS_RESPONSE)) {
                List<MileageLogModel> list = parseJson(responseStr);
                list = Lists.reverse(list);
                saveMilesData(list);
                AppManager.getInstance().getEventBus().post(new MileageLogEvent(list.subList(0,10)));
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
        list = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create().fromJson(jsonArray, type);
        return list;
    }

    private void saveMilesData(List<MileageLogModel> list) {
        new Delete().from(MileageLogModel.class).execute();
        ActiveAndroid.beginTransaction();
        try {
            for(MileageLogModel m : list){
                MileageLogModel item=m;
                item.save();
            }
            ActiveAndroid.setTransactionSuccessful();
        }
        finally {
            ActiveAndroid.endTransaction();
        }
    }
}