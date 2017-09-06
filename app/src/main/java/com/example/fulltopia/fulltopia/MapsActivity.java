package com.example.fulltopia.fulltopia;

import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

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

import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
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
        DatabaseReference databaseReference = database.getReference("activity");

        databaseReference.addValueEventListener(new ValueEventListener() {
            Geocoder geocoder = new Geocoder(MapsActivity.this);
            List<Address> geoResults = null;
            LatLng latLng = null;
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot activitySnapshot: dataSnapshot.getChildren()){
                    String title = activitySnapshot.child("title").getValue().toString();
                    String street = activitySnapshot.child("address").getValue().toString();
                    String city = activitySnapshot.child("city").getValue().toString();
                    String country = activitySnapshot.child("country").getValue().toString();
                    String activityAddress = street + " " + city + " " + country;

                    try {
                        geoResults = geocoder.getFromLocationName(activityAddress, 2);
                        while (geoResults.size()==0) {
                            geoResults = geocoder.getFromLocationName(activityAddress, 2);
                        }
                        if (geoResults.size()>0) {
                            Address address = geoResults.get(0);
                            latLng = new LatLng(address.getLatitude(), address.getLongitude());
                        }
                    } catch (Exception e) {
                        System.out.print(e.getMessage());
                    }

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
        LatLng Hevs = new LatLng(46.293003, 7.536426);
        mMap.addMarker(new MarkerOptions().position(Hevs).title("Première activité à la HES"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(Hevs));
    }
}
