package com.example.fulltopia.fulltopia;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fulltopia.fulltopia.Entities.Community;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

public class SettingsActivity extends AppCompatActivity {

    private Button btnChangeLanguage, btnChangeNotification, btnChangeAddress, btnChangeEmail, btnChangePassword, btnSendResetEmail, btnRemoveUser,btnSendFeedBack,
            changeEmail, changePassword, sendEmail, remove, signOut,
            changeLng, changeNotif, changeAddres, sendfeedback;

    private EditText oldEmail, newEmail, password, newPassword,
            newStreet, newNpa, newCity, newCountry, feedback;

    private TextView language, notification, address, sendUsFeedback;

    private RadioButton lng_fr, lng_eng, notif_yes, notif_no;

    private ProgressBar progressBar;
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;

    public SettingsActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



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
                    startActivity(new Intent(SettingsActivity.this, LoginActivity.class));
                    finish();
                }
            }
        };

        btnChangeLanguage = (Button) findViewById(R.id.change_language_button);
        btnChangeNotification = (Button) findViewById(R.id.change_notification_button);
        btnChangeAddress = (Button) findViewById(R.id.change_address_button);
        btnChangeEmail = (Button) findViewById(R.id.change_email_button);
        btnChangePassword = (Button) findViewById(R.id.change_password_button);
        btnSendResetEmail = (Button) findViewById(R.id.sending_pass_reset_button);
        btnRemoveUser = (Button) findViewById(R.id.remove_user_button);
        btnSendFeedBack = (Button) findViewById(R.id.send_feedback_button);

        changeLng = (Button) findViewById(R.id.changeLanguage);
        changeNotif = (Button) findViewById(R.id.changeNotification);
        changeAddres = (Button) findViewById(R.id.changeAddress);
        changeEmail = (Button) findViewById(R.id.changeEmail);
        changePassword = (Button) findViewById(R.id.changePass);
        sendEmail = (Button) findViewById(R.id.send);
        remove = (Button) findViewById(R.id.remove);
        signOut = (Button) findViewById(R.id.sign_out);
        sendfeedback = (Button) findViewById(R.id.send_feedback);


        oldEmail = (EditText) findViewById(R.id.old_email);
        newEmail = (EditText) findViewById(R.id.new_email);
        password = (EditText) findViewById(R.id.password);
        newPassword = (EditText) findViewById(R.id.newPassword);
        newStreet = (EditText) findViewById(R.id.ET_Street);
        newNpa = (EditText) findViewById(R.id.ET_NPA);
        newCity = (EditText) findViewById(R.id.ET_City);
        newCountry = (EditText) findViewById(R.id.ET_Country);
        feedback = (EditText) findViewById(R.id.multiline_text_feedback);

        lng_fr = (RadioButton) findViewById(R.id.RB_French);
        lng_eng = (RadioButton) findViewById(R.id.RB_English);
        notif_yes = (RadioButton) findViewById(R.id.RB_Yes);
        notif_no = (RadioButton) findViewById(R.id.RB_No);

        language = (TextView) findViewById(R.id.TV_Language);
        address = (TextView) findViewById(R.id.TV_Address);
        notification = (TextView) findViewById(R.id.TV_GetNotif);
        sendUsFeedback = (TextView) findViewById(R.id.send_us_feedback);


        language.setVisibility(View.GONE);
        lng_fr.setVisibility(View.GONE);
        lng_eng.setVisibility(View.GONE);
        changeLng.setVisibility(View.GONE);
        notification.setVisibility(View.GONE);
        notif_yes.setVisibility(View.GONE);
        notif_no.setVisibility(View.GONE);
        changeNotif.setVisibility(View.GONE);
        address.setVisibility(View.GONE);
        newStreet.setVisibility(View.GONE);
        newNpa.setVisibility(View.GONE);
        newCity.setVisibility(View.GONE);
        newCountry.setVisibility(View.GONE);
        changeAddres.setVisibility(View.GONE);
        oldEmail.setVisibility(View.GONE);
        newEmail.setVisibility(View.GONE);
        password.setVisibility(View.GONE);
        newPassword.setVisibility(View.GONE);
        changeEmail.setVisibility(View.GONE);
        changePassword.setVisibility(View.GONE);
        sendEmail.setVisibility(View.GONE);
        remove.setVisibility(View.GONE);
        sendfeedback.setVisibility(View.GONE);
        feedback.setVisibility(View.GONE);
        sendUsFeedback.setVisibility(View.GONE);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        if (progressBar != null) {
            progressBar.setVisibility(View.GONE);
        }


        btnChangeLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                language.setVisibility(View.VISIBLE);
                lng_fr.setVisibility(View.VISIBLE);
                lng_eng.setVisibility(View.VISIBLE);
                changeLng.setVisibility(View.VISIBLE);
                notification.setVisibility(View.GONE);
                notif_yes.setVisibility(View.GONE);
                notif_no.setVisibility(View.GONE);
                changeNotif.setVisibility(View.GONE);
                address.setVisibility(View.GONE);
                newStreet.setVisibility(View.GONE);
                newNpa.setVisibility(View.GONE);
                newCity.setVisibility(View.GONE);
                newCountry.setVisibility(View.GONE);
                changeAddres.setVisibility(View.GONE);
                oldEmail.setVisibility(View.GONE);
                newEmail.setVisibility(View.GONE);
                password.setVisibility(View.GONE);
                newPassword.setVisibility(View.GONE);
                changeEmail.setVisibility(View.GONE);
                changePassword.setVisibility(View.GONE);
                sendEmail.setVisibility(View.GONE);
                remove.setVisibility(View.GONE);
                sendfeedback.setVisibility(View.GONE);
                feedback.setVisibility(View.GONE);
                sendUsFeedback.setVisibility(View.GONE);
            }
        });

        btnChangeNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                language.setVisibility(View.GONE);
                lng_fr.setVisibility(View.GONE);
                lng_eng.setVisibility(View.GONE);
                changeLng.setVisibility(View.GONE);
                notification.setVisibility(View.VISIBLE);
                notif_yes.setVisibility(View.VISIBLE);
                notif_no.setVisibility(View.VISIBLE);
                changeNotif.setVisibility(View.VISIBLE);
                address.setVisibility(View.GONE);
                newStreet.setVisibility(View.GONE);
                newNpa.setVisibility(View.GONE);
                newCity.setVisibility(View.GONE);
                newCountry.setVisibility(View.GONE);
                changeAddres.setVisibility(View.GONE);
                oldEmail.setVisibility(View.GONE);
                newEmail.setVisibility(View.GONE);
                password.setVisibility(View.GONE);
                newPassword.setVisibility(View.GONE);
                changeEmail.setVisibility(View.GONE);
                changePassword.setVisibility(View.GONE);
                sendEmail.setVisibility(View.GONE);
                remove.setVisibility(View.GONE);
                sendfeedback.setVisibility(View.GONE);
                feedback.setVisibility(View.GONE);
                sendUsFeedback.setVisibility(View.GONE);
            }
        });

        btnChangeAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                language.setVisibility(View.GONE);
                lng_fr.setVisibility(View.GONE);
                lng_eng.setVisibility(View.GONE);
                changeLng.setVisibility(View.GONE);
                notification.setVisibility(View.GONE);
                notif_yes.setVisibility(View.GONE);
                notif_no.setVisibility(View.GONE);
                changeNotif.setVisibility(View.GONE);
                address.setVisibility(View.VISIBLE);
                newStreet.setVisibility(View.VISIBLE);
                newNpa.setVisibility(View.VISIBLE);
                newCity.setVisibility(View.VISIBLE);
                newCountry.setVisibility(View.VISIBLE);
                changeAddres.setVisibility(View.VISIBLE);
                oldEmail.setVisibility(View.GONE);
                newEmail.setVisibility(View.GONE);
                password.setVisibility(View.GONE);
                newPassword.setVisibility(View.GONE);
                changeEmail.setVisibility(View.GONE);
                changePassword.setVisibility(View.GONE);
                sendEmail.setVisibility(View.GONE);
                remove.setVisibility(View.GONE);
                sendfeedback.setVisibility(View.GONE);
                feedback.setVisibility(View.GONE);
                sendUsFeedback.setVisibility(View.GONE);
            }
        });

        changeAddres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                if (user != null && !newStreet.getText().toString().trim().equals("")
                        && !newNpa.getText().toString().trim().equals("")
                        && !newCity.getText().toString().trim().equals("")
                        && !newCountry.getText().toString().trim().equals("")) {
                    user.updateEmail(newEmail.getText().toString().trim())
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(SettingsActivity.this, "Email address is updated. Please sign in with new email id!", Toast.LENGTH_LONG).show();
                                        signOut();
                                        progressBar.setVisibility(View.GONE);
                                    } else {
                                        Toast.makeText(SettingsActivity.this, "Failed to update email!", Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                }
                            });
                } else {
                    if (newStreet.getText().toString().trim().equals("")) {
                        newStreet.setError("Enter Street");
                        progressBar.setVisibility(View.GONE);
                    }
                    if (newNpa.getText().toString().trim().equals("")) {
                        newEmail.setError("Enter NPA");
                        progressBar.setVisibility(View.GONE);
                    }
                    if (newCity.getText().toString().trim().equals("")) {
                        newEmail.setError("Enter City");
                        progressBar.setVisibility(View.GONE);
                    }
                    if (newCountry.getText().toString().trim().equals("")) {
                        newEmail.setError("Enter Country");
                        progressBar.setVisibility(View.GONE);
                    }
                }
            }
        });


        btnChangeEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                language.setVisibility(View.GONE);
                lng_fr.setVisibility(View.GONE);
                lng_eng.setVisibility(View.GONE);
                changeLng.setVisibility(View.GONE);
                notification.setVisibility(View.GONE);
                notif_yes.setVisibility(View.GONE);
                notif_no.setVisibility(View.GONE);
                changeNotif.setVisibility(View.GONE);
                address.setVisibility(View.GONE);
                newStreet.setVisibility(View.GONE);
                newNpa.setVisibility(View.GONE);
                newCity.setVisibility(View.GONE);
                newCountry.setVisibility(View.GONE);
                changeAddres.setVisibility(View.GONE);
                oldEmail.setVisibility(View.VISIBLE);
                newEmail.setVisibility(View.VISIBLE);
                password.setVisibility(View.GONE);
                newPassword.setVisibility(View.GONE);
                changeEmail.setVisibility(View.VISIBLE);
                changePassword.setVisibility(View.GONE);
                sendEmail.setVisibility(View.GONE);
                remove.setVisibility(View.GONE);
                sendfeedback.setVisibility(View.GONE);
                feedback.setVisibility(View.GONE);
                sendUsFeedback.setVisibility(View.GONE);
            }
        });

        changeEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                if (oldEmail.getText().toString() != newEmail.getText().toString() && user != null && !newEmail.getText().toString().trim().equals("")) {
                    user.updateEmail(newEmail.getText().toString().trim())
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(SettingsActivity.this, "Email address is updated. Please sign in with new email id!", Toast.LENGTH_LONG).show();
                                        signOut();
                                        progressBar.setVisibility(View.GONE);
                                    } else {
                                        Toast.makeText(SettingsActivity.this, "Failed to update email!", Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                }
                            });
                } else if (newEmail.getText().toString().trim().equals("")) {
                    newEmail.setError("Enter email");
                    progressBar.setVisibility(View.GONE);
                }
            }
        });

        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                language.setVisibility(View.GONE);
                lng_fr.setVisibility(View.GONE);
                lng_eng.setVisibility(View.GONE);
                changeLng.setVisibility(View.GONE);
                notification.setVisibility(View.GONE);
                notif_yes.setVisibility(View.GONE);
                notif_no.setVisibility(View.GONE);
                changeNotif.setVisibility(View.GONE);
                address.setVisibility(View.GONE);
                newStreet.setVisibility(View.GONE);
                newNpa.setVisibility(View.GONE);
                newCity.setVisibility(View.GONE);
                newCountry.setVisibility(View.GONE);
                changeAddres.setVisibility(View.GONE);
                oldEmail.setVisibility(View.GONE);
                newEmail.setVisibility(View.GONE);
                password.setVisibility(View.GONE);
                newPassword.setVisibility(View.VISIBLE);
                changeEmail.setVisibility(View.GONE);
                changePassword.setVisibility(View.VISIBLE);
                sendEmail.setVisibility(View.GONE);
                remove.setVisibility(View.GONE);
                sendfeedback.setVisibility(View.GONE);
                feedback.setVisibility(View.GONE);
                sendUsFeedback.setVisibility(View.GONE);
            }
        });

        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                if (user != null && !newPassword.getText().toString().trim().equals("")) {
                    if (newPassword.getText().toString().trim().length() < 6) {
                        newPassword.setError("Password too short, enter minimum 6 characters");
                        progressBar.setVisibility(View.GONE);
                    } else {
                        user.updatePassword(newPassword.getText().toString().trim())
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(SettingsActivity.this, "Password is updated, sign in with new password!", Toast.LENGTH_SHORT).show();
                                            signOut();
                                            progressBar.setVisibility(View.GONE);
                                        } else {
                                            Toast.makeText(SettingsActivity.this, "Failed to update password!", Toast.LENGTH_SHORT).show();
                                            progressBar.setVisibility(View.GONE);
                                        }
                                    }
                                });
                    }
                } else if (newPassword.getText().toString().trim().equals("")) {
                    newPassword.setError("Enter password");
                    progressBar.setVisibility(View.GONE);
                }
            }
        });

        btnSendResetEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                language.setVisibility(View.GONE);
                lng_fr.setVisibility(View.GONE);
                lng_eng.setVisibility(View.GONE);
                changeLng.setVisibility(View.GONE);
                notification.setVisibility(View.GONE);
                notif_yes.setVisibility(View.GONE);
                notif_no.setVisibility(View.GONE);
                changeNotif.setVisibility(View.GONE);
                address.setVisibility(View.GONE);
                newStreet.setVisibility(View.GONE);
                newNpa.setVisibility(View.GONE);
                newCity.setVisibility(View.GONE);
                newCountry.setVisibility(View.GONE);
                changeAddres.setVisibility(View.GONE);
                oldEmail.setVisibility(View.VISIBLE);
                newEmail.setVisibility(View.GONE);
                password.setVisibility(View.GONE);
                newPassword.setVisibility(View.GONE);
                changeEmail.setVisibility(View.GONE);
                changePassword.setVisibility(View.GONE);
                sendEmail.setVisibility(View.VISIBLE);
                remove.setVisibility(View.GONE);
                sendfeedback.setVisibility(View.GONE);
                feedback.setVisibility(View.GONE);
                sendUsFeedback.setVisibility(View.GONE);
            }
        });

        sendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                if (!oldEmail.getText().toString().trim().equals("")) {
                    auth.sendPasswordResetEmail(oldEmail.getText().toString().trim())
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(SettingsActivity.this, "Reset password email is sent!", Toast.LENGTH_SHORT).show();
                                        progressBar.setVisibility(View.GONE);
                                    } else {
                                        Toast.makeText(SettingsActivity.this, "Failed to send reset email!", Toast.LENGTH_SHORT).show();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                }
                            });
                } else {
                    oldEmail.setError("Enter email");
                    progressBar.setVisibility(View.GONE);
                }
            }
        });

        btnSendFeedBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                language.setVisibility(View.GONE);
                lng_fr.setVisibility(View.GONE);
                lng_eng.setVisibility(View.GONE);
                changeLng.setVisibility(View.GONE);
                notification.setVisibility(View.GONE);
                notif_yes.setVisibility(View.GONE);
                notif_no.setVisibility(View.GONE);
                changeNotif.setVisibility(View.GONE);
                address.setVisibility(View.GONE);
                newStreet.setVisibility(View.GONE);
                newNpa.setVisibility(View.GONE);
                newCity.setVisibility(View.GONE);
                newCountry.setVisibility(View.GONE);
                changeAddres.setVisibility(View.GONE);
                oldEmail.setVisibility(View.GONE);
                newEmail.setVisibility(View.GONE);
                password.setVisibility(View.GONE);
                newPassword.setVisibility(View.GONE);
                changeEmail.setVisibility(View.GONE);
                changePassword.setVisibility(View.GONE);
                sendEmail.setVisibility(View.GONE);
                remove.setVisibility(View.GONE);
                sendfeedback.setVisibility(View.VISIBLE);
                feedback.setVisibility(View.VISIBLE);
                sendUsFeedback.setVisibility(View.VISIBLE);
            }
        });

        sendfeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(feedback.getText().toString().trim().length() > 20) {
                    progressBar.setVisibility(View.VISIBLE);
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference databaseReference = database.getReference();
                    String feedbackString = feedback.getText().toString();

                    try {
                        databaseReference.child("feedback").push().setValue(feedbackString);
                        Toast.makeText(SettingsActivity.this, "Thanks for your feedback", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                else {
                    Toast.makeText(SettingsActivity.this, "Please write at least 20 characters to send your feedback", Toast.LENGTH_SHORT).show();
                }
            }
        });



        btnRemoveUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                if (user != null) {
                    user.delete()
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(SettingsActivity.this, "Your profile is deleted:( Create a account now!", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(SettingsActivity.this, SignupActivity.class));
                                        finish();
                                        progressBar.setVisibility(View.GONE);
                                    } else {
                                        Toast.makeText(SettingsActivity.this, "Failed to delete your account!", Toast.LENGTH_SHORT).show();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                }
                            });
                }
            }
        });

        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
// Handle item selection
        switch (item.getItemId()) {
            case R.id.action_settings:
                Toast.makeText(this, "Go settings", Toast.LENGTH_SHORT).show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //sign out method
    public void signOut() {
        auth.signOut();
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onStart() {
        super.onStart();
        auth.addAuthStateListener(authListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (authListener != null) {
            auth.removeAuthStateListener(authListener);
        }
    }

}