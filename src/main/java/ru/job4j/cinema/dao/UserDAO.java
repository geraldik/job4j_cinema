package ru.job4j.cinema.dao;

import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.cinema.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Repository
public class UserDAO {
    public static final String ADD_USER = "INSERT INTO users(username, email, phone) VALUES (?, ?, ?)";
    public static final String FIND_USER_BY_EMAIL_AND_PHONE = "SELECT * FROM users WHERE email = ? AND phone = ?";
    private final BasicDataSource pool;

    private static final Logger LOG = LoggerFactory.getLogger(UserDAO.class.getName());

    public UserDAO(BasicDataSource pool) {
        this.pool = pool;
    }

    public Optional<User> add(User user) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     ADD_USER,
                     PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPhone());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    user.setId(id.getInt(1));
                }
            }
        } catch (Exception e) {
            LOG.warn("Can't add user", e);
            user = null;
        }
        return Optional.ofNullable(user);
    }

    public Optional<User> findUserByEmailAndPhone(String email, String phone) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(FIND_USER_BY_EMAIL_AND_PHONE)
        ) {
            ps.setString(1, email);
            ps.setString(2, phone);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    return Optional.of(initUser(it));
                }
            }
        } catch (Exception e) {
            LOG.warn("Can't find user by email and phone", e);
        }
        return Optional.empty();
    }

    private static User initUser(ResultSet it) throws SQLException {
        return new User(it.getInt("id"), it.getString("username"),
                it.getString("email"),
                it.getString("phone"));
    }
}
