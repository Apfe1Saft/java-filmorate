package ru.yandex.practicum.filmorate.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dao.GenreDBStorage;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.MPA;

import java.util.HashSet;
import java.util.Set;

@Component
public class GenreDBStorageImpl implements GenreDBStorage {
    private final Logger log = LoggerFactory.getLogger(FilmDBStorageImpl.class);
    private final JdbcTemplate jdbcTemplate;

    public GenreDBStorageImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public Genre getGenreById(int id) {
        SqlRowSet genreRows = jdbcTemplate.queryForRowSet("select * from genres where genre_id = ?", id);
        if(genreRows.next()){
            return new Genre(genreRows.getInt("genre_id"),genreRows.getString("genre_name"));
        }
        return null;
    }

    @Override
    public Set<Genre> getAllGenres() {
        Set<Genre> genres = new HashSet<>();
        SqlRowSet genreRows = jdbcTemplate.queryForRowSet("select * from genres");
        while(genreRows.next()){
            genres.add(new Genre(genreRows.getInt("genre_id"),genreRows.getString("genre_name")));
        }
        return genres;
    }

}
