package com.librarian.controller;

import java.io.InputStream;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.drools.template.DataProvider;
import org.drools.template.DataProviderCompiler;
import org.drools.template.objects.ArrayDataProvider;
import org.kie.api.KieServices;
import org.kie.api.io.Resource;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieContainer;
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

import com.librarian.dto.LoginDTO;
import com.librarian.dto.RegisterDTO;
import com.librarian.dto.TokenDTO;
import com.librarian.model.Book;
import com.librarian.model.User;
import com.librarian.model.UserPreferences;
import com.librarian.repository.BooksRepo;
import com.librarian.service.UserService;

@RestController
@RequestMapping(value = "/api/user")
public class UserController {

    Logger logger = LoggerFactory.getLogger(UserController.class);

    KieServices ks;
    KieContainer kContainer; 
    KieSession ksession;
    UserPreferences u;

    @Autowired
    private UserService userService;

    @Autowired
    private BooksRepo bookRepository;

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
        ks = KieServices.Factory.get();
        kContainer = ks.getKieClasspathContainer(); 
        ksession = kContainer.newKieSession("librarian-session");

        //InputStream template = this.getClass().getResourceAsStream("/rules/templateBase/librarianTempl.drt");
        //InputStream data = this.getClass().getResourceAsStream("template-data.xls");
        //ExternalSpreadsheetCompiler converter = new ExternalSpreadsheetCompiler();
        //String drl = converter.compile(data, template, 3, 2);
        //DataProvider provider = new ArrayDataProvider(new String [][]{
        //    new String[] {"22", "flag_adult"},
        //    new String[] {"12", "flag_ya"},
        //    new String[] {"2", "flag_juvenile"}
        //});

        //DataProviderCompiler compiler = new DataProviderCompiler();
        //String drl = compiler.compile(provider, template);

        List<Book> books = bookRepository.findAll();
        for (Book b : books) {
            ksession.insert(b);
        }
        u = new UserPreferences();
        u.setAge(5);
    }

    @GetMapping(value = "test")
    public ResponseEntity<String> getSomething() {
        logger.info("");
        logger.info(Long.toString(ksession.getFactCount()));
        ksession.insert(u);
        int count = ksession.fireAllRules();
        logger.info("Executed " + count + " rules");
        return ResponseEntity.ok("");
    }

}