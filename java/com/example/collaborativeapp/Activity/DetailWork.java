package com.example.collaborativeapp.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.collaborativeapp.Adapter.HomeWorkAdapter;
import com.example.collaborativeapp.MessageActivity;
import com.example.collaborativeapp.Model.HomeWorkModel;
import com.example.collaborativeapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

public class DetailWork extends AppCompatActivity {
    TextView txteventname,txtmember_fname,txtmember_phone,txtdateclose,txtdetail,txtlocation,txtorganization,txtsum,txttype;
    String event_id,event_member_id,eventname,admin,adminphone,event_type,event_org,event_location,event_detail,member_fname,member_phone,event_date_close;
    Button btn_Click_register_work ,btn_Click_cancle_work;
    String get_member_id,member_id,get_event_id;
    MenuItem menu_favotite;
    MenuItem menu_cancle_favotite;





    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_work);

        SharedPreferences shared = getSharedPreferences("Login", Context.MODE_PRIVATE);
        member_id = shared.getString("member_id", "");


        txteventname = findViewById(R.id.txteventname);
        txtmember_fname = findViewById(R.id.txtmember_fname);
        txtmember_phone = findViewById(R.id.txtmember_phone);
        txtdateclose = findViewById(R.id.txtdateclose);
        txtdetail = findViewById(R.id.txtdetail);
        txtlocation = findViewById(R.id.txtlocation);
        txtorganization = findViewById(R.id.txtorganization);
        txttype = findViewById(R.id.txttype);




        btn_Click_register_work = (Button) findViewById(R.id.btn_Click_register_work);
        btn_Click_cancle_work  = (Button) findViewById(R.id.btn_Click_cancle_work);
        event_id = getIntent().getStringExtra("event_id");
        event_member_id = getIntent().getStringExtra("event_member_id");
        eventname = getIntent().getStringExtra("eventname");
        member_fname = getIntent().getStringExtra("member_fname");
        member_phone = getIntent().getStringExtra("member_phone");
        event_type = getIntent().getStringExtra("event_type");
        event_org = getIntent().getStringExtra("event_org");
        event_location = getIntent().getStringExtra("event_location");
        event_date_close = getIntent().getStringExtra("event_date_close");
        event_detail = getIntent().getStringExtra("event_detail");


//        sum = getIntent().getStringExtra("sum");
        loadShowJoin();
        loadShowInterest();

//        loadShowInterest();

        txteventname.setText(eventname);
//        txtmember_fname.setText(admin);
        txtmember_phone.setText(member_phone);
        txtdateclose.setText(event_date_close);
        txtdetail.setText(event_detail);
        txtlocation.setText(event_location);
        txtorganization.setText(event_org);
        txttype.setText(event_type);
        txtmember_fname.setText(member_fname);
//        txtsum.setText(sum);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        btn_Click_register_work.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),PopActivity.class);
                intent.putExtra("event_id",event_id);
                intent.putExtra("eventname",eventname);
                intent.putExtra("event_location",event_location);
                intent.putExtra("event_type",event_type);
                intent.putExtra("event_org",event_org);
                intent.putExtra("event_detail",event_detail);
                intent.putExtra("member_fname",member_fname);
                startActivity(intent);
            }
        });

        btn_Click_cancle_work.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(DetailWork.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
                builder.setCancelable(true);
                builder.setMessage("คุณต้องการยกเลิกการเข้าร่วมใช่หรือไม่");
                builder.setPositiveButton("ใช่",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                new DeleteAsyn().execute("http://"+ConfigIP.IP+"/volunteer_project/delete_member_join_event.php");
                                btn_Click_register_work.setVisibility(View.VISIBLE);
                                btn_Click_cancle_work.setVisibility(View.GONE);

       }
                        });
                builder.setNegativeButton("ไม่ใช่", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();





//                Intent intent = new Intent(getApplicationContext(),PopActivity.class);
//                startActivity(intent);
            }
        });



    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.menu_item_detail, menu);

