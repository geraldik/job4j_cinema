package ru.job4j.cinema.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.job4j.cinema.dao.UserDAO;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.service.HallService;
import ru.job4j.cinema.service.MovieService;

import javax.servlet.http.HttpSession;

@Controller
public class MovieControl {

    private final MovieService movieService;

    private final HallService hallService;

    public MovieControl(MovieService movieService, HallService hallService) {
        this.movieService = movieService;
        this.hallService = hallService;
    }

    @GetMapping("/movies")
    public String movies(Model model, HttpSession session) {
        model.addAttribute("movies", movieService.findAll());
        User user = SessionControl.getUserSession(session);
        model.addAttribute("user", user);
        return "movies";
    }

    @PostMapping("/chooseRow")
    public String chooseRowPost(@ModelAttribute Ticket ticket,
                              @RequestParam(name = "movie.id") int id, HttpSession session) {
        ticket.setMovieId(id);
        session.setAttribute("ticket", ticket);
        session.setAttribute("movieId", id);
        return "redirect:/chooseRow";
    }

    @GetMapping("/chooseRow")
    public String chooseRowGet(Model model, HttpSession session) {
        model.addAttribute("movie", movieService.findById((Integer) session.getAttribute("movieId")).get());
        model.addAttribute("user", SessionControl.getUserSession(session));
        model.addAttribute("rows", hallService.rows());
        return "chooseRow";
    }

    @PostMapping("/chooseSeat")
    public String chooseCellPost(@ModelAttribute Ticket ticket, HttpSession session) {
        Ticket sessionTicket = (Ticket) session.getAttribute("ticket");
        sessionTicket.setRow(ticket.getRow());
        return "redirect:/chooseSeat";
    }

    @GetMapping("/chooseSeat")
    public String chooseCellGet(Model model, HttpSession session) {
        Ticket sessionTicket = (Ticket) session.getAttribute("ticket");
        model.addAttribute("movie", movieService.findById((Integer) session.getAttribute("movieId")));
        model.addAttribute("user", SessionControl.getUserSession(session));
        model.addAttribute("cells", hallService.getFreeSeats(sessionTicket.getMovieId(), sessionTicket.getRow()));
        return "chooseSeat";
    }
}
