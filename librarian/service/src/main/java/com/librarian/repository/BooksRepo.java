package com.librarian.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.librarian.model.Book;

@Repository
public interface BooksRepo extends JpaRepository<Book, Long> {
    List<Book> findByKey(String key);

    @Query("SELECT DISTINCT book FROM Book book JOIN FETCH book.subjects JOIN FETCH book.authors WHERE LOWER(book.title) LIKE %:phrase%")
    List<Book> findByTitleContains(@Param("phrase") String phrase);

    @Query("SELECT DISTINCT book FROM Book book JOIN FETCH book.subjects JOIN FETCH book.authors")
    List<Book> findAllBooks();
}
