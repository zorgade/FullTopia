package com.example.fulltopia.fulltopia.CommunitiesActivities;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.fulltopia.fulltopia.Entities.Community;
import com.example.fulltopia.fulltopia.R;

import java.util.List;

/**
 * Created by Marc on 02/09/2017.
 */

public class CommunityListAdapter extends ArrayAdapter<Community> {

    private Context context;
    private List<Community> communityList;

    public CommunityListAdapter(Context context, List<Community> communityList){
        super(context, R.layout.tab2allcommunities, communityList);
        this.context=context;
        this.communityList=communityList;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View listViewItem = inflater.inflate(R.layout.community_list_layout, null, true);

        TextView textViewName = (TextView) listViewItem.findViewById(R.id.section_label);

        Community community = communityList.get(position);

        textViewName.setText(community.getName());

        return listViewItem;
    }
}