package com.librarian.repository;

import org.springframework.web.bind.annotation.RestController;
import com.librarian.dto.BookDTO;
import com.librarian.service.RecommendationService;

import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;

import org.apache.http.client.HttpResponseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;




@RestController
@RequestMapping(value = "/api/recommend")
public class RecommendationController {

    @Autowired
    private RecommendationService service;

    @GetMapping("/by/preferences")
    public List<BookDTO> postMethodName(@AuthenticationPrincipal UserDetails user) throws HttpResponseException {
        return service.recommend(user.getUsername());
    }
    
}
