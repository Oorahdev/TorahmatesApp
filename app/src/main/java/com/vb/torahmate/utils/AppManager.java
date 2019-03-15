package com.vb.torahmate.utils;


import android.app.Application;
import android.content.Context;
//import android.support.multidex.MultiDex;
//import android.support.multidex.MultiDexApplication;
import android.util.Log;

import com.activeandroid.ActiveAndroid;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.google.gson.Gson;
import com.path.android.jobqueue.JobManager;
import com.path.android.jobqueue.config.Configuration;
import com.path.android.jobqueue.log.CustomLogger;
import com.vb.torahmate.BuildConfig;
import com.vb.torahmate.R;

import de.greenrobot.event.EventBus;
public class AppManager extends Application {

    public static AppManager sInstance;
    public static JobManager jobManager;
    public static EventBus eventBus;
    public static Gson gson;
    private static Tracker mTracker;

//    @Override
//    protected void attachBaseContext(Context base) {
//        super.attachBaseContext(base);
//        MultiDex.install(this);
//    }

    public static AppManager getInstance() {
        return sInstance;
    }

    public static Context getAppContext() {
        return sInstance.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // Start Crashlytics, disable when in debug mode

        sInstance = this;
        configureJobManager();
        configEventBus();
        configGSON();
        ActiveAndroid.initialize(this);
    }

    private void configureJobManager() {
        Configuration configuration = new Configuration.Builder(this).customLogger(new CustomLogger() {
            private static final String TAG = "JOBS";

            @Override
            public boolean isDebugEnabled() {
                return true;
            }

            @Override
            public void d(String text, Object... args) {
                Log.d(TAG, String.format(text, args));
            }

            @Override
            public void e(Throwable t, String text, Object... args) {
                Log.e(TAG, String.format(text, args), t);
            }

            @Override
            public void e(String text, Object... args) {
                Log.e(TAG, String.format(text, args));
            }
        }).minConsumerCount(1)
                .maxConsumerCount(3)
                .loadFactor(3)
                .consumerKeepAlive(120)
                .build();
        jobManager = new JobManager(this, configuration);
    }

    public static JobManager getJobManager() {
        return jobManager;
    }

    public void configEventBus() {
        eventBus = new EventBus();
    }

    public static EventBus getEventBus() {
        return eventBus;
    }

    private void configGSON() {
        gson = new Gson();
    }

    public static Gson getGSON() {
        return gson;
    }

    synchronized public static Tracker getDefaultTracker() {
        if (mTracker == null) {
            GoogleAnalytics analytics = GoogleAnalytics.getInstance(getInstance());
            //mTracker = analytics.newTracker(R.xml.global_tracker);
            mTracker = analytics.newTracker("UA-xxxxxxx-1");
        }
        return mTracker;
    }
}