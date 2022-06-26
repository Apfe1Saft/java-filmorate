package ru.yandex.practicum.filmorate.dao.impl;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dao.GenreDBStorage;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Set;

@Component
public class GenreDBStorageImpl implements GenreDBStorage {
    @Override
    public Film.Genre getGenreById(int id) {
        Film film = new Film();
        film.setGenres("{" + id + "}");
        return (Film.Genre) film.getGenres().toArray()[0];
    }

    @Override
    public Set<Film.Genre> getAllGenres() {
        Film film = new Film();
        film.setGenres("{" + 1 + "}");
        film.addGenre(2);
        film.addGenre(3);
        film.addGenre(4);
        film.addGenre(5);
        film.addGenre(6);
        return film.getGenres();
    }

}
