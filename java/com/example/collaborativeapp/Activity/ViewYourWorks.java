package com.example.collaborativeapp.Activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.collaborativeapp.Adapter.MyAdapter;
import com.example.collaborativeapp.Adapter.ViewYourWorkAdapter;
import com.example.collaborativeapp.Model.ViewYourWorkModel;
import com.example.collaborativeapp.Model.YourWorkModel;
import com.example.collaborativeapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ViewYourWorks extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private TextView txteventname,txtevent_location,txtevent_date,txtevent_type,txtevent_org,txtevent_detail,txtmember_admin_fname,txtmember_admin_phone;
    private String event_id,eventname,event_location,event_date,event_type,event_org,event_detail,member_id,member_fname,member_lname,member_phone;
    private String member_admin_fname,member_admin_phone;
    private RecyclerView recyclerView;
    private ArrayList<ViewYourWorkModel> list;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_your_work);
        this.setTitle("นิวบัง");
        loadShowHomeWork();
        txteventname = findViewById(R.id.txteventname);
        txtevent_location = findViewById(R.id.txtevent_location);
        txtevent_date = findViewById(R.id.txtevent_date);
        txtevent_type = findViewById(R.id.txtevent_type);
        txtevent_org = findViewById(R.id.txtevent_org);
        txtevent_detail = findViewById(R.id.txtevent_detail);
        txtmember_admin_fname = findViewById(R.id.txtmember_admin_fname);
        txtmember_admin_phone = findViewById(R.id.txtmember_admin_phone);

        event_id = getIntent().getStringExtra("event_id");
        member_admin_fname = getIntent().getStringExtra("member_fname");
        txtmember_admin_fname.setText(member_admin_fname);
        member_admin_phone = getIntent().getStringExtra("member_phone");
        txtmember_admin_phone.setText(member_admin_phone);


        eventname = getIntent().getStringExtra("eventname");
        this.setTitle(eventname);

        event_location = getIntent().getStringExtra("event_location");
        txtevent_location.setText(event_location);

        event_date = getIntent().getStringExtra("event_date");
        txtevent_date.setText(event_date);

        event_type = getIntent().getStringExtra("event_type");
        txtevent_type.setText(event_type);

        event_org = getIntent().getStringExtra("event_org");
        txtevent_org.setText(event_org);

        event_detail = getIntent().getStringExtra("event_detail");
        txtevent_detail.setText(event_detail);

        recyclerView = findViewById(R.id.member_join_list_recycler);
        recyclerView.setLayoutManager( new LinearLayoutManager(this));

        list = new ArrayList<>();
        loadShowHomeWork();

// back page
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }




    private void loadShowHomeWork(){
        final ProgressDialog progressDialog = new ProgressDialog(ViewYourWorks.this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        String url = "http://"+ConfigIP.IP+"/volunteer_project/query_member_list_join_event.php?event_id="+event_id;
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
                Toast.makeText(ViewYourWorks.this.getApplicationContext(), "ไม่สามารถดึงข้อมูลได้ โปรดตรวจสอบการเชื่อมต่อ", Toast.LENGTH_SHORT).show();
            }
        }
        );
        RequestQueue requestQueue = Volley.newRequestQueue(ViewYourWorks.this.getApplicationContext());
        requestQueue.add(stringRequest);
    }
    public void showJSON(String response){
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray("result");

            for (int i = 0; i<result.length(); i++){
                JSONObject collectData = result.getJSONObject(i);

                member_id = collectData.getString("member_id");
                member_fname = collectData.getString("member_fname");
                member_lname = collectData.getString("member_lname");
                member_phone = collectData.getString("member_phone");
                ViewYourWorkModel item = new ViewYourWorkModel(member_id,member_fname,member_lname,member_phone);

                list.add(item);

                //Log.d("item",item);

                // showsearch_listview.setOnItemClickListener(this);

            }


            ViewYourWorkAdapter adapter = new ViewYourWorkAdapter(ViewYourWorks.this, list);
            recyclerView.setAdapter(adapter);

        }catch (JSONException ex) {
            ex.printStackTrace();
        }
    }











    //header icon
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_item1, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


//        ไปผูกกับหน้า  Navigation
        if (id == R.id.menu_edit) {
            Intent intent = new Intent(ViewYourWorks.this, EditYourWorks.class);
            intent.putExtra("event_id", event_id);
            intent.putExtra("event_org", event_org);
            intent.putExtra("event_name", eventname);
            intent.putExtra("event_date", event_date);
            intent.putExtra("event_location", event_location);
            intent.putExtra("event_date", event_date);
            intent.putExtra("event_type", event_type);
            intent.putExtra("event_detail", event_detail);
            intent.putExtra("member_fname", member_fname);
            intent.putExtra("member_phone", member_phone);


            startActivity(intent);
            return true;
        }
        else if (id == R.id.menu_delete) {
            AlertDialog.Builder builder = new AlertDialog.Builder(ViewYourWorks.this, R.style.Theme_AppCompat_Light_Dialog_Alert);
            builder.setCancelable(true);
            builder.setMessage("คุณต้องการลบงานกิจกรรมนี้ใช่หรือไม่");
            builder.setPositiveButton("ใช่",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent myIntent = new Intent(getApplicationContext(), YourWorks.class);
                            startActivity(myIntent);
                            Toast.makeText(ViewYourWorks.this,"ลบสำเร็จ", Toast.LENGTH_LONG).show();




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
        }
        // back page
        else if (id == R.id.home) {
            System.out.print("Home id:"+id);
            Intent myIntent = new Intent(getApplicationContext(), YourWorks.class);
            startActivity(myIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return false;
    }
}
