package com.example.fulltopia.fulltopia;

import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.example.fulltopia.fulltopia.Entities.Activity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    DatabaseReference databaseReference;
    List<Address> addressList;
    int position = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("activity");
        final Geocoder geocoder = new Geocoder(MapsActivity.this);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot activitySnapshot: dataSnapshot.getChildren()){
                    String title = (String) activitySnapshot.child("title").getValue();
                    String street = (String) activitySnapshot.child("address").getValue();
                    String city = (String) activitySnapshot.child("city").getValue();
                    String country = (String) activitySnapshot.child("country").getValue();
                    String activityAddress = street + " " + city + " " + country;
                    try {
                        addressList = geocoder.getFromLocationName(activityAddress,1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Address address = addressList.get(position);
                    LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                    mMap.addMarker(new MarkerOptions().position(latLng).title(title));
                    position++;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                String message = databaseError.getMessage();
                String ok = "ok";
            }
        });

//        addressList = new ArrayList<>();
//        addressList.clear();
//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for(DataSnapshot activitySnapshot: dataSnapshot.getChildren()){
//                    String title = (String) activitySnapshot.child("title").getValue();
//                    String street = (String) activitySnapshot.child("address").getValue();
//                    String city = (String) activitySnapshot.child("city").getValue();
//                    String country = (String) activitySnapshot.child("country").getValue();
//                    String activityAddress = street + " " + city + " " + country;
//                    try {
//                        addressList = geocoder.getFromLocationName(activityAddress,1);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    Address address = addressList.get(0);
//                    LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
//                    mMap.addMarker(new MarkerOptions().position(latLng).title(title));
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                String message = databaseError.getMessage();
//                String ok = "ok";
//            }
//        });
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));


    }
}
