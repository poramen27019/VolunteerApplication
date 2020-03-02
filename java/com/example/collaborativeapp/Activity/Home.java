package com.example.collaborativeapp.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.collaborativeapp.Adapter.HomeWorkAdapter;
import com.example.collaborativeapp.Adapter.MyAdapter;
import com.example.collaborativeapp.Model.HomeWorkModel;
import com.example.collaborativeapp.Model.User;
import com.example.collaborativeapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;




//เพิ่ม  HomeWorkAdapter.OnItemClick
public class Home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {
    public TextView nav_header_name;

    NavigationView nav_view;

    private RecyclerView recyclerView;
    private ArrayList<HomeWorkModel> list;
    private HomeWorkAdapter adapter;
    public Button btnicon_star;
    private ImageView img_profile;
    String num,username,password,event_id,event_member_id,event_name,event_location,event_date_close,event_type,event_org,event_detail,member_fname,member_fname_login,member_phone,logo_event,event_sum;
    String member_id,interest_event_id,interest_member_id,get_member_id,logo_event_path,member_profile_path,member_img_profile;

    FirebaseUser firebaseUser;
    DatabaseReference reference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
        member_id = firebaseUser.getUid();
        Log.d("mmmmmmmid",member_id);

//        member_id ="NvKv0GVOfHaVF7dyUpo1lbXTmck2";


