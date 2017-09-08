package com.example.fulltopia.fulltopia.ActivitiesActivities;

/**
 * Author: Jonathan Joaquim.
 */

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;


import com.example.fulltopia.fulltopia.CommunitiesActivities.SelectedCommunity;
import com.example.fulltopia.fulltopia.Entities.Activity;
import com.example.fulltopia.fulltopia.Entities.Community;
import com.example.fulltopia.fulltopia.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class tab2AllActivities extends Fragment {

    ListView listViewAllActivities;

    DatabaseReference databaseReference;

    List<Activity> allActivities;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab2allactivities, container, false);

        listViewAllActivities = (ListView) rootView.findViewById(R.id.listViewAllActivities);

        allActivities = new ArrayList<>();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("activity");

        return rootView;
    }

    public void onStart(){
        super.onStart();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                allActivities.clear();


                for(DataSnapshot activitySnapshot: dataSnapshot.getChildren()) {
                    if (dataSnapshot.child("communityID").getValue() == null) {
                        String comid = (String) activitySnapshot.child("communityID").getValue();
                        String id = activitySnapshot.getKey();
                        String title = (String) activitySnapshot.child("title").getValue();
                        String adminID = (String) activitySnapshot.child("adminID").getValue();
                        String date_event = (String) activitySnapshot.child("date_event").getValue();
                        String date_deadline = (String) activitySnapshot.child("date_deadline").getValue();
                        String address = (String) activitySnapshot.child("address").getValue();
                        String city = (String) activitySnapshot.child("city").getValue();
                        String NPA = (String) activitySnapshot.child("NPA").getValue();
                        String Country = (String) activitySnapshot.child("country").getValue();
                        String description = (String) activitySnapshot.child("description").getValue();

                        Activity activity = new Activity(id, title, date_event, date_deadline, address, city, NPA, Country, description, adminID);
                        allActivities.add(activity);
                    }
                    ActivityListAdapter adapter = new ActivityListAdapter(getActivity(), allActivities);
                    listViewAllActivities.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                String message = databaseError.getMessage();
                String ok = "ok";
            }
        }
        );

        listViewAllActivities.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Activity activity = (Activity) parent.getItemAtPosition(position);
                String activityID = activity.getActivityID();


                Intent intent = new Intent(listViewAllActivities.getContext(),Selected_activity.class);
                intent.putExtra("idActivity",activityID);


                startActivity(intent);

            }
        });
    }

}
