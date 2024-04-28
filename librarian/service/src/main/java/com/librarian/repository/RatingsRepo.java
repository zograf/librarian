package com.librarian.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.librarian.model.Rating;

public interface RatingsRepo extends JpaRepository<Rating, Long> {

}
