package ru.job4j.cinema.service;

import org.springframework.stereotype.Service;
import ru.job4j.cinema.dao.TicketDAO;
import ru.job4j.cinema.model.Ticket;

@Service
public class TicketService {

    private final TicketDAO ticketDAO;

    public TicketService(TicketDAO ticketDAO) {
        this.ticketDAO = ticketDAO;
    }

    public void addTicket(Ticket ticket) {
        ticketDAO.createTicket(ticket);
    }

}
