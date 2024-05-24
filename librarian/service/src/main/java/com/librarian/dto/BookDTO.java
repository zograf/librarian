package com.librarian.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.librarian.model.Book;
import com.librarian.model.EAge;
import com.librarian.model.ReadBook;
import com.librarian.model.TrendingBook;

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
    public Boolean liked;
    public Boolean newToTrending;

    public BookDTO() { 
        this.liked = null;
        this.newToTrending = null;
    }

    public BookDTO(Book book) {
        this.id = book.id;
        this.title = book.title;
        this.category = new SubjectDTO(book.category);
        this.authors = book.authors.stream().map(AuthorDTO::new).collect(Collectors.toList());
        this.subjects = book.getSubjects().stream().map(SubjectDTO::new).collect(Collectors.toList());
        this.description = book.description;
        this.firstSentence = book.firstSentence;
        this.firstPublishedYear = book.firstPublishedYear;
        this.cover = book.cover;
        this.age = book.age;
        this.liked = null;
        this.newToTrending = null;
    }

    public BookDTO(ReadBook readBook) {
        this.id = readBook.book.id;
        this.title = readBook.book.title;
        this.category = new SubjectDTO(readBook.book.category);
        this.authors = readBook.book.authors.stream().map(AuthorDTO::new).collect(Collectors.toList());
        this.subjects = readBook.book.getSubjects().stream().map(SubjectDTO::new).collect(Collectors.toList());
        this.description = readBook.book.description;
        this.firstSentence = readBook.book.firstSentence;
        this.firstPublishedYear = readBook.book.firstPublishedYear;
        this.cover = readBook.book.cover;
        this.age = readBook.book.age;
        this.liked = readBook.didLike;
        this.newToTrending = null;
    }

    public BookDTO(TrendingBook trending) {
        this.id = trending.book.id;
        this.title = trending.book.title;
        this.category = new SubjectDTO(trending.book.category);
        this.authors = trending.book.authors.stream().map(AuthorDTO::new).collect(Collectors.toList());
        this.subjects = trending.book.getSubjects().stream().map(SubjectDTO::new).collect(Collectors.toList());
        this.description = trending.book.description;
        this.firstSentence = trending.book.firstSentence;
        this.firstPublishedYear = trending.book.firstPublishedYear;
        this.cover = trending.book.cover;
        this.age = trending.book.age;
        this.liked = null;
        this.newToTrending = trending.newInTrending;
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
