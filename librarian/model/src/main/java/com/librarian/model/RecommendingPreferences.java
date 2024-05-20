package com.librarian.model;

public class RecommendingPreferences {
    public UserPreferences up;
    
    public RecommendingPreferences(UserPreferences up) {
        this.up = up;
    }

    public RecommendingPreferences() {
        
    }

    public UserPreferences getUp() {
        return up;
    }

    public void setUp(UserPreferences up) {
        this.up = up;
    }
}
