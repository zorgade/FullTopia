package com.example.fulltopia.fulltopia.CommunitiesActivities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fulltopia.fulltopia.ActivitiesActivities.ActivitiesActivity;
import com.example.fulltopia.fulltopia.Entities.Community;
import com.example.fulltopia.fulltopia.New_activity;
import com.example.fulltopia.fulltopia.R;
import com.example.fulltopia.fulltopia.SettingsActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class SelectedCommunity extends AppCompatActivity {

    Bundle bundle;
    Community currentCommunity;
    String communityID;
    String communityName;
    String communityDescription;
    String communityAdmin;
    String userID;
    TextView communityName_TV;
    TextView communityDescription_TV;
    TextView communityAdmin_TV;
    List<String> memberList;
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;
    FirebaseUser user;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_community);

        final Button buttonSubscribe = (Button) findViewById(R.id.BTN_SubscribeToCommunity);
        final Button buttonUnsubscribe = (Button) findViewById(R.id.BTN_UnsubscribeToCommunity);
        final Button buttonActivities = (Button) findViewById(R.id.BTN_CommunitiesActivity);

        //get firebase auth instance
        auth = FirebaseAuth.getInstance();

        //get current user
        //final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        user = auth.getCurrentUser();
        userID = user.getUid().toString();




        communityName_TV = (TextView)findViewById(R.id.TV_CommunityName);
        communityDescription_TV = (TextView)findViewById(R.id.TV_CommunityDescription);
        communityAdmin_TV = (TextView)findViewById(R.id.TV_CommunityAdminName);

        bundle = getIntent().getExtras();
        communityID = bundle.getString("idCommunity");

        //I retrieve the current community
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference databaseReference = database.getReference("community");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot community: dataSnapshot.getChildren()){
                    if(community.getKey().equals(communityID)){
                        currentCommunity = community.getValue(Community.class);
                        communityName = currentCommunity.getName().toString();
                        communityDescription = currentCommunity.getDescription().toString();
                        communityAdmin = currentCommunity.getAdminID();
                        communityName_TV.setText(communityName);
                        communityDescription_TV.setText(communityDescription);
                        communityAdmin_TV.setText(communityAdmin);
                        memberList = currentCommunity.getMemberList();

                        if(memberList!=null){

                            for(String member: memberList){
                                if(member.equals(userID)){
                                    buttonSubscribe.setVisibility(View.GONE);
                                    buttonUnsubscribe.setVisibility(View.VISIBLE);

                                }
                                else{
                                    buttonSubscribe.setVisibility(View.VISIBLE);
                                    buttonUnsubscribe.setVisibility(View.GONE);
                                }
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getMessage());
            }
        });


        buttonActivities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SelectedCommunity.this, ActivitiesOfCommunity.class);
                i.putExtra("communityID",communityID);
                startActivity(i);
            }
        });


        //Button to subscribe to a community

        buttonSubscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String userID = user.getUid().toString();
                currentCommunity.subscribeToCommunity(userID);

                try {

                    databaseReference.child(communityID).removeValue();
                    databaseReference.child(communityID).setValue(currentCommunity);

                }
                catch(Exception e){
                    e.printStackTrace();
                }

                buttonSubscribe.setVisibility(View.GONE);

            }
        });



        //Button to unsubscribe to a community

        buttonUnsubscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String userID = user.getUid().toString();
                currentCommunity.unsuscribeToCommunity(userID);

                try {

                    databaseReference.child(communityID).removeValue();
                    databaseReference.child(communityID).setValue(currentCommunity);

                }
                catch(Exception e){
                    e.printStackTrace();
                }

                buttonSubscribe.setVisibility(View.GONE);

            }
        });
    }
}
