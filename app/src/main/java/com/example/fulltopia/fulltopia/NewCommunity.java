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

import com.example.fulltopia.fulltopia.Entities.Community;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

public class NewCommunity extends AppCompatActivity {

    private ProgressBar progressBar;
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;

    //Elements du screen
    EditText editText_CommunityName;
    EditText editText_CommunityDescription;
    EditText editText_CommunityAdress;
    EditText editText_CommunityNPA;
    EditText editText_CommunityCity;
    EditText editText_CommunityCountry;
    TextView textView_CommunityImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_community);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.app_name));
        setSupportActionBar(toolbar);

        editText_CommunityName = (EditText) findViewById(R.id.ET_CommunityName);
        editText_CommunityDescription = (EditText) findViewById(R.id.ET_CommunityDescription);

        Button button = (Button) findViewById(R.id.BTN_Community_Create);
        button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference databaseReference = database.getReference();
                Community community;
                String name = editText_CommunityName.getText().toString();
                String description = editText_CommunityDescription.getText().toString();
                Date date = new Date();
                community = new Community(name, date, description);
                databaseReference.child("foo").push().setValue(community);
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
                    startActivity(new Intent(NewCommunity.this, LoginActivity.class));
                    finish();
                }
            }
        };

    }
}
