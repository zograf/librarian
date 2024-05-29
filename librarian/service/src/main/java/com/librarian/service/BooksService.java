package com.librarian.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.librarian.dto.AuthorDTO;
import com.librarian.dto.BookDTO;
import com.librarian.dto.SubjectDTO;
import com.librarian.model.Author;
import com.librarian.model.Book;
import com.librarian.model.Subject;
import com.librarian.repository.BooksRepo;
import com.librarian.repository.SubjectsRepo;

@Service
public class BooksService {

    Logger logger = LoggerFactory.getLogger(BooksService.class);

    @Autowired
    private BooksRepo bookRepository;

    @Autowired
    private SubjectsRepo subjectsRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public List<BookDTO> findByName(String phrase) {
        List<BookDTO> bookDTOs = bookRepository.findByTitleContains(phrase.toLowerCase())
                                               .stream()
                                               .map(BookDTO::new)
                                               .collect(Collectors.toList());
        if (bookDTOs.size() < 500)
            return bookDTOs;
        else
            return bookDTOs.subList(0, 500);
    }

    public List<BookDTO> findByAuthor(Long authorId) {
        List<BookDTO> bookDTOs = bookRepository.findAllByAuthorId(authorId)
                                               .stream()
                                               .map(BookDTO::new)
                                               .collect(Collectors.toList());
        if (bookDTOs.size() < 500)
            return bookDTOs;
        else
            return bookDTOs.subList(0, 500);
    }

    @Transactional
    public Book saveBook(BookDTO book) {
        Book b = new Book();
        b.setTitle(book.getTitle());
        b.setDescription(book.getDescription());
        b.setFirstPublishedYear(book.getFirstPublishedYear());
        b.setFirstSentence(book.getFirstSentence());
        b.setCover(book.getCover());
        b.setTitle(book.getTitle());
        b.setAge(book.getAge());

        Set<Subject> sub = new HashSet<>();
        Map<String, Integer> m = new HashMap<String, Integer>();
        for (SubjectDTO s : book.getSubjects()) {
            Subject ss = new Subject();
            ss.setId(s.getId());
            ss.setKeyword(s.getKeyword());
            ss.setParent(s.getParent());
            ss.setRelevance(s.getRelevance());
            sub.add(ss);

            m.putIfAbsent(ss.getParent(), 0);
            m.put(ss.getParent(), m.get(ss.getParent()) + 1);
        }
        b.setSubjects(sub);

        Set<Author> author = new HashSet<>();
        for (AuthorDTO a : book.getAuthors()) {
            Author aa = new Author();
            aa.setId(a.getId());
            aa.setName(a.getName());
            author.add(aa);
        }
        b.setAuthors(author);

        String s = "";
        Integer max = -1;
        for(Entry<String, Integer> e : m.entrySet()) {
            if (max < e.getValue()) {
                max = e.getValue();
                s = e.getKey();
            }
        }

        b.setCategory(subjectsRepository.findByKeyword(s).get());



        b.setId(bookRepository.findMaxId() + 1);

        entityManager.createNativeQuery("INSERT INTO book (id, key, title, description, first_sentence, subtitle, first_published_year, cover, age, category_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")
                .setParameter(1, b.getId())
                .setParameter(2, b.getKey())
                .setParameter(3, b.getTitle())
                .setParameter(4, b.getDescription())
                .setParameter(5, b.getFirstSentence())
                .setParameter(6, b.getSubtitle())
                .setParameter(7, b.getFirstPublishedYear())
                .setParameter(8, b.getCover())
                .setParameter(9, b.getAge().ordinal())
                .setParameter(10, b.getCategory() != null ? b.getCategory().getId() : null)
                .executeUpdate();

        for (Author authorEntity : b.getAuthors()) {
            entityManager.createNativeQuery("INSERT INTO book_authors (book_id, authors_id) VALUES (?, ?)")
                    .setParameter(1, b.getId())
                    .setParameter(2, authorEntity.getId())
                    .executeUpdate();
        }

        for (Subject subjectEntity : b.getSubjects()) {
            entityManager.createNativeQuery("INSERT INTO book_subjects (book_id, subjects_id) VALUES (?, ?)")
                    .setParameter(1, b.getId())
                    .setParameter(2, subjectEntity.getId())
                    .executeUpdate();
        }

        return b;
    }

}
