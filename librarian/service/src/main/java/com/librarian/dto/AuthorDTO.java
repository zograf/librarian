package com.librarian.dto;

import com.librarian.model.Author;

public class AuthorDTO {
    public Long id;
    public String name;
    
    public AuthorDTO() { }

    public AuthorDTO(Author author) {
        this.id = author.getId();
        this.name = author.getName();
    }
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    
}
