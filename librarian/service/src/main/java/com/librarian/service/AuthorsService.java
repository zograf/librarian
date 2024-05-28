package com.librarian.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.librarian.dto.AuthorDTO;
import com.librarian.repository.AuthorsRepo;

@Service
public class AuthorsService {

    @Autowired 
    private AuthorsRepo repo;

    public List<AuthorDTO> findByKeyword(String phrase) {
        return repo.findByNameContainsIgnoreCase(phrase).stream().map(AuthorDTO::new).collect(Collectors.toList());
    }

}
