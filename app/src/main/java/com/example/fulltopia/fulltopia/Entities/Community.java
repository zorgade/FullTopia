package com.example.fulltopia.fulltopia.Entities;

import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Taylan on 18.08.2017.
 */

public class Community {

    String name;
    Date dateCreationCommunity;
    String description;
    List<Activity> activitiesList;
    List<FirebaseUser> memberList = new ArrayList<>();
    List<FirebaseUser> adminList = new ArrayList<>();


    public Community(){}

    public Community(String name, Date dateCreationCommunity, String description, FirebaseUser creator){
        this.name=name;
        this.dateCreationCommunity=dateCreationCommunity;
        this.description=description;
        this.adminList.add(creator);
    }

    public void addUserToCommunity(FirebaseUser user){
        this.memberList.add(user);
    }

    public void addMemberToAdmin(FirebaseUser user){
        this.adminList.add(user);
        this.memberList.remove(user);
    }

    public void addAdminToMember(FirebaseUser user){
        this.memberList.add(user);
        this.adminList.remove(user);
    }

    public List<FirebaseUser> getMemberList() {
        return this.memberList;
    }

    public void setMemberList(List<FirebaseUser> memberList) {
        this.memberList = memberList;
    }

    public List<FirebaseUser> getAdminList() {
        return this.adminList;
    }

    public void setAdminList(List<FirebaseUser> adminList) {
        this.adminList = adminList;
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
