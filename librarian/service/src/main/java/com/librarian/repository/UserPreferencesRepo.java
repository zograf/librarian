package com.librarian.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.librarian.model.UserPreferences;

@Repository
public interface UserPreferencesRepo extends JpaRepository<UserPreferences, Long> {
    Optional<UserPreferences> findById(Long id);
}
