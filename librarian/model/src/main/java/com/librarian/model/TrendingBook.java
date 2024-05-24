package com.librarian.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.influxdb.annotations.Column;

@Entity
public class TrendingBook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id; 

    @Column
    @OneToOne
    public Book book;

    @Column
    public Integer likedBy;

    @Column
    public boolean newInTrending;

    public TrendingBook() {
    }

    public TrendingBook(Book book, Integer likedBy, boolean newInTrending) {
        this.book = book;
        this.likedBy = likedBy;
        this.newInTrending = newInTrending;
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

    public Integer getLikedBy() {
        return likedBy;
    }

    public void setLikedBy(Integer likedBy) {
        this.likedBy = likedBy;
    }

    public boolean isNewInTrending() {
        return newInTrending;
    }

    public void setNewInTrending(boolean newInTrending) {
        this.newInTrending = newInTrending;
    }
}
