package com.librarian.dto;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.librarian.model.EGender;
import com.librarian.model.ETargetYear;
import com.librarian.model.UserPreferences;

public class UserPreferencesDTO {
    
    Logger logger = LoggerFactory.getLogger(UserPreferencesDTO.class);

    public Long id;
    public List<BookDTO> library;
    public List<BookDTO> readBooks;
    public List<SubjectDTO> likedSubjects;
    public List<SubjectDTO> dislikedSubjects;
    public List<SubjectDTO> additionalSubjects;
    public List<AuthorDTO> likedAuthors;
    public Integer age;
    public ETargetYear targetYear;
    public EGender gender;

    public UserPreferencesDTO(UserPreferences preferences) {
        this.id = preferences.id;
        this.library = preferences.getLibrary().stream().map(BookDTO::new).collect(Collectors.toList());
        this.readBooks = preferences.getReadBooks().stream().map(BookDTO::new).collect(Collectors.toList());
        this.likedSubjects = preferences.getLikedSubjects().stream().map(SubjectDTO::new).collect(Collectors.toList());
        this.dislikedSubjects = preferences.getDislikedSubjects().stream().map(SubjectDTO::new).collect(Collectors.toList());
        this.additionalSubjects = preferences.getAdditionalSubjects().stream().map(SubjectDTO::new).collect(Collectors.toList());
        this.likedAuthors = preferences.getLikedAuthors().stream().map(AuthorDTO::new).collect(Collectors.toList());
        this.age = preferences.age;
        this.targetYear = preferences.targetYear;
        this.gender = preferences.gender;
        logger.debug("Found: " + preferences.getLikedAuthors().size());
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public List<SubjectDTO> getLikedSubjects() {
        return likedSubjects;
    }
    public void setLikedSubjects(List<SubjectDTO> likedSubjects) {
        this.likedSubjects = likedSubjects;
    }
    public List<SubjectDTO> getAdditionalSubjects() {
        return additionalSubjects;
    }
    public void setAdditionalSubjects(List<SubjectDTO> additionalSubjects) {
        this.additionalSubjects = additionalSubjects;
    }
    public Integer getAge() {
        return age;
    }
    public void setAge(Integer age) {
        this.age = age;
    }

}
