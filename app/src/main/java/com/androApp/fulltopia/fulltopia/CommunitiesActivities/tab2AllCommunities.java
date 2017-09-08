package com.androApp.fulltopia.fulltopia.CommunitiesActivities;

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


import com.androApp.fulltopia.fulltopia.Entities.Community;
import com.androApp.fulltopia.fulltopia.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class tab2AllCommunities extends Fragment {

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

    //Method onStart that will call the database and fill the listview with all communities
    @Override
    public void onStart() {
        super.onStart();

        databaseReference.addValueEventListener(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(DataSnapshot dataSnapshot) {

                                                        //I clear the list
                                                        allCommunities.clear();

                                                        //I go in all communities
                                                        for (DataSnapshot communitySnapshot : dataSnapshot.getChildren()) {
                                                            //Store elements of community
                                                            String id = communitySnapshot.getKey();
                                                            String name = (String) communitySnapshot.child("name").getValue();
                                                            String description = (String) communitySnapshot.child("description").getValue();
                                                            String date = (String) communitySnapshot.child("dateCreationCommunity").getValue();
                                                            String userID = (String) communitySnapshot.child("adminID").getValue();

                                                            //Creation of an object Community
                                                            Community community = new Community(id, name, date, description, userID);

                                                            //Fill it in list
                                                            allCommunities.add(community);
                                                        }

                                                        //Test to see if List isn't null
                                                        if (allCommunities != null) {
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

        //ClickListener for each line of ListView that will open SelectedCommunity with correct ID
        listViewAllCommunities.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Community community = (Community) parent.getItemAtPosition(position);
                String communityId = community.getCommunityId();
                String name = community.getName();
                String description = community.getDescription();
                String date = community.getDateCreationCommunity();
                Intent intent = new Intent(listViewAllCommunities.getContext(), SelectedCommunity.class);
                intent.putExtra("communityID", communityId);
                startActivity(intent);

            }
        });

    }
}

