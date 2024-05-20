package com.librarian.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.influxdb.annotations.Column;

@Entity
public class ReadBook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id; 

    @Column
    @OneToOne
    public Book book;

    @Column
    public Boolean didLike;

    public ReadBook() {
    }

    public ReadBook(Book book, Boolean didLike) {
        this.book = book;
        this.didLike = didLike;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Boolean getDidLike() {
        return didLike;
    }

    public void setDidLike(Boolean didLike) {
        this.didLike = didLike;
    }
    
}
