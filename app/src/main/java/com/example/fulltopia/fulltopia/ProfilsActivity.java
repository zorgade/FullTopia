package com.example.fulltopia.fulltopia;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.fulltopia.fulltopia.Entities.Users;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfilsActivity extends AppCompatActivity {

    private EditText inputFName, inputLName, inputUsername, inputStreet, inputNPA,
            inputCity, inputCountry;
    private Button btnSaveprofil;

    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReference();
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;

    private Users currentUsers;
    private String userID = user.getUid().toString();


    String uUID, uFname, uLname, uEmail, uUname, uStreet, uNpa, uCity, uCountry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profils);

        btnSaveprofil = (Button) findViewById(R.id.saveProfile);
        inputFName = (EditText) findViewById(R.id.uFirstname);
        inputLName = (EditText) findViewById(R.id.uLastname);
        inputUsername = (EditText) findViewById(R.id.uUsername);
        inputStreet = (EditText) findViewById(R.id.uStreet);
        inputNPA = (EditText) findViewById(R.id.uNPA);
        inputCity = (EditText) findViewById(R.id.uCity);
        inputCountry = (EditText) findViewById(R.id.uCountry);

        //get firebase auth & DB instance
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();


        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    startActivity(new Intent(ProfilsActivity.this, LoginActivity.class));
                    finish();
                }
            }
        };

        //I retrieve the current community
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    //search for user infos
                    for (DataSnapshot users : dataSnapshot.child("usersInfos").getChildren()) {
                        if (users.getKey().equals(userID)) {
                            currentUsers = users.getValue(Users.class);
                            //String t = currentUsers.getLastname().toString();
                            //uUID = user.getUid().toString();
                            uUID = user.getUid().toString();
                            if(currentUsers.getLastname()!=null) {
                                uLname = currentUsers.getLastname().toString();
                                inputLName.setText(uLname);
                            }
                            if(currentUsers.getFirstname()!=null){
                                uFname = currentUsers.getFirstname().toString();
                                inputFName.setText(uFname);
                            }
                            if(currentUsers.getUsername()!=null) {
                                uUname = currentUsers.getUsername().toString();
                                inputUsername.setText(uUname);
                            }
                            if(currentUsers.getNpa()!=null) {
                                uNpa = currentUsers.getNpa().toString();
                                inputNPA.setText(uNpa);
                            }
                            if(currentUsers.getAddress()!=null) {
                                uStreet = currentUsers.getAddress().toString();
                                inputStreet.setText(uStreet);
                            }
                            if(currentUsers.getCity()!=null) {
                                uCity = currentUsers.getCity().toString();
                                inputCity.setText(uCity);

                            }
                            if(currentUsers.getCountry()!=null) {
                                uCountry = currentUsers.getCountry().toString();
                                inputCountry.setText(uCountry);
                            }
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    System.out.println("The read failed: " + databaseError.getMessage());
                }
            });


        btnSaveprofil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String uID = user.getUid().toString();
                String mail = user.getEmail().toString();

                uFname = inputFName.getText().toString();
                uLname = inputLName.getText().toString();
                uUname = inputUsername.getText().toString();
                uStreet = inputStreet.getText().toString();
                uNpa = inputNPA.getText().toString();
                uCity = inputCity.getText().toString();
                uCountry = inputCountry.getText().toString();


                Users users;
                users = new Users(uID, mail, uFname, uLname, uUname, uStreet, uNpa, uCity, uCountry);

                try {
                    databaseReference.child("usersInfos").child(uID).removeValue();
                    databaseReference.child("usersInfos").child(uID).setValue(users);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                startActivity(new Intent(ProfilsActivity.this, MainActivity.class));
                finish();
            }


        });


    }
}

