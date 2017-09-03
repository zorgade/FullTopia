package com.example.fulltopia.fulltopia.ActivitiesActivities;

/**
 * Author: Jonathan Joaquim.
 */

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;


import com.example.fulltopia.fulltopia.Entities.Activity;
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

                for(DataSnapshot activitySnapchot: dataSnapshot.getChildren()){
                    String title = (String) activitySnapchot.child("title").getValue();
                    String description = (String) activitySnapchot.child("description").getValue();

                    Activity activity = new Activity(title,description);
                    allActivities.add(activity);
                }
                ActivityListAdapter adapter = new ActivityListAdapter(getActivity(), allActivities);
                listViewAllActivities.setAdapter(adapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                String message = databaseError.getMessage();
                String ok = "ok";
            }
        }
        );
    }

}
