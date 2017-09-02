package com.example.fulltopia.fulltopia;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fulltopia.fulltopia.Entities.Activity;
import com.example.fulltopia.fulltopia.Entities.Community;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class New_activity extends AppCompatActivity {

    private ProgressBar progressBar;
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;


    //Firebase image select on device
    private Button mSelectImage;
    private StorageReference mStorage;
    private static final int GALLERY_INTENT=2;
    private ProgressDialog mProgressDialog;

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

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        toolbar.setTitle(getString(R.string.app_name));
//        setSupportActionBar(toolbar);

        editText_activity_title = (EditText) findViewById(R.id.ET_activity_title);
        editText_activity_description = (EditText) findViewById(R.id.ET_activity_description);
        editText_activity_min = (EditText) findViewById(R.id.ET_activity_min_req_part);
        editText_activity_max = (EditText) findViewById(R.id.ET_Activity_max_amount_part);
        editText_activity_description = (EditText) findViewById(R.id.ET_activity_description);
        editText_activity_date_deadline = (EditText) findViewById(R.id.ET_activity_deadline_participation);
        editText_activity_date_event = (EditText) findViewById(R.id.ET_activity_event_date);
        TextView_activity_image = (TextView) findViewById(R.id.ET_activity_image);
        editText_activity_address = (EditText) findViewById(R.id.ET_activity_adress);
        editText_activity_city = (EditText) findViewById(R.id.ET_activity_city);
        editText_activity_NPA = (EditText) findViewById(R.id.ET_activity_NPA);
        editText_activity_country = (EditText) findViewById(R.id.ET_Activity_country);

        Button button = (Button) findViewById(R.id.BTN_activity_create);
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
                Date date_creation = new Date();
                String date_deadline = editText_activity_date_deadline.getText().toString();
                String date_event = editText_activity_date_event.getText().toString();
                String image = TextView_activity_image.getText().toString();
                String address = editText_activity_address.getText().toString();
                String city = editText_activity_city.getText().toString();
                String NPA = editText_activity_NPA.getText().toString();
                String country = editText_activity_country.getText().toString();

                activity = new Activity(title, min_part_required, max_part_required, description, date_creation, date_deadline, date_event, image, address, city, NPA, country);

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

        //upload imag for new activity: https://www.youtube.com/watch?v=mSi7bNk4ySM
        mStorage = FirebaseStorage.getInstance().getReference();
        mSelectImage = (Button) findViewById(R.id.BTN_activity_Gallery);
        mProgressDialog = new ProgressDialog(this);
        mSelectImage.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v){
                Intent i = new Intent(Intent.ACTION_PICK);
                i.setType("image/*");
                startActivityForResult(i, GALLERY_INTENT);
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GALLERY_INTENT && resultCode == RESULT_OK){

            mProgressDialog.setMessage(getString(R.string.UPLOADING));
            mProgressDialog.show();
            Uri uri = data.getData();
            StorageReference filePath = mStorage.child("ActivitiesPhotos").child(uri.getLastPathSegment());
            filePath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    Toast.makeText(New_activity.this, getString(R.string.UP_IMG), Toast.LENGTH_LONG).show();
                    mProgressDialog.dismiss();
                }
            });
        }
    }


}
