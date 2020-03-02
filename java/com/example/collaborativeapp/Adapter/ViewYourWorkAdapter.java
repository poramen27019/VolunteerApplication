package com.example.collaborativeapp.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.collaborativeapp.Activity.ViewYourWorks;
import com.example.collaborativeapp.Activity.YourWorks;
import com.example.collaborativeapp.Model.ViewYourWorkModel;
import com.example.collaborativeapp.Model.YourWorkModel;
import com.example.collaborativeapp.R;

import java.util.List;

public class ViewYourWorkAdapter extends RecyclerView.Adapter<ViewYourWorkAdapter.ViewYourWorkViewHolder> {
    Context context;
    List<ViewYourWorkModel> rowItems;
    ViewYourWorkAdapter.OnItemClickListener mListener;
String get_member_fname = "click";
    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(ViewYourWorkAdapter.OnItemClickListener listener) {
        mListener = listener;
    }

    public ViewYourWorkAdapter(Context context, List<ViewYourWorkModel> rowItems) {
        this.context = context;
        this.rowItems = rowItems;
    }

    @NonNull
    @Override
    public ViewYourWorkAdapter.ViewYourWorkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.member_join_list_cardview, null);
        return new ViewYourWorkAdapter.ViewYourWorkViewHolder(view);
    }

    public  void showAleartDialog(String get_member_fname,String get_member_lname,String get_member_phone){
        AlertDialog.Builder aleart = new AlertDialog.Builder(context);
        aleart.setIcon(R.drawable.user);
        aleart.setTitle(get_member_fname + "   " + get_member_lname);
        aleart.setMessage("เบอร์ติดต่อ : "+get_member_phone);
//        aleart.setMessage("test3");
        aleart.setPositiveButton("close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
//                Toast.makeText(context,"X",Toast.LENGTH_LONG).show();
            }
        });
        aleart.create().show();
//        aleart.setPositiveButton("ตกลก" , new DialogInterface.OnClickListener() {
//         @Override
//         public void onClick(DialogInterface dialogInterface, int i) {
//
//         }
//     });
    }

    @Override
    public void onBindViewHolder(@NonNull ViewYourWorkAdapter.ViewYourWorkViewHolder holder, int position) {
        ViewYourWorkModel rowItem_show = rowItems.get(position);

        final String get_member_id = rowItem_show.getmember_id();
        final String get_member_fname = rowItem_show.getmember_fname();
        final String get_member_lname = rowItem_show.getmember_lname();
        final String get_member_phone = rowItem_show.getmember_phone();
        holder.txtmember_join_fname.setText(get_member_fname);
        holder.txtmember_join_lname.setText(get_member_lname);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(context, ViewYourWorks.class);
//                intent.putExtra("member_join_id", get_member_id);
//
//                context.startActivity(intent);

//                String b = get_member_lname;
                showAleartDialog(get_member_fname,get_member_lname,get_member_phone);





//                Toast.makeText(context, ""+get_member_id, Toast.LENGTH_SHORT).show();

            }
        });

//        Picasso.get().load(get_picture).into(holder.pet_profile);
    }



    @Override
    public int getItemCount() {
        return rowItems.size();
    }

    class ViewYourWorkViewHolder extends RecyclerView.ViewHolder {
        TextView txtmember_join_fname,txtmember_join_lname;


        public ViewYourWorkViewHolder(View itemView) {
            super(itemView);

            txtmember_join_fname = itemView.findViewById(R.id.txtmember_join_fname);
            txtmember_join_lname = itemView.findViewById(R.id.txtmember_join_lname);
        }
    }

}


