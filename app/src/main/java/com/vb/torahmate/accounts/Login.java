package com.vb.torahmate.accounts;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.daimajia.easing.Glider;
import com.daimajia.easing.Skill;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;
import com.path.android.jobqueue.JobManager;
import com.vb.torahmate.R;
import com.vb.torahmate.events.Event;
import com.vb.torahmate.events.LoginErrorEvent;
import com.vb.torahmate.events.MileageGetSessionEvent;
import com.vb.torahmate.events.SuccessEvent;
import com.vb.torahmate.jobs.CallTorahmateJob;
import com.vb.torahmate.jobs.ContactTorahmateJob;
import com.vb.torahmate.jobs.DashboardJob;
import com.vb.torahmate.jobs.LoginInfoJob;
import com.vb.torahmate.jobs.LoginJob;
import com.vb.torahmate.jobs.MileageGetSessionsJob;
import com.vb.torahmate.jobs.UpdateTokenJob;
import com.vb.torahmate.main.MainActivity;
import com.vb.torahmate.main.MyFirebaseInstanceIDService;
import com.vb.torahmate.utils.AppManager;
import com.vb.torahmate.utils.ConnectionPreferences;
import com.vb.torahmate.utils.Constants;
import com.vb.torahmate.utils.SPAccountsManager;
import com.vb.torahmate.utils.Util;
import com.vb.torahmate.widget.GifMovieView;


public class Login extends FragmentActivity implements OnClickListener {

