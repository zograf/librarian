package com.librarian.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.librarian.model.Book;

@Repository
public interface BooksRepo extends JpaRepository<Book, Long> {
    List<Book> findByKey(String key);
}
