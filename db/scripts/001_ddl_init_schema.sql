CREATE TABLE IF NOT EXISTS users (
                       id SERIAL PRIMARY KEY,
                       username VARCHAR NOT NULL,
                       email VARCHAR NOT NULL UNIQUE,
                       phone VARCHAR NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS movie (
                          id SERIAL PRIMARY KEY,
                          name text
);

CREATE TABLE IF NOT EXISTS ticket (
                        id SERIAL PRIMARY KEY,
                        movie_id INT NOT NULL REFERENCES movie(id),
                        pos_row INT NOT NULL,
                        cell INT NOT NULL,
                        user_id INT NOT NULL REFERENCES users(id),
                        UNIQUE (movie_id, pos_row, cell)
);