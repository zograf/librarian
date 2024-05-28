package com.librarian.service;

import java.security.SecureRandom;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.librarian.dto.LoginDTO;
import com.librarian.dto.RegisterDTO;
import com.librarian.dto.TokenDTO;
import com.librarian.model.ERole;
import com.librarian.model.ETargetYear;
import com.librarian.model.User;
import com.librarian.model.UserPreferences;
import com.librarian.repository.IUserRepository;
import com.librarian.repository.UserPreferencesRepo;
import com.librarian.security.JwtUtils;

@Service
public class UserService {

    Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private UserPreferencesRepo userPreferencesRepo;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    // Not used
    // @Autowired
    // private ApplicationEventPublisher eventPublisher;

    public TokenDTO login(LoginDTO loginDto) {
        logger.info("Attempting login...");

        Optional<User> userOpt = userRepository.findByEmail(loginDto.getEmail());
        // Added an or to check if enabled
        if (userOpt.isEmpty()) {
            logger.error("Can't find user with that email!");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "message: Incorrect credentials!");
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));

        logger.info("Password is correct!");

        String jwt;
        User user = (User) authentication.getPrincipal();
        logger.info("Generating JWT token...");
        SecurityContextHolder.getContext().setAuthentication(authentication);
        jwt = jwtUtils.generateJwtToken(authentication);

        TokenDTO tokenDTO = new TokenDTO();
        tokenDTO.setAccessToken(jwt);
        tokenDTO.setRefreshToken(""); // Not used at the moment
        tokenDTO.setUserId(user.getId());
        tokenDTO.setUserRole(user.getRole());
        tokenDTO.setEmail(user.getEmail());

        return tokenDTO;
    }

    public User register(RegisterDTO dto) {
        logger.info("Registering new user...");

        Optional<User> userCheck = userRepository.findByEmail(dto.getEmail());
        if (userCheck.isPresent()) {
            logger.error("User with that email already exists!");
            String value = "message: Account with that email already exists!";
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, value);
        }

        if (dto.getEmail() == null) {
            logger.error("Null values found in register DTO!");
            String value = "message: Bad input data!";
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, value);
        }

        logger.info("Saving user to database...");
        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setRole(ERole.ROLE_USER);

        UserPreferences preferences = new UserPreferences();
        preferences.setAge(dto.getAge());
        preferences.setGender(dto.getGender());
        preferences.setTargetYear(ETargetYear.NOT_IMPORTANT);
        user.setPreferences(userPreferencesRepo.save(preferences));
        
        logger.info("Registered new user!");
        return save(user);
    }


    public User save(User user) {
        return userRepository.save(user);
    }

    // Not used
    // private TokenDTO generateToken(User user) {
    //     String jwt = jwtUtils.generateJwtToken(user);

    //     TokenDTO tokenDTO = new TokenDTO();
    //     tokenDTO.setAccessToken(jwt);
    //     tokenDTO.setRefreshToken(""); // Not used at the moment
    //     tokenDTO.setUserId(user.getId());
    //     tokenDTO.setUserRole(user.getRole());
    //     tokenDTO.setEmail(user.getEmail());

    //     return tokenDTO;
    // }

    public String encodePassword(String password) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10, new SecureRandom(){
            @Override
            public void nextBytes(byte[] bytes){}
        });
        String encodedPassword = passwordEncoder.encode(password);
        final int passwordLength = 31;
        return encodedPassword.substring(encodedPassword.length()-passwordLength);
    }
}
