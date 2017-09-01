package com.example.fulltopia.fulltopia.Entities;

import java.util.Date;
import java.util.List;

/**
 * Created by Taylan on 18.08.2017.
 */

public class Community {
//test
    String name;
    Date dateCreationCommunity;
    String description;
    List<Activity> activitiesList;

    public Community(){}

    public Community(String name, Date dateCreationCommunity, String description){
        this.name=name;
        this.dateCreationCommunity=dateCreationCommunity;
        this.description=description;
    }

    public Date getDateCreationCommunity() {
        return dateCreationCommunity;
    }

    public void setDateCreationCommunity(Date dateCreationCommunity) {
        this.dateCreationCommunity = dateCreationCommunity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Activity> getActivitiesList() {
        return activitiesList;
    }

    public void setActivitiesList(List<Activity> activitiesList) {
        this.activitiesList = activitiesList;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
