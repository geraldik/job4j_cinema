package ru.job4j.cinema.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.service.MovieService;

import javax.servlet.http.HttpSession;

@Controller
public class IndexControl {

    private final MovieService movieService;

    public IndexControl(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/index")
    public String index(Model model, HttpSession session) {
        model.addAttribute("user", SessionControl.getUserSession(session));
        model.addAttribute("movies", movieService.findAll());
        model.addAttribute("ticket", new Ticket());
        return "index";
    }
}