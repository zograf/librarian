package com.librarian.dto;

import java.util.List;

public class MarkAsReadDTO {
    public Long id;
    public boolean liked;
    public List<SubjectDTO> subjects;

    public MarkAsReadDTO() {}
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public boolean isLiked() {
        return liked;
    }
    public void setLiked(boolean liked) {
        this.liked = liked;
    }
    public List<SubjectDTO> getSubjects() {
        return subjects;
    }
    public void setSubjects(List<SubjectDTO> subjects) {
        this.subjects = subjects;
    }
    
}
