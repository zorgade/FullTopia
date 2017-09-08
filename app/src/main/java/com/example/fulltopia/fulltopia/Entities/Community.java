package com.example.fulltopia.fulltopia.Entities;

import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Taylan on 18.08.2017.
 */

public class Community {

    String communityId;
    String name;
    String dateCreationCommunity;
    String description;
    List<Activity> activitiesList;
    List<String> memberList = new ArrayList<>();
    String adminID;


    public String getCommunityId() {
        return communityId;
    }

    public void setCommunityId(String communityId) {
        this.communityId = communityId;
    }

    public Community(){}


    public Community(String name, String dateCreationCommunity, String description, String adminID){
        this.name=name;
        this.dateCreationCommunity=dateCreationCommunity;
        this.description=description;
        this.adminID=adminID;
    }

    public Community(String communityId, String name, String dateCreationCommunity, String description, String adminID){
        this.communityId=communityId;
        this.name=name;
        this.dateCreationCommunity=dateCreationCommunity;
        this.description=description;
        this.adminID = adminID;
    }

    public Community(String communityId, String name, String dateCreationCommunity, String description, String adminID, List<String> memberList){
        this.communityId=communityId;
        this.name=name;
        this.dateCreationCommunity=dateCreationCommunity;
        this.description=description;
        this.adminID = adminID;
        this.memberList = memberList;
    }

    public List<String> getMemberList() {
        return this.memberList;
    }

    public void setMemberList(List<String> memberList) {
        this.memberList = memberList;
    }

    public String getAdminID() {
        return this.adminID;
    }

    public void setAdminID(String adminID) {
        this.adminID = adminID;
    }

    public String getDateCreationCommunity() {
        return dateCreationCommunity;
    }

    public void setDateCreationCommunity(String dateCreationCommunity) {
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

    //Method to subscribe to a community
    public void subscribeToCommunity (String userID){
        this.memberList.add(userID);
    }

    //Method to unsubscribe to a community
    public void unsuscribeToCommunity(String userID){
        this.memberList.remove(userID);
    }

}
