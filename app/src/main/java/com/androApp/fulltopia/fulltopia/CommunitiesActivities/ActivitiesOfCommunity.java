package com.androApp.fulltopia.fulltopia.CommunitiesActivities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.androApp.fulltopia.fulltopia.ActivitiesActivities.ActivityListAdapter;
import com.androApp.fulltopia.fulltopia.ActivitiesActivities.NewCommunityactivity;
import com.androApp.fulltopia.fulltopia.ActivitiesActivities.Selected_activity;
import com.androApp.fulltopia.fulltopia.Entities.Activity;
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

public class ActivitiesOfCommunity extends AppCompatActivity {

    ListView listViewAllActivities;

    DatabaseReference databaseReference;
    Bundle bundle;
    List<Activity> allActivities;
    FloatingActionButton FLABTN_AddCommunityActivity;
    String userID;
    private FirebaseAuth auth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activities_of_community);

        //get firebase auth instance
        auth = FirebaseAuth.getInstance();

        //get current user
        user = auth.getCurrentUser();
        //Store the ID of the current User
        userID = user.getUid().toString();

        listViewAllActivities = (ListView) findViewById(R.id.listViewCommunityActivities);

        allActivities = new ArrayList<>();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("activity");


    }

    public void onStart(){
        super.onStart();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                allActivities.clear();

                bundle = getIntent().getExtras();
                String communityID = bundle.getString("communityID").toString();
                //display only the activity ot the current community
                for(DataSnapshot activitySnapshot: dataSnapshot.getChildren()){
                    String communityId = (String) activitySnapshot.child("communityID").getValue().toString();
                    if(communityId.equals(communityID)){
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

                        ActivityListAdapter adapter = new ActivityListAdapter(getApplicationContext(), allActivities);
                        listViewAllActivities.setAdapter(adapter);

                        //IF HE IS ADMIN -> He can add a new Activity
                        if (userID.equals(communityID)) {
                            FLABTN_AddCommunityActivity.setVisibility(View.VISIBLE);
                        }else{
                            FLABTN_AddCommunityActivity.setVisibility(View.GONE);
                        }

                    }

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                String message = databaseError.getMessage();
                String ok = "ok";
            }
        });

        Button buttonReturn = (Button) findViewById(R.id.BTN_ReturnActivtiesOfCommunity);

        buttonReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ActivitiesOfCommunity.this,SelectedCommunity.class);
                bundle = getIntent().getExtras();
                String communityID = bundle.getString("communityID");
                i.putExtra("communityID",communityID);
                startActivity(i);
            }
        });

        FLABTN_AddCommunityActivity = (FloatingActionButton)findViewById(R.id.FLBABTN_AddCommunityActivity);


        FLABTN_AddCommunityActivity.setOnClickListener(new View.OnClickListener() {
                                                           @Override
                                                           public void onClick(View view) {
                                                               Intent i = new Intent(ActivitiesOfCommunity.this, NewCommunityactivity.class);
                                                               bundle = getIntent().getExtras();
                                                               String communityID = bundle.getString("communityID");
                                                               i.putExtra("communityID",communityID);
                                                               startActivity(i);
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
