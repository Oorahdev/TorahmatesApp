package com.vb.torahmate.main;

import android.util.Log;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

/**
 * Created by twender on 10/24/2017.
 */

public class MyJobService extends JobService {

    private static final String TAG = "MyJobService";

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        Log.d(TAG, "Performing long running task in scheduled job");
        // TODO(developer): add long running task here.
        return false; //answers the question: "is there still work going on?"
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return false;  //answers the question: "should this job be retried?"
    }


}
