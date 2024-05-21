package com.librarian.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.apache.http.client.HttpResponseException;
import org.drools.template.DataProvider;
import org.drools.template.objects.ArrayDataProvider;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.librarian.dto.BookDTO;
import com.librarian.helper.SessionBuilder;
import com.librarian.model.Author;
import com.librarian.model.Book;
import com.librarian.model.BookRank;
import com.librarian.model.Rating;
import com.librarian.model.RecommendingBook;
import com.librarian.model.RecommendingPreferences;
import com.librarian.model.Subject;
import com.librarian.model.UserPreferences;
import com.librarian.repository.BooksRepo;
import com.librarian.repository.RatingsRepo;
import com.librarian.repository.SubjectsRepo;

@Service
public class RecommendationService {

    Logger logger = LoggerFactory.getLogger(RecommendationService.class);

    UserPreferences u;
    KieSession ksession;

    @Autowired
    private BooksRepo bookRepository;

    @Autowired
    private SubjectsRepo subjectsRepository;

    @Autowired
    private RatingsRepo ratingRepository;

    @Autowired
    private UserPreferencesService preferencesService;

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
        sessionBuilder.addRules("/rules/targetYear.drl");
        sessionBuilder.addRules("/rules/likedSubjects.drl");
        sessionBuilder.addRules("/rules/likedAuthors.drl");
        sessionBuilder.addRules("/rules/readBooks.drl");
        sessionBuilder.addRules("/rules/singleBook.drl");
        sessionBuilder.addTemplate("/templates/ageTempl.drt", ageTemplProvider);
        sessionBuilder.addTemplate("/templates/filterAgeTempl.drt", filterAgeTemplProvider);
        sessionBuilder.addTemplate("/templates/categoryFilterTempl.drt", categoryFilterTemplProvider);
        ksession = sessionBuilder.build();

        List<Book> books = bookRepository.findAllBooks();

        //for (int i = 0; i < 1000; i++) {
        //  ksession.insert(books.get(i));
        //}

        for (Book b : books){
            ksession.insert(b);
        }

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

    public List<Book> getRecommendedBooksForBook(Book b, Integer n) {
        logger.info("");
        ksession.insert(new RecommendingBook(b));
        ksession.getAgenda().getAgendaGroup("singleBook").setFocus();
        int count = ksession.fireAllRules();
        logger.info("Executed " + count + " rules");

        List<Book> books = getBooksFromSession(n);

        //ksession.getAgenda().getAgendaGroup("recommended").setFocus();
        //count = ksession.fireAllRules();
        //logger.info("Executed " + count + " rules");

        for (FactHandle handle : ksession.getFactHandles()) {
            ksession.delete(handle);
        }

        //ksession.getAgenda().getAgendaGroup("cleanup").setFocus();
        //count = ksession.fireAllRules();
        //logger.info("Executed " + count + " rules");
        return books;
    }

    public List<Book> getRecommendedBooksForPreferences(UserPreferences u, Integer n) {
        logger.info("");
        logger.info(Long.toString(ksession.getFactCount()));
        ksession.insert(new RecommendingPreferences(u));
        ksession.getAgenda().getAgendaGroup("main").setFocus();
        int count = ksession.fireAllRules();
        logger.info("Executed " + count + " rules");

        List<Book> books = getBooksFromSession(n);

        //ksession.getAgenda().getAgendaGroup("recommended").setFocus();
        //count = ksession.fireAllRules();
        //logger.info("Executed " + count + " rules");

        ksession.getAgenda().getAgendaGroup("cleanup").setFocus();
        count = ksession.fireAllRules();
        logger.info("Executed " + count + " rules");
        return books;
    }

    private List<Book> getBooksFromSession(Integer n) {
        List<Book> books = new ArrayList<>();
        List<BookRank> ranks = new ArrayList<>();
        Collection<?> objects = ksession.getObjects(o -> o instanceof BookRank);
        for (Object obj : objects) {
            BookRank myModel = (BookRank) obj;
            ranks.add(myModel);
        }

        ranks.sort(Comparator.comparingInt(BookRank::getRating).reversed());

        if(ranks.size() != 0) {
            for (int i = 0; i < n; i++) {
                Book bb = ranks.get(i).getBook();
                books.add(bb);
            }

            Integer top = ranks.get(0).getRating();
            Integer i = 0;
            for (BookRank br: ranks) {
                if (br.getRating() != top) break;
                Book bb = ranks.get(i).getBook();
                ArrayList<Author> a = new ArrayList<>(bb.getAuthors());
                logger.info("BOOK " + Integer.toString(i+1) + ": " + bb.getTitle() + " WRITTEN BY: " + a.get(0).getName());
            }
        }

        return books;
    }

    public List<BookDTO> recommend(String username) throws HttpResponseException {
        UserPreferences preferences = preferencesService._get(username);
        return getRecommendedBooksForPreferences(preferences, 8).stream().map(BookDTO::new).collect(Collectors.toList());
    }
}