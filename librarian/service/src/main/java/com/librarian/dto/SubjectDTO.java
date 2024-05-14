package com.librarian.dto;

import com.librarian.model.Subject;

public class SubjectDTO {

    public Long id;
    public String parent;
    public String keyword;
    public Integer relevance;

    public SubjectDTO() { }

    public SubjectDTO(Subject subject) {
        this.id = subject.getId();
        this.parent = subject.getParent();
        this.keyword = subject.getKeyword();
        this.relevance = subject.getRelevance();
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getParent() {
        return parent;
    }
    public void setParent(String parent) {
        this.parent = parent;
    }
    public String getKeyword() {
        return keyword;
    }
    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
    public Integer getRelevance() {
        return relevance;
    }
    public void setRelevance(Integer relevance) {
        this.relevance = relevance;
    }
    
}
