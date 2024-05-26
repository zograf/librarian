package com.librarian.model;

public class PotentialTrendingBook {
    public Long id;
    public Integer likedBy;
    public boolean isTrending;
    
    public PotentialTrendingBook() {
    }
    
    public PotentialTrendingBook(Long id) {
        this.id = id;
        this.likedBy = 0;
        this.isTrending = false;
    }

    public PotentialTrendingBook(Long id, Integer likedBy) {
        this.id = id;
        this.likedBy = likedBy;
        this.isTrending = false;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Integer getLikedBy() {
        return likedBy;
    }
    public void setLikedBy(Integer likedBy) {
        this.likedBy = likedBy;
    }
    public boolean isTrending() {
        return isTrending;
    }
    public void setTrending(boolean isTrending) {
        this.isTrending = isTrending;
    }
    
}
