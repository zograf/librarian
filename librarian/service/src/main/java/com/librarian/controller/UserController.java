package com.librarian.controller;

import java.io.InputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.drools.template.DataProvider;
import org.drools.template.DataProviderCompiler;
import org.drools.template.objects.ArrayDataProvider;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.Agenda;
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

import com.librarian.dto.LoginDTO;
import com.librarian.dto.RegisterDTO;
import com.librarian.dto.TokenDTO;
import com.librarian.helper.SessionBuilder;
import com.librarian.model.Book;
import com.librarian.model.EAge;
import com.librarian.model.Rating;
import com.librarian.model.Subject;
import com.librarian.model.User;
import com.librarian.model.UserPreferences;
import com.librarian.repository.BooksRepo;
import com.librarian.repository.RatingsRepo;
import com.librarian.repository.SubjectsRepo;
import com.librarian.repository.UserPreferencesRepo;
import com.librarian.service.UserService;
import com.twilio.rest.proxy.v1.service.Session;

@RestController
@RequestMapping(value = "/api/user")
public class UserController {

    Logger logger = LoggerFactory.getLogger(UserController.class);

    UserPreferences u;
    KieSession ksession;

    @Autowired
    private UserService userService;

    @Autowired
    private UserPreferencesRepo userPreferencesRepository;

    @Autowired
    private BooksRepo bookRepository;

    @Autowired
    private SubjectsRepo subjectsRepository;

    @Autowired
    private RatingsRepo ratingRepository;

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
    
    @PostConstruct
    public void init() {
        DataProvider ageTemplProvider = new ArrayDataProvider(new String [][]{
            new String[] {"22", "\"flag_adult\"", "100"},
            new String[] {"12", "\"flag_ya\"", "99"},
            new String[] {"2", "\"flag_juvenile\"", "98"}
        });

        DataProvider filterAgeTemplProvider = new ArrayDataProvider(new String [][]{
            new String[] {"\"flag_adult\"", "EAge.ADULT"},
            new String[] {"\"flag_ya\"", "EAge.YOUNG_ADULT"},
            new String[] {"\"flag_juvenile\"", "EAge.JUVENILE"}
        });

        DataProvider categoryFilterTemplProvider = new ArrayDataProvider(new String [][]{
            new String[] {"\"fiction\""},
            new String[] {"\"nonfiction\""},
            new String[] {"\"poetry\""},
            new String[] {"\"psychology\""},
            new String[] {"\"biography\""},
            new String[] {"\"philosophy\""},
            new String[] {"\"science\""},
            new String[] {"\"mathematics\""},
            new String[] {"\"politics\""},
            new String[] {"\"literature\""},
            new String[] {"\"history\""},
            new String[] {"\"magic\""},
            new String[] {"\"mystery\""},
            new String[] {"\"juvenile\""},
            new String[] {"\"horror\""},
            new String[] {"\"romance\""},
            new String[] {"\"young\""},
            new String[] {"\"thriller\""},
        });

        SessionBuilder sessionBuilder = new SessionBuilder();
        sessionBuilder.addRules("/rules/librarian.drl");
        sessionBuilder.addRules("/rules/cleanup.drl");
        sessionBuilder.addRules("/rules/target_year.drl");
        sessionBuilder.addRules("/rules/likedSubjects.drl");
        sessionBuilder.addRules("/rules/likedAuthors.drl");
        sessionBuilder.addRules("/rules/readBooks.drl");
        sessionBuilder.addTemplate("/templates/ageTempl.drt", ageTemplProvider);
        sessionBuilder.addTemplate("/templates/filterAgeTempl.drt", filterAgeTemplProvider);
        sessionBuilder.addTemplate("/templates/categoryFilterTempl.drt", categoryFilterTemplProvider);
        ksession = sessionBuilder.build();

        List<Book> books = bookRepository.findAllBooks();

        for (int i = 0; i < 50000; i++) {
            ksession.insert(books.get(i));
        }
        //for (Book b : books) {
        //    ksession.insert(b);
        //}

        //System.out.println(books.get(0).category.keyword);

        List<Subject> subjects = subjectsRepository.findAll();
        List<Rating> ratings = ratingRepository.findAll();

        for (Subject s : subjects) {
            ksession.insert(s);
        }

        for (Rating r : ratings) {
            ksession.insert(r);
        }
    }

    @GetMapping(value = "test")
    public ResponseEntity<String> getSomething() {
        Optional<UserPreferences> optional = userPreferencesRepository.findById(1L);
        if (optional.isPresent()) {
            u = optional.get();
            logger.info("");
            logger.info(Long.toString(ksession.getFactCount()));
            u.setLikedSubjects(new HashSet<>());
            u.setReadBooks(new HashSet<>());
            u.getReadBooks().add(bookRepository.findById(1L).get());
            u.getReadBooks().add(bookRepository.findById(2L).get());
            u.getReadBooks().add(bookRepository.findById(3L).get());
            u.getReadBooks().add(bookRepository.findById(4L).get());
            u.getReadBooks().add(bookRepository.findById(5L).get());
            for (Subject s : u.getAdditionalSubjects())
                u.getLikedSubjects().add(s);
            ksession.insert(u);
            int count = ksession.fireAllRules();
            logger.info("Executed " + count + " rules");
        }
        return ResponseEntity.ok("");
    }

}