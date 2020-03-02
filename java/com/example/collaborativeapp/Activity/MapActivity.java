package com.example.collaborativeapp.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.collaborativeapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MapActivity extends AppCompatActivity {

    String res_ID,food_ID;
    TextView name,name_res,price,tase;
    String event_id,event_name;
    Double lat,lng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        Fragment fragment = new MapFragment();
        loadFragment(fragment);

//        loadResData();

    }

//    private void loadResData(){
//        final ProgressDialog progressDialog = new ProgressDialog(MapActivity.this);
//        progressDialog.setMessage("Loading...");
//        progressDialog.show();
//
////        String url = "http://"+ConfigIP.IP+"/kkueat/get_res.php?res_ID="+res_ID+"&food_ID="+food_ID;
//        String url = "http://"+ConfigIP.IP+"/volunteer_project/query_location.php";
//        Log.d("eat2",url);
//
//        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                progressDialog.dismiss();
//                showJSON(response);
//                Log.d("eat22","succe");
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(MapActivity.this, "ไม่สามารถดึงข้อมูลได้ โปรดตรวจสอบการเชื่อมต่อ", Toast.LENGTH_SHORT).show();
//            }
//        }
//        );
//        RequestQueue requestQueue = Volley.newRequestQueue(MapActivity.this.getApplicationContext());
//        requestQueue.add(stringRequest);
//    }



//    public void showJSON(String response){
//        try {
//            JSONObject jsonObject = new JSONObject(response);
//            JSONArray result = jsonObject.getJSONArray("result");
//
//            Log.d("eat2" , "success_show");
//
//            for (int i = 0; i<result.length(); i++){
//                JSONObject collectData = result.getJSONObject(i);
//                event_id = collectData.getString("event_id");
//                event_name = collectData.getString("event_name");
////                lat = collectData.getString("event_lat");
////                lng = collectData.getString("event_lng");
////                event_id ="1";
////                event_name = "test";
////                lat = 16.473602;
////                lng = 102.823287;
//
//
//
////                Log.d("lat33" , lat);
////                Log.d("lat34" , lng);
//                Log.d("lat34" , event_id);
//
//
//
//                Fragment fragment = new MapFragment();
//
//                Bundle data = new Bundle();
////                data.putString("lat",lat );
////                data.putString("lng",lng );
//                data.putString("event_name",event_name );
//                data.putString("event_id",event_id );
//                fragment.setArguments(data);
//
//                loadFragment(fragment);
//
//
//            }
//
//        }catch (JSONException ex) {
//            ex.printStackTrace();
//        }
//    }

    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        Log.d("pmappp","frgment1111");
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content_fragment, fragment)
                    .commit();
            return true;
        }
        return false;
    }
}
