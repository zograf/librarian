package com.librarian.service;

import org.apache.http.HttpStatus;
import org.apache.http.client.HttpResponseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.librarian.dto.UserPreferencesDTO;
import com.librarian.model.Author;
import com.librarian.model.Book;
import com.librarian.model.ETargetYear;
import com.librarian.model.Subject;
import com.librarian.model.User;
import com.librarian.model.UserPreferences;
import com.librarian.repository.AuthorsRepo;
import com.librarian.repository.BooksRepo;
import com.librarian.repository.IUserRepository;
import com.librarian.repository.ReadBookRepo;
import com.librarian.repository.SubjectsRepo;
import com.librarian.repository.UserPreferencesRepo;

@Service
public class UserPreferencesService {

    @Autowired 
    private UserPreferencesRepo repo;
    @Autowired 
    private  SubjectsRepo subjectsRepo;
    @Autowired 
    private  AuthorsRepo authorsRepo;
    @Autowired 
    private  IUserRepository userRepo;
    @Autowired
    private ReadBookRepo readBookRepo;
    @Autowired
    private BooksRepo booksRepo;

    Logger logger = LoggerFactory.getLogger(UserPreferencesService.class);

    private UserPreferences _get(String username) {
        User user = userRepo.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
        UserPreferences prefs = user.getPreferences();
        prefs.getLibrary();
        return user.getPreferences();
    }

    public UserPreferencesDTO get(String username) {
        return new UserPreferencesDTO(_get(username));
    }

    public UserPreferencesDTO updateMainInformation(String username, Integer age, ETargetYear targetYear) {
        UserPreferences preferences = _get(username);
        preferences.setAge(age);
        preferences.setTargetYear(targetYear);
        return new UserPreferencesDTO(repo.save(preferences));
    }

    public UserPreferencesDTO addAdditionalSubject(String username, Long subjectId) throws HttpResponseException {
        UserPreferences preferences = _get(username);
        Subject subject = subjectsRepo.findById(subjectId).orElseThrow(() -> new HttpResponseException(HttpStatus.SC_NOT_FOUND, "Subject not found"));
        preferences.getAdditionalSubjects().add(subject);
        return new UserPreferencesDTO(repo.save(preferences));
    }

    public UserPreferencesDTO deleteAdditionalSubject(String username, Long subjectId) throws HttpResponseException {
        UserPreferences preferences = _get(username);
        preferences.getAdditionalSubjects().removeIf((item) -> item.getId().equals(subjectId));
        return new UserPreferencesDTO(repo.save(preferences));
    }

    public UserPreferencesDTO addLikedSubject(String username, Long subjectId) throws HttpResponseException {
        UserPreferences preferences = _get(username);
        Subject subject = subjectsRepo.findById(subjectId).orElseThrow(() -> new HttpResponseException(HttpStatus.SC_NOT_FOUND, "Subject not found"));
        preferences.getLikedSubjects().add(subject);
        return new UserPreferencesDTO(repo.save(preferences));
    }

    public UserPreferencesDTO deleteLikedSubject(String username, Long subjectId) throws HttpResponseException {
        UserPreferences preferences = _get(username);
        preferences.getLikedSubjects().removeIf((item) -> item.getId().equals(subjectId));
        return new UserPreferencesDTO(repo.save(preferences));
    }

    public UserPreferencesDTO addLikedAuthor(String username, Long authorId) throws HttpResponseException {
        UserPreferences preferences = _get(username);
        Author author = authorsRepo.findById(authorId).orElseThrow(() -> new HttpResponseException(HttpStatus.SC_NOT_FOUND, "Author not found"));
        preferences.getLikedAuthors().add(author);
        return new UserPreferencesDTO(repo.save(preferences));
    }

    public UserPreferencesDTO deleteLikedAuthor(String username, Long authorId) throws HttpResponseException {
        UserPreferences preferences = _get(username);
        preferences.getLikedAuthors().removeIf((item) -> item.getId().equals(authorId));
        return new UserPreferencesDTO(repo.save(preferences));
    }

    public UserPreferencesDTO addBookToLibrary(String username, Long bookId) throws HttpResponseException {
        UserPreferences preferences = _get(username);
        Book book = booksRepo.findById(bookId).orElseThrow(() -> new HttpResponseException(HttpStatus.SC_NOT_FOUND, "Book not found"));
        preferences.getLibrary().add(book);
        return new UserPreferencesDTO(repo.save(preferences));
    }

    public UserPreferencesDTO removeBookFromLibrary(String username, Long bookId) throws HttpResponseException {
        UserPreferences preferences = _get(username);
        preferences.getLibrary().removeIf((item) -> item.getId().equals(bookId));
        return new UserPreferencesDTO(repo.save(preferences));
    }
}
