package com.librarian.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.librarian.model.EGender;
import com.librarian.model.UserPreferences;

public class UserPreferencesDTO {
    public Long id;
    // readBooks
    // library
    public List<SubjectDTO> likedSubjects;
    public List<SubjectDTO> additionalSubjects;
    // likedAuthors
    public Integer age;
    // targetYear
    public EGender gender;

    public UserPreferencesDTO(UserPreferences preferences) {
        this.id = preferences.id;
        //this.likedSubjects = preferences.likedSubjects.stream().map(SubjectDTO::new).collect(Collectors.toList());
        this.additionalSubjects = preferences.additionalSubjects.stream().map(SubjectDTO::new).collect(Collectors.toList());
        this.age = preferences.age;
        this.gender = preferences.gender;
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
