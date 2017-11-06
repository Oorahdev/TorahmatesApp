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



import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.vb.torahmate.utils.Constants;
import com.vb.torahmate.utils.HttpRequest;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;


/**
 * Created by twender on 10/24/2017.
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {


    private static final String TAG = "MyFirebaseIIDService";
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
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);

        //send refreshed token to database
      /*  try {
            //connect to database
            connectionClass = new ConnectionClass();
            Connection con = connectionClass.connection();
            if (con == null) {
                Log.d(TAG, "error in connection with sql server");
            }
            //update token
            else {
                String query = "update tKVMLO\n" +
                        "set KVMLO_RegistrationToken = " + refreshedToken + "\n" +
                        "where KVMLO_LoginCode == " + Constants.USER_NAME + ";";
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                if (!rs.next()) {
                    Log.d(TAG, "error executing query");
                }
            }
        }
        catch(Exception e) {
            Log.d(TAG, e.getMessage());
        }*/
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
}


