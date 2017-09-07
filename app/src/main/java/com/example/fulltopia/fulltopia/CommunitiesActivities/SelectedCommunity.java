package com.example.fulltopia.fulltopia.CommunitiesActivities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fulltopia.fulltopia.Entities.Community;
import com.example.fulltopia.fulltopia.R;
import com.example.fulltopia.fulltopia.SettingsActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SelectedCommunity extends AppCompatActivity {

    Bundle bundle;
    Community currentCommunity;
    String communityID;
    String communityName;
    String communityDescription;
    String communityAdmin;
    TextView communityName_TV;
    TextView communityDescription_TV;
    TextView communityAdmin_TV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_community);


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
                        communityAdmin = currentCommunity.getAdminID().toString();
                        communityName_TV.setText(communityName);
                        communityDescription_TV.setText(communityDescription);
                        communityAdmin_TV.setText(communityAdmin);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getMessage());
            }
        });





        //Button to subscribe to a community
        final Button buttonSubscribe = (Button) findViewById(R.id.BTN_SubscribeToCommunity);
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

                buttonSubscribe.setText("Bravo, vous vous êtes bien inscrit!");

            }
        });


    }
}
