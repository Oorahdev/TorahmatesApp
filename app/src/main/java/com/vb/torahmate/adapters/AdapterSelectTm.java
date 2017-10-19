package com.vb.torahmate.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.vb.torahmate.R;
import com.vb.torahmate.models.CallMyTorahmateInfoModel;
import com.vb.torahmate.models.TmInfoModel;

import java.util.List;

/**
 * Created by Najia on 7/28/2015.
 */
public class AdapterSelectTm extends RecyclerView.Adapter<AdapterSelectTm.MyViewHolder> {

    private final List<TmInfoModel> mList;
    private final LayoutInflater mLayoutInflater;
    private final Context mContext;

    public AdapterSelectTm(Context context, List<TmInfoModel> mList) {
        this.mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        this.mList = mList;
    }

    public void delete(int position) {
        notifyItemRemoved(position);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.row_tm, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        TmInfoModel tmInfoModel = mList.get(position);
        String telType = "";
        String telDisplay = "";
        holder.mTitle.setText(tmInfoModel.getName());
        List<CallMyTorahmateInfoModel> infoModel = tmInfoModel.getTelsList();
        for (int i = 0; i < infoModel.size(); i++) {
            CallMyTorahmateInfoModel torahmateInfoModel = tmInfoModel.getTelsList().get(i);
            telType = torahmateInfoModel.getTelType();
            telDisplay = torahmateInfoModel.getTelDisplay();
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        final TextView mTitle;
        final ImageView mIcon;

        public MyViewHolder(View itemView) {
            super(itemView);
            mTitle = (TextView) itemView.findViewById(R.id.list_text);
            mIcon = (ImageView) itemView.findViewById(R.id.list_icon);
        }
    }
}