package com.vb.torahmate.calls;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;

import com.vb.torahmate.utils.Util;

/**
 * Created by Najia on 9/28/2015.
 */
public class MyOutgoingCallHandler extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, final Intent intent) {
        if (Util.checkAppName.equalsIgnoreCase("true") && intent.getAction().equals(Intent.ACTION_NEW_OUTGOING_CALL)) {
            String number = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
            Util.contactNumber=number;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent i = new Intent(context, CustomCallingScreen.class);
                    i.putExtras(intent);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    context.startActivity(i);
                }
            }, 100);
        }
    }
}