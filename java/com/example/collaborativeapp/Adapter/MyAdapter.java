package com.example.collaborativeapp.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.collaborativeapp.Activity.DetailWork;
import com.example.collaborativeapp.Activity.EditYourWorks;
import com.example.collaborativeapp.Activity.Home;
import com.example.collaborativeapp.Activity.ViewYourWorks;
import com.example.collaborativeapp.Activity.YourWorks;
import com.example.collaborativeapp.Model.HomeWorkModel;
import com.example.collaborativeapp.R;
import com.example.collaborativeapp.Model.YourWorkModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;
    List<YourWorkModel> rowItems;
    MyAdapter.OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(MyAdapter.OnItemClickListener listener) {
        mListener = listener;
    }

    public MyAdapter(Context context, List<YourWorkModel> rowItems) {
        this.context = context;
        this.rowItems = rowItems;
    }

    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.cardview, null);
        return new MyAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {
        YourWorkModel rowItem_show = rowItems.get(position);
        final String get_event_id = rowItem_show.getevent_id();
        final String get_eventname = rowItem_show.geteventname();
        final String get_event_date = rowItem_show.getevent_date();
        final String get_event_location = rowItem_show.getevent_location();
        final String get_num2= rowItem_show.getnum2();
        final String get_event_sum= rowItem_show.getevent_sum();
        final String get_event_type = rowItem_show.getevent_type();
        final String get_event_org = rowItem_show.getevent_org();
        final String get_event_detail = rowItem_show.getevent_detail();

        final String get_member_fname = rowItem_show.getmember_fname();
        final String get_member_phone = rowItem_show.getmember_phone();
        holder.txteventname.setText(get_eventname);
        holder.txtevent_sum.setText(get_event_sum);
        holder.txtevent_date.setText(get_event_date);
        holder.txtevent_location.setText(get_event_location);
        holder.txtevent_num.setText(get_num2);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ViewYourWorks.class);
                intent.putExtra("event_id", get_event_id);
                intent.putExtra("eventname", get_eventname);
                intent.putExtra("event_date", get_event_date);
                intent.putExtra("event_location", get_event_location);
                intent.putExtra("event_date", get_event_date);
                intent.putExtra("event_type", get_event_type);
                intent.putExtra("event_org", get_event_org);
                intent.putExtra("event_detail", get_event_detail);

                intent.putExtra("member_fname", get_member_fname);
                intent.putExtra("member_phone", get_member_phone);
                intent.putExtra("event_num", get_num2);
                context.startActivity(intent);

            }
        });

//        Picasso.get().load(get_picture).into(holder.pet_profile);
    }


    @Override
    public int getItemCount() {
        return rowItems.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txteventname, txtevent_date,txtevent_location,txtevent_num,txtevent_sum;


        public MyViewHolder(View itemView) {
            super(itemView);

            txteventname = itemView.findViewById(R.id.txteventname);
            txtevent_date = itemView.findViewById(R.id.txtevent_date);
            txtevent_location = itemView.findViewById(R.id.txtevent_location);
            txtevent_num = itemView.findViewById(R.id.txtevent_num);
            txtevent_sum = itemView.findViewById(R.id.txtevent_sum);
        }
    }

}

