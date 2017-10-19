package com.vb.torahmate.main;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.path.android.jobqueue.JobManager;
import com.vb.torahmate.R;
import com.vb.torahmate.accounts.Login;
import com.vb.torahmate.events.DashboardEvent;
import com.vb.torahmate.events.Event;
import com.vb.torahmate.events.LearningScheduleErrorEvent;
import com.vb.torahmate.events.LearningScheduleEvent;
import com.vb.torahmate.jobs.DashboardJob;
import com.vb.torahmate.jobs.MileageLogJob;
import com.vb.torahmate.jobs.ReportLearningMinutesJob;
import com.vb.torahmate.miles.SelectTmFragment;
import com.vb.torahmate.settings.MileageLogFragment;
import com.vb.torahmate.utils.AppManager;
import com.vb.torahmate.utils.Constants;
import com.vb.torahmate.utils.Nav;
import com.vb.torahmate.utils.SPAccountsManager;
import com.vb.torahmate.utils.Util;

import com.vb.torahmate.miles.SelectTmFragment.Menu;

/**
 * Created by Najia on 7/23/2015.
 */
public class MainFragment extends Fragment implements View.OnClickListener {

    private View mRootView;
    TextView mTvWelcome, mTvMilesCount;
    FrameLayout mFlStartLerning, mFlEnterMinutes;
    LinearLayout mLlBalance, mLlPbar;
    JobManager mJobManager;
    RelativeLayout mRlMain;
    final int TIMER = 800;
    boolean mClickCheck = true;
    int mCounter = 0;
    String updatedTime = "";
    FragmentTransaction fragmentTransaction;
    FragmentTransaction getFragmentTransaction;
    Context context;
    PowerManager.WakeLock mWakeLock;
    ProgressBar pb;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mJobManager = AppManager.getInstance().getJobManager();
        Util.cancelToast();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mRootView == null) {
            Util.cancelToast();
            MainActivity.mToolbar.setVisibility(View.VISIBLE);
            MainActivity.checkExitScreens = true;
            context = AppManager.getAppContext().getApplicationContext();
            mRootView = inflater.inflate(R.layout.fragment_main_new, container, false);
            PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            mWakeLock = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "My Tag");
            mWakeLock.acquire();
            fragmentTransaction = getFragmentManager().beginTransaction();
            getFragmentTransaction = getFragmentManager().beginTransaction();
            mTvWelcome = (TextView) mRootView.findViewById(R.id.tv_welcome);
            mRlMain = (RelativeLayout) mRootView.findViewById(R.id.rl_main);
            mTvMilesCount = (TextView) mRootView.findViewById(R.id.tv_miles);
            mFlStartLerning = (FrameLayout) mRootView.findViewById(R.id.fl_start_lerning);
            mFlEnterMinutes = (FrameLayout) mRootView.findViewById(R.id.fl_enter_minutes);
            mLlBalance = (LinearLayout) mRootView.findViewById(R.id.ll_balance);
            mLlPbar = (LinearLayout) mRootView.findViewById(R.id.ll_pbar);
            mLlPbar.setVisibility(View.GONE);
            pb = (ProgressBar) mRootView.findViewById(R.id.pbar);
            pb.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.dark_blue), android.graphics.PorterDuff.Mode.MULTIPLY);

            if (mClickCheck = true) {
                mFlStartLerning.setOnClickListener(this);
                mFlEnterMinutes.setOnClickListener(this);
                mLlBalance.setOnClickListener(this);
            }
            String totalMiles = SPAccountsManager.getMileageAvailable(context);
            mCounter = Integer.valueOf(totalMiles);
            mTvWelcome.setText(Constants.WELCOME + SPAccountsManager.getFirstName(context) + " !");
            mTvMilesCount.setText(String.valueOf(mCounter));
            updatedTime = SPAccountsManager.getMilesUpdateTime(AppManager.getInstance().getApplicationContext());
            if (SPAccountsManager.getCellNumber(context).length() <= 0) {
                SPAccountsManager.setCellNumber(context, Util.getFromDialerNumber(AppManager.getAppContext()));
            }
            AppManager.getInstance().getEventBus().unregister(this);
            AppManager.getInstance().getEventBus().register(this);
        }

        return mRootView;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Util.cancelToast();
        MainActivity.checkExitScreens = false;
        if(mWakeLock!=null) {
            mWakeLock.release();
        }
    }

    private void launchSelectTorahmateFragment(final Menu menu) {
        try {
            MainFragment fragment = new MainFragment();
            fragmentTransaction.replace(R.id.container, fragment);
            fragmentTransaction.commit();

            SelectTmFragment selectTorahmateFragment = new SelectTmFragment();
            selectTorahmateFragment.setMenu(menu);
            getFragmentTransaction.replace(R.id.container, selectTorahmateFragment);
            getFragmentTransaction.addToBackStack(null);
            getFragmentTransaction.commit();
        } catch (Exception ex) {
            String err = (ex.getMessage() == null) ? "Error" : ex.getMessage();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        context = AppManager.getAppContext().getApplicationContext();
        MainActivity.checkExitScreens = true;
    }

    @Override
    public void onClick(View v) {
        if (v == mFlEnterMinutes) {
            mClickCheck = false;
            if (SPAccountsManager.getCheckLogin((AppManager.getAppContext()))) {
                MainActivity.checkSetScdedule = true;
                launchSelectTorahmateFragment(Menu.ENTER_MINUTES);
            }
        }
        if (v == mFlStartLerning) {
            MainActivity.checkExitScreens = true;
            launchSelectTorahmateFragment(Menu.START_LERNING);
        }
        if (v == mLlBalance) {
            mLlPbar.setVisibility(View.VISIBLE);
            mJobManager.addJobInBackground(new DashboardJob());
            mJobManager.addJobInBackground(new MileageLogJob());
        }
    }

    public void onEventMainThread(DashboardEvent event) {
        mLlPbar.setVisibility(View.GONE);
        launchMileageLogFragment();
    }

    public void onEventMainThread(LearningScheduleEvent event) {
        MainActivity.mCheckLogout = false;
        Util.showToast(getActivity(), getActivity().getResources().getString(R.string.minutes_reported_text));
    }

    public void onEventMainThread(LearningScheduleErrorEvent event) {
        MainActivity.mCheckLogout = false;
        Util.showToast(getActivity(), event.getMsg());
    }

    private void launchMileageLogFragment() {
        MileageLogFragment mileageLogFragment = new MileageLogFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.container, mileageLogFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}