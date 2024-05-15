package com.librarian.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.librarian.dto.BookDTO;
import com.librarian.repository.BooksRepo;

@Service
public class BooksService {

    @Autowired
    private BooksRepo repo;

    public List<BookDTO> findByName(String phrase) {
        return repo.nadjiPoTitleLike(phrase).stream().map(BookDTO::new).collect(Collectors.toList());
    }
}
