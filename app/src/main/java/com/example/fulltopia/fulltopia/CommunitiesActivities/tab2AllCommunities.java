package com.example.fulltopia.fulltopia.CommunitiesActivities;

/**
 * Author: Jonathan Joaquim.
 */

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;


import com.example.fulltopia.fulltopia.Entities.Community;
import com.example.fulltopia.fulltopia.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class tab2AllCommunities extends ListFragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab2allcommunities, container, false);

        final List<Community> allCommunities = new ArrayList<Community>();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference();
        databaseReference.child("community").addValueEventListener(new ValueEventListener() {

            /**
             * this method will be used if the data on the database changes
             * @param dataSnapshot
             */
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //get all children
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                for (DataSnapshot child:children){
                    Community community = child.getValue(Community.class);
                    allCommunities.add(community);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });


        //Make an arrayadapter to show result
        ArrayAdapter<Community> communityAdapter = new ArrayAdapter<Community>(getActivity(), android.R.layout.simple_list_item_1, allCommunities);

        setListAdapter(communityAdapter);

        return rootView;
    }
}

