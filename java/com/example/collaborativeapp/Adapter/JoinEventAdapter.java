package com.example.collaborativeapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.collaborativeapp.Activity.AddWork;
import com.example.collaborativeapp.Activity.DetailWork;
import com.example.collaborativeapp.Activity.Home;

import com.example.collaborativeapp.Activity.Message;
import com.example.collaborativeapp.Activity.ViewYourWorks;
import com.example.collaborativeapp.Model.HomeWorkModel;
import com.example.collaborativeapp.Model.JoinEventModel;
import com.example.collaborativeapp.Model.YourWorkModel;
import com.example.collaborativeapp.R;

import java.util.ArrayList;
import java.util.List;

public class JoinEventAdapter extends RecyclerView.Adapter<JoinEventAdapter.JoinEventViewHolder> {
    Context context;
    List<JoinEventModel> rowItems;
    JoinEventAdapter.OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(JoinEventAdapter.OnItemClickListener listener) {
        mListener = listener;
    }

    public JoinEventAdapter(Context context, List<JoinEventModel> rowItems) {
        this.context = context;
        this.rowItems = rowItems;
    }

    @NonNull
    @Override
    public JoinEventAdapter.JoinEventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.join_event_cardview, null);
        return new JoinEventAdapter.JoinEventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull JoinEventAdapter.JoinEventViewHolder holder, int position) {
        JoinEventModel rowItem_show = rowItems.get(position);
//

//        final String get_member_id = rowItem_show.getmember_id();
        final String get_event_name = rowItem_show.getevent_name();
        final String get_join_event_id = rowItem_show.getjoin_event_id();
        final String get_event_location = rowItem_show.getevent_location();
        final String get_event_date_close = rowItem_show.getevent_date_close();
        final String get_event_type = rowItem_show.getevent_type();
        final String get_event_org = rowItem_show.getevent_org();
        final String get_event_detail = rowItem_show.getevent_detail();
        final String get_member_fname = rowItem_show.getmember_fname();
        final String get_member_phone = rowItem_show.getmember_phone();


//        public String getevent_location() { return event_location; }
//        public String getevent_date_close() { return event_date_close; }
//        public String getevent_type() { return event_type; }
//        public String getevent_org() { return event_org; }
//        public String getevent_detail() { return event_detail; }
//        public String getmember_fname() { return member_fname; }
//        public String getmember_phone() { return member_phone; }


//        final String getevent_join_id = rowItem_show.getevent_join_id();
        holder.txteventname.setText(get_event_name);

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
//                intent.putExtra("event_id", getevent_join_id);
//                intent.putExtra("member_id", get_member_id);

//                intent.putExtra("event_date", get_event_name);


                context.startActivity(intent);

            }
        });

//        Picasso.get().load(get_picture).into(holder.pet_profile);
    }


    @Override
    public int getItemCount() {
        return rowItems.size();
    }

    class JoinEventViewHolder extends RecyclerView.ViewHolder {
        TextView txteventname, txtevent_date;


        public JoinEventViewHolder(View itemView) {
            super(itemView);

            txteventname = itemView.findViewById(R.id.txtjoin_eventname);
//            txtevent_date = itemView.findViewById(R.id.txtevent_date);

        }
    }
}