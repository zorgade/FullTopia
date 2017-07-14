package com.example.fulltopia.fulltopia.Entities;

/**
 * Created by Marc on 14/07/2017.
 */

public class Category {

    String Description;

    public Category(){}

    public Category(String Description){
        this.Description = Description;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}
