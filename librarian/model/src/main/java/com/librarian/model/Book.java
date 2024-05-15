package com.librarian.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id; 

    @Column
    public String key;

    @Column
    public String title;

    //@ManyToOne(cascade = CascadeType.MERGE)
    @ManyToOne
    public Subject category;

    //@ManyToMany(cascade = CascadeType.MERGE)
    @ManyToMany
    public List<Author> authors;

    //@OneToMany(cascade = CascadeType.MERGE)
    @ManyToMany
    public List<Subject> subjects;

    @Column
    public String description;

    @Column
    public String firstSentence;

    @Column
    public String subtitle;

    @Column
    public Integer firstPublishedYear;

    @Column
    public String cover;

    @Column
    public EAge age;

    @OneToMany(cascade = CascadeType.MERGE)
    public List<Rating> ratings;

    @Transient
    public Long categoryId;

    public Book() {

    }

    public Book(String key, String title, Subject category, List<Author> authors, List<Subject> subjects, String description,
            String firstSentence, String subtitle, Integer firstPublishedYear, String cover, EAge age) {
        this.key = key;
        this.title = title;
        this.category = category;
        this.authors = authors;
        this.subjects = subjects;
        this.description = description;
        this.firstSentence = firstSentence;
        this.subtitle = subtitle;
        this.firstPublishedYear = firstPublishedYear;
        this.cover = cover;
        this.age = age;
        this.ratings = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFirstSentence() {
        return firstSentence;
    }

    public void setFirstSentence(String firstSentence) {
        this.firstSentence = firstSentence;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public Integer getFirstPublishedYear() {
        return firstPublishedYear;
    }

    public void setFirstPublishedYear(Integer firstPublishedYear) {
        this.firstPublishedYear = firstPublishedYear;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public EAge getAge() {
        return age;
    }

    public void setAge(EAge age) {
        this.age = age;
    }

    public List<Rating> getRatings() {
        return ratings;
    }

    public void setRatings(List<Rating> ratings) {
        this.ratings = ratings;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public Subject getCategory() {
        return category;
    }

    public void setCategory(Subject category) {
        this.category = category;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
    
}
