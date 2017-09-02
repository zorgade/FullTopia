package com.example.fulltopia.fulltopia.CommunitiesActivities;

/**
 * Author: Jonathan Joaquim.
 */

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.app.Activity;


import com.example.fulltopia.fulltopia.Entities.Community;
import com.example.fulltopia.fulltopia.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class tab2AllCommunities extends Fragment{

    ListView listViewAllCommunities;

    DatabaseReference databaseReference;

    List<Community> allCommunities;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab2allcommunities, container, false);

        listViewAllCommunities = (ListView) rootView.findViewById(R.id.listViewAllCommunity);

        allCommunities = new ArrayList<Community>();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("community");



        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                allCommunities.clear();

                for(DataSnapshot communitySnapshot: dataSnapshot.getChildren()){
                    String name = (String) communitySnapshot.child("name").getValue();
                    String description = (String) communitySnapshot.child("description").getValue();
                    String date = (String) communitySnapshot.child("dateCreationCommunity").getValue();
                    Community community = new Community(name, date, description);
                    allCommunities.add(community);
                }
                CommunityListAdapter adapter = new CommunityListAdapter(tab2AllCommunities.this, allCommunities);
                listViewAllCommunities.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        }
        );
    }

}

