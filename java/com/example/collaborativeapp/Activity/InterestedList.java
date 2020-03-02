package com.example.collaborativeapp.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.example.collaborativeapp.Adapter.InterestAdapter;
import com.example.collaborativeapp.Adapter.JoinEventAdapter;
import com.example.collaborativeapp.Model.InterestModel;
import com.example.collaborativeapp.Model.JoinEventModel;
import com.example.collaborativeapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class InterestedList extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private RecyclerView recyclerView;
    private ArrayList<InterestModel> list;

    String member_id,interest_event_id,logo_event;
    String  member_fname,event_name,member_phone,event_type,event_org,event_location,event_date,event_detail,logo_event_path;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interested_list);
        // back page
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        SharedPreferences shared = getSharedPreferences("Login", Context.MODE_PRIVATE);

        member_id = shared.getString("member_id", "");



        recyclerView = findViewById(R.id.interest_event_recycler);
        recyclerView.setLayoutManager( new LinearLayoutManager(this));
        list = new ArrayList<>();
        loadShowHomeWork();



    }




    private void loadShowHomeWork(){
        final ProgressDialog progressDialog = new ProgressDialog(InterestedList.this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        String url = "http://"+ConfigIP.IP+"/volunteer_project/query_interest_adapter.php?member_id="+member_id;
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
                Toast.makeText(InterestedList.this.getApplicationContext(), "ไม่สามารถดึงข้อมูลได้ โปรดตรวจสอบการเชื่อมต่อ", Toast.LENGTH_SHORT).show();
            }
        }
        );
        RequestQueue requestQueue = Volley.newRequestQueue(InterestedList.this.getApplicationContext());
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
                interest_event_id = collectData.getString("event_id");
                event_location = collectData.getString("event_location");
                event_date= collectData.getString("event_date");
                event_type = collectData.getString("event_type");
                event_org = collectData.getString("event_org");
                event_detail = collectData.getString("event_detail");
                member_fname = collectData.getString("member_fname");
                member_phone = collectData.getString("member_phone");
                logo_event = collectData.getString("event_logo");
                logo_event_path = "http://"+ConfigIP.IP+"/volunteer_project/"+logo_event;

                InterestModel item = new InterestModel(event_name,interest_event_id,event_location,event_date,event_type,event_org,event_detail,member_fname,member_phone,logo_event_path);
                list.add(item);



            }


            InterestAdapter adapter = new InterestAdapter(InterestedList.this, list);
            recyclerView.setAdapter(adapter);

        }catch (JSONException ex) {
            ex.printStackTrace();
        }
    }






















    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        //Icon +
//        if (id == R.id.menuAddWork) {
//            Intent i = new Intent(InterestedList.this, AddWork.class);
//            startActivity(i);
//            return true;
//        }

        // back page
        if (id == R.id.home) {
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
