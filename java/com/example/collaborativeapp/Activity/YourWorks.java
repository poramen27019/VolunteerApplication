package com.example.collaborativeapp.Activity;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;

import android.content.SharedPreferences;
import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;

import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.collaborativeapp.Adapter.HomeWorkAdapter;
import com.example.collaborativeapp.Adapter.MyAdapter;
import com.example.collaborativeapp.Model.HomeWorkModel;
import com.example.collaborativeapp.Model.YourWorkModel;
import com.example.collaborativeapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class YourWorks extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DatabaseReference reference;
    private RecyclerView recyclerView;
    private ArrayList<YourWorkModel> list;
    private MyAdapter adapter;
    private FirebaseAuth firebaseAuth;
String event_id,eventname,event_member_id,event_date,event_type,event_org,event_detail,event_location,member_fname,member_phone,num2,event_sum;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_work);
        SharedPreferences shared = getSharedPreferences("Login", Context.MODE_PRIVATE);
        event_member_id = shared.getString("member_id", "");
Log.d("member_id_test",event_member_id);
        recyclerView = findViewById(R.id.myRecycler);
        recyclerView.setLayoutManager( new LinearLayoutManager(this));

        list = new ArrayList<>();
        loadShowHomeWork();

// back page
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void loadShowHomeWork(){
        final ProgressDialog progressDialog = new ProgressDialog(YourWorks.this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        String url = "http://"+ConfigIP.IP+"/volunteer_project/query_event_member_admin.php?member_id="+event_member_id;
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
                Toast.makeText(YourWorks.this.getApplicationContext(), "ไม่สามารถดึงข้อมูลได้ โปรดตรวจสอบการเชื่อมต่อ", Toast.LENGTH_SHORT).show();
            }
        }
        );
        RequestQueue requestQueue = Volley.newRequestQueue(YourWorks.this.getApplicationContext());
        requestQueue.add(stringRequest);
    }
    public void showJSON(String response){
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray("result");

            for (int i = 0; i<result.length(); i++){
                JSONObject collectData = result.getJSONObject(i);
                event_id = collectData.getString("event_id");
                eventname = collectData.getString("event_name");
                event_date = collectData.getString("event_date");
                event_type = collectData.getString("event_type");
                event_org = collectData.getString("event_org");
                event_detail = collectData.getString("event_detail");
                event_date = collectData.getString("event_date");
                event_location = collectData.getString("event_location");
                member_fname = collectData.getString("member_fname");
                member_phone = collectData.getString("member_phone");
                num2 = collectData.getString("num2");
                event_sum = collectData.getString("event_sum");

                SharedPreferences shared = getSharedPreferences("Yourevent", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = shared.edit();

                editor.putString("event_name",eventname);
                editor.putString("event_org",event_org);
                editor.commit();


                YourWorkModel item = new YourWorkModel(event_id,eventname,event_date,event_type,event_org,event_detail,event_location,member_fname,member_phone,num2,event_sum);
                list.add(item);

                //Log.d("item",item);

                // showsearch_listview.setOnItemClickListener(this);

            }


            MyAdapter adapter = new MyAdapter(YourWorks.this, list);
            recyclerView.setAdapter(adapter);

        }catch (JSONException ex) {
            ex.printStackTrace();
        }
    }


    //header icon
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        //Icon +
        if (id == R.id.menuAddWork) {
            Intent i = new Intent(YourWorks.this, AddWork.class);
            startActivity(i);
            return true;
        }

        // back page
        else if (id == R.id.home) {
            System.out.print("Home id:"+id);
            Intent myIntent = new Intent(getApplicationContext(), Home.class);
            startActivity(myIntent);
            return true;
        }

        //ไปผูกกับหน้า  Navigation
//        if (id == R.id.iconHamburger) {
//            Intent i = new Intent(YourWorks.this, AddWork.class);
//            startActivity(i);
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return false;
    }
}
