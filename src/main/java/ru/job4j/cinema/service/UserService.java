package ru.job4j.cinema.service;

import org.springframework.stereotype.Service;
import ru.job4j.cinema.dao.UserDAO;
import ru.job4j.cinema.model.User;

import java.util.Optional;

@Service
public class UserService {

    private final UserDAO userDAO;

    public UserService(UserDAO  store) {
        this.userDAO = store;
    }

    public Optional<User> add(User user) {
        return userDAO.add(user);
    }

    public Optional<User> findUserByEmailAndPhone(String email, String phone) {
        return userDAO.findUserByEmailAndPhone(email, phone);
    }
}