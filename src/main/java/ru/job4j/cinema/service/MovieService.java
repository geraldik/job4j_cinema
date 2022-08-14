package ru.job4j.cinema.service;

import org.springframework.stereotype.Service;
import ru.job4j.cinema.dao.MovieDAO;
import ru.job4j.cinema.model.Movie;

import java.util.Collection;
import java.util.Optional;

@Service
public class MovieService {

    private final MovieDAO movieDAO;

    public MovieService(MovieDAO movieDAO) {
        this.movieDAO = movieDAO;
    }

    public Collection<Movie> findAll() {
        return movieDAO.findAll();
    }

    public Optional<Movie> findById(int id) {
        return movieDAO.findById(id);
    }

}
