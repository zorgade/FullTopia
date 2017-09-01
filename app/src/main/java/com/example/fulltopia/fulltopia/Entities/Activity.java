package com.example.fulltopia.fulltopia.Entities;



import java.util.Date;

/**
 * Created by Vincent on 13.07.2017.
 */


public class Activity {

    //String UserId;
    //String CommunityId;
    private String Title;
    private Long Min_User_Required;
    private Long Max_User_Required;
    private String Description;
    private Date DateCreationActivity;
    private Date DateDealineSubmit;
    private Date Date_event;
    //String CategoryId;
    private String image;
    private Location location;
    private String VideoLink;

    public Activity(){}

    public Activity(String Title, String Description){
        this.Title=Title;
        this.Description=Description;
    }


//    public Activity(String Title, Long Min_User_Required, Long Max_User_Required, String Description, Date DateCreationActivity, Date DateDealineSubmit, Date Date_event, String image, Location location, String VideoLink){
//        this.Title=Title;
//        this.Min_User_Required=Min_User_Required;
//        this.Max_User_Required=Max_User_Required;
//        this.Description=Description;
//        this.DateCreationActivity=DateCreationActivity;
//        this.DateDealineSubmit=DateDealineSubmit;
//        this.Date_event=Date_event;
//        this.image=image;
//        this.location=location;
//        this.VideoLink=VideoLink;
//    }

    public  String getTitle() {
        return Title;
    }
    public void   setTitle(String title) {
        Title = title;
    }

    public Long   getMin_User_Required() {
        return Min_User_Required;
    }
    public void   setMin_User_Required(Long min_User_Required) {
        Min_User_Required = min_User_Required;
    }


    public Long   getMax_User_Required() {
        return Max_User_Required;
    }
    public void   setMax_User_Required(Long max_User_Required) {
        Max_User_Required = max_User_Required;
    }


    public String getDescription() {
        return Description;
    }
    public void   setDescription(String description) {
        Description = description;
    }


    public Date getDateCreationActivity() {
        return DateCreationActivity;
    }

    public void setDateCreationActivity(Date dateCreationActivity) {
        DateCreationActivity = dateCreationActivity;
    }

    public Date   getDateDealineSubmit() {
        return DateDealineSubmit;
    }

    public void   setDateDealineSubmit(Date dateDealineSubmit) {
        DateDealineSubmit = dateDealineSubmit;
    }


    public Date   getDate_event() {
        return Date_event;
    }

    public void   setDate_event(Date date_event) {
        Date_event = date_event;
    }


    public String getImage() {
        return image;
    }

    public void   setImage(String image) {
        this.image = image;
    }


    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getVideoLink() {
        return VideoLink;
    }

    public void   setVideoLink(String videoLink) {
        VideoLink = videoLink;
    }
}
