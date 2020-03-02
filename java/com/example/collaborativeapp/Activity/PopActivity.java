package com.example.collaborativeapp.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.collaborativeapp.Constructor.UserProfile;
import com.example.collaborativeapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

public class PopActivity extends Activity {
    public FirebaseAuth firebaseAuth;
    private DatabaseReference reference;
    public DatabaseReference myref;

    TextView txtmember_fname,popNameWork,poplocation;
    String location,member_id,member_fname,username,password,member_fname_login;
    Button btn_confirm_join_work;
    String  event_id,eventname,member_phone,event_type,event_org,event_location,dateclose,event_detail;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop);

        SharedPreferences shared = getSharedPreferences("Login", Context.MODE_PRIVATE);
        username = shared.getString("username", "");
        password = shared.getString("pass", "");
        member_id = shared.getString("member_id", "");
        member_fname_login = shared.getString("member_fname", "");

        Log.d("member_id_pop",member_id);
        DisplayMetrics dn = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dn);
        int width = dn.widthPixels;
        int height = dn.heightPixels;
        getWindow().setLayout((int)(width*.8),(int)(height*.6));
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = -20;
        getWindow().setAttributes(params);


        event_id = getIntent().getStringExtra("event_id");
        eventname = getIntent().getStringExtra("eventname");
        member_fname = getIntent().getStringExtra("member_fname");
        member_phone = getIntent().getStringExtra("member_phone");
        event_type = getIntent().getStringExtra("event_type");
        event_org = getIntent().getStringExtra("event_org");
        event_location = getIntent().getStringExtra("event_location");
        dateclose = getIntent().getStringExtra("dateclose");
        event_detail = getIntent().getStringExtra("event_detail");


        popNameWork = findViewById(R.id.popNameWork);
        txtmember_fname = findViewById(R.id.txtmember_fname);
        poplocation = findViewById(R.id.poplocation);


        location = getIntent().getStringExtra("location");
        member_fname = getIntent().getStringExtra("member_fname");

        txtmember_fname.setText(member_fname_login);
        popNameWork.setText(eventname);
        poplocation.setText(location);

        btn_confirm_join_work = (Button) findViewById(R.id.btn_confirm_join_work);



        btn_confirm_join_work.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new InsertAsyn().execute("http://"+ConfigIP.IP+"/volunteer_project/insert_member_join_event.php?member_id=" );
//                Toast.makeText(getApplicationContext(), "สมัครสำเร็จ", Toast.LENGTH_SHORT).show();

            }
        });





    }

    public class InsertAsyn extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            try{
                OkHttpClient _okHttpClient = new OkHttpClient();
                RequestBody _requestBody = new FormBody.Builder()
                        .add("event_id", event_id)
                        .add("member_id", member_id)
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
                Toast.makeText(getApplicationContext(), "ท่านสมัครเรียบร้อยแล้ว",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(PopActivity.this, JoinEvent.class);
                intent.putExtra("member_id",member_id);
                intent.putExtra("event_id",event_id);
                intent.putExtra("eventname",eventname);
                intent.putExtra("event_location",event_location);
                intent.putExtra("event_type",event_type);
                intent.putExtra("event_org",event_org);
                intent.putExtra("event_detail",event_detail);
                intent.putExtra("member_fname",member_fname);


                startActivity(intent);
            }else {
                Toast.makeText(getApplicationContext(), "ยกเลิกการสมัครเรียบร้อย",Toast.LENGTH_LONG).show();
            }
        }
    }

}
