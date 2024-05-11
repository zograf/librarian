package com.librarian.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.librarian.model.UserPreferences;

public interface UserPreferencesRepo extends JpaRepository<UserPreferences, Long> {

}
