package com.androApp.fulltopia.fulltopia.CommunitiesActivities;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.androApp.fulltopia.fulltopia.Entities.Community;
import com.androApp.fulltopia.fulltopia.R;

import java.util.List;

/**
 * Created by Marc on 02/09/2017.
 */

public class CommunityListAdapter extends ArrayAdapter<Community> {

    //Declaration of variable
    private Context context;
    private List<Community> communityList;

    //Constructor of the ArrayAdapter with an ArrayList
    public CommunityListAdapter(Context context, List<Community> communityList){
        super(context, R.layout.tab2allcommunities, communityList);
        this.context=context;
        this.communityList=communityList;

    }

    //Method to set text
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View listViewItem = inflater.inflate(R.layout.community_list_layout, null, true);

        TextView textViewName = (TextView) listViewItem.findViewById(R.id.section_label);

        //We get an object Community in the list
        Community community = communityList.get(position);

        //We set the TextView with the name of the community
        textViewName.setText(community.getName());

        //Return the View
        return listViewItem;
    }
}
