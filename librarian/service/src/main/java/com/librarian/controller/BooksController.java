package com.librarian.controller;

import org.springframework.web.bind.annotation.RestController;

import com.librarian.dto.BookDTO;
import com.librarian.service.BooksService;
import com.librarian.service.TrendingService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping(value = "/api/books")
public class BooksController {

    @Autowired
    private BooksService service;

    @Autowired
    private TrendingService trendingService;

    @PostMapping("/like")
    public List<BookDTO> getFiltered(@RequestParam String phrase) {
        return service.findByName(phrase);
    }

    @PostMapping("/author")
    public List<BookDTO> findByAuthor(@RequestParam Long authorId) {
        return service.findByAuthor(authorId);
    }
    

    @GetMapping("/trending")
    public List<BookDTO> getTrending() {
        return trendingService.get();
    }
    
}
