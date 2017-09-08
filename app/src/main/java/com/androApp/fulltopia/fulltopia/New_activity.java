package com.androApp.fulltopia.fulltopia;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.androApp.fulltopia.fulltopia.ActivitiesActivities.ActivitiesActivity;
import com.androApp.fulltopia.fulltopia.Entities.Activity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

public class New_activity extends AppCompatActivity {

    private ProgressBar progressBar;
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;
    Bundle bundle;


    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    //Elements du screen
    EditText editText_activity_title;
    EditText editText_activity_min;
    EditText editText_activity_max;
    EditText editText_activity_description;
    EditText editText_activity_date_deadline;
    EditText editText_activity_date_event;
    TextView TextView_activity_image;
    EditText editText_activity_address;
    EditText editText_activity_city;
    EditText editText_activity_NPA;
    EditText editText_activity_country;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_activity);

        editText_activity_title = (EditText) findViewById(R.id.ET_communityactivity_title);
        editText_activity_description = (EditText) findViewById(R.id.ET_activity_description);
        editText_activity_min = (EditText) findViewById(R.id.ET_activity_min_req_part);
        editText_activity_max = (EditText) findViewById(R.id.ET_Activity_max_amount_part);
        editText_activity_date_deadline = (EditText) findViewById(R.id.ET_activity_deadline_participation);
        editText_activity_date_event = (EditText) findViewById(R.id.ET_activity_event_date);
        editText_activity_address = (EditText) findViewById(R.id.ET_activity_adress);
        editText_activity_city = (EditText) findViewById(R.id.ET_activity_city);
        editText_activity_NPA = (EditText) findViewById(R.id.ET_activity_NPA);
        editText_activity_country = (EditText) findViewById(R.id.ET_Activity_country);

        bundle = getIntent().getExtras();
        String communityID = bundle.getString("communityID");

        Button buttonCreateActivity = (Button) findViewById(R.id.BTN_activity_create);

        buttonCreateActivity.setOnClickListener(new View.OnClickListener() {
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
                String image = TextView_activity_image.getText().toString();
                String address = editText_activity_address.getText().toString();
                String city = editText_activity_city.getText().toString();
                String NPA = editText_activity_NPA.getText().toString();
                String country = editText_activity_country.getText().toString();
                String adminID = user.getUid();


                activity = new Activity(title, min_part_required, max_part_required, description, date_creation, date_deadline, date_event, image, address, city, NPA, country, adminID,"");

                try {
                    databaseReference.child("activity").push().setValue(activity);
                    Intent i = new Intent(New_activity.this, ActivitiesActivity.class);
                    startActivity(i);
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }
        });

        Button buttonReturn = (Button) findViewById(R.id.BTN_Activity_Return);

        buttonReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(New_activity.this,ActivitiesActivity.class);
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
                    startActivity(new Intent(New_activity.this, LoginActivity.class));
                    finish();
                }
            }
        };

    }
}
