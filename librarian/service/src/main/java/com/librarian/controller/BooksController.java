package com.librarian.controller;

import org.springframework.web.bind.annotation.RestController;

import com.librarian.dto.BookDTO;
import com.librarian.service.BooksService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping(value = "/api/books")
public class BooksController {

    @Autowired
    private BooksService service;

    @PostMapping("/like")
    public List<BookDTO> getFiltered(@RequestParam String phrase) {
        return service.findByName(phrase);
    }
}
