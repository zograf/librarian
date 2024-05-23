package com.librarian.service;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.librarian.helper.DataHelper;
import com.librarian.model.Author;
import com.librarian.model.Book;
import com.librarian.model.Rating;
import com.librarian.model.Subject;

@Service
public class DataService {

    private final JdbcTemplate jdbcTemplate;

    //@Autowired
    public DataService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void insertAuthors(List<Author> authors) {
        String sql = "INSERT INTO author VALUES (?, ?, ?)";

        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                Author author = authors.get(i);
                ps.setLong(1, author.getId());
                ps.setString(2, author.getKey());
                ps.setString(3, author.getName());
            }

            @Override
            public int getBatchSize() {
                return authors.size();
            }
        });
    }

    public void insertSubjects(List<Subject> subjects) {
        String sql = "INSERT INTO subject VALUES (?, ?, ?, ?)";

        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                Subject subject = subjects.get(i);
                ps.setLong(1, subject.getId());
                ps.setString(2, subject.getKeyword());
                ps.setString(3, subject.getParent());
                ps.setInt(4, subject.getRelevance());
            }

            @Override
            public int getBatchSize() {
                return subjects.size();
            }
        });
    }

    public void insertBooks(List<Book> books) {
        String sql = "INSERT INTO book VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                Book book = books.get(i);
                ps.setLong(1, book.getId());
                ps.setInt(2, book.getAge().ordinal());
                ps.setString(3, book.getCover());
                ps.setString(4, book.getDescription());
                ps.setInt(5, book.getFirstPublishedYear());
                ps.setString(6, book.getFirstSentence());
                ps.setString(7, book.getKey());
                ps.setString(8, book.getSubtitle());
                ps.setString(9, book.getTitle());
                ps.setLong(10, book.getCategoryId());
            }

            @Override
            public int getBatchSize() {
                return books.size();
            }
        });
    }

    public void insertRatings(List<Rating> ratings) {
        String sql = "INSERT INTO rating VALUES (?, ?, ?, ?)";

        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                Rating rating = ratings.get(i);
                ps.setLong(1, rating.getId());
                ps.setDate(2, Date.valueOf(rating.getDate().toLocalDate()));
                //ps.setDate(2, Date.from(rating.getDate().atZone(ZoneId.systemDefault()).toInstant()));
                ps.setInt(3, rating.getRating());
                ps.setLong(4, rating.getBookId());
            }

            @Override
            public int getBatchSize() {
                return ratings.size();
            }
        });
    }

    public void insertJoined(List<DataHelper> items, String table) {
        String sql = "INSERT INTO " + table + " VALUES (?, ?)";

        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                DataHelper data = items.get(i);
                ps.setInt(1, data.getFirstId());
                ps.setInt(2, data.getSecondId());
            }

            @Override
            public int getBatchSize() {
                return items.size();
            }
        });
    }

}