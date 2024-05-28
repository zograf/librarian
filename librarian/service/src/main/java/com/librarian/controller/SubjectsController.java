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
import org.springframework.web.bind.annotation.GetMapping;



@RestController
@RequestMapping(value = "/api/subjects")
public class SubjectsController {

    Logger logger = LoggerFactory.getLogger(SubjectsController.class);

    @Autowired
    private SubjectsService service;

    @PostMapping("/like")
    public List<SubjectDTO> getFiltered(@RequestParam String phrase) {
        return service.findByKeyword(phrase);
    }

    @GetMapping("/main")
    public List<SubjectDTO> getMainCategories() {
        return service.findMainCategories();
    }
    
    
}
