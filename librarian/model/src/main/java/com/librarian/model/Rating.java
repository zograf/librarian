package com.librarian.model;

import java.time.LocalDateTime;

import javax.persistence.*;

@Entity
public class Rating { 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @ManyToOne(cascade = CascadeType.MERGE)
    public Book book;

    @Column
    public Integer rating;

    @Column
    public LocalDateTime date;

    @Transient
    public String dateString;

    @Transient
    public Long bookId;

    public Rating() {

    }

    public Rating(Book book, Integer rating, LocalDateTime date) {
        this.book = book;
        this.rating = rating;
        this.date = date;
    }

    public Rating(Integer rating, LocalDateTime date) {
        this.rating = rating;
        this.date = date;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getDateString() {
        return dateString;
    }

    public void setDateString(String dateString) {
        this.dateString = dateString;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }
}
