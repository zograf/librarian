package com.librarian.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.kie.api.runtime.KieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.librarian.dto.BookDTO;
import com.librarian.helper.SessionBuilder;
import com.librarian.model.Book;
import com.librarian.model.PotentialTrendingBook;
import com.librarian.model.TrendingBook;
import com.librarian.model.UserPreferences;
import com.librarian.repository.BooksRepo;
import com.librarian.repository.TrendingBooksRepo;
import com.librarian.repository.UserPreferencesRepo;

@Service
public class TrendingService {

    Logger logger = LoggerFactory.getLogger(TrendingService.class);

    @Autowired
    private TrendingBooksRepo trendingRepo;

    @Autowired
    private UserPreferencesRepo preferencesRepo;

    @Autowired
    private BooksRepo booksRepo;

    KieSession ksession;

    @PostConstruct
    public void init() {
        SessionBuilder sessionBuilder = new SessionBuilder();
        sessionBuilder.addRules("/rules/trending.drl");
        ksession = sessionBuilder.build();

        
    }
    
    private void initData() {
        List<TrendingBook> trending = trendingRepo.findAllByNewInTrending();
        for (TrendingBook tb: trending) {
            ksession.insert(tb);
        }

        List<UserPreferences> preferences = preferencesRepo.findAll();
        for (UserPreferences up: preferences) {
            ksession.insert(up);
        }
    }

    private void handlePastTrending() {
        for (Object obj : ksession.getObjects(o -> o instanceof TrendingBook)) {
            TrendingBook toRemove = (TrendingBook) obj;
            TrendingBook trending = trendingRepo.findById(toRemove.id).get();
            trending.setNewInTrending(false);
            trendingRepo.save(trending);
        }
    }

    private void handleNewTrending() {
        for (Object obj : ksession.getObjects(o -> o instanceof PotentialTrendingBook)) {
            PotentialTrendingBook trending = (PotentialTrendingBook) obj;
            Book book = booksRepo.findById(trending.id).get();
            logger.info("Got new Trending book! Title: " + book.title);
            trendingRepo.save(new TrendingBook(book, trending.likedBy, true));
        }
    }

    public void updateTrendingBooks() {
        initData();
        ksession.getAgenda().getAgendaGroup("trending").setFocus();
        int count = ksession.fireAllRules();
        logger.info("Executed " + count + " rules");

        handlePastTrending();
        handleNewTrending();
        
    }

    public List<BookDTO> get() {
        return trendingRepo.findAll().stream().map(BookDTO::new).collect(Collectors.toList());
    }
}
