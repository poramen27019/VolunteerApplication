package com.example.collaborativeapp.Activity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;

import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;


import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.collaborativeapp.Constructor.WorkProfile;
import com.example.collaborativeapp.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

public class AddWork extends AppCompatActivity {

    private ImageView img_date_close,ShowSelectedImage ;
    private ImageView select_location;

    private TextView lat,lnt;

    private EditText txtOrgAddWork,txtNameAddWork,txteventadd_date,txtDetailAddWork,txtLocationAddWork,txtSumAddWork;
    private Spinner spinnerTypeWork;
    String event_org,event_name,event_detail,event_date,event_location,event_type,event_logo,event_sum;
    String url,m_id;
    Button btn_add_event,btnBrowse;
    String username,password,event_member_id;
    String finalresult1,finalresult2;
    Double lat2,lnt2;

    //Dropdown
    private ArrayList<String> Typework = new ArrayList<String>();
    private  static  final int PICX_IMAGE = 100;
    Uri imageUri;
    //date
    Calendar calendar;
    DatePickerDialog DatePickerDialog;


    int PICK_IMAGE_REQUEST = 1;
    Bitmap bitmap;
    Uri filePath;





    @SuppressLint("WrongViewCast")
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_work);

        SharedPreferences shared = getSharedPreferences("Login", Context.MODE_PRIVATE);
        username = shared.getString("username", "");
        password = shared.getString("pass", "");
        event_member_id = shared.getString("member_id", "");

        btn_add_event = findViewById(R.id.btn_add_event);
        btnBrowse = findViewById(R.id.btnBrowse);
        btnBrowse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });
//        ShowSelectedImage = (CircleImageView)findViewById(R.id.imageView);
        ShowSelectedImage = findViewById(R.id.imageView_addevent);

        select_location = findViewById(R.id.select_location);

        select_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddWork.this, MapEventActivity.class);
                startActivity(intent);
            }
        });

        lat2 = getIntent().getDoubleExtra("lat2",0.00);
        lnt2 = getIntent().getDoubleExtra("lnt2",0.00);



        Log.d("gettttlat3",lat2+"---"+lat2);

        lat = findViewById(R.id.latitude);
        lnt = findViewById(R.id.longitude);

        finalresult1 = new Double(lat2).toString();
        finalresult2 = new Double(lnt2).toString();

        lat.setText(finalresult1);
        lnt.setText(finalresult2);


        //Dropdown
        spinnerTypeWork = findViewById(R.id.spinnerTypeWork);
        createTypeworkData();
        ArrayAdapter<String> adapterThai = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, Typework);
        spinnerTypeWork.setAdapter(adapterThai);

        //date
        txteventadd_date = findViewById(R.id.txteventadd_date);
        img_date_close = findViewById(R.id.img_date_close);
        img_date_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               final Calendar c = Calendar.getInstance();
                int mDay = c.get(Calendar.DAY_OF_MONTH);
                int mMonth = c.get(Calendar.MONTH);
                int mYear = c.get(Calendar.YEAR);

//                LocalDate now = LocalDate.now(); //2015-11-23
//                LocalDate lastDay = now.with(TemporalAdjusters.lastDayOfMonth()); //2015-11-30


                DatePickerDialog datePickerDialog = new DatePickerDialog(AddWork.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        txteventadd_date.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                    }
                },mYear,mMonth,mDay);
                datePickerDialog.show();
            }
        });


//                DatePickerDialog datePickerDialog = new DatePickerDialog(AddPetActivity.this,new DatePickerDialog.OnDateSetListener() {
//                            @Override
//                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//                                txtBirthday.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
//                            }
//                        }, mYear, mMonth, mDay);
//                datePickerDialog.show();
//            }
//        });









        txtOrgAddWork = findViewById(R.id.txteventadd_org);
        txtNameAddWork= findViewById(R.id.txteventadd_name);
        txtDetailAddWork = findViewById(R.id.txteventadd_detail);
        txtLocationAddWork= findViewById(R.id.txteventadd_location);
        txtSumAddWork= findViewById(R.id.txteventadd_sum);
//        firebaseAuth = FirebaseAuth.getInstance();


        btnBrowse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
            }
        });


        btn_add_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                event_org = txtOrgAddWork.getText().toString();
                event_name = txtNameAddWork.getText().toString();
                event_detail = txtDetailAddWork.getText().toString();
                event_location = txtLocationAddWork.getText().toString();
                event_type = spinnerTypeWork.getSelectedItem().toString();
                event_date = txteventadd_date.getText().toString();
                event_sum = txtSumAddWork.getText().toString();

                if (event_org.isEmpty() || event_name.isEmpty() || event_detail.isEmpty() || event_date.isEmpty() ||
                        event_location.isEmpty() || event_type.isEmpty() || event_type.isEmpty() || event_sum.isEmpty()  ){
                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(AddWork.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
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
                    new InsertAsyn().execute("http://"+ConfigIP.IP+"/volunteer_project/insert_event.php?" );
//                    Toast.makeText(AddWork.this, "กรุณากรอกข้อมูลให้ครบ", Toast.LENGTH_LONG).show();
                }

            }
        });

    }

private void openGallery(){
        Intent gallery = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery,PICX_IMAGE);
}
//@Override
//protected void  onActivityResult(int requestCode,int resultCode,Intent data){
//    super.onActivityResult(requestCode,resultCode,data);
//    if(resultCode == RESULT_OK && requestCode == PICX_IMAGE){
//        imageUri = data.getData();
//        event_logo.setImageURI(imageUri);
//    }
//}

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                ShowSelectedImage.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public class InsertAsyn extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            try{
                OkHttpClient _okHttpClient = new OkHttpClient();
                event_logo = getStringImage(bitmap);
                RequestBody _requestBody = new FormBody.Builder()

                        .add("event_org", event_org)
                        .add("event_name", event_name)
                        .add("event_detail", event_detail)
                        .add("event_date", event_date)
                        .add("event_location", event_location)
                        .add("event_type", event_type)
                        .add("event_member_id",event_member_id)
                        .add("event_logo", event_logo)
                        .add("event_sum", event_sum)
                        .add("event_lat", finalresult1)
                        .add("event_lnt", finalresult2)
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
                Toast.makeText(getApplicationContext(), "บันทึกข้อมูลเรียบร้อยแล้ว",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AddWork.this, YourWorks.class);
                startActivity(intent);
            }else {
                Toast.makeText(getApplicationContext(), "ไม่สามารถบันทึกข้อมูลได้",Toast.LENGTH_SHORT).show();
            }
        }
    }

    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }



    //Dropdown
    private void createTypeworkData(){
        Typework.add("จิตอาสา");
        Typework.add("กิจกรรม");
//        Typework.add("อนุรักษณ์ท่องเที่ยว");

    }

    }




