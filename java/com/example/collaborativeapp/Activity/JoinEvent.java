package com.example.collaborativeapp.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.collaborativeapp.Adapter.HomeWorkAdapter;
import com.example.collaborativeapp.Adapter.JoinEventAdapter;
import com.example.collaborativeapp.Adapter.MyAdapter;
import com.example.collaborativeapp.Model.HomeWorkModel;
import com.example.collaborativeapp.Model.JoinEventModel;
import com.example.collaborativeapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JoinEvent  extends AppCompatActivity  {
    private RecyclerView recyclerView;
    private ArrayList<JoinEventModel> list;
    private JoinEventAdapter adapter;

    String username,password,member_fname_login,event_id,event_name,member_id,join_event_id;
    String  member_fname,eventname,member_phone,event_type,event_org,event_location,event_date_close,event_detail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_event);


        SharedPreferences shared = getSharedPreferences("Login", Context.MODE_PRIVATE);
        username = shared.getString("username", "");
        password = shared.getString("pass", "");
        member_id = shared.getString("member_id", "");


//        event_id = getIntent().getStringExtra("event_id");
//        event_name = getIntent().getStringExtra("eventname");
//        member_fname = getIntent().getStringExtra("member_fname");
//        member_phone = getIntent().getStringExtra("member_phone");
//        event_type = getIntent().getStringExtra("event_type");
//        event_org = getIntent().getStringExtra("event_org");
//        event_location = getIntent().getStringExtra("event_location");
//        dateclose = getIntent().getStringExtra("dateclose");
//        event_detail = getIntent().getStringExtra("event_detail");

        recyclerView = findViewById(R.id.join_event_recycler);
        recyclerView.setLayoutManager( new LinearLayoutManager(this));
        list = new ArrayList<>();
        loadShowHomeWork();
}

    private void loadShowHomeWork(){
        final ProgressDialog progressDialog = new ProgressDialog(JoinEvent.this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        String url = "http://"+ConfigIP.IP+"/volunteer_project/query_join_list_adapter.php?member_id="+member_id;
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
                Toast.makeText(JoinEvent.this.getApplicationContext(), "ไม่สามารถดึงข้อมูลได้ โปรดตรวจสอบการเชื่อมต่อ", Toast.LENGTH_SHORT).show();
            }
        }
        );
        RequestQueue requestQueue = Volley.newRequestQueue(JoinEvent.this.getApplicationContext());
        requestQueue.add(stringRequest);
    }
    public void showJSON(String response){
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray("result");

            for (int i = 0; i<result.length(); i++){
                JSONObject collectData = result.getJSONObject(i);
//                event_join_id = collectData.getString("event_id");
//                member_id = collectData.getString("member_id");
                event_name = collectData.getString("event_name");
                join_event_id = collectData.getString("event_id");
                event_location = collectData.getString("event_location");
                event_date_close = collectData.getString("event_date_close");
                event_type = collectData.getString("event_type");
                event_org = collectData.getString("event_org");
                event_detail = collectData.getString("event_detail");
                member_fname = collectData.getString("member_fname");
                member_phone = collectData.getString("member_phone");
                JoinEventModel item = new JoinEventModel(
                        event_name,
                        join_event_id,event_location,event_date_close,event_type,event_org,event_detail,member_fname,member_phone
                        );
                list.add(item);



            }


            JoinEventAdapter adapter = new JoinEventAdapter(JoinEvent.this, list);
            recyclerView.setAdapter(adapter);
        }catch (JSONException ex) {
            ex.printStackTrace();
        }
    }
}
