package com.librarian.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.librarian.dto.BookDTO;
import com.librarian.repository.BooksRepo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class BooksService {

    Logger logger = LoggerFactory.getLogger(BooksService.class);

    @Autowired
    private BooksRepo bookRepository;

    public List<BookDTO> findByName(String phrase) {
        return bookRepository.findByTitleContains(phrase.toLowerCase()).stream().map(BookDTO::new).collect(Collectors.toList());
    }

    public List<BookDTO> findByAuthor(Long authorId) {
        return bookRepository.findAllByAuthorId(authorId).stream().map(BookDTO::new).collect(Collectors.toList());
    }

}
