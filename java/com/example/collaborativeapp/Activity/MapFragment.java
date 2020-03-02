package com.example.collaborativeapp.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.collaborativeapp.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;


public class MapFragment extends Fragment {
    private FusedLocationProviderClient client;
    String event_name,event_id,lat,lng,event_org;
    String get_event_org,getevent_org;
    Double Dlat,Dlng;
    String latitude, longtitude;
    double DoubleLatitude, DoubleLongtitude;
    public static ArrayList<RowItemLocation> row_event_location = new ArrayList<>();


    public MapFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_map, container, false);

        client = LocationServices.getFusedLocationProviderClient(getActivity());


        if (android.support.v4.app.ActivityCompat.checkSelfPermission(getActivity(), ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

        }

        client.getLastLocation().addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {

                    DoubleLatitude = location.getLatitude();
                    DoubleLongtitude = location.getLongitude();
                    latitude = String.valueOf(location.getLatitude());
                    longtitude = String.valueOf(location.getLongitude());
                }
            }
        });

//        loadShow();
        requestPermission();
        loadShowPet();
//        getlat = getArguments().getString("lat");
//        getlng = getArguments().getString("lng");
//        getlat ="16.473602";
//        getlng ="102.823287";


//        Log.d("gettttlat",getlat);
//        lat = Double.valueOf(getlat);
//        lng = Double.valueOf(getlng);




        return rootView;
    }

    private void requestPermission() {
        android.support.v4.app.ActivityCompat.requestPermissions(getActivity(), new String[]{ACCESS_FINE_LOCATION}, 1);
    }

    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }
//    private void loadShow(){
//        Log.d("loadmap","loadmappp");
//        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.frg);  //use SuppoprtMapFragment for using in fragment instead of activity  MapFragment = activity   SupportMapFragment = fragment
//        mapFragment.getMapAsync(new OnMapReadyCallback() {
//            @Override
//            public void onMapReady(GoogleMap mMap) {
//                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
//
//                mMap.clear(); //clear old markers
//
//                CameraPosition googlePlex = CameraPosition.builder()
//                        .target(new LatLng(16.474556,102.822730))
//                        .zoom(15)
//                        .bearing(0)
//                        .tilt(45)
//                        .build();
//
//                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(googlePlex), 100, null);
//
//                mMap.addMarker(new MarkerOptions()
//                        .position(new LatLng(16.475382, 102.823963))
//                        .title("event_name"));
//
//            }
//        });
//    }


    private void loadShowPet(){
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.show();

//        String url = "http://"+ConfigIP.IP+"/dogcat/get_pet_nearme.php?user_id=" + get_user_id;
        String url = "http://"+ConfigIP.IP+"/volunteer_project/query_location.php";
        Log.d("get_url",url);
        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                showJSON(response);
                Log.d("get_success","success");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity().getApplicationContext(), "ไม่สามารถดึงข้อมูลได้ โปรดตรวจสอบการเชื่อมต่อ", Toast.LENGTH_SHORT).show();
            }
        }
        );
        RequestQueue requestQueue = Volley.newRequestQueue(this.getContext().getApplicationContext());
        requestQueue.add(stringRequest);
    }

    public void showJSON(String response){
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray("result");


            for (int i = 0; i<result.length(); i++){
                JSONObject collectData = result.getJSONObject(i);

                lat = collectData.getString("event_lat");
                lng = collectData.getString("event_lng");
                event_id = collectData.getString("event_id");
                event_name = collectData.getString("event_name");
                event_org = collectData.getString("event_org");

                Dlat = Double.valueOf(lat);
                Dlng = Double.valueOf(lng);

                Log.d("get_lat2",String.valueOf(Dlat));


                RowItemLocation item = new RowItemLocation(Dlat,Dlng,event_id,event_name,event_org);
                row_event_location.add(item);

//                Log.d("mapp", "SIZE2::" +  String.valueOf(row_event_location.size()));
//                Log.d("get_url", "SIZE" +  String.valueOf(row_event_location.size()));
            }
//            Log.d("mapp", "SIZE3::" +  String.valueOf(row_event_location.size()));
//            loadShow();
            loadMap();
        }catch (JSONException ex) {
            ex.printStackTrace();
        }
    }

    public void loadMap() {

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.frg);  //use SuppoprtMapFragment for using in fragment instead of activity  MapFragment = activity   SupportMapFragment = fragment
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

                mMap.clear(); //clear old markers

                CameraPosition googlePlex = CameraPosition.builder()
                        .target(new LatLng(16.474556,102.822730))
                        .zoom(15)
                        .bearing(0)
                        .tilt(45)
                        .build();

                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(googlePlex), 100, null);

                mMap.addMarker(new MarkerOptions()
                        .position(new LatLng(16.475382, 102.823963))
                        .title("event_name"));


                for (int k = 0; k < row_event_location.size(); k++){
                                    mMap.addMarker(new MarkerOptions()
                                            .position(new LatLng(row_event_location.get(k).getevent_lat(), row_event_location.get(k).getevent_lng()))
                                            .title(row_event_location.get(k).getevent_name())
                                            .snippet(row_event_location.get(k).getevent_id()));



                                          get_event_org  = row_event_location.get(k).getevent_org();

                                    Log.d("Mapp", String.valueOf(row_event_location.get(k)));
                                     Log.d("get_event_org",get_event_org);
                    Log.d("getevent_name2",row_event_location.get(k).getevent_name());

                }

                mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                    @Override
                    public void onInfoWindowClick(Marker marker) {
                        String Titlemak = marker.getTitle();
                        String Snippetmak = marker.getSnippet();


                        Intent intent = new Intent(getActivity(), DetailWork.class);
                        intent.putExtra("event_id",Snippetmak);
                        intent.putExtra("eventname",Titlemak);
//                        intent.putExtra("event_org",getevent_org);




                        Log.d("evemme_id",Snippetmak);
//                        Log.d("getevent_org55",getevent_org);
                        startActivity(intent);

//                        FragmentManager fm = getFragmentManager();
//                        Bundle data = new Bundle();//create bundle instance
//                        data.putString("event_id", Snippetmak);
//                        data.putString("event_name", Snippetmak);


//                        data.putString("event_id", Snippetmak);
//                        data.putString("event_name", Snippetmak);




//                        FragmentTransaction ft = fm.beginTransaction();
//                        ProfileFragment ssf = new ProfileFragment();
//                        ssf.setArguments(data);
//                        ft.replace(R.id.content_fragment, ssf);
//                        ft.commit();
//                                    Log.d("ccmap", "onMarkerClick");
//                                    Log.d("ccmap", Titlemak);
//                                    Log.d("ccmap", Snippetmak);
//                                    startMap();
                    }
                });
            }
        });
