package com.librarian.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.librarian.model.TrendingBook;

@Repository
public interface TrendingBooksRepo extends JpaRepository<TrendingBook, Long> {

    @Query("SELECT DISTINCT tbook FROM TrendingBook tbook JOIN FETCH tbook.book book JOIN FETCH book.authors JOIN FETCH book.subjects")
    List<TrendingBook> findAll();

    @Query("SELECT DISTINCT tbook FROM TrendingBook tbook JOIN FETCH tbook.book book JOIN FETCH book.authors JOIN FETCH book.subjects WHERE tbook.newInTrending = true")
    List<TrendingBook> findAllByNewInTrending();
}
