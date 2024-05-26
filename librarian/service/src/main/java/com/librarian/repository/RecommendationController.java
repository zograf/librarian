package com.librarian.repository;

import org.springframework.web.bind.annotation.RestController;
import com.librarian.dto.BookDTO;
import com.librarian.dto.StatsDTO;
import com.librarian.service.RecommendationService;

import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;

import org.apache.http.client.HttpResponseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping(value = "/api/recommend")
public class RecommendationController {

    @Autowired
    private RecommendationService service;

    @GetMapping("/by/preferences")
    public List<BookDTO> postMethodName(@AuthenticationPrincipal UserDetails user) throws HttpResponseException {
        return service.recommend(user.getUsername());
    }

    @GetMapping("/by/book/{bookId}")
    public List<BookDTO> getMethodName(@AuthenticationPrincipal UserDetails user, @PathVariable Long bookId) throws HttpResponseException {
        return service.recommend(bookId);
    }

    @GetMapping("/stats")
    public ResponseEntity<StatsDTO> getMethodName(@AuthenticationPrincipal UserDetails user) throws HttpResponseException {
        return new ResponseEntity<StatsDTO>(service.getMostCommonCategories(user.getUsername()), HttpStatus.OK);
    }
    
}
