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
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
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

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

public class UserMember extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
String member_id,member_fname,member_lname,member_phone,member_username,member_profile,member_profile_path;
    private TextView txtmember_fname,txtmember_lname,txtmember_phone;
    private TextView txtmember_username;
    private ImageView img_profile;
    Button btnProfile_update;

    FirebaseUser firebaseUser;
    DatabaseReference reference;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                if (user.getImageURL().equals("default")){
                    img_profile.setImageResource(R.mipmap.ic_launcher);
                } else {

                    //change this
                    img_profile = findViewById(R.id.img_profile);
                    Glide.with(getApplicationContext()).load(user.getImageURL()).into(img_profile);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        // back page
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnProfile_update = (Button) findViewById(R.id.btnprofile_update);

        SharedPreferences shared = getSharedPreferences("Login", Context.MODE_PRIVATE);
        member_id = shared.getString("member_id", "");
        Log.d("member_id_user",member_id);





        loadShowHomeWork();

    }

    private void loadShowHomeWork(){
        final ProgressDialog progressDialog = new ProgressDialog(UserMember.this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        String url = "http://"+ConfigIP.IP+"/volunteer_project/query_member_profile.php?member_id="+member_id;
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
                Toast.makeText(UserMember.this.getApplicationContext(), "ไม่สามารถดึงข้อมูลได้ โปรดตรวจสอบการเชื่อมต่อ", Toast.LENGTH_SHORT).show();
            }
        }
        );
        RequestQueue requestQueue = Volley.newRequestQueue(UserMember.this.getApplicationContext());
        requestQueue.add(stringRequest);
    }
    public void showJSON(String response){
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("result");
            JSONObject jsonObject1 = jsonArray.getJSONObject(0);

            member_username = jsonObject1.getString("member_username");
             member_fname = jsonObject1.getString("member_fname");
             member_lname = jsonObject1.getString("member_lname");
             member_phone = jsonObject1.getString("member_phone");


            txtmember_username = (TextView) findViewById(R.id.txtmember_username);
            txtmember_fname = (TextView) findViewById(R.id.txtmember_fname);
            txtmember_lname = (TextView) findViewById(R.id.txtmember_lname);
            txtmember_phone = (TextView) findViewById(R.id.txtmember_phone);



            txtmember_username.setText(member_username);
            txtmember_fname.setText(member_fname);
            txtmember_lname.setText(member_lname);
            txtmember_phone.setText(member_phone);

            } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //header icon
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_profile, menu);
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
        if (id == R.id.menu_edit_profile) {
            Intent i = new Intent(UserMember.this, EditMemberProfile.class);
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