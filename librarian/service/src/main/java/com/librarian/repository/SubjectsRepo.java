package com.librarian.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.librarian.model.Subject;
import java.util.List;
import java.util.Optional;

@Repository
public interface SubjectsRepo extends JpaRepository<Subject, Long> {
    List<Subject> findByKeywordContainsIgnoreCase(String phrase);
    List<Subject> findByParent(String parent);
    Optional<Subject> findByKeyword(String keyword);
}
