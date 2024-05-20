package com.librarian.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.librarian.dto.BookDTO;
import com.librarian.model.Book;
import com.librarian.repository.BooksRepo;
import com.librarian.repository.SubjectsRepo;

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

}
