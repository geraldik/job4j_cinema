package ru.job4j.cinema.service;

import org.springframework.stereotype.Service;
import ru.job4j.cinema.dao.TicketDAO;
import ru.job4j.cinema.model.Ticket;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class HallService {

    private static final int RAWS_NUMBER = 5;
    private static final int CELLS_NUMBER = 5;

    private final TicketDAO ticketDAO;

    public HallService(TicketDAO ticketDAO) {
        this.ticketDAO = ticketDAO;
    }

    public List<Integer> rows() {
        List<Integer> rows = new ArrayList<>();
        for (int i = 1; i <= RAWS_NUMBER; i++) {
            rows.add(i);
        }
        return rows;
    }

    public List<Integer> cells() {
        List<Integer> cells = new ArrayList<>();
        for (int i = 1; i <= CELLS_NUMBER; i++) {
            cells.add(i);
        }
        return cells;
    }

    private Collection<Ticket> findByMovieIdAndRow(int movieId, int row) {
        return ticketDAO.findByMovieIdAndRow(movieId, row);
    }

    public Collection<Integer> getFreeSeats(int movieId, int row) {
        List<Integer> cells = cells();
        findByMovieIdAndRow(movieId, row).forEach(x -> {
            if (cells.contains(x.getCell())) {
                cells.remove(Integer.valueOf(x.getCell()));
            }
        });
        return cells;
    }

}
