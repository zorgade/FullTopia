package com.androApp.fulltopia.fulltopia;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class AppEngineActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_engine);

            // Get ListView object from xml
            final ListView listView = (ListView) findViewById(R.id.listView);

            // Create a new Adapter
            final ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_list_item_1, android.R.id.text1);

            // Assign adapter to ListView
            listView.setAdapter(adapter);

            // Connect to the Firebase database
            final FirebaseDatabase database = FirebaseDatabase.getInstance();

            // Get a reference to the fulltopia child items it the database
            final DatabaseReference myRef = database.getReference("fulltopiaChat");

            // Assign a listener to detect changes to the child items
            // of the database reference.
            myRef.addChildEventListener(new ChildEventListener(){

                // This function is called once for each child that exists
                // when the listener is added. Then it is called
                // each time a new child is added.
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                    String value = dataSnapshot.getValue(String.class);
                    adapter.add(value);
                }

                // This function is called each time a child item is removed.
                public void onChildRemoved(DataSnapshot dataSnapshot){
                    String value = dataSnapshot.getValue(String.class);
                    adapter.remove(value);
                }

                // The following functions are also required in ChildEventListener implementations.
                public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName){}
                public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName){}

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Log.w("TAG:", "Failed to read value.", error.toException());
                }
            });

            // Add items via the Button and EditText at the bottom of the window.
            final EditText text = (EditText) findViewById(R.id.todoText);
            final Button button = (Button) findViewById(R.id.addButton);

            button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    // Create a new child with a auto-generated ID.
                    DatabaseReference childRef = myRef.push();
                    DatabaseReference databaseReference = database.getReference();

                    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    String uMail= user.getEmail().toString();

                    // Set the child's data to the value passed in from the text box.
                    childRef.setValue(uMail+": "+text.getText().toString());

                }
            });

            // Delete items when clicked
            /*listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {

                    Query myQuery = myRef.orderByValue().equalTo((String)
                            listView.getItemAtPosition(position));

                    myQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.hasChildren()) {
                                DataSnapshot firstChild = dataSnapshot.getChildren().iterator().next();
                                firstChild.getRef().removeValue();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    })
                    ;}
            })
            ;*/}
    }
