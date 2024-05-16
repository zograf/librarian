package com.librarian.dto;

import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.Hibernate;

import com.librarian.model.Book;
import com.librarian.model.EAge;

public class BookDTO {
    public Long id; 
    public String title;
    public SubjectDTO category;
    public List<AuthorDTO> authors;
    public List<SubjectDTO> subjects;
    public String description;
    public String firstSentence;
    public String subtitle;
    public Integer firstPublishedYear;
    public String cover;
    public EAge age;
    // ratings missing

    public BookDTO() { }

    public BookDTO(Book book) {
        this.id = book.id;
        this.title = book.title;
        this.category = new SubjectDTO(book.category);
        //this.authors = book.authors.stream().map(AuthorDTO::new).collect(Collectors.toList());
        this.subjects = book.getSubjects().stream().map(SubjectDTO::new).collect(Collectors.toList());
        this.description = book.description;
        this.firstSentence = book.firstSentence;
        this.firstPublishedYear = book.firstPublishedYear;
        this.cover = book.cover;
        this.age = book.age;
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

    public SubjectDTO getCategory() {
        return category;
    }

    public void setCategory(SubjectDTO category) {
        this.category = category;
    }

    public List<AuthorDTO> getAuthors() {
        return authors;
    }

    public void setAuthors(List<AuthorDTO> authors) {
        this.authors = authors;
    }

    public List<SubjectDTO> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<SubjectDTO> subjects) {
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

}
