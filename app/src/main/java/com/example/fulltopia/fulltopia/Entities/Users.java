package com.example.fulltopia.fulltopia.Entities;

/**
 * Created by Taylan on 18.08.2017.
 */

public class Users {

    String userId;
    String lastname;
    String firstname;
    String username;
    String password;
    String email;
    String address;
    String city;
    String npa;
    String country;

    public Users() {

    }

    public Users(String userId, String email) {
        this.userId = userId;
        this.email = email;
    }

    public Users(String uID, String mail, String uFname, String uLname, String uUname, String uStreet, String uNpa, String uCity, String uCountry) {
        this.userId = uID;
        this.email = mail;
        this.firstname = uFname;
        this.lastname = uLname;
        this.username = uUname;
        this.address = uStreet;
        this.npa = uNpa;
        this.city = uCity;
        this.country = uCountry;
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getNpa() {
        return npa;
    }

    public void setNpa(String npa) {
        this.npa = npa;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
