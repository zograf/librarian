package com.librarian.model;

public class Flag {
    public String name;
    public UserPreferences up; 

    public Flag() {

    }

    public Flag(String name, UserPreferences up) {
        this.name = name;
        this.up = up;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserPreferences getUp() {
        return up;
    }

    public void setUp(UserPreferences up) {
        this.up = up;
    }
}
