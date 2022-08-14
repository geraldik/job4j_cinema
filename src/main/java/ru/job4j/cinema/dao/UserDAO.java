package ru.job4j.cinema.dao;

import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.cinema.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;

@Repository
public class UserDAO {
    private final BasicDataSource pool;

    private static final Logger LOG = LoggerFactory.getLogger(UserDAO.class.getName());

    public UserDAO(BasicDataSource pool) {
        this.pool = pool;
    }

    public User add(User user) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "INSERT INTO users(username, email, phone) VALUES (?, ?, ?)",
                     PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
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
        return user;
    }

    public Optional<User> findById(int id) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("SELECT * FROM users WHERE id = ?")
        ) {
            ps.setInt(1, id);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    return Optional.of(new User(it.getInt("id"), it.getString("name"),
                            it.getString("email"),
                            it.getString("phone")));
                }
            }
        } catch (Exception e) {
            LOG.warn("Can't find user by id", e);
        }
        return Optional.empty();
    }

    public Optional<User> findUserByEmailAndPhone(String email, String phone) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("SELECT * FROM users WHERE email = ? AND phone = ?")
        ) {
            ps.setString(1, email);
            ps.setString(2, phone);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    return Optional.of(new User(it.getInt("id"), it.getString("username"),
                            it.getString("email"),
                            it.getString("phone")));
                }
            }
        } catch (Exception e) {
            LOG.warn("Can't find user by email and phone", e);
        }
        return Optional.empty();
    }
}
