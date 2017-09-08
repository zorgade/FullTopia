package com.example.fulltopia.fulltopia;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.fulltopia.fulltopia.ActivitiesActivities.ActivitiesActivity;
import com.example.fulltopia.fulltopia.CommunitiesActivities.CommunitiesActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


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
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    finish();
                }
            }
        };

        Toolbar toolbar =   (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        Intent i;
        switch (item.getItemId()) {
            case R.id.action_settings:
                i = new Intent(this, SettingsActivity.class);
                startActivity(i);
                return true;
            case R.id.goProfil:
                i = new Intent(this, ProfilsActivity.class);
                startActivity(i);
                return true;
            case R.id.action_signOut:
                signOut();
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //sign out method
    public void signOut() {
        auth.signOut();
    }


    public void goActivities(View view) {
        Intent i = new Intent(this, ActivitiesActivity.class);
        startActivity(i);
    }
    public void goCommunities(View view) {
        Intent i = new Intent(this, CommunitiesActivity.class);
        startActivity(i);
    }

    public void goTestAppEngine(View view) {
        Intent i = new Intent(this, AppEngineActivity.class);
        startActivity(i);
    }

    public void goToActivitiesMap(View view) {
        Intent i = new Intent(this, MapsActivity.class);
        startActivity(i);
    }
}
