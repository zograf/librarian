package com.librarian.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.librarian.model.UserPreferences;

@Repository
public interface UserPreferencesRepo extends JpaRepository<UserPreferences, Long> {

    Optional<UserPreferences> findById(@Param("id") Long id);

    @Query("SELECT DISTINCT prefs FROM UserPreferences prefs " +
       "LEFT JOIN FETCH prefs.library book " +
       "LEFT JOIN FETCH book.subjects " +
       "LEFT JOIN FETCH book.authors " +
       "LEFT JOIN FETCH prefs.readBooks read " +
       "LEFT JOIN FETCH read.book read_book " +
       "LEFT JOIN FETCH read_book.subjects " +
       "LEFT JOIN FETCH read_book.authors " +
       "WHERE prefs.id = :id")
    List<UserPreferences> findAllByIdCustom(@Param("id") Long id);

    @Query("SELECT DISTINCT prefs FROM UserPreferences prefs " +
       "LEFT JOIN FETCH prefs.library book " +
       "LEFT JOIN FETCH book.subjects " +
       "LEFT JOIN FETCH book.authors " +
       "LEFT JOIN FETCH prefs.readBooks read " +
       "LEFT JOIN FETCH read.book read_book " +
       "LEFT JOIN FETCH read_book.subjects " +
       "LEFT JOIN FETCH read_book.authors")
    List<UserPreferences> findAll();
}
