package com.example.collaborativeapp.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

public class EditMemberProfile extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    String member_id,member_fname,member_lname,member_phone,member_username,member_profile;
    private EditText txtmember_fname,txtmember_lname,txtmember_phone;
    private TextView txtmember_username;
    private ImageView img_profile ;
    Button btnProfile_update,btn_Browse_profile;

    private  static  final int PICX_IMAGE = 100;
    int PICK_IMAGE_REQUEST = 1;
    Bitmap bitmap;
    Uri filePath;

    FirebaseUser firebaseUser;
    DatabaseReference reference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_member_profile);


        // back page
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnProfile_update = (Button) findViewById(R.id.btnprofile_update);

        SharedPreferences shared = getSharedPreferences("Login", Context.MODE_PRIVATE);
        member_id = shared.getString("member_id", "");


        loadShowHomeWork();


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

//        ShowSelectedImage = (ImageView)findViewById(R.id.img_profile);
//        ShowSelectedImage.setLayerType(View.LAYER_TYPE_SOFTWARE, null);

//        btn_Browse_profile = findViewById(R.id.btn_Browse_profile);
//        btn_Browse_profile.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                openGallery();
//            }
//        });
//        btn_Browse_profile.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent();
//                intent.setType("image/*");
//                intent.setAction(Intent.ACTION_GET_CONTENT);
//                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
//            }
//        });


        btnProfile_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                member_fname = txtmember_fname.getText().toString();
                member_lname = txtmember_lname.getText().toString();
                member_phone = txtmember_phone.getText().toString();

                if (member_fname.isEmpty() || member_lname.isEmpty() || member_phone.isEmpty()){
                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(EditMemberProfile.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
                    builder.setCancelable(true);
                    builder.setMessage("กรุณากรอกข้อมูลให้ครบ");
                    builder.setNegativeButton("ตกลง", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

                    android.app.AlertDialog dialog = builder.create();
                    dialog.show();




                }
                else {
                    new InsertAsyn().execute("http://"+ConfigIP.IP+"/volunteer_project/update_member_profile.php");
//                    Toast.makeText(AddWork.this, "กรุณากรอกข้อมูลให้ครบ", Toast.LENGTH_LONG).show();

                }
            }
        });



    }

//    private void openGallery(){
//        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
//        startActivityForResult(gallery,PICX_IMAGE);
//    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
//
//            filePath = data.getData();
//            try {
//                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
//                ShowSelectedImage.setImageBitmap(bitmap);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }


    private void loadShowHomeWork(){
        final ProgressDialog progressDialog = new ProgressDialog(EditMemberProfile.this);
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
                Toast.makeText(EditMemberProfile.this.getApplicationContext(), "ไม่สามารถดึงข้อมูลได้ โปรดตรวจสอบการเชื่อมต่อ", Toast.LENGTH_SHORT).show();
            }
        }
        );
        RequestQueue requestQueue = Volley.newRequestQueue(EditMemberProfile.this.getApplicationContext());
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

//            txtmember_username = (TextView) findViewById(R.id.txtmember_username);
            txtmember_fname = (EditText) findViewById(R.id.txtmember_fname);
            txtmember_lname = (EditText) findViewById(R.id.txtmember_lname);
            txtmember_phone = (EditText) findViewById(R.id.txtmember_phone);

//            txtmember_username.setText(member_username);
            txtmember_fname.setText(member_fname);
            txtmember_lname.setText(member_lname);
            txtmember_phone.setText(member_phone);
//            SharedPreferences shared = getSharedPreferences("Login", Context.MODE_PRIVATE);
//            SharedPreferences.Editor editor = shared.edit();


//            editor.putString("member_fname",member_fname);
//            editor.putString("member_lname",member_lname);
//            editor.putString("member_phone",member_phone);


        } catch (JSONException e) {
            e.printStackTrace();
        }


    }


    public class InsertAsyn extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            try{
                OkHttpClient _okHttpClient = new OkHttpClient();
//                member_profile = getStringImage(bitmap);
                RequestBody _requestBody = new FormBody.Builder()
                        .add("member_fname", member_fname)
                        .add("member_lname", member_lname)
                        .add("member_id", member_id)
                        .add("member_phone", member_phone)
//                        .add("member_profile", member_profile)
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
                Toast.makeText(getApplicationContext(), "แก้ไข้อมูลเรียบร้อยแล้ว",Toast.LENGTH_SHORT).show();
                Intent i = new Intent(EditMemberProfile.this, UserMember.class);
                startActivity(i);
            }else {
                Toast.makeText(getApplicationContext(), "แก้ไขข้อมูลไม่สำเร็จ",Toast.LENGTH_SHORT).show();
            }
        }
    }


//    public String getStringImage(Bitmap bmp){
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
//        byte[] imageBytes = baos.toByteArray();
//        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
//        return encodedImage;
//    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement



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
