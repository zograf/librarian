package com.librarian.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.librarian.model.Author;

@Repository
public interface AuthorsRepo extends JpaRepository<Author, Long> {
    List<Author> findByKey(String key);
    List<Author> findByNameContainsIgnoreCase(String phrase);
}