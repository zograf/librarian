package com.librarian.model;

import java.util.List;

public class UserPreferences {
    public Long id;
    public List<Book> readBooks;
    public List<Book> library;
    public List<Subject> likedSubjects;
    public List<Subject> additionalSubjects;
    public List<Author> likedAuthors;
    public Integer age;
    public ETargetYear targetYear;
    public EGender gender;

    public UserPreferences() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Book> getReadBooks() {
        return readBooks;
    }

    public void setReadBooks(List<Book> readBooks) {
        this.readBooks = readBooks;
    }

    public List<Book> getLibrary() {
        return library;
    }

    public void setLibrary(List<Book> library) {
        this.library = library;
    }

    public List<Subject> getLikedSubjects() {
        return likedSubjects;
    }

    public void setLikedSubjects(List<Subject> likedSubjects) {
        this.likedSubjects = likedSubjects;
    }

    public List<Subject> getAdditionalSubjects() {
        return additionalSubjects;
    }

    public void setAdditionalSubjects(List<Subject> additionalSubjects) {
        this.additionalSubjects = additionalSubjects;
    }

    public List<Author> getLikedAuthors() {
        return likedAuthors;
    }

    public void setLikedAuthors(List<Author> likedAuthors) {
        this.likedAuthors = likedAuthors;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public ETargetYear getTargetYear() {
        return targetYear;
    }

    public void setTargetYear(ETargetYear targetYear) {
        this.targetYear = targetYear;
    }

    public EGender getGender() {
        return gender;
    }

    public void setGender(EGender gender) {
        this.gender = gender;
    }
}
