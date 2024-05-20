package com.librarian.model;

public class Flag {
    public String name;
    public RecommendingPreferences rp; 

    public Flag() {

    }

    public Flag(String name, RecommendingPreferences rp) {
        this.name = name;
        this.rp = rp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RecommendingPreferences getRp() {
        return rp;
    }

    public void setRp(RecommendingPreferences rp) {
        this.rp = rp;
    }
}