//        menu_favotite = menu.findItem(R.id.menu_favotite);
//        menu_cancle_favotite = menu.findItem(R.id.menu_cancle_favotite);

//        Log.d("get_member_id","44444444");
        return true;
    }



//    private void setMenufavotiteEnabled(boolean enabled) {
//        menu_favotite.setVisible(enabled);
//    }
//    private void setMenucanclefavotiteEnabled(boolean enabled2) {
//        menu_cancle_favotite.setVisible(enabled2);
//    }










    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        //Icon +
//        if (id == R.id.menu_favotite) {
//            new InsertAsyn().execute("http://"+ConfigIP.IP+"/volunteer_project/insert_member_interest_event.php");
//            setMenufavotiteEnabled(false);
//            setMenucanclefavotiteEnabled(true);
//
//        }
//        if (id == R.id.menu_cancle_favotite) {
//            new DeleteAsynInterest().execute("http://"+ConfigIP.IP+"/volunteer_project/delete_member_interest.php");
//            setMenucanclefavotiteEnabled(false);
//            setMenufavotiteEnabled(true);
//        }
        if (id == R.id.menu_chat) {

            Intent intent = new Intent(DetailWork.this, MessageActivity.class);
            intent.putExtra("userid",event_member_id);
            startActivity(intent);
//            Intent i = new Intent(DetailWork.this, Message.class);
//            startActivity(i);
//            return true;
        }
        if (id == R.id.menu_report) {
            Intent intent = new Intent(DetailWork.this, ReportEvent.class);
            intent.putExtra("event_id_report",event_id);
            startActivity(intent);
            return true;
        }
        // back page
        else if (id == R.id.home) {
            System.out.print("Home id:"+id);
            Intent myIntent = new Intent(getApplicationContext(), Home.class);
            startActivity(myIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }




    // Insert Interest
    public class InsertAsyn extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            try{
                OkHttpClient _okHttpClient = new OkHttpClient();
                RequestBody _requestBody = new FormBody.Builder()
                        .add("interest_event_id",event_id )
                        .add("interest_member_id", member_id)
                        .build();

                okhttp3.Request request = new okhttp3.Request.Builder().url(strings[0]).post(_requestBody).build();
                _okHttpClient.newCall(request).execute();
                return "successfully";

            }catch(IOException e){
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            if (result != null){
                Toast.makeText(getApplicationContext(), "บักทึกในรายการที่สนใจเรียบ้อย "+event_id,Toast.LENGTH_LONG).show();
//                Intent intent = new Intent(DetailWork.this, InterestedList.class);
//
//                startActivity(intent);
            }else {
                Toast.makeText(getApplicationContext(), "ยกเลิกเรียบร้อย",Toast.LENGTH_LONG).show();
            }
        }
    }



    // Check Interest
    private void loadShowInterest(){
        final ProgressDialog progressDialog = new ProgressDialog(DetailWork.this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        String url = "http://203.154.83.137/newpond/volunteer_project/query_check_interest.php?member_id="
                +member_id+"&event_id="+event_id;
        Log.d("get_url",url);
        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                showJSONInterest(response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DetailWork.this.getApplicationContext(), "ไม่สามารถดึงข้อมูลได้ โปรดตรวจสอบการเชื่อมต่อ", Toast.LENGTH_SHORT).show();
            }
        }
        );
        RequestQueue requestQueue = Volley.newRequestQueue(DetailWork.this.getApplicationContext());
        requestQueue.add(stringRequest);
    }
    public void showJSONInterest(String response){
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray("result");

            if(result.length() !=0){
                for (int i = 0; i<result.length(); i++) {
                    JSONObject collectData = result.getJSONObject(i);
                    get_member_id = collectData.getString("member_id");
                    get_event_id = collectData.getString("event_id");

//                    setMenufavotiteEnabled(false);
//                    setMenucanclefavotiteEnabled(true);

                    Log.d("member_id", member_id);

                    Log.d("get_url2", "yessss");
                }

            }else {
//                setMenucanclefavotiteEnabled(false);
//                setMenufavotiteEnabled(true);
                Log.d("get_url2","noooooo");
            }
        }catch (JSONException ex) {
            Log.d("get_url2","noooooo");
//            setMenucanclefavotiteEnabled(false);
//            setMenufavotiteEnabled(true);
            ex.printStackTrace();
        }
