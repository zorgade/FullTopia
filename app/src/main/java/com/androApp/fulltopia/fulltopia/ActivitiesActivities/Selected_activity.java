package com.androApp.fulltopia.fulltopia.ActivitiesActivities;

        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.TextView;

        import com.androApp.fulltopia.fulltopia.R;
        import com.google.firebase.auth.FirebaseAuth;
        import com.google.firebase.auth.FirebaseUser;
        import com.google.firebase.database.DataSnapshot;
        import com.google.firebase.database.DatabaseError;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.google.firebase.database.ValueEventListener;

        import java.util.List;

public class Selected_activity extends AppCompatActivity {

    DatabaseReference databaseReference;
    Bundle bundle;
    com.androApp.fulltopia.fulltopia.Entities.Activity currentActivity;
    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String activityAdmin;
    List<String> memberList;
    TextView activityAdmin_TV;
    String admin;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_activity);


        final Button buttonSubscribe = (Button) findViewById(R.id.BTN_Subscribe);
        final Button buttonUnsubscribe = (Button) findViewById(R.id.BTN_UnsubscribeToActivity);



        userID = user.getUid().toString();

        final TextView activityName_TV = (TextView)findViewById(R.id.TV_TitleActivity);

        //final TextView adminName_TV = (TextView)findViewById(R.id.TV_Admin);

        final TextView eventDate_TV = (TextView)findViewById(R.id.TV_Event_Date_descr);
        final TextView deadlineDate_TV = (TextView)findViewById(R.id.TV_Deadline_descr);
        final TextView address_TV = (TextView)findViewById(R.id.TV_Address);
        final TextView city_TV = (TextView)findViewById(R.id.TV_City);
        final TextView NPA_TV = (TextView)findViewById(R.id.TV_NPA);
        final TextView country_TV = (TextView)findViewById(R.id.TV_Country);
        final TextView contributor = (TextView)findViewById(R.id.TV_NBRContributor);
        final TextView description = (TextView)findViewById(R.id.TV_Description);
        activityAdmin_TV = (TextView)findViewById(R.id.TV_AdminName);


        bundle = getIntent().getExtras();
        final String activityID = bundle.getString("idActivity");

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("activity");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot activity: dataSnapshot.getChildren()){
                    if(activity.getKey().equals(activityID)){
                        currentActivity = activity.getValue(com.androApp.fulltopia.fulltopia.Entities.Activity.class);
                        activityAdmin = currentActivity.getAdminID();
                        activityName_TV.setText(currentActivity.getTitle().toString());
                        eventDate_TV.setText(currentActivity.getDate_event());
                        deadlineDate_TV.setText(currentActivity.getDate_deadline());
                        address_TV.setText(currentActivity.getAddress());
                        city_TV.setText(currentActivity.getCity());
                        NPA_TV.setText(currentActivity.getNPA());
                        country_TV.setText(currentActivity.getCountry());
                        description.setText(currentActivity.getDescription());

                        setUserAdmin(activityAdmin);

                        memberList = currentActivity.getMemberList();

                        //IF HE IS ADMIN
                        if(userID.equals(activityAdmin)){
                            buttonSubscribe.setVisibility(View.GONE);
                            buttonUnsubscribe.setVisibility(View.GONE);
                        }
                        else{
                            //IF LIST != 0
                            if(memberList.size()!=0){

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
                            else{
                                buttonSubscribe.setVisibility(View.VISIBLE);
                                buttonUnsubscribe.setVisibility(View.GONE);
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

        //Button to subscribe to a community

        buttonSubscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String userID = user.getUid().toString();
                currentActivity.subscribeToActivitiy(userID);

                try {

                    databaseReference.child(activityID).removeValue();
                    databaseReference.child(activityID).setValue(currentActivity);

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
                currentActivity.unsuscribeToActivity(userID);

                    try {

                    databaseReference.child(activityID).removeValue();
                    databaseReference.child(activityID).setValue(currentActivity);

                }
                catch(Exception e){
                    e.printStackTrace();
                }

                buttonSubscribe.setVisibility(View.GONE);

            }
        });
    }

    private void setUserAdmin(String activityAdmin) {
        DatabaseReference mCurrentUserAdmin = FirebaseDatabase.getInstance().getReference("usersInfos").child(activityAdmin);
        mCurrentUserAdmin.addValueEventListener(new ValueEventListener(){

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                admin = dataSnapshot.child("email").getValue().toString();
                activityAdmin_TV.setText(admin);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
}
