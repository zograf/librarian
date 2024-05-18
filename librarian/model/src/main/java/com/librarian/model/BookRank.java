package com.librarian.model;

public class BookRank {
    public Book book;
    public Integer rating;
    public UserPreferences userPreferences;


    // -1 -> For deleting
    // 0 -> Created
    // 1 -> Passed the main cat filter
    public Integer progress;

    public BookRank() {

    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public UserPreferences getUserPreferences() {
        return userPreferences;
    }

    public void setUserPreferences(UserPreferences userPreferences) {
        this.userPreferences = userPreferences;
    }

    public Integer getProgress() {
        return progress;
    }

    public void setProgress(Integer progress) {
        this.progress = progress;
    }
}