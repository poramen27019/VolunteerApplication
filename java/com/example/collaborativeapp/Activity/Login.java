package com.example.collaborativeapp.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.collaborativeapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Login extends AppCompatActivity {

    private EditText txtUsername_login,txtPassword_login;
    private FirebaseAuth firebaseAuth;
    Button btnUserLogin;

//    private static final int REFRESH_SCREEN = 1;
//    String member_username,member_password,url;
//    TextView txtUsername,txtPassword,linkregis;


    EditText email, password;
    Button btn_login;

    FirebaseAuth auth;
    TextView forgot_password;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setTitle("Login");
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        auth = FirebaseAuth.getInstance();

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        btn_login = findViewById(R.id.btnUserLogin);
//        forgot_password = findViewById(R.id.forgot_password);

//        forgot_password.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(LoginActivity.this, ResetPasswordActivity.class));
//            }
//        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txt_email = email.getText().toString();
                String txt_password = password.getText().toString();

                if (TextUtils.isEmpty(txt_email) || TextUtils.isEmpty(txt_password)){
                    Toast.makeText(Login.this, "All fileds are required", Toast.LENGTH_SHORT).show();
                } else {

                    auth.signInWithEmailAndPassword(txt_email, txt_password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()){
                                        Intent intent = new Intent(Login.this, Home.class);

                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Toast.makeText(Login.this, "Authentication failed!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });
    }
}


//        txtUsername_login = (EditText) findViewById(R.id.txtUsername_login);
//        txtPassword_login = (EditText) findViewById(R.id.txtPassword_login);
//        btnUserLogin = (Button) findViewById(R.id.btnUserLogin);
//        FirebaseApp.initializeApp(this);
//      firebaseAuth = FirebaseAuth.getInstance();

























//        btnUserLogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                member_username = txtUsername_login.getText().toString();
//                member_password = txtPassword_login.getText().toString();
//
//
//                if (member_username.isEmpty() || member_password.isEmpty()) {
//                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Login.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
//                    builder.setCancelable(true);
//                    builder.setMessage("กรุณากรอกข้อมูลให้ครบ");
//                    builder.setNegativeButton("ตกลง", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.cancel();
//                        }
//                    });
//
//                    android.app.AlertDialog dialog = builder.create();
//                    dialog.show();
//
//
//                } else {
//                    //ไปทำ funtion Login
//                    login(member_username, member_password);
//
//                }
//
//
//            }
//        });
//
//
//    }

//        public void login(final String username, final String password){url = "http://"+ConfigIP.IP+"/volunteer_project/login.php?member_username="+username+"&member_password="+password;
//
//            RequestQueue requestQueue = Volley.newRequestQueue(Login.this);
//            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
//                @Override
//                public void onResponse(String response) {
//                    try {
//                        JSONObject jsonObject = new JSONObject(response);
//                        JSONArray jsonArray = jsonObject.getJSONArray("result");
//                        JSONObject jsonObject1 = jsonArray.getJSONObject(0);
//
//
//                        String member_id = jsonObject1.getString("member_id");
//                        String user = jsonObject1.getString("member_username");
//                        String pass = jsonObject1.getString("member_password");
//                        String member_fname = jsonObject1.getString("member_fname");
//                        String member_profile = jsonObject1.getString("member_profile");
//
//                        SharedPreferences shared = getSharedPreferences("Login", Context.MODE_PRIVATE);
//                        SharedPreferences.Editor editor = shared.edit();
//
//                        editor.putString("pass",pass);
//                        editor.putString("username",user);
//                        editor.putString("member_id",member_id);
//                        editor.putString("member_fname",member_fname);
//                        editor.putString("member_profile",member_profile);
//                        editor.commit();
//
//                        final ProgressDialog progressDialog = ProgressDialog.show(Login.this, "กำลังเข้าสู่ระบบ...", "โปรดรอซักครู่...", true);
//                        Intent intent = new Intent(Login.this, Home.class);
//                        startActivity(intent);
//                        Toast.makeText(Login.this, "ยินดีต้อนรับ! "+username, Toast.LENGTH_LONG).show();
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                        Toast.makeText(Login.this, "รหัสผ่านไม่ถูกต้อง ", Toast.LENGTH_LONG).show();
//                    }
//
//                }
//            }, new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                    Log.i("HiteshURLerror",""+error);
//                }
//            });
//            requestQueue.add(stringRequest);
//        }
































//    public void btnUserLogin_Click(View v) {
//        final ProgressDialog progressDialog = ProgressDialog.show(Login.this, "กำลังเข้าสู่ระบบ...", "โปรดรอซักครู่...", true);
//        Intent i = new Intent(Login.this,Home.class);
//        startActivity(i);
//        (firebaseAuth.signInWithEmailAndPassword(txtEmailLogin.getText().toString(), txtPwd.getText().toString()))
//                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        progressDialog.dismiss();
//                        if (task.isSuccessful()) {
//                            Toast.makeText(Login.this, "เข้าสู่ระบบเรียบร้อย", Toast.LENGTH_LONG).show();
//                            Intent i = new Intent(Login.this, Home.class);
//                            i.putExtra("Email", firebaseAuth.getCurrentUser().getEmail());
//                            startActivity(i);
//                        } else {
//                            Log.e("ERROR", task.getException().toString());
//                            Toast.makeText(Login.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
//                        }
//                    }
//                });
//    }


