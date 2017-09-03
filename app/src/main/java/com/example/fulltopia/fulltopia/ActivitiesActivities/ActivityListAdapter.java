package com.example.fulltopia.fulltopia.ActivitiesActivities;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.fulltopia.fulltopia.Entities.Activity;
import com.example.fulltopia.fulltopia.Entities.Community;
import com.example.fulltopia.fulltopia.R;

import java.util.List;

/**
 * Author: Jonathan Joaquim.
 */

public class ActivityListAdapter extends ArrayAdapter<Activity> {

    private Context context;
    private List<Activity> activityList;

    public ActivityListAdapter(Context context, List<Activity> activityList){
        super(context, R.layout.tab2allactivities, activityList);
        this.context=context;
        this.activityList=activityList;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View listViewItem = inflater.inflate(R.layout.activity_list_layout, null, true);

        TextView textViewName = (TextView) listViewItem.findViewById(R.id.section_label);

        Activity activity = activityList.get(position);

        textViewName.setText(activity.getTitle());

        return listViewItem;
    }
}
