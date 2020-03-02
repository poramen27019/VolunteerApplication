package com.example.collaborativeapp.Activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.collaborativeapp.Constructor.UserProfile;
import com.example.collaborativeapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;


public class Register extends AppCompatActivity {

//    private EditText txtfname,txtlname,txtusername,txtpassword,txtphone;
//    private Button btnRegisterUser,btncancel;
//    String fname,lname,username,password,phone ;


    EditText username, email, password;
    Button btn_register;
    String txt_username,txt_email,txt_password,userid,token;

    FirebaseAuth auth;
    DatabaseReference reference;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.register);



        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        btn_register = findViewById(R.id.btn_register);

        auth = FirebaseAuth.getInstance();

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 txt_username = username.getText().toString();
                 txt_email = email.getText().toString();
                 txt_password = password.getText().toString();

                if (TextUtils.isEmpty(txt_username) || TextUtils.isEmpty(txt_email) || TextUtils.isEmpty(txt_password)){
                    Toast.makeText(Register.this, "All fileds are required", Toast.LENGTH_SHORT).show();
                } else if (txt_password.length() < 6 ){
                    Toast.makeText(Register.this, "password must be at least 6 characters", Toast.LENGTH_SHORT).show();
                } else {
                    register(txt_username, txt_email, txt_password);
                }
            }
        });
    }

    private void register(final String username, String email, String password){

        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            FirebaseUser firebaseUser = auth.getCurrentUser();
                            assert firebaseUser != null;

                             userid = firebaseUser.getUid();
                             token = FirebaseInstanceId.getInstance().getToken();




                            Log.d("userifd",userid);
                            reference = FirebaseDatabase.getInstance().getReference("Users").child(userid);

                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put("id", userid);
                            hashMap.put("username", username);
                            hashMap.put("imageURL", "default");
                            hashMap.put("status", "offline");
                            hashMap.put("search", username.toLowerCase());



                            reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        new InsertAsyn().execute("http://"+ConfigIP.IP+"/volunteer_project/register_member.php?");
                                        Intent intent = new Intent(Register.this, Login.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            });
                        } else {
                            Toast.makeText(Register.this, "You can't register woth this email or password", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public class InsertAsyn extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            try{
                OkHttpClient _okHttpClient = new OkHttpClient();
                RequestBody _requestBody = new FormBody.Builder()

                        .add("userid",userid)
                        .add("email",txt_email)
//                        .add("lname",lname)
                        .add("username",txt_username)
                        .add("password",txt_password)
//                        .add("phone",phone)
                        .build();
                Log.d("useriddd",userid);

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
                Toast.makeText(getApplicationContext(), "สมัครสมาชิกเรียบร้อยแล้ว",Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(Register.this, Login.class);
//                startActivity(intent);
            }else {
                Toast.makeText(getApplicationContext(), "สมัครสมาชิกไม่สำเร็จ",Toast.LENGTH_SHORT).show();
            }
        }
    }


}





//        txtfname = (EditText) findViewById(R.id.txtfname);
//        txtlname = (EditText) findViewById(R.id.txtlname);
//        txtusername = (EditText) findViewById(R.id.txtusername);
//        txtpassword = (EditText) findViewById(R.id.txtpassword);
//        txtphone = (EditText) findViewById(R.id.txtphone);
//        btnRegisterUser = findViewById(R.id.btnRegisterUser);
//
//        btnRegisterUser.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                fname = txtfname.getText().toString();
//                lname = txtlname.getText().toString();
//                username = txtusername.getText().toString();
//                password = txtpassword.getText().toString();
//                phone = txtphone.getText().toString();
//
//                if (fname.isEmpty() || lname.isEmpty() || username.isEmpty() || password.isEmpty() ||
//                        phone.isEmpty()  ){
//                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Register.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
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
//                }
//                else {
//                    new InsertAsyn().execute("http://"+ConfigIP.IP+"/volunteer_project/register_member.php?");
////                    Toast.makeText(AddWork.this, "กรุณากรอกข้อมูลให้ครบ", Toast.LENGTH_LONG).show();
//
//
//                }
//
//            }
//        });
//    }



//    public class InsertAsyn extends AsyncTask<String, Void, String> {
//        @Override
//        protected String doInBackground(String... strings) {
//            try{
//                OkHttpClient _okHttpClient = new OkHttpClient();
//                RequestBody _requestBody = new FormBody.Builder()
//
//                        .add("fname",fname)
//                        .add("lname",lname)
//                        .add("username",username)
//                        .add("password",password)
//                        .add("phone",phone)
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
//                Toast.makeText(getApplicationContext(), "สมัครสมาชิกเรียบร้อยแล้ว",Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(Register.this, Login.class);
//                startActivity(intent);
//            }else {
//                Toast.makeText(getApplicationContext(), "สมัครสมาชิกไม่สำเร็จ",Toast.LENGTH_SHORT).show();
//            }
//        }
