package com.example.collaborativeapp.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.collaborativeapp.R;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

public class ReportEvent extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private RadioGroup report_group;
    private RadioButton report_button;
    private Button btn_report;
    private EditText txtreport;
    String report1,report2,member_id,event_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_event);

        SharedPreferences shared = getSharedPreferences("Login", Context.MODE_PRIVATE);
        member_id = shared.getString("member_id", "");


        btn_report = findViewById(R.id.btn_report);
        event_id = getIntent().getStringExtra("event_id_report");
        txtreport = (EditText) findViewById(R.id.txtreport);
        btn_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                report_group = (RadioGroup) findViewById(R.id.radio);
                int selectedId = report_group.getCheckedRadioButtonId();
                report_button = (RadioButton) findViewById(selectedId);
                report1 = report_button.getText().toString();

                report2 = txtreport.getText().toString();
                Log.d("clickkkk",report2);

                new InsertAsyn().execute("http://"+ConfigIP.IP+"/volunteer_project/insert_report.php?");

            }
        });

    }

    public class InsertAsyn extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            try{
                OkHttpClient _okHttpClient = new OkHttpClient();
                RequestBody _requestBody = new FormBody.Builder()

                        .add("report1", report1)
                        .add("report2", report2)
                        .add("member_id", member_id)
                        .add("event_id", event_id)
                        .build();

                okhttp3.Request request = new okhttp3.Request.Builder().url(strings[0]).post(_requestBody).build();
                _okHttpClient.newCall(request).execute();
                Log.d("report",event_id);
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
        Intent intent = new Intent(ReportEvent.this, Home.class);
        startActivity(intent);
        }else {
        Toast.makeText(getApplicationContext(), "ไม่สามารถบันทึกข้อมูลได้",Toast.LENGTH_SHORT).show();
        }
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
//            Intent i = new Intent(Message.this, AddWork.class);
//            startActivity(i);
//            return true;
//        }

        // back page
        if (id == R.id.home) {
            System.out.print("Home id:"+id);
            Intent myIntent = new Intent(getApplicationContext(), DetailWork.class);
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