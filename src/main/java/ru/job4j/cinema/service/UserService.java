package ru.job4j.cinema.service;

import org.springframework.stereotype.Service;
import ru.job4j.cinema.dao.MovieDAO;
import ru.job4j.cinema.dao.TicketDAO;
import ru.job4j.cinema.dao.UserDAO;
import ru.job4j.cinema.model.Movie;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.model.User;

import java.util.HashMap;
import java.util.Optional;

@Service
public class UserService {

    private final UserDAO userDAO;

    private final TicketDAO ticketDAO;

    private final MovieDAO movieDAO;

    public UserService(UserDAO  store, TicketDAO ticketDAO, MovieDAO movieDAO) {
        this.userDAO = store;
        this.ticketDAO = ticketDAO;
        this.movieDAO = movieDAO;
    }

    public Optional<User> add(User user) {
        return userDAO.add(user);
    }

    public Optional<User> findUserByEmailAndPhone(String email, String phone) {
        return userDAO.findUserByEmailAndPhone(email, phone);
    }

    public HashMap<Ticket, Movie> getUserTickets(User user) {
        HashMap<Ticket, Movie> map = new HashMap<>();
        ticketDAO.findByUser(user).forEach(ticket ->
                map.put(ticket, movieDAO.findById(ticket.getMovieId()).get()));
        return map;
    }
}