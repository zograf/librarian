package com.librarian;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.json.JSONException;
import org.json.JSONObject;
import org.kie.api.KieServices;
import org.kie.api.builder.KieScanner;
import org.kie.api.runtime.KieContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.librarian.helper.DataHelper;
import com.librarian.model.Author;
import com.librarian.model.Book;
import com.librarian.model.EAge;
import com.librarian.model.Rating;
import com.librarian.model.Subject;
import com.librarian.repository.AuthorsRepo;
import com.librarian.repository.BooksRepo;
import com.librarian.repository.RatingsRepo;
import com.librarian.repository.SubjectsRepo;
import com.librarian.service.DataService;

@SpringBootApplication
public class ServiceApplication  {
	
	private static Logger log = LoggerFactory.getLogger(ServiceApplication.class);
    @Autowired private AuthorsRepo authorsRepo;
    @Autowired private SubjectsRepo subjectsRepo;
    @Autowired private BooksRepo booksRepo;
    @Autowired private RatingsRepo ratingsRepo;
    @Autowired private DataService dataService;

    // PRE RUNOVANJA APLIKACIJE DA NE BI PSOVAO SVE ZIVO
    // PROVERI DA LI JE 'create' ILI 'update' I DA LI JE
    // ZAKOMENTARISAN onApplicationStart BODY
	public static void main(String[] args) {
		SpringApplication.run(ServiceApplication.class, args);
	}

    @PostConstruct
    public void onApplicationStart() {
        //LoadData();
    }

    public void LoadData() {
        log.info("Loading authors");
        LoadAuthors("L:\\FTN\\sbnz\\author.csv");
        log.info("Loading subjects");
        LoadSubjects("L:\\FTN\\sbnz\\subject.csv");
        log.info("Loading books");
        LoadBooks("L:\\FTN\\sbnz\\book.csv");
        log.info("Loading ratings");
        LoadRatings("L:\\FTN\\sbnz\\rating.csv");
        log.info("Loading book_authors");
        LoadJoined("L:\\FTN\\sbnz\\book_authors.csv", "book_authors");
        log.info("Loading book_subjects");
        LoadJoined("L:\\FTN\\sbnz\\book_subjects.csv", "book_subjects");
        log.info("Finished loading");
    }


	@Bean
	public KieContainer kieContainer() {
		KieServices ks = KieServices.Factory.get();
		KieContainer kContainer = ks
				.newKieContainer(ks.newReleaseId("com.librarian", "kjar", "0.0.1-SNAPSHOT"));
		KieScanner kScanner = ks.newKieScanner(kContainer);
		kScanner.start(1000);
		return kContainer;
	}

