package ru.job4j.cinema.model;

public class Ticket {

    private int id;

    private int movieId;

    private int row;

    private int cell;

    private int userId;

    public Ticket() {
    }

    public Ticket(int id, int movieId, int row, int cell, int userId) {
        this.id = id;
        this.movieId = movieId;
        this.row = row;
        this.cell = cell;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCell() {
        return cell;
    }

    public void setCell(int cell) {
        this.cell = cell;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

}
