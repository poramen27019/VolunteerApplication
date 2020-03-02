package com.example.collaborativeapp.Activity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.collaborativeapp.Adapter.MyAdapter;
import com.example.collaborativeapp.Model.YourWorkModel;
import com.example.collaborativeapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

public class EditYourWorks extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private Spinner edit_spinnerTypeWork;
    private ImageView img_date_close , select_location_edit; ;
    private EditText txteventadd_date;
    private Spinner txteventedit_type;
    private EditText txtedit_event_org ,txteventedit_name,txteventadd_detail,txteventedit_date,txteventedit_location,txteventadd_phone;
    //Dropdown
    private ArrayList<String> edit_Typework = new ArrayList<String>();
    private  static  final int PICX_IMAGE = 100;


    Uri imageUri;
    String event_id,event_name,member_id,event_date,event_type,event_org,event_detail,event_location,member_fname,member_phone;
    //date
    Calendar calendar;
    DatePickerDialog datePickerDialog;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_your_work);
        SharedPreferences shared = getSharedPreferences("Login", Context.MODE_PRIVATE);
        member_id = shared.getString("member_id", "");

//        SharedPreferences shared2 = getSharedPreferences("Yourevent", Context.MODE_PRIVATE);
//        event_name = shared2.getString("event_name", "");
//        final String event_org = shared2.getString("event_org", "");

        // back page
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        select_location_edit = findViewById(R.id.select_location_edit);
        select_location_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditYourWorks.this, MapEventActivity.class);
                startActivity(intent);
            }
        });




        //Dropdown
        edit_spinnerTypeWork = findViewById(R.id.txtevent_type);
        createTypeworkData();
        ArrayAdapter<String> adapterThai = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, edit_Typework);
        edit_spinnerTypeWork.setAdapter(adapterThai);

        //date
        txteventadd_date = findViewById(R.id.txteventadd_date);
        img_date_close = findViewById(R.id.img_date_close);
        img_date_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                calendar=Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                datePickerDialog = new DatePickerDialog(EditYourWorks.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        txteventadd_date.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                },day,month,year);
                datePickerDialog.show();
            }
        });


        event_id = getIntent().getStringExtra("event_id");
        event_org = getIntent().getStringExtra("event_org");
        event_name = getIntent().getStringExtra("event_name");
        event_location = getIntent().getStringExtra("event_location");
        event_date = getIntent().getStringExtra("event_date");
        event_type = getIntent().getStringExtra("event_type");
        event_detail = getIntent().getStringExtra("event_detail");
        member_fname = getIntent().getStringExtra("member_fname");
        member_phone = getIntent().getStringExtra("member_phone");

        txtedit_event_org =  findViewById(R.id.txteventadd_org);
        txteventedit_name = findViewById(R.id.txteventadd_name);
        txteventadd_detail = findViewById(R.id.txteventadd_detail);
        txteventedit_type = findViewById(R.id.txtevent_type);
        txteventedit_date = findViewById(R.id.txteventadd_date);
        txteventedit_location = findViewById(R.id.txteventadd_location);
        txteventadd_phone = findViewById(R.id.txteventadd_phone);


        txtedit_event_org.setText(event_org);
        txteventedit_name.setText(event_name);
        txteventadd_detail.setText(event_detail);
//        txteventedit_type.setText(event_type);
        txteventedit_date.setText(event_date);
        txteventedit_location.setText(event_location);
        txteventadd_phone.setText(member_phone);
//        edit_spinnerTypeWork.setAdapter(adapterThai);
    }


    private void createTypeworkData(){
        edit_Typework.add("จิตอาสา");
        edit_Typework.add("กิจกรรม");
        edit_Typework.add("ค่ายอาสา");


    }

    public class InsertAsyn extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            try{
                OkHttpClient _okHttpClient = new OkHttpClient();
                RequestBody _requestBody = new FormBody.Builder()
                        .add("event_org", event_org)
                        .add("event_name", event_name)
                        .add("event_detail", event_detail)
                        .add("event_location", event_location)
                        .add("event_date", event_date)
                        .add("event_type", event_type)
                        .add("member_phone", member_phone)



                        .add("event_id", event_id)
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
                Intent intent = new Intent(EditYourWorks.this, ViewYourWorks.class);
                intent.putExtra("event_id", event_id);
                intent.putExtra("event_org", event_org);
                intent.putExtra("eventname", event_name);
                intent.putExtra("event_detail", event_detail);
                intent.putExtra("event_date", event_date);
                intent.putExtra("event_location", event_location);
                intent.putExtra("event_type", event_type);
                intent.putExtra("member_fname", member_fname);
                intent.putExtra("member_phone", member_phone);
                startActivity(intent);
            }else {
                Toast.makeText(getApplicationContext(), "แก้ไขข้อมูลไม่สำเร็จ",Toast.LENGTH_SHORT).show();
            }
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_save_edit_event, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        if (id == R.id.menu_save_edit_event) {
//            Intent i = new Intent(EditYourWorks.this, ViewYourWorks.class);
//            startActivity(i);


            event_org = txtedit_event_org.getText().toString();
            event_name = txteventedit_name.getText().toString();
            event_detail = txteventadd_detail.getText().toString();

            event_date = txteventedit_date.getText().toString();
            event_location = txteventedit_location.getText().toString();
            member_phone = txteventadd_phone.getText().toString();



            if (event_org.isEmpty() || event_name.isEmpty() || event_detail.isEmpty() || event_date.isEmpty() || event_location.isEmpty() || event_type.isEmpty() || member_phone.isEmpty() ){
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(EditYourWorks.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
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
                new InsertAsyn().execute("http://"+ConfigIP.IP+"/volunteer_project/update_events.php");
//                    Toast.makeText(AddWork.this, "กรุณากรอกข้อมูลให้ครบ", Toast.LENGTH_LONG).show();

            }


            Toast.makeText(EditYourWorks.this,"บันทึกการแก้ไข", Toast.LENGTH_LONG).show();

        }

        // back page
//        if (id == R.id.home) {
//            System.out.print("Home id:"+id);
//            Intent myIntent = new Intent(getApplicationContext(), ViewYourWorks.class);
//            startActivity(myIntent);
//            return true;
//        }

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
