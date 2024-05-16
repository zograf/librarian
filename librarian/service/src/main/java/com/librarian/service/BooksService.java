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

    //@Transactional(propagation=Propagation.REQUIRED, readOnly=true, noRollbackFor=Exception.class) - Ovo je mega sporo i ima slican problem kao join fetch
    public List<BookDTO> findByName(String phrase) {
        //List<Book> books = bookRepository.findByTitleContains(phrase);
        //for (Book b : books) {
        //    logger.info(Integer.toString(b.getSubjects().size()));
        //}
        //logger.info(Integer.toString(books.size()));
        return bookRepository.findByTitleContains(phrase).stream().map(BookDTO::new).collect(Collectors.toList());
        //return new ArrayList<>();
    }
}