    public void LoadJoined(String path, String table) {
        List<DataHelper> items = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader(path))) {
            for(String line; (line = br.readLine()) != null;) {
                String[] splitted = line.split(",");
                DataHelper a = new DataHelper();
                a.setFirstId(Integer.parseInt(splitted[0]));
                a.setSecondId(Integer.parseInt(splitted[1]));
                items.add(a);
            }
            dataService.insertJoined(items, table);
        }
        catch(FileNotFoundException notFound) { }
        catch(IOException ioException) { }

    }

    public void LoadAuthors(String path) {
        List<Author> items = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader(path))) {
            for(String line; (line = br.readLine()) != null;) {
                String[] splitted = line.split(",");
                Author a = new Author();
                a.setId(Long.parseLong(splitted[0]));
                a.setKey(splitted[1]);
                a.setName(splitted[2]);
                items.add(a);
                //JSONObject item = new JSONObject(line);
                //items.add(new Author(item.getString("key"), item.getString("name")));
            }
            //authorsRepo.saveAll(items);
            dataService.insertAuthors(items);
        }
        catch(FileNotFoundException notFound) { }
        catch(IOException ioException) { }
    }

    public void LoadSubjects(String path) {
        List<Subject> items = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader(path))) {
            for(String line; (line = br.readLine()) != null;) {
                String[] splitted = line.split(",");
                Subject s = new Subject();
                s.setId(Long.parseLong(splitted[0]));
                s.setKeyword(splitted[1]);
                s.setParent(splitted[2]);
                s.setRelevance(Integer.parseInt(splitted[3]));
                items.add(s);
                //JSONObject item = new JSONObject(line);
                //items.add(new Subject(item.getString("parent"), item.getString("keyword"), item.getInt("relevance")));
            }
            //subjectsRepo.saveAll(items);
            dataService.insertSubjects(items);
        }
        catch(FileNotFoundException notFound) { }
        catch(IOException ioException) { }
    }

    public void LoadRatings(String path) {
        List<Rating> items = new ArrayList<>();
        HashMap<String, Book> bookMap = new HashMap<>();
        for (Book b : booksRepo.findAll())
            bookMap.put(b.key, b);

        try(BufferedReader br = new BufferedReader(new FileReader(path))) {
            for(String line; (line = br.readLine()) != null;) {
                String[] splitted = line.split(",");
                Rating r = new Rating();
                r.setId(Long.parseLong(splitted[0]));
                String[] dateArray = splitted[1].split("-");
                LocalDateTime dateTime = LocalDateTime.of(Integer.parseInt(dateArray[0]), Integer.parseInt(dateArray[1]), Integer.parseInt(dateArray[2]), 0, 0);
                r.setDate(dateTime);
                if (dateTime == null)
                    log.info("AYO");
                r.setRating(Integer.parseInt(splitted[2]));
                r.setBookId(Long.parseLong(splitted[3]));
                items.add(r);
                // item.getString("book_key")
                // item.getInt("rating")
                // item.getString("date")
                //List<Book> potentialBooks = booksRepo.findByKey(item.getString("book_key"));
                //if (potentialBooks.isEmpty()) continue;
                //String[] dateArray = item.getString("date").split("-");
                //String key = item.getString("key");
                //LocalDateTime dateTime = LocalDateTime.of(Integer.parseInt(dateArray[0]), Integer.parseInt(dateArray[1]), Integer.parseInt(dateArray[2]), 0, 0);
                //if (bookMap.get(key) != null)
                //    items.add(new Rating(bookMap.get(key), item.getInt("rating"), dateTime));
            }
            //ratingsRepo.saveAll(items);
            dataService.insertRatings(items);
        }
        catch(FileNotFoundException notFound) { 
            System.out.println("ERROR notFound");
            System.out.println(notFound.getMessage());
        }
        catch(IOException ioException) { 
            System.out.println("ERROR ioException");
            System.out.println(ioException.getMessage());
        }
    }

    public void LoadBooks(String path){
        List<Book> items = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader(path))) {
            for(String line; (line = br.readLine()) != null;) {
                String[] splitted = line.split(",");
                Book b = new Book();

                b.setId(Long.parseLong(splitted[0]));
                b.setAge(EAge.values()[Integer.parseInt(splitted[1])]);
                b.setCover(splitted[2]);
                b.setDescription(splitted[3]);
                try{
                    b.setFirstPublishedYear(Integer.parseInt(splitted[4]));
                } catch (Exception ex) { 
                    b.setFirstPublishedYear(-1);
                }
                b.setFirstSentence(splitted[5]);
                b.setKey(splitted[6]);
                b.setSubtitle(splitted[7]);
                b.setTitle(splitted[8]);
                b.setCategoryId(Long.parseLong(splitted[9]));

                if (b.getDescription().length() > 250) 
                    b.setDescription(b.getDescription().substring(0, 245) + "...");

                if (b.getFirstSentence().length() > 250) 
                    b.setFirstSentence(b.getFirstSentence().substring(0, 245) + "...");

                if (b.getTitle().length() > 250) 
                    b.setTitle(b.getTitle().substring(0, 245) + "...");

                if (b.getSubtitle().length() > 250) 
                    b.setSubtitle(b.getSubtitle().substring(0, 245) + "...");
                //Book book = new Book(
                //    item.getString("key"),
                //    title,
                //    main_cat,
                //    authors,
                //    subjectList,
                //    desc,
                //    firstSentence,
                //    subtitle,
                //    year,
                //    item.getString("cover"),
                //    EAge.values()[item.getInt("age_group")]);
                items.add(b);
            }
            dataService.insertBooks(items);
        }
        catch(FileNotFoundException notFound) { 
            System.out.println("ERROR notFound");
            System.out.println(notFound.getMessage());
        }
        catch(IOException ioException) { 
            System.out.println("ERROR ioException");
            System.out.println(ioException.getMessage());
        }
    }

    public void LoadBooksOld(String path) {
        Integer counter = 0;
        LocalDateTime start = LocalDateTime.now();
        List<Book> items = new ArrayList<>();
        HashMap<String, Subject> subjectMap = new HashMap<>();
        HashMap<String, Author> authorMap = new HashMap<>();
        log.info("Started adding books");

        for (Author a : authorsRepo.findAll())
            authorMap.put(a.key, a);
        log.info("Added Author map");

        for (Subject s : subjectsRepo.findAll()) 
            subjectMap.put(s.keyword, s);
        log.info("Added Subject map");

        try(BufferedReader br = new BufferedReader(new FileReader(path))) {
            for(String line; (line = br.readLine()) != null;) {
                JSONObject item = new JSONObject(line);

                Set<Subject> subjects = new HashSet<Subject>();
                for(int i = 0; i < item.getJSONArray("subjects").length(); i++) {
                    String keyword = item.getJSONArray("subjects").getString(i);
                    if (subjectMap.get(keyword) != null)
                        subjects.add(subjectMap.get(keyword));
                }
                List<Author> authors = new ArrayList<>();
                for(int i = 0; i < item.getJSONArray("authors").length(); i++) {
                    String keyword = item.getJSONArray("authors").getString(i);
                    if (authorMap.get(keyword) != null)
                        authors.add(authorMap.get(keyword));
                }
                if (authors.isEmpty()) continue;
                if (subjects.isEmpty()) continue;
                Integer year = -1;
                try {
                    year = item.getInt("first_published_year");
                } catch(Exception ex) {
                    log.info("THIS MF: " + item.getString("first_published_year"));
                }

                String desc = item.getString("description");
                String firstSentence = item.getString("first_sentence");
                String title = item.getString("title");
                String subtitle = item.getString("subtitle");

                if (desc.length() > 250) 
                    desc = item.getString("description").substring(0, 245) + "...";

                if (firstSentence.length() > 250) 
                    firstSentence = item.getString("first_sentence").substring(0, 245) + "...";

                if (title.length() > 250) {
                    title = item.getString("title").substring(0, 245) + "...";
                }

                if (subtitle.length() > 250) {
                    subtitle = item.getString("subtitle").substring(0, 245) + "...";
                }

                Subject main_cat = subjectMap.get(item.getString("category"));
                try{
                    //log.info("BOOK START");
                    //log.info("Main cat: " + main_cat.keyword);
                    //log.info("REMOVING");
                    //for (Subject s : subjects)
                    //    log.info(s.keyword);
                    subjects.remove(main_cat);
                    //log.info("REMOVED");
                    //for (Subject s : subjects)
                    //    log.info(s.keyword);
                    //log.info("END");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                
                List<Subject> subjectList = new ArrayList<Subject>(subjects);

                Book book = new Book(
                    item.getString("key"),
                    title,
                    main_cat,
                    authors,
                    subjectList,
                    desc,
                    firstSentence,
                    subtitle,
                    year,
                    item.getString("cover"),
                    EAge.values()[item.getInt("age_group")]
                );
                items.add(book);
                counter += 1;
                if (counter % 10000 == 0) {
                    log.info("Counter: " + counter.toString());
                }
                //booksRepo.save(book);
            }
            booksRepo.saveAll(items);
        }
        catch(FileNotFoundException notFound) { 
            System.out.println("ERROR notFound");
            System.out.println(notFound.getMessage());
        }
        catch(IOException ioException) { 
            System.out.println("ERROR ioException");
            System.out.println(ioException.getMessage());
        }
        catch(JSONException jsonException) { 
            System.out.println("ERROR jsonException");
            System.out.println(jsonException.getMessage());
        }
    }
	
	/*
	 * KieServices ks = KieServices.Factory.get(); KieContainer kContainer =
	 * ks.newKieContainer(ks.newReleaseId("drools-spring-v2",
	 * "drools-spring-v2-kjar", "0.0.1-SNAPSHOT")); KieScanner kScanner =
	 * ks.newKieScanner(kContainer); kScanner.start(10_000); KieSession kSession =
	 * kContainer.newKieSession();
	 */
}
