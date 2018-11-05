package com.example.platelminto.betterpocket;

import android.graphics.drawable.Drawable;

public class Person {
    private String firstname;
    private String lastname;
    private String role;
    private String description;
    private Drawable image;

    public Person(){}

    public Person(String fname, String lname, String role, String description, Drawable image) {
        this.firstname = fname;
        this.lastname = lname;
        this.role = role;
        this.description = description;
        this.image = image;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getName() {
        return firstname + " " + lastname;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Drawable getImage() {
        return image;
    }

    public void setImage(Drawable image) {
        this.image = image;
    }
}