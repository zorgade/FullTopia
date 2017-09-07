package com.example.fulltopia.fulltopia.Entities;



import java.util.Date;

/**
 * Created by Vincent on 13.07.2017.
 */


public class Activity {

    private String activityID;
    private String Title;
    private String Min_part_required;
    private String Max_part_required;
    private String Description;
    private Date Date_creation_activity;
    private String Date_deadline;
    private String Date_event;
    private String image;
    private String Address;
    private String City;
    private String NPA;
    private String Country;
    private String adminID;

    public Activity() {
    }

    public Activity(String title, String min_part_required, String max_part_required, String description, Date date_creation_activity, String date_dealine, String date_event, String image, String address, String city, String NPA, String country, String adminID) {
        this.Title = title;
        this.Min_part_required = min_part_required;
        this.Max_part_required = max_part_required;
        this.Description = description;
        this.Date_creation_activity = date_creation_activity;
        this.Date_deadline = date_dealine;
        this.Date_event = date_event;
        this.image = image;
        this.Address = address;
        this.City = city;
        this.NPA = NPA;
        this.Country = country;
        this.adminID = adminID;
    }

    public Activity(String id, String title, String date_event, String date_deadline, String address, String city, String NPA, String Country, String description, String adminID) {
        this.activityID = id;
        this.Title = title;
        this.Date_event = date_event;
        this.Date_deadline = date_deadline;
        this.Address = address;
        this.City = city;
        this.NPA = NPA;
        this.Country = Country;
        this.Description = description;
        this.adminID = adminID;
        this.adminID = adminID;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getMin_part_required() {
        return Min_part_required;
    }

    public void setMin_part_required(String min_part_required) {
        Min_part_required = min_part_required;
    }

    public String getMax_part_required() {
        return Max_part_required;
    }

    public void setMax_part_required(String max_part_required) {
        Max_part_required = max_part_required;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public Date getDate_creation_activity() {
        return Date_creation_activity;
    }

    public void setDate_creation_activity(Date date_creation_activity) {
        Date_creation_activity = date_creation_activity;
    }

    public String getDate_event() {
        return Date_event;
    }

    public void setDate_event(String date_event) {
        Date_event = date_event;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String adress) {
        Address = adress;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getNPA() {
        return NPA;
    }

    public void setNPA(String NPA) {
        this.NPA = NPA;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getDate_deadline() {
        return Date_deadline;
    }

    public void setDate_deadline(String date_deadline) {
        Date_deadline = date_deadline;
    }

    public String getAdminID() {
        return adminID;
    }

    public void setAdminID(String adminID) {
        this.adminID = adminID;
    }

    public String getActivityID() {
        return activityID;
    }

    public void setActivityID(String activityID) {
        this.activityID = activityID;
    }
}

