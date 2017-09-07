package com.example.fulltopia.fulltopia.ActivitiesActivities;

        import android.app.Activity;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.widget.TextView;

        import com.example.fulltopia.fulltopia.R;
        import com.google.firebase.auth.FirebaseAuth;
        import com.google.firebase.auth.FirebaseUser;
        import com.google.firebase.database.DataSnapshot;
        import com.google.firebase.database.DatabaseError;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.google.firebase.database.ValueEventListener;

public class Selected_activity extends AppCompatActivity {

    DatabaseReference databaseReference;
    Bundle bundle;
    com.example.fulltopia.fulltopia.Entities.Activity currentActivity;
    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_activity);


        final TextView activityName_TV = (TextView)findViewById(R.id.TV_TitleActivity);
        final TextView adminName_TV = (TextView)findViewById(R.id.TV_Admin);
        final TextView eventDate_TV = (TextView)findViewById(R.id.TV_Event_Date_descr);
        final TextView deadlineDate_TV = (TextView)findViewById(R.id.TV_Deadline_descr);
        final TextView address_TV = (TextView)findViewById(R.id.TV_Address);
        final TextView city_TV = (TextView)findViewById(R.id.TV_City);
        final TextView NPA_TV = (TextView)findViewById(R.id.TV_NPA);
        final TextView country_TV = (TextView)findViewById(R.id.TV_Country);
        final TextView contributor = (TextView)findViewById(R.id.TV_NBRContributor);
        final TextView description = (TextView)findViewById(R.id.TV_Description);

        bundle = getIntent().getExtras();
        final String activityID = bundle.getString("idActivity");

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("activity");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot activity: dataSnapshot.getChildren()){
                    if(activity.getKey().equals(activityID)){
                        currentActivity = activity.getValue(com.example.fulltopia.fulltopia.Entities.Activity.class);
                        adminName_TV.setText(user.getEmail().toString());
                        activityName_TV.setText(currentActivity.getTitle().toString());
                        eventDate_TV.setText(currentActivity.getDate_event());
                        deadlineDate_TV.setText(currentActivity.getDate_deadline());
                        address_TV.setText(currentActivity.getAddress());
                        city_TV.setText(currentActivity.getCity());
                        NPA_TV.setText(currentActivity.getNPA());
                        country_TV.setText(currentActivity.getCountry());
                        description.setText(currentActivity.getDescription());
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getMessage());
            }
        });

    }
}
