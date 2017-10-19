package com.vb.torahmate.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vb.torahmate.R;
import com.vb.torahmate.models.MileageLogModel;

import java.util.Collections;
import java.util.List;

/**
 * Created by Najia on 7/27/2015.
 */
public class AdapterMileageLog extends RecyclerView.Adapter<AdapterMileageLog.MyViewHolder> {

    private List<MileageLogModel> mMileageLogList = Collections.emptyList();
    private final LayoutInflater mLayoutInflater;
    private final Context mContext;

    public AdapterMileageLog(Context context, List<MileageLogModel> data) {
        this.mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        this.mMileageLogList = data;
    }

    public void addItems(List<MileageLogModel> data,int position){
        this.mMileageLogList.addAll(position,data);
        this.notifyItemInserted(position);
    }

    public void addEmptyRow(int position){
        this.mMileageLogList.add(new MileageLogModel());
        this.notifyItemInserted(position);
    }

    public void removeEmptyRow(int position){
        this.mMileageLogList.remove(position);
        this.notifyItemInserted(position);
    }

    public void delete(int position) {
        notifyItemRemoved(position);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.row_mileage_log, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        MileageLogModel mileageLogModel = mMileageLogList.get(position);
        String dateTime = mileageLogModel.getDate();
        holder.mName.setText(mileageLogModel.getName2());
        holder.mDate.setText(dateTime);
        holder.mMiles.setText(mileageLogModel.getMinutes());
        holder.mVerified.setText(mileageLogModel.getVerified().equals("1") ? "Verified" : "Unverified");
    }

    @Override
    public int getItemCount() {
        return mMileageLogList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        final TextView mName, mDate, mMiles, mVerified;

        public MyViewHolder(View itemView) {
            super(itemView);
            mName = (TextView) itemView.findViewById(R.id.tv_log_name);
            mDate = (TextView) itemView.findViewById(R.id.tv_log_date);
            mMiles = (TextView) itemView.findViewById(R.id.tv_log_miles);
            mVerified = (TextView) itemView.findViewById(R.id.tv_log_verified);
        }
    }
}