package com.androApp.fulltopia.fulltopia.ActivitiesActivities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.androApp.fulltopia.fulltopia.CommunitiesActivities.ActivitiesOfCommunity;
import com.androApp.fulltopia.fulltopia.Entities.Activity;
import com.androApp.fulltopia.fulltopia.LoginActivity;
import com.androApp.fulltopia.fulltopia.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

public class NewCommunityactivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;
    Bundle bundle;



    private ProgressDialog mProgressDialog;
    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    //Elements of the screen
    EditText editText_activity_title;
    EditText editText_activity_min;
    EditText editText_activity_max;
    EditText editText_activity_description;
    EditText editText_activity_date_deadline;
    EditText editText_activity_date_event;
    EditText editText_activity_address;
    EditText editText_activity_city;
    EditText editText_activity_NPA;
    EditText editText_activity_country;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_communityactivity);

        editText_activity_title = (EditText) findViewById(R.id.ET_communityactivity_title);
        editText_activity_min = (EditText) findViewById(R.id.ET_communityactivity_MinPart);
        editText_activity_max = (EditText) findViewById(R.id.ET_communityactivity_MaxPart);
        editText_activity_description = (EditText) findViewById(R.id.ET_communityactivity_description);
        editText_activity_date_deadline = (EditText) findViewById(R.id.ET_communityactivity_deadline);
        editText_activity_date_event = (EditText) findViewById(R.id.ET_communityactivity_eventdate);
        editText_activity_address = (EditText) findViewById(R.id.ET_communityactivity_address);
        editText_activity_city = (EditText) findViewById(R.id.ET_communityactivity_city);
        editText_activity_NPA = (EditText) findViewById(R.id.ET_communityactivity_NPA);
        editText_activity_country = (EditText) findViewById(R.id.ET_communityactivity_country);

        //On click to the button create activity, an object "activity" is going to be construct and sent on Firebase
        Button button = (Button) findViewById(R.id.BTN_communityactivity_CreateActivity);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference databaseReference = database.getReference();

                //retrieve informations from editTexts
                Activity activity;
                String title = editText_activity_title.getText().toString();
                String min_part_required = editText_activity_min.getText().toString();
                String max_part_required = editText_activity_max.getText().toString();
                String description = editText_activity_description.getText().toString();
                Date date = new Date();
                String date_creation = date.toString();
                String date_deadline = editText_activity_date_deadline.getText().toString();
                String date_event = editText_activity_date_event.getText().toString();
                String address = editText_activity_address.getText().toString();
                String city = editText_activity_city.getText().toString();
                String NPA = editText_activity_NPA.getText().toString();
                String country = editText_activity_country.getText().toString();
                String adminID = user.getUid();

                //Get the communitId to display on wich community this activity belongs to
                bundle = getIntent().getExtras();
                String communityID = bundle.getString("communityID");


                activity = new Activity(title, min_part_required, max_part_required, description, date_creation, date_deadline, date_event, address, city, NPA, country, adminID, communityID);

                try {
                    databaseReference.child("activity").push().setValue(activity);
                    Intent i = new Intent(NewCommunityactivity.this, ActivitiesOfCommunity.class);
                    i.putExtra("communityID",communityID);
                    startActivity(i);
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }
        });

        //onClick on the return button, go back the previous page
        //the communityID has to be sent back too, because we have to now on wich community we are going back to
        Button buttonReturn = (Button) findViewById(R.id.BTN_communityactivity_Return);

        buttonReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(NewCommunityactivity.this,ActivitiesOfCommunity.class);
                bundle = getIntent().getExtras();
                String communityID = bundle.getString("communityID");
                i.putExtra("communityID",communityID);
                startActivity(i);
            }
        });

        //get firebase auth instance
        auth = FirebaseAuth.getInstance();

        //get current user
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    startActivity(new Intent(NewCommunityactivity.this, LoginActivity.class));
                    finish();
                }
            }
        };



    }
}
