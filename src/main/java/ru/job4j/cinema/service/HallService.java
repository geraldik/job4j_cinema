package ru.job4j.cinema.service;

import org.springframework.stereotype.Service;
import ru.job4j.cinema.dao.TicketDAO;
import ru.job4j.cinema.model.Ticket;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.IntStream;

@Service
public class HallService {

    private static final int RAWS_NUMBER = 5;
    private static final int CELLS_NUMBER = 5;

    private final TicketDAO ticketDAO;

    public HallService(TicketDAO ticketDAO) {
        this.ticketDAO = ticketDAO;
    }

    private List<Integer> generateList(final int n) {
        return IntStream.rangeClosed(1, n).boxed().toList();
    }

    private Collection<Ticket> findByMovieIdAndRow(int movieId, int row) {
        return ticketDAO.findByMovieIdAndRow(movieId, row);
    }

    private Collection<Ticket> findByMovieId(int movieId) {
        return ticketDAO.findByMovieId(movieId);
    }

    public Collection<Integer> getFreeSeats(int movieId, int row) {
        List<Integer> cells = generateList(CELLS_NUMBER);
        findByMovieIdAndRow(movieId, row).forEach(x -> {
            if (cells.contains(x.getCell())) {
                cells.remove(Integer.valueOf(x.getCell()));
            }
        });
        return cells;
    }

    public Collection<Integer> getFreeRows(int movieId) {
        List<Integer> rows = generateList(RAWS_NUMBER);
        findByMovieId(movieId).forEach(x -> {
            if (getFreeSeats(movieId, x.getRow()).isEmpty()) {
                rows.remove(Integer.valueOf(x.getRow()));
            }
        });
        return rows;
    }
}
