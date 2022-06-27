package ru.yandex.practicum.filmorate.dao;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.Set;

public interface GenreDBStorage {
    Genre getGenreById(int id);

    Set<Genre> getAllGenres();
}
