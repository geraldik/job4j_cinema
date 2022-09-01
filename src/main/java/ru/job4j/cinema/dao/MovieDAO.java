package ru.job4j.cinema.dao;

import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.cinema.model.Movie;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class MovieDAO {

    public static final String FIND_BY_ID = "SELECT * FROM movie WHERE id = ?";
    public static final String FIND_ALL = "SELECT * FROM movie";
    private final BasicDataSource pool;

    private static final Logger LOG = LoggerFactory.getLogger(UserDAO.class.getName());

    public MovieDAO(BasicDataSource pool) {
        this.pool = pool;
    }

    public Optional<Movie> findById(int id) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(FIND_BY_ID)
        ) {
            ps.setInt(1, id);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    return Optional.of(initMovie(it));
                }
            }
        } catch (Exception e) {
            LOG.warn("Can't find session by id", e);
        }
        return Optional.empty();
    }

    public List<Movie> findAll() {
        List<Movie> movies = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(FIND_ALL)
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    movies.add(initMovie(it));
                }
            }
        } catch (Exception e) {
            LOG.warn("Can't find all sessions", e);
        }
        return movies;
    }

    private static Movie initMovie(ResultSet it) throws SQLException {
        return new Movie(it.getInt("id"), it.getString("name"));
    }
}
