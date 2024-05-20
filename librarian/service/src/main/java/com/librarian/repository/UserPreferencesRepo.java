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

    @Query("SELECT prefs FROM UserPreferences prefs JOIN FETCH prefs.library book JOIN FETCH book.subjects JOIN FETCH book.authors JOIN FETCH prefs.readBooks read JOIN FETCH read.book read_book JOIN FETCH read_book.subjects JOIN FETCH read_book.authors WHERE prefs.id = :id")
    List<UserPreferences> findAllByIdCustom(@Param("id") Long id);
}
