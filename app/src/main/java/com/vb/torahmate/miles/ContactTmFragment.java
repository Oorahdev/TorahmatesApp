package com.vb.torahmate.miles;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.provider.CallLog;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.activeandroid.query.Select;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.path.android.jobqueue.JobManager;
import com.vb.torahmate.R;
import com.vb.torahmate.adapters.AdapterTmTel;
import com.vb.torahmate.events.ErrorEvent;
import com.vb.torahmate.events.LearningScheduleEvent;
import com.vb.torahmate.main.MainActivity;
import com.vb.torahmate.main.MainFragment;
import com.vb.torahmate.models.CallMyTorahmateInfoModel;
import com.vb.torahmate.models.MilesReportLearningModel;
import com.vb.torahmate.models.TmInfoModel;
import com.vb.torahmate.utils.AppManager;
import com.vb.torahmate.utils.Constants;
import com.vb.torahmate.utils.SPAccountsManager;
import com.vb.torahmate.utils.Util;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Najia on 7/28/2015.
 */
public class ContactTmFragment extends Fragment {

    private View mRootView;
    private TextView mTvNoContactInfo;
    private RecyclerView mRecyclerView;
    private TextView tvTmName;
    public static String mDateAndTime = "";
    private JobManager mJobManager;
    String mRelatedId = "";
    String mRelatedTo = "";
    String mName = "";
    int mPosition = 0;
    Handler handler = new Handler();
    boolean checkCurrent = false;
    boolean checkCurrentOne = false;
    boolean checkKeyBoard = false;
    ProgressBar pb;
    PowerManager.WakeLock mWakeLock;
    Context context;

