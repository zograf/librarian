package com.librarian.model;

import javax.persistence.*;

import org.kie.api.definition.type.Position;

@Entity
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column
    @Position(0)
    public String parent;

    @Column
    @Position(1)
    public String keyword;

    @Column
    public Integer relevance;

    public Subject() {
        
    }

    public Subject(String parent, String keyword, Integer relevance) {
        this.parent = parent;
        this.keyword = keyword;
        this.relevance = relevance;
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
