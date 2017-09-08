package com.androApp.fulltopia.fulltopia.CommunitiesActivities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.androApp.fulltopia.fulltopia.Entities.Community;
import com.androApp.fulltopia.fulltopia.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class SelectedCommunity extends AppCompatActivity {

    //Variables like Community, elements of Community, userID, memberList, admin
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
    private FirebaseAuth auth;
    FirebaseUser user;
    String admin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_community);

        //Declaration of buttons and link it with layout
        final Button buttonSubscribe = (Button) findViewById(R.id.BTN_SubscribeToCommunity);
        final Button buttonUnsubscribe = (Button) findViewById(R.id.BTN_UnsubscribeToCommunity);
        final Button buttonActivities = (Button) findViewById(R.id.BTN_CommunitiesActivity);

        //get firebase auth instance
        auth = FirebaseAuth.getInstance();

        //get current user
        user = auth.getCurrentUser();
        //Store the ID of the current User
        userID = user.getUid().toString();

        //We declare the TextViews to set texts and link it with layout
        communityName_TV = (TextView) findViewById(R.id.TV_CommunityName);
        communityDescription_TV = (TextView) findViewById(R.id.TV_CommunityDescription);
        communityAdmin_TV = (TextView) findViewById(R.id.TV_CommunityAdminName);

        //We get back the current Community ID to use it
        bundle = getIntent().getExtras();
        communityID = bundle.getString("communityID");

        //I retrieve the current community
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference databaseReference = database.getReference();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot community : dataSnapshot.child("community").getChildren()) {
                    //Test to find the Community Selected
                    if (community.getKey().equals(communityID)) {
                        //Get back the object and link it with Community class
                        currentCommunity = community.getValue(Community.class);
                        //Store values I will need to set TextViews
                        communityName = currentCommunity.getName().toString();
                        communityDescription = currentCommunity.getDescription().toString();
                        communityAdmin = currentCommunity.getAdminID();

                        //I set texts of TextViews
                        communityName_TV.setText(communityName);
                        communityDescription_TV.setText(communityDescription);

                        //I call method to search the admin name by his ID and to set admin TextView
                        setUserAdmin(communityAdmin);

                        //I store the memberList to set correct buttons
                        memberList = currentCommunity.getMemberList();

                        //IF HE IS ADMIN -> NO BUTTONS
                        if (userID.equals(communityAdmin)) {
                            buttonSubscribe.setVisibility(View.GONE);
                            buttonUnsubscribe.setVisibility(View.GONE);
                            buttonActivities.setVisibility(View.VISIBLE);

                        } else {
                            //IF LIST != 0
                            if (memberList.size() != 0) {
                                //Test in List to see if currentUser is in memberList -> BUTTON UNSUSCRIBE
                                for (String member : memberList) {
                                    if (member.equals(userID)) {
                                        buttonSubscribe.setVisibility(View.GONE);
                                        buttonUnsubscribe.setVisibility(View.VISIBLE);
                                        buttonActivities.setVisibility(View.VISIBLE);
                                    }
                                    //currentUser isn't in List -> BUTTON SUSCRIBE
                                    else {
                                        buttonSubscribe.setVisibility(View.VISIBLE);
                                        buttonUnsubscribe.setVisibility(View.GONE);
                                        buttonActivities.setVisibility(View.GONE);
                                    }
                                }
                            }
                            //IF LIST == 0 SO NO OTHER MEMBER (ONLY ADMIN)
                            else {
                                buttonSubscribe.setVisibility(View.VISIBLE);
                                buttonUnsubscribe.setVisibility(View.GONE);
                                buttonActivities.setVisibility(View.GONE);
                            }
                        }
                    }
                }
            }

            //Error in Firebase Database Connection
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getMessage());
            }
        });


        //ClickListener of button New Activity in Community -> Redirect to ActivitiesOfCommunity
        buttonActivities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SelectedCommunity.this, ActivitiesOfCommunity.class);
                i.putExtra("communityID", communityID);
                startActivity(i);
            }
        });


        //ClickListener of button to subscribe to a community
        buttonSubscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Get currentUserID
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String userID = user.getUid().toString();

                //Method to subscribe in Community.Class
                currentCommunity.subscribeToCommunity(userID);

                try {

                    //I delete old value on Firebase Database
                    databaseReference.child("community").child(communityID).removeValue();
                    //I store new value on Firebase Database
                    databaseReference.child("community").child(communityID).setValue(currentCommunity);

                } catch (Exception e) {
                    e.printStackTrace();
                }

                buttonSubscribe.setVisibility(View.GONE);

            }
        });


        //ClickListener of button to subscribe to a community
        buttonUnsubscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Get currentUserID
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String userID = user.getUid().toString();

                //Method to unsubscribe in Community.Class
                currentCommunity.unsuscribeToCommunity(userID);

                try {
                    //I delete old value on Firebase Database
                    databaseReference.child("community").child(communityID).removeValue();
                    //I store new value on Firebase Database
                    databaseReference.child("community").child(communityID).setValue(currentCommunity);

                } catch (Exception e) {
                    e.printStackTrace();
                }

                buttonSubscribe.setVisibility(View.GONE);

            }
        });
    }

    //Method to get the mail of admin of the community and to set TextView with it
    private void setUserAdmin(String communityAdmin) {
        //I search the object usersInfos on Firebase with good ID
        DatabaseReference mCurrentUserAdmin = FirebaseDatabase.getInstance().getReference("usersInfos").child(communityAdmin);
        mCurrentUserAdmin.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //I store the email value on String
                admin = dataSnapshot.child("email").getValue().toString();
                //I set TextView text with it
                communityAdmin_TV.setText(admin);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
}
