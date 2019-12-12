package com.example.shopkeepersdiary;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.collection.LLRBNode;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    private Context mContext;
    private List<HistoryView> mUsers;

    public HistoryAdapter(Context mContext,List<HistoryView> mUsers)
    {
        this.mContext=mContext;
        this.mUsers=mUsers;

    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.history_item,viewGroup,false);
        return new HistoryAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        final HistoryView user=mUsers.get(i);
        String flag=user.getHstatus();
        if(user.getHremain().equals("Pending Money : 0"))
        {
            viewHolder.hmoney.setBackgroundColor(Color.rgb(96,189,239));
            viewHolder.hstatus.setBackgroundColor(Color.rgb(96,189,239));
            viewHolder.hremain.setBackgroundColor(Color.rgb(96,189,239));
            viewHolder.hdate.setBackgroundColor(Color.rgb(96,189,239));
            viewHolder.htime.setBackgroundColor(Color.rgb(96,189,239));
            viewHolder.hreason.setBackgroundColor(Color.rgb(96,189,239));

            viewHolder.hreason.setText("......Thank You........");


            viewHolder.hremain.setText("NIL");

        }
        else if(flag.equals("Added"))
        {
            viewHolder.hmoney.setBackgroundColor(Color.rgb(255,0,0));
            viewHolder.hstatus.setBackgroundColor(Color.rgb(255,0,0));
            viewHolder.hremain.setBackgroundColor(Color.rgb(255,0,0));
            viewHolder.hdate.setBackgroundColor(Color.rgb(255,0,0));
            viewHolder.htime.setBackgroundColor(Color.rgb(255,0,0));
            viewHolder.hreason.setBackgroundColor(Color.rgb(255,0,0));
            viewHolder.hreason.setText(user.getHreason());
            viewHolder.hremain.setText(user.getHremain());
        } else {

            viewHolder.hmoney.setBackgroundColor(Color.rgb(0,133,119));
            viewHolder.hstatus.setBackgroundColor(Color.rgb(0,133,119));
            viewHolder.hremain.setBackgroundColor(Color.rgb(0,133,119));
            viewHolder.hdate.setBackgroundColor(Color.rgb(0,133,119));
            viewHolder.htime.setBackgroundColor(Color.rgb(0,133,119));
            viewHolder.hreason.setBackgroundColor(Color.rgb(0,133,119));
            viewHolder.hreason.setText("-----------------");
            viewHolder.hremain.setText(user.getHremain());

        }
        viewHolder.hmoney.setText(user.getHmoney());
        viewHolder.hstatus.setText(user.getHstatus());
        viewHolder.hdate.setText(user.getHdate());
        viewHolder.htime.setText(user.getHtime());



    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView hmoney,hstatus,hdate,htime,hremain,hreason;

        public ViewHolder(View itemView) {
            super(itemView);

            hmoney=itemView.findViewById(R.id.hmoney);
            hstatus=itemView.findViewById(R.id.hstatus);
            hdate=itemView.findViewById(R.id.hdate);
            htime=itemView.findViewById(R.id.htime);
            hremain=itemView.findViewById(R.id.remain);
            hreason=itemView.findViewById(R.id.hreason);

        }
    }
}
