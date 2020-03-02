package com.example.collaborativeapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.collaborativeapp.Activity.ConfigIP;
import com.example.collaborativeapp.Activity.DetailWork;
import com.example.collaborativeapp.Activity.Home;
import com.example.collaborativeapp.Activity.InterestedList;
import com.example.collaborativeapp.Activity.PopActivity;
import com.example.collaborativeapp.Activity.ViewYourWorks;
import com.example.collaborativeapp.Model.HomeWorkModel;

import com.example.collaborativeapp.R;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

public class HomeWorkAdapter extends RecyclerView.Adapter<HomeWorkAdapter.HomeWorkViewHolder>{

    Context context;
    List<HomeWorkModel> rowItems;
    private OnItemClick mCallback;


    public interface OnItemClick {
        void onClick (int position);
    }

    public void setOnClick (OnItemClick listener){
        this.mCallback = listener;
    }



    public HomeWorkAdapter(Context context, List<HomeWorkModel> rowItems) {
        this.context = context;
        this.rowItems = rowItems;
//        this.rowItems = new ArrayList<>();
    }

    @NonNull
    @Override
    public HomeWorkAdapter.HomeWorkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.homework_cardview, null);
        return new HomeWorkAdapter.HomeWorkViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeWorkAdapter.HomeWorkViewHolder holder, int position) {

        HomeWorkModel  rowItem_show = rowItems.get(position);
        final String  get_event_id = rowItem_show.getevent_id();
        final String  get_event_member_id = rowItem_show.getevent_member_id();
        final String  get_eventname = rowItem_show.geteventname();
        final String get_event_location = rowItem_show.getevent_location();
        final String get_event_date_close = rowItem_show.getevent_date_close();
        final String get_event_type = rowItem_show.getevent_type();
        final String get_event_org = rowItem_show.getevent_org();
        final String get_event_detail = rowItem_show.getevent_detail();
        final String get_member_fname = rowItem_show.getmember_fname();
        final String get_member_phone = rowItem_show.getmember_phone();
        final String get_num = rowItem_show.getnum();
        final String get_event_sum = rowItem_show.getevent_sum();
        final String get_logo_event_path= rowItem_show.getlogo_event_path();

        Picasso.get().load(get_logo_event_path).into(holder.event_logo);

        holder.btnicon_star.setVisibility(View.VISIBLE);
        holder.txteventname.setText(get_eventname);
        holder.txtnum.setText(get_num);
        holder.txtevent_sum.setText(get_event_sum);
        holder.txtevent_location.setText(get_event_location);
        holder.txtmember_fname.setText(get_member_fname);
        holder.txthome_eventadd_date_close.setText(get_event_date_close);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailWork.class);
                intent.putExtra("event_id",get_event_id);
                intent.putExtra("event_member_id",get_event_member_id);
                intent.putExtra("eventname",get_eventname);
                intent.putExtra("event_location",get_event_location);
                intent.putExtra("event_date_close",get_event_date_close);
                intent.putExtra("event_type",get_event_type);
                intent.putExtra("event_org",get_event_org);
                intent.putExtra("event_detail",get_event_detail);
                intent.putExtra("member_fname",get_member_fname);
                intent.putExtra("member_phone",get_member_phone);
                intent.putExtra("num",get_num);
                intent.putExtra("event_sum",get_event_sum);
                context.startActivity(intent);




            }
        });

//        Picasso.get().load(get_picture).into(holder.pet_profile);
    }



    @Override
    public int getItemCount() {
        return rowItems.size();
    }

    class HomeWorkViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener {
        TextView txteventname,txtevent_location,txthome_eventadd_date_close,txtmember_fname,txtnum,txtevent_sum;
        Button btnicon_star,btnicon_star2 ;
         ImageView event_logo;
        public HomeWorkViewHolder(View itemView){
            super(itemView);

            txteventname = itemView.findViewById(R.id.txteventname);
            txtevent_location = itemView.findViewById(R.id.txtevent_location);
            txthome_eventadd_date_close = itemView.findViewById(R.id.txthome_eventadd_date_close);
            txtmember_fname = itemView.findViewById(R.id.txtmember_fname);
            txtnum = itemView.findViewById(R.id.txtnum);
            txtevent_sum = itemView.findViewById(R.id.txtevent_sum);
            btnicon_star = itemView.findViewById(R.id.btnicon_star);
            btnicon_star2 = itemView.findViewById(R.id.btnicon_star2);

            event_logo = itemView.findViewById(R.id.event_logo);


            itemView.setOnClickListener(this);
            btnicon_star.setOnClickListener(this);


            itemView.setOnClickListener(this);
            btnicon_star2.setOnClickListener(this);

        }


        @Override
        public void onClick(View view) {
            if (view.getId() == btnicon_star.getId()){
//                Toast.makeText(context, "Click Icon" + String.valueOf(getAdapterPosition()), Toast.LENGTH_SHORT).show();
                if (rowItems != null){
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION){
                    mCallback.onClick(position);

                    btnicon_star.setVisibility(View.GONE);

                }
            }

            }if (view.getId() == btnicon_star2.getId()){



            }


            else{
                Toast.makeText(context, "Click Item" + String.valueOf(getAdapterPosition()), Toast.LENGTH_SHORT).show();
            }
//
        }




//        public class InsertAsyn extends AsyncTask<String, Void, String> {
//
//            @Override
//
//            protected String doInBackground(String... strings) {
//                try{
//
//                    OkHttpClient _okHttpClient = new OkHttpClient();
//                    RequestBody _requestBody = new FormBody.Builder()
////                            .add("event_id",get_event_id )
////                            .add("member_id", get_member_id)
//                            .build();
//
//                    okhttp3.Request request = new okhttp3.Request.Builder().url(strings[0]).post(_requestBody).build();
//                    _okHttpClient.newCall(request).execute();
//                    return "successfully";
//
//                }catch(IOException e){
//                    e.printStackTrace();
//                }
//                return null;
//            }
//            @Override
//            protected void onPostExecute(String result) {
//                super.onPostExecute(result);
//
//                if (result != null){
//                    Toast.makeText(context.getApplicationContext(), "ท่านสมัครเรียบร้อยแล้ว",Toast.LENGTH_LONG).show();
//                    Intent intent = new Intent(context, InterestedList.class);
////                intent.putExtra("member_id",member_id);
////                intent.putExtra("event_id",event_id);
////                intent.putExtra("eventname",eventname);
////                intent.putExtra("event_location",event_location);
////                intent.putExtra("event_type",event_type);
////                intent.putExtra("event_org",event_org);
////                intent.putExtra("event_detail",event_detail);
////                intent.putExtra("member_fname",member_fname);
//
//
//                    context.startActivity(intent);
//                }else {
//                    Toast.makeText(context.getApplicationContext(), "ยกเลิกการสมัครเรียบร้อย",Toast.LENGTH_LONG).show();
//                }
//            }
//        }


//        public void onClick(View View) {
//            btnicon_star.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Toast.makeText(context,"กดได้", Toast.LENGTH_SHORT).show();
//                }
//            });
//
//        }
    }

}
