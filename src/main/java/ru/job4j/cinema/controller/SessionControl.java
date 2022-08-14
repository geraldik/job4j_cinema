package ru.job4j.cinema.controller;

import ru.job4j.cinema.model.User;

import javax.servlet.http.HttpSession;

public class SessionControl {

    public static User getUserSession(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            user = new User();
            user.setUsername("Гость");
        }
        return user;
    }
}
