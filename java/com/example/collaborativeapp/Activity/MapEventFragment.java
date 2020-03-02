package com.example.collaborativeapp.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MapEventFragment extends Fragment {
    private FusedLocationProviderClient client;
    String event_name,event_id,lat,lng,event_org;

    int lat1,lnt1;
    String get_event_org,getevent_org;
    Double Dlat,Dlng;
    String latitude, longtitude;
    double DoubleLatitude, DoubleLongtitude;
    public MapEventFragment() {
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
        View rootView = inflater.inflate(R.layout.fragment_map_event, container, false);

//        loadShow();
        loadMap();
//        getlat = getArguments().getString("lat");
//        getlng = getArguments().getString("lng");
//        getlat ="16.473602";
//        getlng ="102.823287";


//        Log.d("gettttlat",getlat);
//        lat = Double.valueOf(getlat);
//        lng = Double.valueOf(getlng);

        return rootView;
    }
    public void loadMap() {

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.frg2);  //use SuppoprtMapFragment for using in fragment instead of activity  MapFragment = activity   SupportMapFragment = fragment
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(final GoogleMap mMap) {
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
//
                mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

                    @Override
                    public void onMapClick(LatLng point) {
                        MarkerOptions marker = new MarkerOptions()
                                .position(new LatLng(point.latitude, point.longitude))
                                .title("New Marker");


                        mMap.clear(); //clear old markers
                        mMap.addMarker(marker);

                        System.out.println(point.latitude + "---" + point.longitude);
                        Log.d("gettttlat2",(point.latitude + "---" + point.longitude));
                        Intent intent = new Intent(getActivity().getApplication(), AddWork.class);
                        intent.putExtra("lat2",point.latitude);
                        intent.putExtra("lnt2",point.longitude);
                        startActivity(intent);
                    }
                });



            }
        });

    }






}