    private AdapterTmTel mAdapter;
    List<CallMyTorahmateInfoModel> mTelList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mJobManager = AppManager.getInstance().getJobManager();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mRootView == null) {
            MainActivity.checkExitScreens = false;
            mRootView = inflater.inflate(R.layout.fragment_contact_tm, container, false);
            context = AppManager.getAppContext().getApplicationContext();
            tvTmName = (TextView) mRootView.findViewById(R.id.tv_tm_name);
            mTvNoContactInfo = (TextView) mRootView.findViewById(R.id.tv_no_contact_info);
            mRecyclerView = (RecyclerView) mRootView.findViewById(R.id.rv_tm_info);
            mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
            pb = (ProgressBar) mRootView.findViewById(R.id.pbar);
            mRelatedId = getArguments().getString("getRelatedId");
            mRelatedTo = getArguments().getString("getRelatedTo");
            mName = getArguments().getString("getName");
            mPosition = getArguments().getInt("getPosition");
            tvTmName.setText(mName);

            ArrayList<MilesReportLearningModel> miles = new Select().from(MilesReportLearningModel.class).execute();
            if (miles.size() > 0) {
                List<TmInfoModel> mCallTorahmatesList = parseJson(miles.get(0).getResponseString());
                mTelList = mCallTorahmatesList.get(mPosition).getTelsList();
                if(mTelList.size() > 0) {
                    setAdapter(mTelList);
                } else {
                    mRecyclerView.setVisibility(View.GONE);
                    mTvNoContactInfo.setVisibility(View.VISIBLE);
                }
            } else {
                if (Util.isConnectingToInternet(getActivity())) {
                    //TODO
                } else {
                    Util.showToast(getActivity(), getResources().getString(R.string.connection_msg));
                }
            }

            mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), mRecyclerView, new ClickListener() {
                @Override
                public void onClick(View view, int position) {
                    CallMyTorahmateInfoModel model = mTelList.get(position);
                    SPAccountsManager.setRelatedToVal(AppManager.getAppContext(), mRelatedTo);
                    SPAccountsManager.setRelatedIdVal(AppManager.getAppContext(), mRelatedId);
                    String mTelDisplay = model.getTelDisplay();
                    String mTelType = model.getTelType();
                    if (mTelType.equals(getActivity().getResources().getString(R.string.tel_type_email))) {
                        Util.sendEmail(getActivity(), mTelDisplay);
                    } else {
                        if (!(Util.getFromDialerNumber(getActivity()) == null)) {
                            Util.getFromDialerNumber(getActivity());
                            Util.call(getActivity(), mTelDisplay, mName);
                            Util.checkAppName = "true";
                            Util.checkCallEnd = false;
                        } else {
                            Util.showToast(getActivity(), getActivity().getResources().getString(R.string.missing_sim_card));
                        }
                    }
                }

                @Override
                public void onLongClick(View view, int position) {
                }
            }));

            AppManager.getInstance().getEventBus().unregister(this);
            AppManager.getInstance().getEventBus().register(this);
        }
        return mRootView;
    }

    private void setAdapter(List<CallMyTorahmateInfoModel> mTelList) {
        mAdapter = new AdapterTmTel(getActivity(), mTelList);
        mRecyclerView.setAdapter(mAdapter);
    }

    public void onEventMainThread(ErrorEvent event) {
        pb.setVisibility(View.GONE);
        if (getUserVisibleHint())
            Util.showToast(getActivity(), event.getMsg());
    }
    
    public void onEventMainThread(LearningScheduleEvent event) {
        if (checkCurrent == false) {
            pb.setVisibility(View.GONE);
            MainActivity.mCheckLogout = false;
            Util.showToast(getActivity(), getActivity().getResources().getString(R.string.minutes_reported_text));
            if (checkCurrentOne == false) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Util.cancelToast();
                        goBack();
                    }
                }, 2000);
            }
        }
    }
    
     private void goBack() {
        hideKeyboard();
        Util.cancelToast();
        getFragmentManager().popBackStack();
        getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
        launchMainFragment();
    }
    
    private void launchMainFragment() {
        MainFragment fragment = new MainFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.commit();
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

//    private void getCallDetails() {
//        Cursor managedCursor = getActivity().getContentResolver().query(CallLog.Calls.CONTENT_URI,
//                null,
//                null,
//                null,
//                CallLog.Calls.DATE + " DESC " + " LIMIT 1");
//        assert managedCursor != null;
//        int date = managedCursor.getColumnIndex(CallLog.Calls.DATE);
//        int duration = managedCursor.getColumnIndex(CallLog.Calls.DURATION);
//        while (managedCursor.moveToNext()) {
//            callDate = managedCursor.getString(date);
//            callDuration = managedCursor.getString(duration);
//        }
//        mJobManager.addJobInBackground(new SendCallLogsJob(mRelatedTo, mRelatedId,
//                Util.callLength(Integer.parseInt(callDuration)), callDate, Util.contactNumber));
//        managedCursor.close();
//    }

    @Override
    public void onResume() {
        super.onResume();
//        Util.cancelToast();
//        mRelatedTo = SPAccountsManager.getRelatedToVal(getActivity());
//        mRelatedId = SPAccountsManager.getRelatedIdVal(getActivity());
//        if (Util.checkCallEnd) {
//            getCallDetails();
//            Util.checkCallEnd = false;
//        }
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
        Util.cancelToast();
    }

    @Override
    public void onPause() {
        super.onPause();
        checkCurrent = true;
        checkCurrentOne = true;
        Util.cancelToast();
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
        Util.cancelToast();
    }

    private List<TmInfoModel> parseJson(String str) {
        List<TmInfoModel> list;
        JsonObject jsonObject = new JsonParser().parse(str).getAsJsonObject();
        JsonArray jsonArray = jsonObject.getAsJsonArray(Constants.RESULT_DATA);
        Type type = new TypeToken<ArrayList<TmInfoModel>>() {
        }.getType();
        list = AppManager.getInstance().getGSON().fromJson(jsonArray, type);
        return list;
    }

    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {
        private final GestureDetector gestureDetector;
        private final ClickListener clickListener;
        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept)  {
        }
        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }
    }
}