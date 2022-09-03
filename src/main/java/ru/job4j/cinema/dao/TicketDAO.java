package ru.job4j.cinema.dao;

import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class TicketDAO {
    private static final String CREATE_TICKET = "INSERT INTO ticket(movie_id, pos_row, cell, user_id) VALUES (?, ?, ?, ?)";
    private static final String FIND_BY_MOVIE_ID_AND_ROW = "SELECT * FROM ticket WHERE movie_id = ? AND pos_row = ?";
    private static final String FIND_BY_USER = "SELECT * FROM ticket WHERE user_id = ?";
    private static final String FIND_BY_MOVIE_ID = "SELECT * FROM ticket WHERE movie_id = ?";
    private final BasicDataSource pool;

    private static final Logger LOG = LoggerFactory.getLogger(UserDAO.class.getName());

    public TicketDAO(BasicDataSource pool) {
        this.pool = pool;
    }

    public Optional<Ticket> createTicket(Ticket ticket) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     CREATE_TICKET,
                     PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setInt(1, ticket.getMovieId());
            ps.setInt(2, ticket.getRow());
            ps.setInt(3, ticket.getCell());
            ps.setInt(4, ticket.getUserId());
            LOG.info(ps.toString());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    ticket.setId(id.getInt(1));
                }
            }
        } catch (Exception e) {
            LOG.warn("Can't add ticket", e);
            ticket = null;
        }
        return Optional.ofNullable(ticket);
    }

    public List<Ticket> findByMovieIdAndRow(int movieId, int row) {
        List<Ticket> tickets = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     FIND_BY_MOVIE_ID_AND_ROW)
        ) {
            ps.setInt(1, movieId);
            ps.setInt(2, row);
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    tickets.add(initTicket(it));
                }
            }
        } catch (Exception e) {
            LOG.warn("Can't find tickets by movie_id and pos_row", e);
        }
        return tickets;
    }

    public List<Ticket> findByMovieId(int movieId) {
        List<Ticket> tickets = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     FIND_BY_MOVIE_ID)
        ) {
            ps.setInt(1, movieId);
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    tickets.add(initTicket(it));
                }
            }
        } catch (Exception e) {
            LOG.warn("Can't find tickets by movie_id", e);
        }
        return tickets;
    }

    public List<Ticket> findByUser(User user) {
        List<Ticket> tickets = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     FIND_BY_USER)
        ) {
            ps.setInt(1, user.getId());
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    tickets.add(initTicket(it));
                }
            }
        } catch (Exception e) {
            LOG.warn("Can't find tickets for user", e);
        }
        return tickets;
    }

    private static Ticket initTicket(ResultSet it) throws SQLException {
        return new Ticket(it.getInt("id"), it.getInt("movie_id"),
                it.getInt("pos_row"),
                it.getInt("cell"),
                it.getInt("user_id"));
    }
}
