package com.vb.torahmate.main;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.activeandroid.query.Select;
import com.path.android.jobqueue.JobManager;
import com.vb.torahmate.R;
import com.vb.torahmate.adapters.AdapterContactTorahmate;
import com.vb.torahmate.events.ContactTorahmateEvent;
import com.vb.torahmate.events.ErrorEvent;
import com.vb.torahmate.jobs.ContactTorahmateJob;
import com.vb.torahmate.models.ContactTorahmateModel;
import com.vb.torahmate.utils.AppManager;
import com.vb.torahmate.utils.Util;
import com.vb.torahmate.widget.GifMovieView;

import java.util.List;

/**
 * Created by Najia on 7/23/2015.
 */
public class ContactUsFragment extends Fragment {

    private View mRootView;
    private RecyclerView mRecyclerView;
    private AdapterContactTorahmate mAdapter;
    private JobManager mJobManager;
    private List<ContactTorahmateModel> mContactList;
    ProgressBar pb;

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
            mRootView = inflater.inflate(R.layout.fragment_contact_us, container, false);
            MainActivity.checkExitScreens = false;
            mRecyclerView = (RecyclerView) mRootView.findViewById(R.id.rv_contactsList);
            pb = (ProgressBar) mRootView.findViewById(R.id.pbar);

            mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            mContactList = new Select().from(ContactTorahmateModel.class).execute();
            if (mContactList.size() != 0) {
                loadContectsData();
            } else {
                if (Util.isConnectingToInternet(getActivity())) {
                    pb.setVisibility(View.VISIBLE);
                    runJob();
                } else {
                    Util.showToast(getActivity(), getResources().getString(R.string.connection_msg));
                }
            }
            AppManager.getInstance().getEventBus().unregister(this);
            AppManager.getInstance().getEventBus().register(this);
        }

        return mRootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        Util.cancelToast();
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

    static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {
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

    }

    public void onEventMainThread(ContactTorahmateEvent event) {
        pb.setVisibility(View.GONE);
        if (this.isVisible()) {
            mContactList = event.getList();
            loadContectsData();
        }
    }

    @Override
    public void onDestroyView() {
        if (mRootView.getParent() != null) {
            ((ViewGroup) mRootView.getParent()).removeView(mRootView);
        }
        super.onDestroyView();
    }

    private void runJob() {
        mJobManager.addJobInBackground(new ContactTorahmateJob());
    }

    private void loadContectsData() {
        mAdapter = new AdapterContactTorahmate(getActivity(), mContactList);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), mRecyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
            }

            @Override
            public void onLongClick(View view, int position) {
            }
        }));
    }

}