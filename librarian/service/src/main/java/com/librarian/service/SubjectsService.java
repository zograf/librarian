package com.librarian.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.librarian.repository.SubjectsRepo;
import com.librarian.dto.SubjectDTO;

@Service
public class SubjectsService {

    @Autowired
    private SubjectsRepo subjectsRepo;

    public List<SubjectDTO> findByKeyword(String phrase) {
        return subjectsRepo.findByKeywordContains(phrase).stream().map(SubjectDTO::new).collect(Collectors.toList());
    }
}
