package com.librarian.service;

import org.apache.http.HttpStatus;
import org.apache.http.client.HttpResponseException;
import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.librarian.dto.MarkAsReadDTO;
import com.librarian.dto.SubjectDTO;
import com.librarian.dto.UserPreferencesDTO;
import com.librarian.model.Author;
import com.librarian.model.Book;
import com.librarian.model.ETargetYear;
import com.librarian.model.ReadBook;
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
    private UserPreferencesRepo userPreferencesRepo;
    @Autowired
    private ReadBookRepo readBookRepo;
    @Autowired
    private BooksRepo booksRepo;

    Logger logger = LoggerFactory.getLogger(UserPreferencesService.class);

    private UserPreferences _get(String username) {
        logger.info("Fetching the user with username: " + username);
        User user = userRepo.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
        return _get(user.getPreferences().getId());
    }
    private UserPreferences _get(Long id) {
        try {
            UserPreferences prefs = userPreferencesRepo.findAllById(id).get(0);
            logger.debug("Found items of size: " + prefs.getLibrary().size() + ":", prefs.getLibrary());
            return prefs;
        }
        catch(IndexOutOfBoundsException ex) {
            throw new UsernameNotFoundException("User Preferences Not Found for id: " + id);
        }
    }
    private UserPreferencesDTO _save(UserPreferences preferences) {
        return new UserPreferencesDTO(_get(repo.save(preferences).getId()));
    }

    public UserPreferencesDTO get(String username) {
        return new UserPreferencesDTO(_get(username));
    }

    public UserPreferencesDTO updateMainInformation(String username, Integer age, ETargetYear targetYear) {
        UserPreferences preferences = _get(username);
        preferences.setAge(age);
        preferences.setTargetYear(targetYear);
        return _save(preferences);
    }

    public UserPreferencesDTO addAdditionalSubject(String username, Long subjectId) throws HttpResponseException {
        UserPreferences preferences = _get(username);
        Subject subject = subjectsRepo.findById(subjectId).orElseThrow(() -> new HttpResponseException(HttpStatus.SC_NOT_FOUND, "Subject not found"));
        preferences.getAdditionalSubjects().add(subject);
        return _save(preferences);
    }

    public UserPreferencesDTO deleteAdditionalSubject(String username, Long subjectId) throws HttpResponseException {
        UserPreferences preferences = _get(username);
        preferences.getAdditionalSubjects().removeIf((item) -> item.getId().equals(subjectId));
        return _save(preferences);
    }

    public UserPreferencesDTO addLikedSubject(String username, Long subjectId) throws HttpResponseException {
        UserPreferences preferences = _get(username);
        Subject subject = subjectsRepo.findById(subjectId).orElseThrow(() -> new HttpResponseException(HttpStatus.SC_NOT_FOUND, "Subject not found"));
        preferences.getLikedSubjects().add(subject);        
        return _save(preferences);

    }

    public UserPreferencesDTO deleteLikedSubject(String username, Long subjectId) throws HttpResponseException {
        UserPreferences preferences = _get(username);
        preferences.getLikedSubjects().removeIf((item) -> item.getId().equals(subjectId));
        return _save(preferences);
    }

    public UserPreferencesDTO addLikedAuthor(String username, Long authorId) throws HttpResponseException {
        UserPreferences preferences = _get(username);
        Author author = authorsRepo.findById(authorId).orElseThrow(() -> new HttpResponseException(HttpStatus.SC_NOT_FOUND, "Author not found"));
        preferences.getLikedAuthors().add(author);
        return _save(preferences);
    }

    public UserPreferencesDTO deleteLikedAuthor(String username, Long authorId) throws HttpResponseException {
        UserPreferences preferences = _get(username);
        preferences.getLikedAuthors().removeIf((item) -> item.getId().equals(authorId));
        return _save(preferences);
    }

    public UserPreferencesDTO addBookToLibrary(String username, Long bookId) throws HttpResponseException {
        UserPreferences preferences = _get(username);
        Book book = booksRepo.findById(bookId).orElseThrow(() -> new HttpResponseException(HttpStatus.SC_NOT_FOUND, "Book not found"));
        preferences.getLibrary().add(book);
        return _save(preferences);
    }

    public UserPreferencesDTO removeBookFromLibrary(String username, Long bookId) throws HttpResponseException {
        UserPreferences preferences = _get(username);
        preferences.getLibrary().removeIf((item) -> item.getId().equals(bookId));
        return _save(preferences);
    }

    public UserPreferencesDTO markReadBook(String username, MarkAsReadDTO readBook) throws HttpResponseException {
        UserPreferences preferences = _get(username);
        preferences.getLibrary().removeIf((item) -> item.getId().equals(readBook.getId()));
        Book book = booksRepo.findById(readBook.getId()).orElseThrow(() -> new HttpResponseException(HttpStatus.SC_NOT_FOUND, "Book not found"));
        ReadBook read = new ReadBook(book, readBook.isLiked());
        preferences.getReadBooks().add(readBookRepo.save(read));
        for (SubjectDTO target : readBook.getSubjects()) {
            Subject subject = subjectsRepo.findById(target.getId()).orElseThrow(() -> new HttpResponseException(HttpStatus.SC_NOT_FOUND, "Subject " + target.getKeyword() + " not found"));
            if (readBook.isLiked()) preferences.getLikedSubjects().add(subject);
            else preferences.getDislikedSubjects().add(subject);
        }
        return _save(preferences);
    }

}
