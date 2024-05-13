package com.librarian.model;

import java.util.Set;
import javax.persistence.*;


@Entity
public class UserPreferences {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @OneToMany
    public Set<Book> readBooks;

    @OneToMany
    public Set<Book> library;

    @OneToMany
    public Set<Subject> likedSubjects;

    @OneToMany(fetch = FetchType.EAGER)
    public Set<Subject> additionalSubjects;

    @OneToMany(fetch = FetchType.EAGER)
    public Set<Author> likedAuthors;

    @Column
    public Integer age;

    public ETargetYear targetYear;

    public EGender gender;

    public UserPreferences() { }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Book> getReadBooks() {
        return readBooks;
    }

    public void setReadBooks(Set<Book> readBooks) {
        this.readBooks = readBooks;
    }

    public Set<Book> getLibrary() {
        return library;
    }

    public void setLibrary(Set<Book> library) {
        this.library = library;
    }

    public Set<Subject> getLikedSubjects() {
        return likedSubjects;
    }

    public void setLikedSubjects(Set<Subject> likedSubjects) {
        this.likedSubjects = likedSubjects;
    }

    public Set<Subject> getAdditionalSubjects() {
        return additionalSubjects;
    }

    public void setAdditionalSubjects(Set<Subject> additionalSubjects) {
        this.additionalSubjects = additionalSubjects;
    }

    public Set<Author> getLikedAuthors() {
        return likedAuthors;
    }

    public void setLikedAuthors(Set<Author> likedAuthors) {
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
