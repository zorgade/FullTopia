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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class tab1MyCommunities extends Fragment {

    //Declaration of variables we need, like the ListView, the database and the list of communities and the Firebase User and the member List
    ListView listViewMyCommunities;
    DatabaseReference databaseReference;
    List<Community> myCommunities;
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;
    FirebaseUser user;
    String userID;
    List<String> memberList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab1mycommunities, container, false);

        //get firebase auth instance
        auth = FirebaseAuth.getInstance();

        //get current user ID
        user = auth.getCurrentUser();
        userID = user.getUid().toString();

        //I link the listView with the LV in the layout
        listViewMyCommunities = (ListView) rootView.findViewById(R.id.listViewMyCommunity);

        //Creation of the arrayList
        myCommunities = new ArrayList<>();

        //Reference of the database : branch community
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("community");


        return rootView;
    }

    //Method onStart that will call the database and fill the listview with my communities (ADMIN OR IN MEMBERLIST)
    @Override
    public void onStart() {
        super.onStart();

        databaseReference.addValueEventListener(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(DataSnapshot dataSnapshot) {

                                                        //I clear the List
                                                        myCommunities.clear();

                                                        //I go in all communities
                                                        for (DataSnapshot communitySnapshot : dataSnapshot.getChildren()) {

                                                            //Store elements of Community
                                                            String id = communitySnapshot.getKey();
                                                            String name = (String) communitySnapshot.child("name").getValue();
                                                            String description = (String) communitySnapshot.child("description").getValue();
                                                            String date = (String) communitySnapshot.child("dateCreationCommunity").getValue();
                                                            String adminID = (String) communitySnapshot.child("adminID").getValue();
                                                            memberList = (List<String>) communitySnapshot.child("memberList").getValue();

                                                            //Create a new object Community
                                                            Community community = new Community(id, name, date, description, adminID, memberList);

                                                            //Test to see if currentUser is admin of Community
                                                            if (communitySnapshot.child("adminID").getValue().equals(userID)) {
                                                                //Add it to list if he is
                                                                myCommunities.add(community);
                                                            } else {
                                                                if (memberList != null) {
                                                                    //Test to see if currentUser is in memberList
                                                                    for (String memberID : memberList) {
                                                                        if (memberID.equals(userID)) {
                                                                            //Add it if he is
                                                                            myCommunities.add(community);
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                        //Test if no communities
                                                        if (myCommunities != null) {
                                                            CommunityListAdapter adapter = new CommunityListAdapter(getActivity(), myCommunities);
                                                            listViewMyCommunities.setAdapter(adapter);
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
        listViewMyCommunities.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Community community = (Community) parent.getItemAtPosition(position);
                String communityId = community.getCommunityId();
                Intent intent = new Intent(listViewMyCommunities.getContext(), SelectedCommunity.class);
                intent.putExtra("communityID", communityId);
                startActivity(intent);

            }
        });

    }
}
