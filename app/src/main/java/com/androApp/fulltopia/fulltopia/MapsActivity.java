package com.androApp.fulltopia.fulltopia;

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
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //FirebaseDatabase database = FirebaseDatabase.getInstance();
        //DatabaseReference databaseReference = database.getReference("activity");

        /*databaseReference.addValueEventListener(new ValueEventListener() {
            List<Address> addressList = null;
            LatLng latLng = null;
            Geocoder geocoder = new Geocoder(MapsActivity.this, Locale.getDefault());
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Loop to get information from every children of the activity tree
                for(DataSnapshot activitySnapshot: dataSnapshot.getChildren()){
                    String title = activitySnapshot.child("title").getValue().toString();
                    String street = activitySnapshot.child("address").getValue().toString();
                    String city = activitySnapshot.child("city").getValue().toString();
                    String country = activitySnapshot.child("country").getValue().toString();
                    // Concat the string information in order to have a complete, more precise address
                    String activityAddress = street + " " + city + " " + country;

                    try {
                        // Return an array of Addresses corresponding to the activityAddress string.
                        addressList = geocoder.getFromLocationName(activityAddress, 1);
                        // Loop while there's no result found (this function can fail quite often so the loop is required)
                        while (addressList.size()==0) {
                            addressList = geocoder.getFromLocationName(activityAddress, 1);
                        }
                        // Get the first Address in the AddressList
                        if (addressList.size()>0) {
                            Address address = addressList.get(0);
                            // Create a LatLng object in order to be able to place a marker on the map
                            latLng = new LatLng(address.getLatitude(), address.getLongitude());
                        }
                    } catch (Exception e) {
                        System.out.print(e.getMessage());
                    }
                    //Add a marker using the coordinates and the title that we retrieved earlier.
                    mMap.addMarker(new MarkerOptions().position(latLng).title(title));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                String message = databaseError.getMessage();
                String ok = "ok";
            }
        });*/
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
        LatLng Hevs2 = new LatLng(46.3,7.5);
        mMap.addMarker(new MarkerOptions().position(Hevs2).title("Deuxième activité à la HES"));
        mMap.addMarker(new MarkerOptions().position(Hevs).title("Première activité à la HES"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(Hevs));
    }
}
