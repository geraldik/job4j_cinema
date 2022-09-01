package ru.job4j.cinema.service;

import org.springframework.stereotype.Service;
import ru.job4j.cinema.dao.MovieDAO;
import ru.job4j.cinema.dao.TicketDAO;
import ru.job4j.cinema.model.Movie;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.model.User;

import java.util.HashMap;
import java.util.Map;

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
        HashMap<Ticket, Movie> map = new HashMap<>();
        ticketDAO.findByUser(user).forEach(ticket ->
                map.put(ticket, movieDAO.findById(ticket.getMovieId()).get()));
        return map;
    }

}
