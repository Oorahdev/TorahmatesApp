package com.vb.torahmate.settings;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.activeandroid.query.Select;
import com.path.android.jobqueue.JobManager;
import com.vb.torahmate.R;
import com.vb.torahmate.adapters.AdapterMileageLog;
import com.vb.torahmate.events.ErrorEvent;
import com.vb.torahmate.events.MileageLogEvent;
import com.vb.torahmate.jobs.MileageLogJob;
import com.vb.torahmate.main.InfoFragment;
import com.vb.torahmate.main.MainActivity;
import com.vb.torahmate.models.MileageLogModel;
import com.vb.torahmate.utils.AppManager;
import com.vb.torahmate.utils.SPAccountsManager;
import com.vb.torahmate.utils.Util;

import java.util.List;

/**
 * Created by Najia on 7/27/2015.
 */
public class MileageLogFragment extends Fragment implements View.OnClickListener {

    Context context;
    private View mRootView;
    private RecyclerView mRecyclerView;
    private AdapterMileageLog mAdapter;
    private JobManager mJobManager;
    private List<MileageLogModel> mMileageLogList, totalMiles;
    ProgressBar pb, pb2;
    private Handler delayHandler;
    private ImageView ivInfo;
    private TextView tvAvlbMiles, tvVerified, tvUnverified, tvMinutesTotal, tvAvailable, tvRedeemed, tvMilesTotal;

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
            mRootView = inflater.inflate(R.layout.fragment_mileage_log, container, false);
            context = AppManager.getAppContext().getApplicationContext();
            MainActivity.checkExitScreens = false;

            tvAvlbMiles = (TextView) mRootView.findViewById(R.id.tv_available_miles_val);
            tvVerified = (TextView) mRootView.findViewById(R.id.tv_verified_val);
            tvUnverified = (TextView) mRootView.findViewById(R.id.tv_unverified_val);
            tvMinutesTotal = (TextView) mRootView.findViewById(R.id.tv_minutes_total_val);
            tvAvailable = (TextView) mRootView.findViewById(R.id.tv_available_val);
            tvRedeemed = (TextView) mRootView.findViewById(R.id.tv_redeemed_val);
            tvMilesTotal = (TextView) mRootView.findViewById(R.id.tv_milage_total_val);

            tvAvlbMiles.setText(SPAccountsManager.getMileageAvailable(context));
            tvVerified.setText(SPAccountsManager.getMinutesVerified(context));
            tvUnverified.setText(SPAccountsManager.getMinutesUnverified(context));
            tvMinutesTotal.setText(SPAccountsManager.getMinutesTotal(context));
            tvAvailable.setText(SPAccountsManager.getMileageAvailable(context));
            tvRedeemed.setText(SPAccountsManager.getMilesUsed(context));
            tvMilesTotal.setText(SPAccountsManager.getMilesTotal(context));

            ivInfo = (ImageView) mRootView.findViewById(R.id.iv_info);
            ivInfo.setOnClickListener(this);

            mRecyclerView = (RecyclerView) mRootView.findViewById(R.id.rv_mileage_list);
            mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    if (dy > 0) {
                        int lastItem = ((LinearLayoutManager) mRecyclerView.getLayoutManager()).findLastVisibleItemPosition();
                        if (lastItem == recyclerView.getAdapter().getItemCount() - 1 && delayHandler == null) {
                            loadMoreItems();
                        }
                    }
                }
            });
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//            mRecyclerView.setVerticalScrollBarEnabled(true);
            pb = (ProgressBar) mRootView.findViewById(R.id.pbar);
            pb.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.dark_blue), android.graphics.PorterDuff.Mode.MULTIPLY);
            pb2 = (ProgressBar) mRootView.findViewById(R.id.pbar_b);
            if (Util.isConnectingToInternet(getActivity())) {
                pb.setVisibility(View.VISIBLE);
                runJob();
            } else {
                Util.showToast(getActivity(), getResources().getString(R.string.connection_msg));
            }
            AppManager.getInstance().getEventBus().unregister(this);
            AppManager.getInstance().getEventBus().register(this);
        }

        return mRootView;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        AppManager.getInstance().getEventBus().unregister(this);
    }

    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    @Override
    public void onClick(View v) {
        if (v == ivInfo) {
            launchInfoFragment();
        }
    }

    static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {
        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept)  {
        }
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

    public void onEventMainThread(ErrorEvent event) {
        pb.setVisibility(View.GONE);
    }

    public void onEventMainThread(MileageLogEvent event) {
        pb.setVisibility(View.GONE);
        mMileageLogList = event.getList();
        mAdapter = new AdapterMileageLog(getActivity(), mMileageLogList);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), mRecyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
            }

            @Override
            public void onLongClick(View view, int position) {
            }
        }));
        totalMiles = new Select().from(MileageLogModel.class).execute();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        if (mRootView.getParent() != null) {
            ((ViewGroup) mRootView.getParent()).removeView(mRootView);
        }
        super.onDestroyView();
    }

    private void runJob() {
        mJobManager.addJobInBackground(new MileageLogJob());
    }

    private void loadMoreItems() {
//        mAdapter.addEmptyRow(mAdapter.getItemCount());
        pb.setVisibility(View.VISIBLE);
        delayHandler = new Handler();
        delayHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                int currentSize = mAdapter.getItemCount();
                if (totalMiles.size() > currentSize) {
                    List<MileageLogModel> list;
                    if (totalMiles.size() > currentSize + 10) {
                        list = totalMiles.subList(currentSize, currentSize + 10);
                    } else {
                        list = totalMiles.subList(currentSize, totalMiles.size());
                    }
//                    mAdapter.removeEmptyRow(mAdapter.getItemCount());
                    mAdapter.addItems(list, currentSize);
                    mAdapter.notifyItemInserted(currentSize);
                }
                pb.setVisibility(View.GONE);
                delayHandler = null;
            }
        }, 1000);
    }

    private void launchInfoFragment() {
        InfoFragment infoFragment = new InfoFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.container, infoFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}