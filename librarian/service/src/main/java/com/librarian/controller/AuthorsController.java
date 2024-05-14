package com.librarian.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.librarian.dto.AuthorDTO;
import com.librarian.service.AuthorsService;

@RestController()
@RequestMapping(value = "/api/authors")
public class AuthorsController {

    @Autowired
    private AuthorsService service;

    @PostMapping("/like")
    public List<AuthorDTO> getFiltered(@RequestParam String phrase) {
        return service.findByKeyword(phrase);
    }

}
