package com.example.shopkeepersdiary;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> implements Filterable {


    private Context mContext;
    private List<CustomerView> mUsers;
    private List<CustomerView> mUsersFull;


    public UserAdapter(Context mContext,List<CustomerView> mUsers)
    {
        this.mContext=mContext;
        this.mUsers=mUsers;
        mUsersFull=new ArrayList<>(mUsers);

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.customer_item,viewGroup,false);
        return new UserAdapter.ViewHolder(view);

    }



    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        final CustomerView user=mUsers.get(i);
        viewHolder.username.setText(user.getUsername());
        viewHolder.mob.setText(user.getMob());
        String img=user.getImageURL();
        Uri u= Uri.parse(img);

        Picasso.with(mContext).load(u.toString()).into(viewHolder.profile_image);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(mContext, info.class);
                i.putExtra("userid",user.getId());
                mContext.startActivity(i);

            }
        });
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    @Override
    public Filter getFilter() {
        return mUserFilter;
    }

    private Filter mUserFilter=new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<CustomerView> filteredList= new ArrayList<>();

            if(constraint==null || constraint.length()==0) {
                filteredList.addAll(mUsersFull);
            } else {
                String filterpattern = constraint.toString().toLowerCase().trim();

                for (CustomerView cv : mUsersFull) {
                    if(cv.getUsername().toLowerCase().contains(filterpattern)) {
                        filteredList.add(cv);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values=filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            mUsers.clear();
            mUsers.addAll((List) results.values);
            notifyDataSetChanged();

        }
    };

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView username;
        public TextView mob;
        public CircleImageView profile_image;

        public ViewHolder(View itemView) {
            super(itemView);

            username=itemView.findViewById(R.id.username);
            mob=itemView.findViewById(R.id.mob);
            profile_image=itemView.findViewById(R.id.cprof);

        }
    }

}
