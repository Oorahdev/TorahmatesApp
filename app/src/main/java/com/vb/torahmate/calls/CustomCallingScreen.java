package com.vb.torahmate.calls;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.internal.telephony.ITelephony;
import com.vb.torahmate.R;
import com.vb.torahmate.utils.AppManager;
import com.vb.torahmate.utils.Constants;
import com.vb.torahmate.utils.Util;

import java.lang.reflect.Method;


/**
 * Created by Najia on 9/28/2015.
 */
public class CustomCallingScreen extends Activity {
    ImageView mVolumeControl, mEndCall, mMute, mProfilePic;
    AudioManager mAudioManager;
    public static Chronometer myChronometer;
    boolean m_muted = false;
    boolean speakerCheck = false;
    TextView tvName, tvNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_calling_screen);
        Util.googleTracking(Constants.ACTIVITY_CAT, this.getClass().getSimpleName());
       // Appsee.start(Constants.APPSEE_KEY);
        mVolumeControl = (ImageView) findViewById(R.id.iv_vol_circle);
        mEndCall = (ImageView) findViewById(R.id.iv_call_action);
        mMute = (ImageView) findViewById(R.id.iv_vol);
        myChronometer = (Chronometer) findViewById(R.id.timer);
        tvName = (TextView) findViewById(R.id.tv_torahmate_name);
        tvNumber = (TextView) findViewById(R.id.tv_torahmate_number);
        mProfilePic = (ImageView) findViewById(R.id.iv_profile_pic);
        if (!Util.getContactPhoto(Util.contactNumber).equalsIgnoreCase("123")) {
            Uri uriImg = Uri.parse(Util.getContactPhoto(Util.contactNumber));
            mProfilePic.setImageURI(uriImg);
        } else {
            mProfilePic.setImageResource(R.drawable.img_default);
        }
        tvName.setText(Util.contactName);
        tvNumber.setText(Util.contactNumber);
        CustomCallingScreen.myChronometer.setVisibility(View.VISIBLE);
        CustomCallingScreen.myChronometer.start();
        mAudioManager = (AudioManager) getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
        mEndCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ignoreCall();
                Util.checkAppName = "true";
                Util.checkCallEnd = true;
                Util.showLoginToast(AppManager.getAppContext().getApplicationContext(), "call ended");
            }
        });

        mVolumeControl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (speakerCheck == false) {
                    mAudioManager.setSpeakerphoneOn(true);
                    mVolumeControl.setImageResource(R.drawable.img_volume_comtrol_red);
                    speakerCheck = true;
                } else if (speakerCheck == true) {
                    mAudioManager.setSpeakerphoneOn(false);
                    mVolumeControl.setImageResource(R.drawable.img_volume_comtrol);
                    speakerCheck = false;
                }
            }
        });

        mMute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (m_muted == false) {
                    mAudioManager.setMicrophoneMute(true);
                    mMute.setImageResource(R.drawable.img_mute_icon_red);
                    m_muted = true;

                } else if (m_muted == true) {
                    mAudioManager.setMicrophoneMute(false);
                    mMute.setImageResource(R.drawable.img_mute_icon);
                    m_muted = false;
                }
            }
        });
    }

    private void ignoreCall() {
        ignoreCallAidl();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }

        }, 2000);
    }

    private void exitCleanly() {
        moveTaskToBack(true);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }

        }, 3000);
    }

    private void ignoreCallAidl() {
        try {
            TelephonyManager telephonyManager = (TelephonyManager) getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
            Class c = Class.forName(telephonyManager.getClass().getName());
            Method m = c.getDeclaredMethod("getITelephony");
            m.setAccessible(true);
            ITelephony telephonyService = (ITelephony) m.invoke(telephonyManager);
            telephonyService.endCall();
            myChronometer.stop();
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Util.cancelToast();
                finish();
            }

        }, 3000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Util.checkAppName = "false";
        Util.cancelToast();
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Util.cancelToast();
    }
}