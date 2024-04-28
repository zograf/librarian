package com.librarian;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.FieldError;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.librarian.model.Author;
import com.librarian.model.Book;
import com.librarian.model.EAge;
import com.librarian.model.Rating;
import com.librarian.model.Subject;
import com.librarian.repository.AuthorsRepo;
import com.librarian.repository.BooksRepo;
import com.librarian.repository.RatingsRepo;
import com.librarian.repository.SubjectsRepo;

import org.apache.tomcat.util.json.JSONParser;
import org.json.JSONException;
import org.json.JSONObject;
import org.kie.api.KieServices;
import org.kie.api.builder.KieScanner;
import org.kie.api.runtime.KieContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
public class ServiceApplication  {
	
	private static Logger log = LoggerFactory.getLogger(ServiceApplication.class);
    @Autowired private AuthorsRepo authorsRepo;
    @Autowired private SubjectsRepo subjectsRepo;
    @Autowired private BooksRepo booksRepo;
    @Autowired private RatingsRepo ratingsRepo;

	public static void main(String[] args) {
		SpringApplication.run(ServiceApplication.class, args);
	}

    @PostConstruct
    public void onApplicationStart() {
        //LoadAuthors("D:\\FTN\\librarian\\data_processing\\authors_filtered.txt");
        //LoadSubjects("D:\\FTN\\librarian\\data_processing\\subjects_mod.txt");
        LoadBooks("D:\\FTN\\librarian\\data_processing\\filtered_books_6.txt");
        //LoadRatings("D:\\FTN\\librarian\\data_processing\\ratings.txt");
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

    public void LoadAuthors(String path) {
        List<Author> items = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader(path))) {
            for(String line; (line = br.readLine()) != null;) {
                JSONObject item = new JSONObject(line);
                items.add(new Author(item.getString("key"), item.getString("name")));
            }
            authorsRepo.saveAllAndFlush(items);
        }
        catch(FileNotFoundException notFound) { }
        catch(IOException ioException) { }
        catch(JSONException jsonException) { }
    }

    public void LoadSubjects(String path) {
        List<Subject> items = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader(path))) {
            for(String line; (line = br.readLine()) != null;) {
                JSONObject item = new JSONObject(line);
                items.add(new Subject(item.getString("parent"), item.getString("keyword")));
            }
            subjectsRepo.saveAllAndFlush(items);
        }
        catch(FileNotFoundException notFound) { }
        catch(IOException ioException) { }
        catch(JSONException jsonException) { }
    }

    public void LoadRatings(String path) {
        List<Rating> items = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try(BufferedReader br = new BufferedReader(new FileReader(path))) {
            for(String line; (line = br.readLine()) != null;) {
                JSONObject item = new JSONObject(line);
                // item.getString("book_key")
                // item.getInt("rating")
                // item.getString("date")
                List<Book> potentialBooks = booksRepo.findByKey(item.getString("book_key"));
                if (potentialBooks.isEmpty()) continue;
                items.add(new Rating(potentialBooks.get(0), item.getInt("rating"), LocalDateTime.parse(item.getString("date"), formatter)));
            }
            ratingsRepo.saveAllAndFlush(items);
        }
        catch(FileNotFoundException notFound) { }
        catch(IOException ioException) { }
        catch(JSONException jsonException) { }
    }

    public void LoadBooks(String path) {
        List<Book> items = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader(path))) {
            for(String line; (line = br.readLine()) != null;) {
                JSONObject item = new JSONObject(line);

                List<Subject> subjects = new ArrayList<>();
                for(int i = 0; i < item.getJSONArray("subjects").length(); i++) {
                    List<Subject> potentialSubjects = subjectsRepo.findByKeyword(item.getJSONArray("subjects").getString(i));
                    if (potentialSubjects.size() > 0) subjects.add(potentialSubjects.get(0)); 
                }
                List<Author> authors = new ArrayList<>();
                for(int i = 0; i < item.getJSONArray("authors").length(); i++) {
                    List<Author> potentialAuthors = authorsRepo.findByKey(item.getJSONArray("authors").getString(i));
                    if (potentialAuthors.size() > 0) authors.add(potentialAuthors.get(0));
                }
                if (authors.isEmpty()) continue;
                Book book = new Book(
                    item.getString("key"),
                    item.getString("title"),
                    subjectsRepo.findByKeyword(item.getString("category")).get(0),
                    authors,
                    subjects,
                    item.getString("description"),
                    item.getString("first_sentence"),
                    item.getString("subtitle"),
                    item.getInt("first_published_year"),
                    item.getString("cover"),
                    EAge.values()[item.getInt("age_group")]
                );
                items.add(book);
            }
            booksRepo.saveAllAndFlush(items);
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
