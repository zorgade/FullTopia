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

    public Users(String userId, String address, String city, String npa, String country) {
        this.userId = userId;
        this.address = address;
        this.city = city;
        this.npa = npa;
        this.country = country;
    }

    public Users(String address, String city, String npa, String country) {
        this.address = address;
        this.city = city;
        this.npa = npa;
        this.country = country;
    }

    public Users(String userId, String email) {
        this.userId = userId;
        this.email = email;
    }

    public Users(String lastname, String firstname, String username, String address, String city, String npa, String country) {
        this.lastname = lastname;
        this.firstname = firstname;
        this.username = username;
        this.address = address;
        this.city = city;
        this.npa = npa;
        this.country = country;
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
