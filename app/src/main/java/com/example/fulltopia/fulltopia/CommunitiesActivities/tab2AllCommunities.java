package com.example.fulltopia.fulltopia.CommunitiesActivities;

/**
 * Author: Jonathan Joaquim.
 */

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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

    //Declaration of variables we need, like the ListView, the database and the list of communities
    ListView listViewAllCommunities;
    DatabaseReference databaseReference;
    List<Community> allCommunities;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab2allcommunities, container, false);

        //I link the listView with the LV in the layout
        listViewAllCommunities = (ListView) rootView.findViewById(R.id.listViewAllCommunity);

        //Creation of the arrayList
        allCommunities = new ArrayList<>();

        //Reference of the database : branch community
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("community");

        return rootView;
    }

    //Method onStart that will call the database and fill the listview
    @Override
    public void onStart() {
        super.onStart();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                allCommunities.clear();

                for(DataSnapshot communitySnapshot: dataSnapshot.getChildren()) {
                    if (communitySnapshot.child("communityID") == null) {
                        String id = communitySnapshot.getKey();
                        String name = (String) communitySnapshot.child("name").getValue();
                        String description = (String) communitySnapshot.child("description").getValue();
                        String date = (String) communitySnapshot.child("dateCreationCommunity").getValue();
                        String userID = (String) communitySnapshot.child("adminID").getValue();
                        Community community = new Community(id, name, date, description, userID);
                        allCommunities.add(community);
                    }
                    CommunityListAdapter adapter = new CommunityListAdapter(getActivity(), allCommunities);
                    listViewAllCommunities.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                String message = databaseError.getMessage();
                String ok = "ok";
                
            }
        }
        );

        listViewAllCommunities.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Community community = (Community) parent.getItemAtPosition(position);
                String communityId = community.getCommunityId();
                String name = community.getName();
                String description = community.getDescription();
                String date = community.getDateCreationCommunity();
                Intent intent = new Intent(listViewAllCommunities.getContext(),SelectedCommunity.class);
                intent.putExtra("idCommunity",communityId);
                startActivity(intent);

            }
        });

    }


}

