package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;

public interface FilmStorage {
    default void add(Film film) {
    }

    default void remove(long l) {
    }

    default void update(Film film) {
    }
}
