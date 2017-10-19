package com.vb.torahmate.miles;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.activeandroid.query.Select;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.path.android.jobqueue.JobManager;
import com.vb.torahmate.R;
import com.vb.torahmate.adapters.AdapterSelectTm;
import com.vb.torahmate.events.CallTorahmateEvent;
import com.vb.torahmate.events.ErrorEvent;
import com.vb.torahmate.events.TimeOutError;
import com.vb.torahmate.jobs.CallTorahmateJob;
import com.vb.torahmate.main.MainActivity;
import com.vb.torahmate.models.TmInfoModel;
import com.vb.torahmate.models.MilesReportLearningModel;
import com.vb.torahmate.utils.AppManager;
import com.vb.torahmate.utils.Constants;
import com.vb.torahmate.utils.Util;
import com.vb.torahmate.widget.GifMovieView;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Najia on 7/28/2015.
 */
public class SelectTmFragment extends Fragment {

    private View mRootView;
    private RecyclerView mRecyclerView;
    private AdapterSelectTm mAdapter;
    private JobManager mJobManager;
    List<TmInfoModel> mCallTorahmatesList;
    String getRelatedId = "";
    String getRelatedTo = "";
    String getName = "";
    ProgressBar pb;
    Menu menu = Menu.START_LERNING;

    public enum Menu {START_LERNING, ENTER_MINUTES}

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mJobManager = AppManager.getInstance().getJobManager();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mRootView == null) {
            MainActivity.checkExitScreens = false;
            mRootView = inflater.inflate(R.layout.fragment_select_tm, container, false);
            mRecyclerView = (RecyclerView) mRootView.findViewById(R.id.drawer_list);
            pb = (ProgressBar) mRootView.findViewById(R.id.pbar);
            mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
            ArrayList<MilesReportLearningModel> miles = new Select().from(MilesReportLearningModel.class).execute();
            if (miles.size() > 0) {
                mCallTorahmatesList = parseJson(miles.get(0).getResponseString());
                setAdapter(mCallTorahmatesList);
            } else {
                if (Util.isConnectingToInternet(getActivity())) {
                    pb.setVisibility(View.VISIBLE);
                    mJobManager.addJobInBackground(new CallTorahmateJob(this.getClass().getSimpleName()));
                } else {
                    Util.showToast(getActivity(), getResources().getString(R.string.connection_msg));
                }
            }
            mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), mRecyclerView, new ClickListener() {
                @Override
                    public void onClick(View view, int position) {
                        TmInfoModel model = mCallTorahmatesList.get(position);
                        getRelatedTo = model.getRelatedTo();
                        getRelatedId = model.getRelatedId();
                        getName = model.getName();
                        FragmentManager fragmentManager = getFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    Bundle bundle = new Bundle();
                    bundle.putString("getRelatedTo", getRelatedTo);
                    bundle.putString("getRelatedId", getRelatedId);
                    bundle.putString("getName", getName);
                    bundle.putInt("getPosition", position);
                    if(menu == Menu.ENTER_MINUTES)
                    {
                        //SelectDateFragment reportMinutes = new SelectDateFragment();
                        EnterMinutesFragment reportMinutes = new EnterMinutesFragment();
                        reportMinutes.setArguments(bundle);
                        fragmentTransaction.replace(R.id.container, reportMinutes);
                    }
                    else
                    {
                        ContactTmFragment contactTm = new ContactTmFragment();
                        contactTm.setArguments(bundle);
                        fragmentTransaction.replace(R.id.container, contactTm);
                    }
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
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

    private void setAdapter(List<TmInfoModel> mCallTorahmatesList) {
        mAdapter = new AdapterSelectTm(getActivity(), mCallTorahmatesList);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        AppManager.getInstance().getEventBus().unregister(this);
    }

    public void onEventMainThread(ErrorEvent event) {
        pb.setVisibility(View.GONE);
        if (getUserVisibleHint())
            Util.showToast(getActivity(), event.getMsg());
    }

    public void onEventMainThread(TimeOutError event) {
        pb.setVisibility(View.GONE);
        if (event.getContext().equalsIgnoreCase(this.getClass().getSimpleName())) {
            Util.showToast(getActivity(), event.getMsg());
        }

    }

    public void onEventMainThread(CallTorahmateEvent event) {
        pb.setVisibility(View.GONE);
        setAdapter(event.getList());
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {
        private final GestureDetector gestureDetector;
        private final ClickListener clickListener;

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


    @Override
    public void onDestroyView() {
        if (mRootView.getParent() != null) {
            ((ViewGroup) mRootView.getParent()).removeView(mRootView);
        }
        super.onDestroyView();
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
}