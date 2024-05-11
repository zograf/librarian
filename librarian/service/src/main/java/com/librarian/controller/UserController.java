package com.librarian.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Size;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.librarian.dto.LoginDTO;
import com.librarian.dto.RegisterDTO;
import com.librarian.dto.TokenDTO;
import com.librarian.model.User;
import com.librarian.repository.IUserRepository;
import com.librarian.service.UserService;

@RestController
@RequestMapping(value = "/api/user")
public class UserController {

    Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @PostMapping(value="register", consumes = "application/json")
    public ResponseEntity<String> registerUser(@RequestBody RegisterDTO dto) {
        logger.info("Registering new user with email {}...", dto.getEmail());

        User user = userService.register(dto);
        logger.info("Successfully registered user {}", user.getEmail());

        logger.info("Returning status 200!");
        return new ResponseEntity<>(user.email, HttpStatus.OK);
    }

    @PostMapping(value = "login", consumes = "application/json")
    public ResponseEntity<TokenDTO> login(@RequestBody LoginDTO loginDto, HttpServletRequest request) {
        logger.info("Trying login with email {}...", loginDto.getEmail());
        return ResponseEntity.ok(userService.login(loginDto));
    }

    @GetMapping(value = "test")
    public ResponseEntity<String> getSomething() {
        logger.info("GET REQUEST WENT THROUGH");
        return ResponseEntity.ok("HELLO WORLD");
    }

}