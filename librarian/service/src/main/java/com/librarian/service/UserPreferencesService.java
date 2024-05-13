package com.librarian.service;

import org.apache.http.HttpStatus;
import org.apache.http.client.HttpResponseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.librarian.dto.UserPreferencesDTO;
import com.librarian.model.Subject;
import com.librarian.model.User;
import com.librarian.model.UserPreferences;
import com.librarian.repository.IUserRepository;
import com.librarian.repository.SubjectsRepo;
import com.librarian.repository.UserPreferencesRepo;

@Service
public class UserPreferencesService {

    @Autowired UserPreferencesRepo repo;
    @Autowired SubjectsRepo subjectsRepo;
    @Autowired IUserRepository userRepo;

    private UserPreferences _get(String username) {
        User user = userRepo.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
        return user.getPreferences();
    }


    public UserPreferencesDTO get(String username) {
        return new UserPreferencesDTO(_get(username));
    }

    public UserPreferencesDTO addAdditionalSubject(String username, Long subjectId) throws HttpResponseException {
        UserPreferences preferences = _get(username);
        Subject subject = subjectsRepo.findById(subjectId).orElseThrow(() -> new HttpResponseException(HttpStatus.SC_NOT_FOUND, "Subject not found"));
        preferences.additionalSubjects.add(subject);
        return new UserPreferencesDTO(repo.save(preferences));
    }
}
