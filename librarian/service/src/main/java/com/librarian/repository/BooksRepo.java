package com.librarian.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.librarian.model.Book;

@Repository
public interface BooksRepo extends JpaRepository<Book, Long> {
    List<Book> findByKey(String key);

    //@Query("SELECT book FROM Book book JOIN FETCH book.subjects WHERE book.title LIKE :phrase") - Ovo bi mozda radilo ali dobavi onoliko knjiga koliko one imaju subjekata
    List<Book> findByTitleContains(@Param("phrase") String phrase);
}
