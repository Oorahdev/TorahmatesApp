package com.vb.torahmate.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.vb.torahmate.events.NetworkStateChanged;

import de.greenrobot.event.EventBus;


/**
 * Created by Najia on 7/22/2015.
 */
public class NetworkStateReceiver extends BroadcastReceiver {

    private static boolean firstConnect = true;

    public void onReceive(Context context, Intent intent) {
        if (isConnected(context)) {
            L.m("connected");
            EventBus.getDefault().post(new NetworkStateChanged(true));
        } else {
            if (firstConnect) {
                L.m("disconnected");
                EventBus.getDefault().post(new NetworkStateChanged(false));
                firstConnect = false;
            } else {
                firstConnect = true;
            }
        }
    }

    public boolean isConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnected();
        return isConnected;
    }
}
