package com.example.fulltopia.fulltopia.ActivitiesActivities;

/**
 * Author: Jonathan Joaquim.
 */

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;


import com.example.fulltopia.fulltopia.CommunitiesActivities.CommunityListAdapter;
import com.example.fulltopia.fulltopia.CommunitiesActivities.SelectedCommunity;
import com.example.fulltopia.fulltopia.Entities.Community;
import com.example.fulltopia.fulltopia.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class tab1MyActivities extends Fragment {

    //Declaration of variables we need, like the ListView, the database and the list of communities and the Firebase User and the member List
    ListView listViewMyActivities;
    DatabaseReference databaseReference;
    List<com.example.fulltopia.fulltopia.Entities.Activity> myActivities;
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;
    FirebaseUser user;
    String userID;
    List<String> memberList;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab1myactivities, container, false);

        //get firebase auth instance
        auth = FirebaseAuth.getInstance();

        //get current user
        //final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        user = auth.getCurrentUser();
        userID = user.getUid().toString();

        //I link the listView with the LV in the layout
        listViewMyActivities = (ListView) rootView.findViewById(R.id.listViewMyActivity);

        //Creation of the arrayList
        myActivities = new ArrayList<>();

        //Reference of the database : branch community
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("activity");

        return rootView;
    }

    //Method onStart that will call the database and fill the listview
    @Override
    public void onStart() {
        super.onStart();

        databaseReference.addValueEventListener(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(DataSnapshot dataSnapshot) {

                                                        myActivities.clear();

                                                        for(DataSnapshot activitySnapshot: dataSnapshot.getChildren()){

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
                                                            memberList = (List<String>) activitySnapshot.child("memberList").getValue();

                                                            com.example.fulltopia.fulltopia.Entities.Activity activity = new com.example.fulltopia.fulltopia.Entities.Activity(id,title,date_event,date_deadline,address,city,NPA,Country,description,adminID);


                                                            if(activitySnapshot.child("adminID").getValue().equals(userID)) {
                                                                myActivities.add(activity);
                                                            }
                                                            else{
                                                                if(memberList!=null){
                                                                    for(String memberID: memberList){
                                                                        if(memberID.equals(userID)){
                                                                            myActivities.add(activity);
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                        if(myActivities!=null){
                                                            ActivityListAdapter adapter = new ActivityListAdapter(getActivity(), myActivities);
                                                            listViewMyActivities.setAdapter(adapter);
                                                        }
                                                    }

                                                    @Override
                                                    public void onCancelled(DatabaseError databaseError) {
                                                        String message = databaseError.getMessage();
                                                        String ok = "ok";
                                                    }
                                                }
        );

        listViewMyActivities.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                com.example.fulltopia.fulltopia.Entities.Activity activity = (com.example.fulltopia.fulltopia.Entities.Activity) parent.getItemAtPosition(position);
                String activityID = activity.getActivityID();


                Intent intent = new Intent(listViewMyActivities.getContext(),Selected_activity.class);
                intent.putExtra("idActivity",activityID);


                startActivity(intent);

            }
        });

    }
}