//        try {
//            JSONObject jsonObject = new JSONObject(response);
//            JSONArray result = jsonObject.getJSONArray("result");
//
//            for (int i = 0; i<result.length(); i++){
//                JSONObject collectData = result.getJSONObject(i);
//                get_member_id = collectData.getString("member_id");
//                get_event_id = collectData.getString("event_id");
//
//                setMenufavotiteEnabled(false);
//                setMenucanclefavotiteEnabled(true);
//
//                Log.d("member_id",member_id);
//                Log.d("member_id1","'gggggggg");
//                Log.d("get_url2","yessss");
//
//            }
//        }catch (JSONException ex) {
//            Log.d("get_url2","noooooo");
////            setMenucanclefavotiteEnabled(false);
////            setMenufavotiteEnabled(true);
//        }
    }




    // check Join
    private void loadShowJoin(){
        final ProgressDialog progressDialog = new ProgressDialog(DetailWork.this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        String url = "http://"+ConfigIP.IP+"/volunteer_project/query_member_join_event.php?member_id="
        +member_id+"&event_id="+event_id;
        Log.d("get_url",url);
        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                showJSON(response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DetailWork.this.getApplicationContext(), "ไม่สามารถดึงข้อมูลได้ โปรดตรวจสอบการเชื่อมต่อ", Toast.LENGTH_SHORT).show();
            }
        }
        );
        RequestQueue requestQueue = Volley.newRequestQueue(DetailWork.this.getApplicationContext());
        requestQueue.add(stringRequest);
    }
    public void showJSON(String response){
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray("result");

            for (int i = 0; i<result.length(); i++){
                JSONObject collectData = result.getJSONObject(i);
                get_member_id = collectData.getString("member_id");
                btn_Click_register_work.setVisibility(View.GONE);
                btn_Click_cancle_work.setVisibility(View.VISIBLE);
            }
        }catch (JSONException ex) {
            btn_Click_register_work.setText("สมัคร");
        }
    }




    //Cancle join
    public class DeleteAsyn extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            try{
                OkHttpClient _okHttpClient = new OkHttpClient();
                RequestBody _requestBody = new FormBody.Builder()
                        .add("member_id",member_id)
                        .add("event_id",event_id)
                        .build();

                okhttp3.Request request = new okhttp3.Request.Builder().url(strings[0]).post(_requestBody).build();
                _okHttpClient.newCall(request).execute();
                return "successfully";

            }catch(IOException e){
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            if (result != null){
                Toast.makeText(getApplicationContext(), "ยกเลิกการเข้าร่วมเรียบร้อย",Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(getApplicationContext(), "การยกเลิกไม่สำเร็จ",Toast.LENGTH_SHORT).show();
            }
        }
    }


    //Cancle Interest
    public class DeleteAsynInterest extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            try{
                OkHttpClient _okHttpClient = new OkHttpClient();
                RequestBody _requestBody = new FormBody.Builder()
                        .add("member_id",get_member_id)
                        .add("event_id",get_event_id)
                        .build();

                okhttp3.Request request = new okhttp3.Request.Builder().url(strings[0]).post(_requestBody).build();
                _okHttpClient.newCall(request).execute();
                return "successfully";

            }catch(IOException e){
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            if (result != null){
                Toast.makeText(getApplicationContext(), "ยกเลิกสนใจเเรียบร้อย",Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(getApplicationContext(), "การยกเลิกไม่สำเร็จ",Toast.LENGTH_SHORT).show();
            }
        }
    }

}
