package ru.job4j.cinema.service;

import org.springframework.stereotype.Service;
import org.thymeleaf.expression.Lists;
import ru.job4j.cinema.dao.MovieDAO;
import ru.job4j.cinema.dao.TicketDAO;
import ru.job4j.cinema.model.Movie;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toMap;

@Service
public class TicketService {

    private final TicketDAO ticketDAO;
    private final MovieDAO movieDAO;

    public TicketService(TicketDAO ticketDAO, MovieDAO movieDAO) {
        this.ticketDAO = ticketDAO;
        this.movieDAO = movieDAO;
    }

    public void addTicket(Ticket ticket) {
        ticketDAO.createTicket(ticket);
    }

    public Map<Ticket, Movie> getUserTickets(User user) {
        var userTickets = ticketDAO.findByUser(user);
        var moviesIds = userTickets.stream()
                .map(Ticket::getMovieId)
                .collect(Collectors.toSet());
        var movies = movieDAO.findByIdIn( new ArrayList<>(moviesIds)).stream()
                .collect(toMap(Movie::getId, m -> m));
        return userTickets.stream()
                .collect(toMap(t -> t, t -> movies.get(t.getMovieId())));
    }
}
