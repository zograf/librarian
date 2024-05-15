package com.librarian.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.librarian.dto.BookDTO;
import com.librarian.repository.BooksRepo;

@Service
public class BooksService {

    @Autowired
    private BooksRepo repo;

    //@Transactional(propagation=Propagation.REQUIRED, readOnly=true, noRollbackFor=Exception.class) - Ovo je mega sporo i ima slican problem kao join fetch
    public List<BookDTO> findByName(String phrase) {
        return repo.findByTitleContains(phrase).stream().map(BookDTO::new).collect(Collectors.toList());
    }
}
