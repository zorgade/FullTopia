package com.example.fulltopia.fulltopia.Entities;

/**
 * Created by Marc on 13/07/2017.
 */

public class Location {

    private String Adress;
    private String City;
    private Long NPA;
    private String State;
    private String Country;
    private String Latitude;
    private String Longitude;

    public Location(){}

    public Location(String Adress, String City, Long NPA, String State, String Country, String Latitude, String Longitude){
        this.Adress = Adress;
        this.City = City;
        this.NPA = NPA;
        this.State = State;
        this.Country = Country;
        this.Latitude = Latitude;
        this.Longitude = Longitude;
    }

    public String getAdress() {
        return Adress;
    }

    public void setAdress(String adress) {
        Adress = adress;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public Long getNPA() {
        return NPA;
    }

    public void setNPA(Long NPA) {
        this.NPA = NPA;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String longitude) {
        Longitude = longitude;
    }
}
