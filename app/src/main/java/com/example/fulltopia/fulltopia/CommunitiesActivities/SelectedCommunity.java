package com.example.fulltopia.fulltopia.CommunitiesActivities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.fulltopia.fulltopia.R;

public class SelectedCommunity extends AppCompatActivity {

    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_community);

        TextView communityName_TV = (TextView)findViewById(R.id.TV_CommunityName);
        TextView communityDescription_TV = (TextView)findViewById(R.id.TV_CommunityDescription);
        bundle = getIntent().getExtras();
        String communityName = bundle.getString("name");
        String communityDescription = bundle.getString("description");
        communityName_TV.setText(communityName);
        communityDescription_TV.setText(communityDescription);
    }
}
