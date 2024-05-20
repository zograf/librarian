package com.librarian.controller;

import org.apache.http.client.HttpResponseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.librarian.dto.MarkAsReadDTO;
import com.librarian.dto.UserPreferencesDTO;
import com.librarian.model.ETargetYear;
import com.librarian.service.UserPreferencesService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping(value = "/api/user/preferences")
public class UserPreferencesController {

    Logger logger = LoggerFactory.getLogger(UserPreferencesController.class);

    @Autowired
    private UserPreferencesService service;

    @GetMapping("/")
    public ResponseEntity<UserPreferencesDTO> getPreferences(@AuthenticationPrincipal UserDetails user) throws HttpResponseException {
        logger.info("Entry -> Getting prefs for user: " + user.getUsername());
        return new ResponseEntity<UserPreferencesDTO>(service.get(user.getUsername()), HttpStatus.OK);
    }

    @PutMapping("/main")
    public ResponseEntity<UserPreferencesDTO> putMethodName(@AuthenticationPrincipal UserDetails user, @RequestParam Integer age, @RequestParam ETargetYear targetYear) throws HttpResponseException {
        return new ResponseEntity<UserPreferencesDTO>(service.updateMainInformation(user.getUsername(), age, targetYear), HttpStatus.OK);
    }

    @PutMapping("/subjects/additional/{subjectId}")
    public ResponseEntity<UserPreferencesDTO> addAdditional(@AuthenticationPrincipal UserDetails user, @PathVariable Long subjectId) throws HttpResponseException {
        return new ResponseEntity<UserPreferencesDTO>(service.addAdditionalSubject(user.getUsername(), subjectId), HttpStatus.OK);
    }

    @DeleteMapping("/subjects/additional/{subjectId}")
    public ResponseEntity<UserPreferencesDTO> deleteAdditional(@AuthenticationPrincipal UserDetails user, @PathVariable Long subjectId) throws HttpResponseException {
        return new ResponseEntity<UserPreferencesDTO>(service.deleteAdditionalSubject(user.getUsername(), subjectId), HttpStatus.OK);
    }

    @PutMapping("/subjects/liked/{subjectId}")
    public ResponseEntity<UserPreferencesDTO> addLiked(@AuthenticationPrincipal UserDetails user, @PathVariable Long subjectId) throws HttpResponseException {
        return new ResponseEntity<UserPreferencesDTO>(service.addLikedSubject(user.getUsername(), subjectId), HttpStatus.OK);
    }

    @DeleteMapping("/subjects/liked/{subjectId}")
    public ResponseEntity<UserPreferencesDTO> deleteLiked(@AuthenticationPrincipal UserDetails user, @PathVariable Long subjectId) throws HttpResponseException {
        return new ResponseEntity<UserPreferencesDTO>(service.deleteLikedSubject(user.getUsername(), subjectId), HttpStatus.OK);
    }
    
    @PutMapping("/authors/{authorId}")
    public ResponseEntity<UserPreferencesDTO> addLikedAuthor(@AuthenticationPrincipal UserDetails user, @PathVariable Long authorId) throws HttpResponseException {
        return new ResponseEntity<UserPreferencesDTO>(service.addLikedAuthor(user.getUsername(), authorId), HttpStatus.OK);
    }

    @DeleteMapping("/authors/{authorId}")
    public ResponseEntity<UserPreferencesDTO> deleteLikedAuthor(@AuthenticationPrincipal UserDetails user, @PathVariable Long authorId) throws HttpResponseException {
        return new ResponseEntity<UserPreferencesDTO>(service.deleteLikedAuthor(user.getUsername(), authorId), HttpStatus.OK);
    }

    @PutMapping("/library/{bookId}")
    public ResponseEntity<UserPreferencesDTO> addBookToLibrary(@AuthenticationPrincipal UserDetails user, @PathVariable Long bookId) throws HttpResponseException {
        return new ResponseEntity<UserPreferencesDTO>(service.addBookToLibrary(user.getUsername(), bookId), HttpStatus.OK);
    }

    @DeleteMapping("/library/{bookId}")
    public ResponseEntity<UserPreferencesDTO> removeBookFromLibrary(@AuthenticationPrincipal UserDetails user, @PathVariable Long bookId) throws HttpResponseException {
        return new ResponseEntity<UserPreferencesDTO>(service.removeBookFromLibrary(user.getUsername(), bookId), HttpStatus.OK);
    }

    @PostMapping("/book/read")
    public ResponseEntity<UserPreferencesDTO> markBookAsRead(@AuthenticationPrincipal UserDetails user, @RequestBody MarkAsReadDTO dto) throws HttpResponseException {
        return new ResponseEntity<UserPreferencesDTO>(service.markReadBook(user.getUsername(), dto), HttpStatus.OK);
    }
    
}