        SharedPreferences shared = getSharedPreferences("Login", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = shared.edit();
                        editor.putString("member_id",member_id);
                        editor.commit();






//        SharedPreferences shared2 = getSharedPreferences("Login", Context.MODE_PRIVATE);
//        username = shared.getString("username", "");
//        password = shared.getString("pass", "");
//        member_fname_login = shared.getString("member_fname_login", "");
////        member_id = shared.getString("member_id", "");
//        member_img_profile = shared.getString("member_profile", "");
//
//
//        SharedPreferences shared_profile = getSharedPreferences("member_profile", Context.MODE_PRIVATE);
//
//        member_profile_path = "http://"+ConfigIP.IP+"/volunteer_project/"+member_img_profile;



//        SharedPreferences shared2 = getSharedPreferences("Event_interest", Context.MODE_PRIVATE);
//        interest_event_id = shared2.getString("interest_event_id", "");
//
//        Check_interest();


        recyclerView = findViewById(R.id.HomeWorkRecycler);
        recyclerView.setLayoutManager( new LinearLayoutManager(this));
        list = new ArrayList<>();
        loadShowHomeWork();


Log.d("member_id",member_id);


        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
//                username.setText(user.getUsername());
                nav_view = findViewById(R.id.nav_view);
                View headerView = nav_view.getHeaderView(0); //เรียกใช้ header
                nav_header_name = headerView.findViewById(R.id.nav_header_name);
                nav_header_name.setText(user.getUsername());

                if (user.getImageURL().equals("default")){
                    img_profile.setImageResource(R.mipmap.ic_launcher);
                } else {

                    //change this
                    img_profile = headerView.findViewById(R.id.head_imageView);
//                    img_profile.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
                    Glide.with(getApplicationContext()).load(user.getImageURL()).into(img_profile);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




//        Picasso.get().load(member_profile_path).into(img_profile);

//        Log.d("pic",member_profile_path);

        //tab ข้างบน
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView =  findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

//
//    @Override
//    public void onClick(int position) {
//
//        HomeWorkModel click = list.get(position);
//        SharedPreferences shared = getSharedPreferences("Event_interest", Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = shared.edit();
//
//        editor.putString("interest_event_id",click.getevent_id());
//        interest_event_id = click.getevent_id();
//        editor.commit();
//        new InsertAsyn().execute("http://"+ConfigIP.IP+"/volunteer_project/insert_member_interest_event.php");
//
//    }
//

//    private void Check_interest(){
//        final ProgressDialog progressDialog = new ProgressDialog(Home.this);
//        progressDialog.setMessage("Loading...");
//        progressDialog.show();
//
//        String url = "http://"+ConfigIP.IP+"/volunteer_project/query_check_interest.php?member_id="
//                +member_id+"&event_id="+event_id;
//        Log.d("get_url",url);
//        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                progressDialog.dismiss();
//                showJSON2(response);
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(Home.this.getApplicationContext(), "ไม่สามารถดึงข้อมูลได้ โปรดตรวจสอบการเชื่อมต่อ", Toast.LENGTH_SHORT).show();
//            }
//        }
//        );
//        RequestQueue requestQueue = Volley.newRequestQueue(Home.this.getApplicationContext());
//        requestQueue.add(stringRequest);
//    }
//    public void showJSON2(String response){
//        try {
//            JSONObject jsonObject = new JSONObject(response);
//            JSONArray result = jsonObject.getJSONArray("result");
//
//            for (int i = 0; i<result.length(); i++){
//                JSONObject collectData = result.getJSONObject(i);
//                get_member_id = collectData.getString("member_id");
//
//                SharedPreferences shared = getSharedPreferences("check", Context.MODE_PRIVATE);
//                SharedPreferences.Editor editor = shared.edit();
//
//                editor.putString("get_member_id",get_member_id);
//
//                editor.commit();
//
////                btn_Click_register_work.setVisibility(View.GONE);
////                btn_Click_cancle_work.setVisibility(View.VISIBLE);
//            }
//        }catch (JSONException ex) {
//
//        }
//    }
//






//
//    // Insert Interest
//    public class InsertAsyn extends AsyncTask<String, Void, String> {
//        @Override
//        protected String doInBackground(String... strings) {
//            try{
//                OkHttpClient _okHttpClient = new OkHttpClient();
//                RequestBody _requestBody = new FormBody.Builder()
//                        .add("interest_event_id",interest_event_id )
//                        .add("interest_member_id", member_id)
//                        .build();
//
//                okhttp3.Request request = new okhttp3.Request.Builder().url(strings[0]).post(_requestBody).build();
//                _okHttpClient.newCall(request).execute();
//                return "successfully";
//
//            }catch(IOException e){
//                e.printStackTrace();
//            }
//            return null;
//        }
//        @Override
//        protected void onPostExecute(String result) {
//            super.onPostExecute(result);
//
//            if (result != null){
//                Toast.makeText(getApplicationContext(), "เลือก "+interest_event_id,Toast.LENGTH_LONG).show();
//                Intent intent = new Intent(Home.this, InterestedList.class);
//
//                startActivity(intent);
//            }else {
//                Toast.makeText(getApplicationContext(), "ยกเลิกการสมัครเรียบร้อย",Toast.LENGTH_LONG).show();
//            }
//        }
//    }





    //Show in cardview
    private void loadShowHomeWork(){
        final ProgressDialog progressDialog = new ProgressDialog(Home.this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        String url = "http://"+ConfigIP.IP+"/volunteer_project/query_event_all.php";
//        String url = "http://203.154.83.137/newpond/volunteer_project/query_event_all.php";
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
                Toast.makeText(Home.this.getApplicationContext(), "ไม่สามารถดึงข้อมูลได้ โปรดตรวจสอบการเชื่อมต่อ", Toast.LENGTH_SHORT).show();
            }
        }
        );
        RequestQueue requestQueue = Volley.newRequestQueue(Home.this.getApplicationContext());
        requestQueue.add(stringRequest);
    }
    public void showJSON(String response){
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray("result");
            for (int i = 0; i<result.length(); i++){
                JSONObject collectData = result.getJSONObject(i);

                event_id = collectData.getString("event_id");
                event_member_id = collectData.getString("event_member_id");
                event_name = collectData.getString("event_name");
                event_location = collectData.getString("event_location");
                event_date_close = collectData.getString("event_date_close");
                event_type = collectData.getString("event_type");
                event_org = collectData.getString("event_org");
                event_detail = collectData.getString("event_detail");
                member_fname = collectData.getString("member_fname");
                member_phone = collectData.getString("member_phone");
                num = collectData.getString("num");
                event_sum = collectData.getString("event_sum");
                logo_event = collectData.getString("event_logo");
                logo_event_path = "http://"+ConfigIP.IP+"/volunteer_project/"+logo_event;
//                logo_event_path = "http://203.154.83.137/newpond/volunteer_project/"+logo_event;


                Log.d("logo_event",logo_event_path);


                HomeWorkModel item = new HomeWorkModel(event_id,event_member_id,event_name,event_location,event_date_close,event_type,event_org,event_detail,member_fname,member_phone,num,event_sum,logo_event_path);
                list.add(item);

            }
            HomeWorkAdapter adapter = new HomeWorkAdapter(Home.this, list);
            recyclerView.setAdapter(adapter);
//            adapter.setOnClick(Home.this);

        }catch (JSONException ex) {
            ex.printStackTrace();
        }
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.head_search, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menuHome) {
            Intent i = new Intent(Home.this, MapActivity.class);
            startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
            Intent i = new Intent(Home.this,MapEventActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_list) {
            Intent i = new Intent(Home.this,JoinEvent.class);
            startActivity(i);
        } else if (id == R.id.nav_profile) {
            Intent i = new Intent(Home.this, UserMember.class);
            startActivity(i);
//        } else if (id == R.id.nav_manage) {
        } else if (id == R.id.nav_logout) {


                AlertDialog.Builder builder = new AlertDialog.Builder(Home.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
                builder.setCancelable(true);
                builder.setMessage("คุณต้องการออกระบบใช่หรือไม่");
                builder.setPositiveButton("ใช่",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                SharedPreferences shared = getSharedPreferences("Login", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = shared.edit();
                                editor.clear();
                                editor.commit();
                                Intent intent = new Intent(Home.this, Login.class);
                                startActivity(intent);
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

        } else if (id == R.id.nav_send) {

            Intent i = new Intent(Home.this, ChatHome.class);
            startActivity(i);
        } else if (id == R.id.nav_interesting) {
            Intent i = new Intent(Home.this, InterestedList.class);
            startActivity(i);

        }else if (id == R.id.nav_add_work) {
            Intent i = new Intent(Home.this, YourWorks.class);
            startActivity(i);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}

