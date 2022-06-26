package ru.yandex.practicum.filmorate.dao;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Set;

public interface GenreDBStorage {
    public Film.Genre getGenreById(int id);

    public Set<Film.Genre> getAllGenres();
}
