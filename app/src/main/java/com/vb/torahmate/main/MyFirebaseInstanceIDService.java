package com.vb.torahmate.main;

/**
 * Copyright 2016 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */



import android.content.Intent;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.gson.JsonObject;
import com.path.android.jobqueue.JobManager;
import com.vb.torahmate.R;
import com.vb.torahmate.accounts.Login;
import com.vb.torahmate.events.ErrorEvent;
import com.vb.torahmate.events.UpdateTokenEvent;
import com.vb.torahmate.jobs.LoginJob;
import com.vb.torahmate.jobs.UpdateTokenJob;
import com.vb.torahmate.utils.AppManager;
import com.vb.torahmate.utils.AppURL;
import com.vb.torahmate.utils.Constants;
import com.vb.torahmate.utils.HttpRequest;
import com.vb.torahmate.utils.L;
import com.vb.torahmate.utils.SPAccountsManager;
import com.vb.torahmate.utils.Util;

import static com.vb.torahmate.utils.Util.mConnReceiver;


/**
 * Created by twender on 10/24/2017.
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {


    private static final String TAG = "MyFirebaseIIDService";
    private JobManager mJobManager = AppManager.getInstance().getJobManager();
    public String refreshedToken;
    //ConnectionClass connectionClass;

    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the InstanceID token
     * is initially generated so this is where you would retrieve the token.
     */
    // [START refresh_token]
    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);

        //run UpdateTokenJob
        //runJob(refreshedToken);

        //send refreshed token to database
        //this happens in Login.java because it can only be entered into kvmlo once user logs in

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        sendRegistrationToServer(refreshedToken);
    }
    // [END refresh_token]

    /**
     * Persist token to third-party servers.
     *
     * Modify this method to associate the user's FCM InstanceID token with any server-side account
     * maintained by your application.
     *
     * @param token The new token.
     */
    private void sendRegistrationToServer(String token) {
        // TODO: Implement this method to send token to your app server.
    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub

        try{
            if(mConnReceiver!=null)
                unregisterReceiver(mConnReceiver);
        }catch(Exception e)
        {

        }
        super.onDestroy();

    }

    private void runJob(String refreshedToken) {
        mJobManager.addJobInBackground(new UpdateTokenJob(refreshedToken));
    }

    public String getRefreshedToken() {
        //return this.refreshedToken;
        return FirebaseInstanceId.getInstance().getToken();
    }
}


