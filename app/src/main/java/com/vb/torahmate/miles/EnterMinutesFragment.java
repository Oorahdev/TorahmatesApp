package com.vb.torahmate.miles;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.support.v4.app.Fragment;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.path.android.jobqueue.JobManager;
import com.vb.torahmate.R;
import com.vb.torahmate.events.ErrorEvent;
import com.vb.torahmate.jobs.ReportLearningMinutesJob;
import com.vb.torahmate.main.MainActivity;
import com.vb.torahmate.utils.AppManager;
import com.vb.torahmate.utils.InputFilterMinMax;
import com.vb.torahmate.utils.Nav;
import com.vb.torahmate.utils.Util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by psoloveichik on 2/27/2017.
 */

public class EnterMinutesFragment extends Fragment implements View.OnClickListener {

    private View mRootView;
    public static String mDateAndTime = "";
    String strMinutes;
    private JobManager mJobManager;
    String mRelatedId = "";
    String mRelatedTo = "";
    Handler handler = new Handler();
    boolean checkCurrent = false;
    boolean checkCurrentOne = false;
    boolean checkKeyBoard = false;
    ProgressBar pb;
    String mDate = "";
    PowerManager.WakeLock mWakeLock;
    Context context;
    Button btnSubmit;
    EditText mEtMinutes, mEtDate;
    Calendar myCalendar = Calendar.getInstance();
    Date date;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mJobManager = AppManager.getInstance().getJobManager();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mRootView == null) {
            MainActivity.checkExitScreens = false;
            mRootView = inflater.inflate(R.layout.fragment_enter_minutes, container, false);
            context = AppManager.getAppContext().getApplicationContext();
            PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            mWakeLock = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "My Tag");
            mWakeLock.acquire();
            pb = (ProgressBar) mRootView.findViewById(R.id.pbar);
            btnSubmit = (Button) mRootView.findViewById(R.id.btn_minutes_submit);
            btnSubmit.setOnClickListener(this);
            mEtDate = (EditText) mRootView.findViewById(R.id.et_date);
            mEtDate.setOnClickListener(this);
            mEtMinutes = (EditText) mRootView.findViewById(R.id.et_minutes);
            mEtMinutes.setFilters(new InputFilter[]{ new InputFilterMinMax("1", "99")});

            mRelatedId = getArguments().getString("getRelatedId");
            mRelatedTo = getArguments().getString("getRelatedTo");
            AppManager.getInstance().getEventBus().unregister(this);
            AppManager.getInstance().getEventBus().register(this);
        }
        return mRootView;
    }

    private void runJob() {
//        strMinutes = String.valueOf(np.getValue());
        strMinutes = String.valueOf(mEtMinutes.getText());
        pb.setVisibility(View.VISIBLE);
        btnSubmit.setOnClickListener(null);
        mJobManager.addJobInBackground(new ReportLearningMinutesJob(mRelatedId, mRelatedTo, strMinutes, mDate));
    }

    public void onEventMainThread(ErrorEvent event) {
        pb.setVisibility(View.GONE);
        if (getUserVisibleHint())
            Util.showToast(getActivity(), event.getMsg());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void hideKeyboard() {
        if (checkKeyBoard == true) {
            View view = getActivity().getCurrentFocus();
            if (view != null) {
                InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        checkCurrent = false;
    }

    @Override
    public void onStart() {
        super.onStart();
        checkCurrent = false;
        checkCurrentOne = false;
    }

    @Override
    public void onDestroyView() {
        if (mRootView.getParent() != null) {
            ((ViewGroup) mRootView.getParent()).removeView(mRootView);
            hideKeyboard();
            Util.cancelToast();

        }
        checkCurrentOne = true;
        super.onDestroyView();
        hideKeyboard();
//        Util.cancelToast();
    }

    @Override
    public void onPause() {
        super.onPause();
        checkCurrent = true;
        checkCurrentOne = true;
//        Util.cancelToast();
        pb.setVisibility(View.GONE);
        handler.removeCallbacksAndMessages(null);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (mWakeLock != null) {
            mWakeLock.release();
        }
        hideKeyboard();
        mDateAndTime = "";
        checkCurrentOne = true;
//        Util.cancelToast();
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.et_date) {
            new DatePickerDialog(getActivity(), R.style.datepicker, dateListener, myCalendar.get(Calendar.YEAR),
                    myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
        }
        else if (v.getId() == R.id.btn_minutes_submit) {
            MainActivity.mCheckLogout = true;
            hideKeyboard();
            if (Util.isConnectingToInternet(getActivity())) {
                //runJob();
//                strMinutes = String.valueOf(np.getValue());
                strMinutes = String.valueOf(mEtMinutes.getText());
                mDate = String.valueOf(mEtDate.getText());
                btnSubmit.setOnClickListener(null);
                mJobManager.addJobInBackground(new ReportLearningMinutesJob(mRelatedId, mRelatedTo, strMinutes, mDate));
                Nav.mainFragment(getFragmentManager());
//                Util.showToastSubmitting();
                Util.showToast(getActivity(), getResources().getString(R.string.submitting));

            } else {
                Util.showToast(getActivity(), getResources().getString(R.string.connection_msg));
            }
        }
    }

    private DatePickerDialog.OnDateSetListener dateListener = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            String myFormat = "M/d/yyyy"; //In which you need put here
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

            mEtDate.setText(sdf.format(myCalendar.getTime()));

            // set selected date into datepicker also
            //dpResult.init(year, month, day, null);

        }
    };

}
