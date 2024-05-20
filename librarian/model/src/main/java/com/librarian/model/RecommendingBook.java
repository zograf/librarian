package com.librarian.model;

public class RecommendingBook {
    public Book book;
    
    public RecommendingBook(Book book) {
        this.book = book;
    }

    public RecommendingBook() {
        
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
}