package com.librarian.dto;

import java.util.List;

public class StatsDTO {
    public List<String> mostCommonCategories;

    public StatsDTO() { }

    public StatsDTO(List<String> mostCommonCategories) {
        this.mostCommonCategories = mostCommonCategories;
    }

    public List<String> getMostCommonCategories() {
        return mostCommonCategories;
    }

    public void setMostCommonCategories(List<String> mostCommonCategories) {
        this.mostCommonCategories = mostCommonCategories;
    }    
}
