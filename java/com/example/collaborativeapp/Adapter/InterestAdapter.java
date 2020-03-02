package com.example.collaborativeapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.collaborativeapp.Activity.DetailWork;
import com.example.collaborativeapp.Model.InterestModel;
import com.example.collaborativeapp.Model.JoinEventModel;
import com.example.collaborativeapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class InterestAdapter extends RecyclerView.Adapter<InterestAdapter.InterestEventViewHolder> {
    Context context;
    List<InterestModel> rowItems;
    InterestAdapter.OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(InterestAdapter.OnItemClickListener listener) {
        mListener = listener;
    }

    public InterestAdapter(Context context, List<InterestModel> rowItems) {
        this.context = context;
        this.rowItems = rowItems;
    }

    @NonNull
    @Override
    public InterestAdapter.InterestEventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.interest_event_cardview, null);
        return new InterestAdapter.InterestEventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InterestAdapter.InterestEventViewHolder holder, int position) {
        InterestModel rowItem_show = rowItems.get(position);

        final String get_event_name = rowItem_show.getevent_name();
        final String get_join_event_id = rowItem_show.getjoin_event_id();
        final String get_event_location = rowItem_show.getevent_location();
        final String get_event_date_close = rowItem_show.getevent_date();
        final String get_event_type = rowItem_show.getevent_type();
        final String get_event_org = rowItem_show.getevent_org();
        final String get_event_detail = rowItem_show.getevent_detail();
        final String get_member_fname = rowItem_show.getmember_fname();
        final String get_member_phone = rowItem_show.getmember_phone();
        final String get_logo_event_path= rowItem_show.getlogo_event_path();


        Picasso.get().load(get_logo_event_path).into(holder.txtevent_logo);

        holder.txtinterest_eventname.setText(get_event_name);
        holder.txtinterest_location.setText(get_event_location);
        holder.txtinterest_date.setText(get_event_date_close);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailWork.class);
                intent.putExtra("eventname", get_event_name);
                intent.putExtra("event_id", get_join_event_id);
                intent.putExtra("event_location", get_event_location);
                intent.putExtra("vent_date_close", get_event_date_close);
                intent.putExtra("event_type", get_event_type);
                intent.putExtra("event_org", get_event_org);
                intent.putExtra("event_detail", get_event_detail);
                intent.putExtra("member_fname", get_member_fname);
                intent.putExtra("member_phone", get_member_phone);
//                intent.putExtra("event_logo", get_event_logo);


                context.startActivity(intent);

            }
        });


    }


    @Override
    public int getItemCount() {
        return rowItems.size();
    }

    class InterestEventViewHolder extends RecyclerView.ViewHolder {
        TextView txtinterest_eventname, txtinterest_location,txtinterest_date;
        ImageView txtevent_logo;

        public InterestEventViewHolder(View itemView) {
            super(itemView);
            txtinterest_eventname = itemView.findViewById(R.id.txtinterest_eventname);
            txtinterest_location = itemView.findViewById(R.id.txtinterest_location);
            txtinterest_date = itemView.findViewById(R.id.txtinterest_date);


            txtevent_logo = itemView.findViewById(R.id.txtevent_logo);
        }
    }
}