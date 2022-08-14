package ru.job4j.cinema.dao;

import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.cinema.model.Movie;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class TicketDAO {
    private final BasicDataSource pool;

    private static final Logger LOG = LoggerFactory.getLogger(UserDAO.class.getName());

    public TicketDAO(BasicDataSource pool) {
        this.pool = pool;
    }

    public Optional<Ticket> createTicket(Ticket ticket) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "INSERT INTO ticket(movie_id, pos_row, cell, user_id) VALUES (?, ?, ?, ?)",
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

    public Optional<Ticket> findByMovieIdAndRowCell(Movie movie, int row, int cell) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "SELECT * FROM ticket WHERE movie_id = ? AND pos_row = ? AND cell = ?")
        ) {
            ps.setInt(1, movie.getId());
            ps.setInt(2, row);
            ps.setInt(3, cell);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    return Optional.of(new Ticket(it.getInt("id"), it.getInt("session_id"),
                            it.getInt("pos_row"),
                            it.getInt("cell"),
                            it.getInt("user_id")));
                }
            }
        } catch (Exception e) {
            LOG.warn("Can't find ticket by movie_id, pos_row and cell", e);
        }
        return Optional.empty();
    }

    public Optional<List<Ticket>> findAllTicketsForMovieId(int movieId) {
        List<Ticket> tickets = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "SELECT * FROM ticket WHERE movie_id = ?")
        ) {
            ps.setInt(1, movieId);
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    tickets.add(new Ticket(it.getInt("id"), it.getInt("movie_id"),
                        it.getInt("pos_row"),
                        it.getInt("cell"),
                        it.getInt("user_id")));
                }
                return Optional.of(tickets);
            }
        } catch (Exception e) {
            LOG.warn("Can't find all tickets", e);
        }
        return Optional.empty();
    }

    public List<Ticket> findByMovieIdAndRow(int movieId, int row) {
        List<Ticket> tickets = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "SELECT * FROM ticket WHERE movie_id = ? AND pos_row = ?")
        ) {
            ps.setInt(1, movieId);
            ps.setInt(2, row);
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    tickets.add(new Ticket(it.getInt("id"), it.getInt("movie_id"),
                            it.getInt("pos_row"),
                            it.getInt("cell"),
                            it.getInt("user_id")));
                }
            }
        } catch (Exception e) {
            LOG.warn("Can't find tickets by movie_id and pos_row", e);
        }
        return tickets;
    }

    public List<Ticket> findByUser(User user) {
        List<Ticket> tickets = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "SELECT * FROM ticket WHERE user_id = ?")
        ) {
            ps.setInt(1, user.getId());
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    tickets.add(new Ticket(it.getInt("id"), it.getInt("movie_id"),
                            it.getInt("pos_row"),
                            it.getInt("cell"),
                            it.getInt("user_id")));
                }
            }
        } catch (Exception e) {
            LOG.warn("Can't find tickets for user", e);
        }
        return tickets;
    }
}
