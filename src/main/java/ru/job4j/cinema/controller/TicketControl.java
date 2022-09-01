package ru.job4j.cinema.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.service.TicketService;

import javax.servlet.http.HttpSession;

@Controller
public class TicketControl {

    private final TicketService ticketService;

    public TicketControl(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping("/tickets")
    public String ticketsPost(@ModelAttribute Ticket ticket, HttpSession session) {
        System.out.println(ticket);
        Ticket sessionTicket = (Ticket) session.getAttribute("ticket");
        System.out.println(sessionTicket);
        sessionTicket.setCell(ticket.getCell());
        sessionTicket.setUserId(SessionControl.getUserSession(session).getId());
        ticketService.addTicket(sessionTicket);
        return "redirect:/tickets";
    }

    @GetMapping("/tickets")
    public String ticketsGet(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        model.addAttribute("user", SessionControl.getUserSession(session));
        model.addAttribute("tickets", ticketService.getUserTickets(user));
        return "tickets";
    }
}
