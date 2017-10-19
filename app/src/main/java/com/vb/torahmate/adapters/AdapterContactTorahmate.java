package com.vb.torahmate.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vb.torahmate.R;
import com.vb.torahmate.models.ContactTorahmateModel;
import com.vb.torahmate.utils.Util;

import java.util.Collections;
import java.util.List;

public class AdapterContactTorahmate extends RecyclerView.Adapter<AdapterContactTorahmate.MyViewHolder> {
    private List<ContactTorahmateModel> mContactList = Collections.emptyList();
    private final LayoutInflater mLayoutInflater;
    private final Context mContext;

    public AdapterContactTorahmate(Context context, List<ContactTorahmateModel> data) {
        this.mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        this.mContactList = data;
    }

    public void delete(int position) {
        mContactList.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.row_contact_torahmate, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        ContactTorahmateModel current = mContactList.get(position);
        final String cellNumber = current.getExtension();
        String cellNumberStr = current.getExtension();
        String emailAddress = current.getEmail();
        String coordinator = current.getCoordinator();
        String torahmateName = current.getCoordinator();
        if (position == 0) {
            coordinator = mContext.getString(R.string.tm_coordinator);
        } else {
            holder.torahnamteName.setVisibility(View.GONE);
        }

        if (emailAddress.equals("")) {
            holder.mEmailLayout.setVisibility(View.GONE);
        } else {
            holder.mEmailLayout.setVisibility(View.VISIBLE);
        }
        if (cellNumber.equals("")) {
            holder.mCallLayout.setVisibility(View.GONE);
        } else {
            holder.mCallLayout.setVisibility(View.VISIBLE);
        }
        if (cellNumber.matches("(\\d){3}")) {
            cellNumberStr = mContext.getString(R.string.extension) + cellNumber;
        }
        holder.torahnamteName.setText(torahmateName);
        holder.mTorahmateTitle.setText(coordinator);
        holder.mPhoneNumber.setText(cellNumberStr);
        holder.mPhoneNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(Util.getFromDialerNumber(mContext) == null)) {
                    Util.getFromDialerNumber(mContext);
                    String number = holder.mPhoneNumber.getText().toString();
                    if (number.equalsIgnoreCase(mContext.getString(R.string.torahas_number)))
                        Util.call(mContext, mContext.getString(R.string.torahmate_number), holder.mTorahmateTitle.getText().toString());
                    else if (number.matches("(" + mContext.getString(R.string.extension) + ")(\\d){3}"))
                        Util.call(mContext, mContext.getString(R.string.torahmate_number), holder.mTorahmateTitle.getText().toString(),
                                cellNumber);
                    else
                        Util.call(mContext, number, holder.mTorahmateTitle.getText().toString());
                    Util.checkAppName = "true";
                    Util.checkCallEnd = false;
                } else {
                    Util.showToast(mContext, mContext.getResources().getString(R.string.missing_sim_card));
                }
            }
        });
        holder.mEmailAddress.setText(emailAddress);
        holder.mEmailAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Util.sendEmail(mContext, holder.mEmailAddress.getText().toString());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mContactList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        final TextView mTorahmateTitle;
        final TextView mPhoneNumber;
        final TextView mEmailAddress;
        final LinearLayout mEmailLayout;
        final LinearLayout mCallLayout;
        private TextView torahnamteName;

        public MyViewHolder(View itemView) {
            super(itemView);
            mTorahmateTitle = (TextView) itemView.findViewById(R.id.tv_torahmate_title);
            mPhoneNumber = (TextView) itemView.findViewById(R.id.tv_phone_number);
            mEmailAddress = (TextView) itemView.findViewById(R.id.tv_email_address);
            mEmailLayout = (LinearLayout) itemView.findViewById(R.id.ll_email);
            mCallLayout = (LinearLayout) itemView.findViewById(R.id.ll_call);
            torahnamteName = (TextView) itemView.findViewById(R.id.torahnamte_name);
        }
    }
}