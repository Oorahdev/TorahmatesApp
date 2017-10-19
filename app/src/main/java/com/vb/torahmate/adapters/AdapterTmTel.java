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

import java.util.List;

public class AdapterTmTel extends RecyclerView.Adapter<AdapterTmTel.MyViewHolder> {

    private final List<CallMyTorahmateInfoModel> mList;
    private final LayoutInflater mLayoutInflater;
    private final Context mContext;

    public AdapterTmTel(Context context, List<CallMyTorahmateInfoModel> mList) {
        this.mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        this.mList = mList;
    }

    public void delete(int position) {
        notifyItemRemoved(position);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.row_contact_info, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        CallMyTorahmateInfoModel tmTelsModel = mList.get(position);
        holder.mTel.setText(tmTelsModel.getTelDisplay());
        String telType = tmTelsModel.getTelType();
        holder.mTelType.setText(telType);
        if (telType.equals("EMAIL")) {
            holder.mTelIcon.setImageResource(R.drawable.ic_email);
        } else {
            holder.mTelIcon.setImageResource(R.drawable.ic_phone);
//        } else if (telType.equals("HOME")) {
//            holder.mTelIcon.setImageResource(R.drawable.img_tel);
//        } else if (telType.equals("PHONE")) {
//            holder.mTelIcon.setImageResource(R.drawable.reciver3);
//        } else if (telType.equals("WORK")) {
//            holder.mTelIcon.setImageResource(R.drawable.img_office);
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        final TextView mTel;
        final TextView mTelType;
        final ImageView mTelIcon;

        public MyViewHolder(View itemView) {
            super(itemView);
            mTel = (TextView) itemView.findViewById(R.id.tv_tel);
            mTelType = (TextView) itemView.findViewById(R.id.tv_tel_type);
            mTelIcon = (ImageView) itemView.findViewById(R.id.iv_tel);
        }
    }
}