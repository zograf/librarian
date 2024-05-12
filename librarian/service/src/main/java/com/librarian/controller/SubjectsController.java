package com.librarian.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.librarian.dto.SubjectDTO;
import com.librarian.service.SubjectsService;

import org.springframework.web.bind.annotation.PostMapping;


@RestController
@RequestMapping(value = "/api/subjects")
public class SubjectsController {

    Logger logger = LoggerFactory.getLogger(SubjectsController.class);

    @Autowired
    private SubjectsService subjectsService;

    @PostMapping("/like")
    public List<SubjectDTO> getFilteredKeywords(@RequestParam String phrase) {
        return subjectsService.findByKeyword(phrase);
    }
    
}