    JobManager mJobManager;
    EditText mEtEmail, mEtPass;
    String mLoginEmail, mLoginPassword;
    String mCheckedPassword = "";
    String mImeiNumber = "";
    Button mBtnSignIn;
    CheckBox mCheckBox;
    public static TextView messageBarLogin;
    Boolean mCheck = true;
    private AwesomeValidation mAwesomeValidation;
    RelativeLayout mLogoLayout, mWelcomeLayout, mEmailLayout, mPassLayout;
    LinearLayout mRlForm, mCheckLayout, mContainerLayout, mProgressBarLayout;
    private ImageView card, mIvLogo;
    //GifMovieView gifMovieView;
    ProgressBar pb;
    private int runningTasks = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_login);
        Util.googleTracking(Constants.ACTIVITY_CAT, this.getClass().getSimpleName());
   //     Appsee.start(Constants.APPSEE_KEY);
        mJobManager = AppManager.getInstance().getJobManager();
        mAwesomeValidation = new AwesomeValidation(ValidationStyle.UNDERLABEL);
        mAwesomeValidation.setContext(AppManager.getAppContext().getApplicationContext());
        initialization();
        this.registerReceiver(this.mConnReceiver,
                new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        mImeiNumber = Util.getDeviceIMEINumber(AppManager.getAppContext().getApplicationContext());
        String getLoginImeiNumber = SPAccountsManager.getLoginImeiNumber(AppManager.getInstance().getApplicationContext());
        String getLoginEmail = SPAccountsManager.getLoginEmail(AppManager.getInstance().getApplicationContext());
        String getcheckedPassword = SPAccountsManager.getCheckedPassword(AppManager.getInstance().getApplicationContext());
        if (getcheckedPassword.equalsIgnoreCase("false") && mImeiNumber.equalsIgnoreCase(getLoginImeiNumber)) {
            mEtEmail.setText(getLoginEmail);
            String emailText = mEtEmail.getText().toString();
            if (getcheckedPassword.equalsIgnoreCase("false") && emailText.equalsIgnoreCase(getLoginEmail) && mImeiNumber.equalsIgnoreCase(getLoginImeiNumber)) {
                String getPass = SPAccountsManager.getLoginPassword(AppManager.getInstance().getApplicationContext());
                mEtPass.setText(getPass);
                mCheckBox.setChecked(true);
                mCheck = false;
            } else {
                mEtPass.setText("");
            }
        }
        mEtEmail.setSelection(mEtEmail.getText().length());
        mImeiNumber = Util.getDeviceIMEINumber(AppManager.getAppContext().getApplicationContext());
        runAnimation();
        mBtnSignIn.setOnClickListener(this);
        mCheckLayout.setOnClickListener(this);
        mCheckBox.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                setChecks();
            }
        });
        AppManager.getInstance().getEventBus().unregister(this);
        AppManager.getInstance().getEventBus().register(this);
    }

    private void runAnimation() {
        AnimatorSet set = new AnimatorSet();
        mLogoLayout.setVisibility(View.VISIBLE);
        mWelcomeLayout.setVisibility(View.VISIBLE);
        set.playTogether(
                Glider.glide(Skill.Linear, 2000, ObjectAnimator.ofFloat(mLogoLayout, "translationY", -340, -20)
                ), Glider.glide(Skill.Linear, 2000, ObjectAnimator.ofFloat(mWelcomeLayout, "translationY", 340, -5)

                ));
        set.setDuration(1500);
        set.start();
        MainActivity.checkExitScreens = true;

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                emailLayoutAnimation();
            }
        }, 2000);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                passwordLayoutanimation();
            }
        }, 2300);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                checkLayoutanimation();
            }
        }, 2700);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                buttonAnimation();
            }
        }, 3000);
    }

    private void emailLayoutAnimation() {
        mEmailLayout.setVisibility(View.VISIBLE);
        YoYo.with(Techniques.FadeIn)
                .duration(1000)
                .playOn(mEmailLayout);
    }

    private void passwordLayoutanimation() {
        mPassLayout.setVisibility(View.VISIBLE);
        YoYo.with(Techniques.FadeIn)
                .duration(1000)
                .playOn(mPassLayout);
    }

    private void checkLayoutanimation() {
        mCheckLayout.setVisibility(View.VISIBLE);
        YoYo.with(Techniques.FadeIn)
                .duration(1000)
                .playOn(mCheckLayout);
    }

    private void buttonAnimation() {
        mBtnSignIn.setVisibility(View.VISIBLE);
        YoYo.with(Techniques.FadeIn)
                .duration(1000)
                .playOn(mBtnSignIn);
    }

    private void setChecks() {
        if (mCheckBox.isChecked()) {
            mCheckBox.setChecked(true);
            mCheck = false;
            mCheckedPassword = "false";
            SPAccountsManager.setCheckedPassword(AppManager.getAppContext().getApplicationContext(), mCheckedPassword);
        } else {
            mCheckBox.setChecked(false);
            mCheck = true;
            mCheckedPassword = "true";
            SPAccountsManager.setCheckedPassword(AppManager.getAppContext().getApplicationContext(), mCheckedPassword);
        }
    }

    @Override
    public void onDestroy() {
        try {
            if(Util.mConnReceiver != null) {
                unregisterReceiver(Util.mConnReceiver);
            }
        }catch(Exception e){}
        super.onDestroy();
        Util.cancelToast();
        MainActivity.checkExitScreens = true;
        AppManager.getInstance().getEventBus().unregister(this);
        finish();
    }

    @Override
    public void onResume() {
        super.onResume();
        MainActivity.checkExitScreens = true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_sign_in:
                if (Util.isConnectingToInternet(AppManager.getAppContext().getApplicationContext())) {
                    mEtEmail.setCursorVisible(false);
                    mLoginEmail = mEtEmail.getText().toString();
                    mLoginPassword = mEtPass.getText().toString();
                    if (!mLoginEmail.equals("") && !mLoginPassword.equals("")) {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mJobManager.addJobInBackground(new LoginJob(mLoginEmail, mLoginPassword));
                                mProgressBarLayout.setVisibility(View.VISIBLE);
                                mLogoLayout.setVisibility(View.GONE);
                                mWelcomeLayout.setVisibility(View.GONE);
                                mEmailLayout.setVisibility(View.GONE);
                                mPassLayout.setVisibility(View.GONE);
                                mCheckLayout.setVisibility(View.GONE);
                            }
                        }, 300);
                    } else {
                        Util.showLoginToast(AppManager.getAppContext().getApplicationContext(), AppManager.getAppContext().getApplicationContext().getResources().getString(R.string.missing_fields));
                    }
                } else {
                    messageBarLogin.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.checkbox_layout:
                setChecks();
                break;
        }
    }

    public void onEventMainThread(SuccessEvent successEvent) {
        Util.cancelToast();
        SPAccountsManager.setLoginPassword(AppManager.getAppContext().getApplicationContext(), mLoginPassword);
        SPAccountsManager.setLoginEmail(AppManager.getAppContext().getApplicationContext(), mLoginEmail);
        if (!mCheck) {
            mCheckedPassword = "false";
            String loginImeiNumber = Util.getDeviceIMEINumber(AppManager.getAppContext().getApplicationContext());
            SPAccountsManager.setCheckedPassword(AppManager.getAppContext().getApplicationContext(), mCheckedPassword);
            SPAccountsManager.setLoginImeiNumber(AppManager.getAppContext().getApplicationContext(), loginImeiNumber);
        } else {
            mCheckedPassword = "true";
            SPAccountsManager.setCheckedPassword(AppManager.getAppContext().getApplicationContext(), mCheckedPassword);
            SPAccountsManager.setLoginImeiNumber(AppManager.getAppContext().getApplicationContext(), "");
        }
        Util.cancelToast();
        hideKeyboard();

        runningTasks = 6;
        mJobManager.addJobInBackground(new LoginInfoJob());
        mJobManager.addJobInBackground(new DashboardJob());
        mJobManager.addJobInBackground(new MileageGetSessionsJob());
        mJobManager.addJobInBackground(new CallTorahmateJob(this.getClass().getSimpleName()));
        mJobManager.addJobInBackground(new ContactTorahmateJob());
        MyFirebaseInstanceIDService Iid = new MyFirebaseInstanceIDService();
        String refreshedToken = Iid.getRefreshedToken();
        if (refreshedToken != null) {
            mJobManager.addJobInBackground(new UpdateTokenJob(refreshedToken));
        }
    }

    public void onEventMainThread(Event event) {
        runningTasks--;
        if(runningTasks == 0) {
            startActivity(new Intent(Login.this, MainActivity.class));
            Util.checkMilesUpdates = false;
            finish();
        }
    }

    public void onEventMainThread(LoginErrorEvent errorEvent) {
        mProgressBarLayout.setVisibility(View.GONE);
        Util.cancelToast();
        Util.showLoginToast(AppManager.getAppContext().getApplicationContext(), errorEvent.getMsg());
        mLogoLayout.setVisibility(View.VISIBLE);
        mWelcomeLayout.setVisibility(View.VISIBLE);
        mEmailLayout.setVisibility(View.VISIBLE);
        mPassLayout.setVisibility(View.VISIBLE);
        mCheckLayout.setVisibility(View.VISIBLE);
        mEtEmail.setCursorVisible(true);
    }

    private void initialization() {
//        gifMovieView = (GifMovieView) findViewById(R.id.pbar_gif_img);
        pb = (ProgressBar) findViewById(R.id.pbar);
        mIvLogo = (ImageView) findViewById(R.id.iv_logo);
        mEtEmail = (EditText) findViewById(R.id.et_email);
        mEtPass = (EditText) findViewById(R.id.et_password);
        mCheckBox = (CheckBox) findViewById(R.id.check_box);
        messageBarLogin = (TextView) findViewById(R.id.message_bar_login);
        mBtnSignIn = (Button) findViewById(R.id.btn_sign_in);
        mProgressBarLayout = (LinearLayout) findViewById(R.id.progress_bar_layout);
        mLogoLayout = (RelativeLayout) findViewById(R.id.tv_login_Layout);
        mRlForm = (LinearLayout) findViewById(R.id.ll_form);
        mWelcomeLayout = (RelativeLayout) findViewById(R.id.rl_tv_welcome);
        mCheckLayout = (LinearLayout) findViewById(R.id.checkbox_layout);
        mContainerLayout = (LinearLayout) findViewById(R.id.ll_main);
        mEmailLayout = (RelativeLayout) findViewById(R.id.email_Layout);
        mPassLayout = (RelativeLayout) findViewById(R.id.password_Layout);
        mAwesomeValidation.addValidation(mEtEmail, Util.regexEmail, AppManager.getAppContext().getApplicationContext().getResources().getString(R.string
                .missing_email));
        mAwesomeValidation.addValidation(mEtPass, Util.regexPassword, AppManager.getAppContext().getApplicationContext().getResources().getString(R.string.missing_password));
        pb = (ProgressBar) findViewById(R.id.pbar);
        pb.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.white), android.graphics.PorterDuff.Mode.MULTIPLY);
    }

    private void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) AppManager.getAppContext().getApplicationContext().getSystemService(AppManager.getAppContext().getApplicationContext().INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public static BroadcastReceiver mConnReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            NetworkInfo currentNetworkInfo = (NetworkInfo) intent.getParcelableExtra(ConnectivityManager.EXTRA_NETWORK_INFO);
            if (currentNetworkInfo.isConnected()) {
                messageBarLogin.setVisibility(View.GONE);
                if (ConnectionPreferences.getCheckConnected((AppManager.getAppContext())) == true) {

                    ConnectionPreferences.setCheckConnected(AppManager.getAppContext(), false);
                }
            } else {
                if (ConnectionPreferences.getCheckConnected((AppManager.getAppContext())) == false) {
                    messageBarLogin.setVisibility(View.VISIBLE);
                }
            }
        }
    };

    public void onBackPressed() {
        finish();
    }

}