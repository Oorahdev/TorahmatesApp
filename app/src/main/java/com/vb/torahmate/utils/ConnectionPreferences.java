package com.vb.torahmate.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Najia on 11/13/2015.
 */
public class ConnectionPreferences {
    private static final String PREF_NAME = "prefName";
    private static final String IS_CONNECTED = "is_connected";

    public static boolean getCheckConnected(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return preferences.getBoolean(IS_CONNECTED, false);
    }

    public static void setCheckConnected(Context context, boolean checkLogin) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(IS_CONNECTED, checkLogin);
        editor.commit();
    }
}
