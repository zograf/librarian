package com.librarian;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

import javax.annotation.PostConstruct;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.FieldError;
import org.json.JSONException;
import org.json.JSONObject;
import org.kie.api.KieServices;
import org.kie.api.builder.KieScanner;
import org.kie.api.runtime.KieContainer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
public class ServiceApplication  {
	
	private static Logger log = LoggerFactory.getLogger(ServiceApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(ServiceApplication.class, args);
	}

    @PostConstruct
    public void onApplicationStart() {
        LoadAuthors("D:\\FTN\\librarian\\data_processing\\authors_filtered.txt");
        LoadSubjects("D:\\FTN\\librarian\\data_processing\\subjects_mod.txt");
        LoadRatings("D:\\FTN\\librarian\\data_processing\\ratings_mod.txt");
        LoadBooks("D:\\FTN\\librarian\\data_processing\\filtered_book_6.txt");
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
        try(BufferedReader br = new BufferedReader(new FileReader(path))) {
            for(String line; (line = br.readLine()) != null;) {
                JSONObject item = new JSONObject(line);
                // item.getString("key")
                // item.getString("name")
            }
        }
        catch(FileNotFoundException notFound) { }
        catch(IOException ioException) { }
        catch(JSONException jsonException) { }
    }

    public void LoadSubjects(String path) {
        try(BufferedReader br = new BufferedReader(new FileReader(path))) {
            for(String line; (line = br.readLine()) != null;) {
                JSONObject item = new JSONObject(line);
                // item.getString("parent")
                // item.getString("keyword")
            }
        }
        catch(FileNotFoundException notFound) { }
        catch(IOException ioException) { }
        catch(JSONException jsonException) { }
    }

    public void LoadRatings(String path) {
        try(BufferedReader br = new BufferedReader(new FileReader(path))) {
            for(String line; (line = br.readLine()) != null;) {
                JSONObject item = new JSONObject(line);
                // item.getString("book_key")
                // item.getInt("rating")
                // item.getString("date")
            }
        }
        catch(FileNotFoundException notFound) { }
        catch(IOException ioException) { }
        catch(JSONException jsonException) { }
    }

    public void LoadBooks(String path) {
        try(BufferedReader br = new BufferedReader(new FileReader(path))) {
            for(String line; (line = br.readLine()) != null;) {
                JSONObject item = new JSONObject(line);
                // item.getString("key")
                // item.getString("title")
                // item.getJSONArray("authors")
                // item.getString("description")
                // item.getString("first_sentence")
                // item.getString("subtitle")
                // item.getString("cover")
                // item.getString("category")
                // item.getInt("age_group")
                // item.getInt("first_published_year")
                // item.getJSONArray("subjects")
            }
        }
        catch(FileNotFoundException notFound) { }
        catch(IOException ioException) { }
        catch(JSONException jsonException) { }
    }
	
	/*
	 * KieServices ks = KieServices.Factory.get(); KieContainer kContainer =
	 * ks.newKieContainer(ks.newReleaseId("drools-spring-v2",
	 * "drools-spring-v2-kjar", "0.0.1-SNAPSHOT")); KieScanner kScanner =
	 * ks.newKieScanner(kContainer); kScanner.start(10_000); KieSession kSession =
	 * kContainer.newKieSession();
	 */
}
