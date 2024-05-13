package com.librarian.controller;

import org.apache.http.client.HttpResponseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.librarian.dto.UserPreferencesDTO;
import com.librarian.service.UserPreferencesService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;



@RestController
@RequestMapping(value = "/api/user/preferences")
public class UserPreferencesController {

    @Autowired
    private UserPreferencesService service;

    @GetMapping("/")
    public ResponseEntity<UserPreferencesDTO> getPreferences(@AuthenticationPrincipal UserDetails user) {
        return new ResponseEntity<UserPreferencesDTO>(service.get(user.getUsername()), HttpStatus.OK);
    }

    @PutMapping("/additional/{subjectId}")
    public ResponseEntity<UserPreferencesDTO> addAdditional(@AuthenticationPrincipal UserDetails user, @PathVariable Long subjectId) throws HttpResponseException{
        return new ResponseEntity<UserPreferencesDTO>(service.addAdditionalSubject(user.getUsername(), subjectId), HttpStatus.OK);
    }

    @DeleteMapping("/additional/{subjectId}")
    public ResponseEntity<UserPreferencesDTO> deleteAdditional(@AuthenticationPrincipal UserDetails user, @PathVariable Long subjectId) throws HttpResponseException{
        return new ResponseEntity<UserPreferencesDTO>(service.deleteAdditionalSubject(user.getUsername(), subjectId), HttpStatus.OK);
    }
}
