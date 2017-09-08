package com.example.fulltopia.fulltopia;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fulltopia.fulltopia.Entities.Community;
import com.example.fulltopia.fulltopia.Entities.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Locale;

import static com.example.fulltopia.fulltopia.Entities.Language.languageCurrent;

public class SettingsActivity extends AppCompatActivity {

    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReference();
    String uAddress;
    String npa;
    String city;
    String country;
    private Button btnChangeLanguage, btnChangeNotification, btnChangeAddress, btnChangeEmail, btnChangePassword, btnSendResetEmail, btnRemoveUser, btnSendFeedBack,
            changeEmail, changePassword, sendEmail, remove, signOut,
            changeLng, changeNotif, changeAddres, sendfeedback;
    private EditText oldEmail, newEmail, password, newPassword,
            newStreet, newNpa, newCity, newCountry, feedback;
    private TextView language, notification, address, sendUsFeedback;
    private RadioButton lng_fr, lng_eng, notif_yes, notif_no;
    private ProgressBar progressBar;
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;
    private FirebaseAnalytics mFirebaseAnalytics;
    private String userID = user.getUid().toString();
    private Bundle bundle;

    private RadioGroup radio = null;
    Context context;



    public SettingsActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        context = this;
        setRadioButton();

        //get firebase auth & DB instance
        auth = FirebaseAuth.getInstance();


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

                // set listener
                radio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {

                        View radioButton = radio.findViewById(checkedId);
                        int index = radio.indexOfChild(radioButton);

                        //  set preferences language;
                        if (index == 0)
                            setFr();
                        else
                            setEn();

                    }
                });
            }
        });

        btnChangeNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                language.setVisibility(View.GONE);
                lng_fr.setVisibility(View.GONE);
                lng_eng.setVisibility(View.GONE);
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

                userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
                databaseReference = database.getReference("userAddress");
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot userAdress : dataSnapshot.getChildren()) {
                            if (userAdress.getKey().equals(userID)) {
                                uAddress = (String) userAdress.child("address").getValue();
                                city = (String) userAdress.child("city").getValue();
                                country = (String) userAdress.child("country").getValue();
                                npa = (String) userAdress.child("npa").getValue();

                                newStreet.setText(uAddress);
                                newNpa.setText(npa);
                                newCity.setText(city);
                                newCountry.setText(country);

                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        System.out.println("The read failed: " + databaseError.getMessage());

                    }
                });

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

                    //FirebaseDatabase database = FirebaseDatabase.getInstance();
                    //DatabaseReference databaseReference = database.getReference();
                    //FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    Users userAddress;
                    uAddress = newStreet.getText().toString();
                    npa = newNpa.getText().toString();
                    city = newCity.getText().toString();
                    country = newCountry.getText().toString();
                    userAddress = new Users(uAddress, npa, city, country);

                    try {
                        databaseReference.child(userID).removeValue();
                        databaseReference.child(userID).setValue(userAddress);
                        Toast.makeText(SettingsActivity.this, getString(R.string.AddressChange), Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.GONE);


                    } catch (Exception e) {
                        e.printStackTrace();
                    }

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
                if (feedback.getText().toString().trim().length() > 20) {
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
                } else {
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

    private void setRadioButton() {
        radio = (RadioGroup) findViewById(R.id.radioGroupLanguage);
        if (languageCurrent.equals("fr")) {
            RadioButton radioBtn_French = (RadioButton) radio.findViewById(R.id.RB_French);
            radioBtn_French.setChecked(true);
//            radioBtn_English.setChecked(false);
        } else {
            RadioButton radioBtn_English = (RadioButton) radio.findViewById(R.id.RB_English);
            radioBtn_English.setChecked(true);
        }
    }

    //if user choose french
    public void setFr() {
        changeLang("fr");
        Toast.makeText(SettingsActivity.this, "Vous avez choisi le français.", Toast.LENGTH_SHORT).show();
        refresh_activity();
    }

    //if user choose english
    public void setEn() {
        changeLang("en");
        Toast.makeText(SettingsActivity.this, "English is selected", Toast.LENGTH_SHORT).show();
        refresh_activity();
    }

    //change the language of the app and save it
    public void changeLang(String lang) {
        languageCurrent = lang;
        if (lang.equalsIgnoreCase(""))
            return;
        Locale myLocale = new Locale(lang);
        saveLocale(lang);
        Locale.setDefault(myLocale);
        android.content.res.Configuration config = new android.content.res.Configuration();
        config.locale = myLocale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
    }
    //save choosed language into the sharredpreferance of the phone
    public void saveLocale(String lang) {
        String langPref = "Language";
        SharedPreferences prefs = getSharedPreferences("CommonPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(langPref, lang);
        editor.commit();
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
    private void refresh_activity() {
        Intent refresh = new Intent(context, SettingsActivity.class);
        startActivity(refresh);//Start the same Activity
        finish(); //finish Activity.
    }

}