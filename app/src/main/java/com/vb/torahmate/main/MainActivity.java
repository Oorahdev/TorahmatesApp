package com.vb.torahmate.main;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.activeandroid.query.Delete;
import com.appsee.Appsee;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.path.android.jobqueue.JobManager;
import com.vb.torahmate.R;
import com.vb.torahmate.accounts.Login;
import com.vb.torahmate.events.ErrorEvent;
import com.vb.torahmate.jobs.UpdateTokenJob;
import com.vb.torahmate.main.MyFirebaseInstanceIDService;
import com.vb.torahmate.models.GetTorahmatesModel;
import com.vb.torahmate.models.MilesReportLearningModel;
import com.vb.torahmate.utils.AppManager;
import com.vb.torahmate.utils.Constants;
import com.vb.torahmate.utils.L;
import com.vb.torahmate.utils.Nav;
import com.vb.torahmate.utils.SPAccountsManager;
import com.vb.torahmate.utils.Util;

import java.util.Date;

public class MainActivity extends AppCompatActivity implements
        View.OnClickListener,
        FragmentManager.OnBackStackChangedListener,
        NavigationDrawerFragment.FragmentDrawerListener {

    public static Toolbar mToolbar;
    public static TextView tvTitle;
    private static JobManager mJobManager;
    private static FragmentManager fragmentManager;
    Context context;
    NavigationDrawerFragment drawerFragment;
    public static Boolean mCheckLogout = false;
    boolean exitCheck = true;
    public static FrameLayout container;
    public static LinearLayout parentLayout;
    public static RelativeLayout rlToolBar;
    public static boolean checkSetScdedule = false;
    public static boolean checkExitScreens = false;
    public static TextView messageBar;
    IntentFilter s_intentFilter = new IntentFilter();


    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Util.googleTracking(Constants.ACTIVITY_CAT, this.getClass().getSimpleName());
        Appsee.start(Constants.APPSEE_KEY);
        s_intentFilter.addAction(Intent.ACTION_TIME_TICK);
        s_intentFilter.addAction(Intent.ACTION_TIMEZONE_CHANGED);
        s_intentFilter.addAction(Intent.ACTION_TIME_CHANGED);
//        registerReceiver(mTimeChangedReceiver, s_intentFilter);
        mCheckLogout = false;
        Util.cancelToast();
        mToolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        container = (FrameLayout) findViewById(R.id.container);
        parentLayout = (LinearLayout) findViewById(R.id.ll_parent);
        rlToolBar = (RelativeLayout) findViewById(R.id.rv_tool_bar);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        messageBar = (TextView) findViewById(R.id.message_bar);
        context = getApplicationContext();
        mJobManager = AppManager.getJobManager();
        drawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        drawerFragment.setDrawerListener(this);
        mToolbar.setOnClickListener(this);
        fragmentManager = getSupportFragmentManager();
        fragmentManager.addOnBackStackChangedListener(this);
        this.registerReceiver(Util.mConnReceiver,
                new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
//        weekDay = Util.getCurrentDayName();
        setLogoListener();
        AppManager.getEventBus().unregister(this);
        AppManager.getEventBus().register(this);




    }

    private void setLogoListener() {
        tvTitle.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
            Nav.mainFragment(fragmentManager);
            return true;
            }
        });
    }

    public void runJob() {
//        mJobManager.addJobInBackground(new LoginInfoJob());
//        mJobManager.addJobInBackground(new DashboardJob());
//        mJobManager.addJobInBackground(new MileageGetSessionsJob());
//        mJobManager.addJobInBackground(new CallTorahmateJob(this.getClass().getSimpleName()));
//        mJobManager.addJobInBackground(new ContactTorahmateJob());
    }

    private void checkLogin() {     // this work is perform in onCreate
        if (SPAccountsManager.getCheckLogin((AppManager.getAppContext()))) {
            checkExitScreens = true;
            runJob();
            Nav.mainFragment(fragmentManager);
        } else {
            finish();
            startActivity(new Intent(MainActivity.this, Login.class));
            /*MyFirebaseInstanceIDService Iid = new MyFirebaseInstanceIDService();
            String refreshedToken = Iid.getRefreshedToken();
            mJobManager.addJobInBackground(new UpdateTokenJob(refreshedToken));*/

        }
    }

    public void onEventMainThread(ErrorEvent event) {

    }

//    private final BroadcastReceiver mTimeChangedReceiver = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            final String action = intent.getAction();
//            if (action.equals(Intent.ACTION_TIME_CHANGED) ||
//                    action.equals(Intent.ACTION_TIMEZONE_CHANGED) || action.equals(Intent.ACTION_TIME_TICK)) {
////                setAlarm();
//            }
//        }
//    };

    @Override
    protected void onStart() {
        super.onStart();
        checkLogin();
    }

    @Override
    public void onClick(View v) {
        if (v == mToolbar) {
            hideKeyboard();
        }
    }

    @Override
    public void onDestroy() {
        try {
            if(Util.mConnReceiver!=null) {
                unregisterReceiver(Util.mConnReceiver);
            }
        }catch(Exception e){}
        super.onDestroy();
    }

    @Override
    public void onDrawerItemSelected(View view, int position) {
        Util.cancelToast();
        if (!SPAccountsManager.getCheckLogin(MainActivity.this)) {
            Util.showLoginToast(this, getResources().getString(R.string.login_required));
        } else {
            displayView(position);
            hideKeyboard();
        }
    }

    public void displayView(int position) {
        Fragment fragment = null;
        mCheckLogout = false;
        switch (position) {
            case 0:
                fragment = new ContactUsFragment();
                break;
            case 1:
                Util.sendEmail(this, context.getString(R.string.torah_email));
                break;
            case 2:
                exitCheck = false;
                checkExitScreens = true;
                mCheckLogout = true;
                clearCredentials();
                startActivity(new Intent(MainActivity.this, Login.class));
                finish();
                break;
            default:
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.container, fragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }

    @Override
    public void onBackStackChanged() {
        if (mCheckLogout) {
            getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            exitCheck = false;
            checkExitScreens = true;
        } else {
            L.m("back stack changes" + getFragmentManager().getBackStackEntryCount());
        }
    }

    public void clearCredentials() {
        clearData();
        SPAccountsManager.setCheckLogin(MainActivity.this, false);
        SPAccountsManager.setUserID(MainActivity.this, "");
        SPAccountsManager.setPassword(MainActivity.this, "");
        SPAccountsManager.setToken(MainActivity.this, "");
        SPAccountsManager.setAuthLogInToken(MainActivity.this, "");
    }

    private void clearData() {
        new Delete().from(GetTorahmatesModel.class).execute();
        new Delete().from(MilesReportLearningModel.class).execute();
    }

    public void onBackPressed() {
        Log.d("checkMilesUpdates", checkExitScreens + "");
        Util.checkAppName = "false";
        Util.cancelToast();
        if (checkExitScreens) {
            Util.cancelToast();
            finish();
        }
        if (mCheckLogout) {
            exitCheck = false;
            checkExitScreens = true;
        } else {
            Util.cancelToast();
            int count = getFragmentManager().getBackStackEntryCount();
            Log.d("checkMilesUpdates", count + "");

            if (count != 0 && checkExitScreens) {
                finish();
            } else if (!checkExitScreens) {
                super.onBackPressed();
            }
        }
    }

    private void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) this.getSystemService(INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

}