//        client = LocationServices.getFusedLocationProviderClient(getActivity());
//
//
//        if (android.support.v4.app.ActivityCompat.checkSelfPermission(getActivity(), ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//
//        }
//
//        client.getLastLocation().addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
//            @Override
//            public void onSuccess(Location location) {
//                if (location != null) {
//
//                    DoubleLatitude = location.getLatitude();
//                    DoubleLongtitude = location.getLongitude();
//                    latitude = String.valueOf(location.getLatitude());
//                    longtitude = String.valueOf(location.getLongitude());
//                    Log.d("mapp", "onSuccess");
//                    Log.d("mapp", "latitude" + latitude);
//                    Log.d("mapp", "longtitude" + longtitude);


//                    SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.frg);  //use SuppoprtMapFragment for using in fragment instead of activity  MapFragment = activity   SupportMapFragment = fragment
//                    mapFragment.getMapAsync(new OnMapReadyCallback(){
//                        @Override
//                        public void onMapReady(GoogleMap mMap) {


//                Log.d("mapp", latitude);
//                Log.d("mapp", longtitude);
//                            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
//
//                            mMap.clear(); //clear old markers
//
//                            CameraPosition googlePlex = CameraPosition.builder()
//                                .target(new LatLng(16.474556,102.822730))
//                                .zoom(15)
//                                .bearing(0)
//                                .tilt(45)
//                                .build();

//                            CameraPosition googlePlex = CameraPosition.builder()
//                                    .target(new LatLng(Dlat, Dlng))
//                                    .zoom(15)
//                                    .bearing(0)
//                                    .tilt(25)
//                                    .build();

//                            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(googlePlex), 100, null);
//
//
//                            mMap.addMarker(new MarkerOptions()
//                                    .position(new LatLng(Dlat, Dlng))
//                                    .title("It's Me!!"));
//

//                            Log.d("mapp", "SIZE1::" +  String.valueOf(row_pet_location.size()));
//
//                            for (int k = 0; k < row_event_location.size(); k++){
//
//
//
//                                    mMap.addMarker(new MarkerOptions()
//                                            .position(new LatLng(row_event_location.get(k).getevent_lat(), row_event_location.get(k).getevent_lng()))
//                                            .title(row_event_location.get(k).getevent_name()));
//
//                                    Log.d("Mapp", String.valueOf(row_event_location.get(k)));
//
//                            }
//
//                        }
//                    });
//                }
//                Log.d("mapp", "onSuccessNooo");
//            }
//        });
//    }
    }
}

