package com.librarian.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.librarian.model.ReadBook;

@Repository
public interface ReadBookRepo extends JpaRepository<ReadBook, Long> {

}
