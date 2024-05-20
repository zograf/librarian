package com.librarian.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.kie.api.runtime.KieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.librarian.dto.BookDTO;
import com.librarian.dto.LoginDTO;
import com.librarian.dto.RegisterDTO;
import com.librarian.dto.TokenDTO;
import com.librarian.model.Book;
import com.librarian.model.User;
import com.librarian.model.UserPreferences;
import com.librarian.repository.BooksRepo;
import com.librarian.repository.UserPreferencesRepo;
import com.librarian.service.RecommendationService;
import com.librarian.service.UserService;

@RestController
@RequestMapping(value = "/api/user")
public class UserController {

    Logger logger = LoggerFactory.getLogger(UserController.class);

    UserPreferences u;
    KieSession ksession;

    @Autowired
    private UserService userService;

    @Autowired
    private BooksRepo booksRepository;

    @Autowired
    private UserPreferencesRepo userPreferencesRepository;

    @Autowired
    private RecommendationService recommendationService;

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
    public List<BookDTO> getSomething() {
        List<UserPreferences> optional = userPreferencesRepository.findAllByIdCustom(1L);
        if (optional.size() != 0) {
            u = optional.get(0);
            List<Book> books = recommendationService.getRecommendedBooksForPreferences(u, 5);
            return books.stream().map(BookDTO::new).collect(Collectors.toList());
        }
        return new ArrayList<BookDTO>();
    }

    @GetMapping(value = "testtest")
    public List<BookDTO> getSomethingTest() {
        Optional<Book> book = booksRepository.findById(7123L);
        if (book.isPresent()) {
            Book b = book.get();
            List<Book> ret = recommendationService.getRecommendedBooksForBook(b, 5);
            return ret.stream().map(BookDTO::new).collect(Collectors.toList());
        }
        return new ArrayList<BookDTO>();
    }

}