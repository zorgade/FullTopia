package com.example.fulltopia.fulltopia;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.fulltopia.fulltopia.Entities.Activity;
import com.example.fulltopia.fulltopia.Entities.Community;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

public class New_activity extends AppCompatActivity {

    private ProgressBar progressBar;
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;

    //Elements du screen
    EditText editText_activity_title;
    EditText editText_activity_description;
//    EditText editText_activity_min;
//    EditText editText_activity_max;
//    EditText editText_activity_date_creation;
//    EditText editText_activity_date_deadline;
//    EditText editText_activity_date_event;
//    TextView editText_activity_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_activity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //toolbar.setTitle(getString(R.string.app_name));
        //setSupportActionBar(toolbar);

        editText_activity_title = (EditText) findViewById(R.id.ET_activity_title);
        editText_activity_description = (EditText) findViewById(R.id.ET_activity_description);

        Button button = (Button) findViewById(R.id.BTN_activity_create);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference databaseReference = database.getReference();
                Activity activity;
                String title = editText_activity_title.getText().toString();
                String description = editText_activity_description.getText().toString();
//                String min = editText_activity_min.getText().toString();
//                Long min_part = Long.parseLong(min);
//                String max = editText_activity_max.getText().toString();
//                Long max_part = Long.parseLong(min);
//
//                Date date = new Date();

                activity = new Activity(title, description);
                //activity = new Activity(title, min_part, max_part, description, date_creation, date_deadline, date_event);

                try {
                    databaseReference.child("activity").push().setValue(activity);
                }
                catch(Exception e){
                    e.printStackTrace();
                }
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
