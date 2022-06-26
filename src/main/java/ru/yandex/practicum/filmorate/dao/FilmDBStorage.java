package ru.yandex.practicum.filmorate.dao;

import ru.yandex.practicum.filmorate.model.Film;

import javax.xml.bind.ValidationException;
import java.util.List;

public interface FilmDBStorage {
    void createFilm(Film film);

    void updateFilm(Film film);

    void removeFilm(int film);

    void addLike(int filmId, int userId);

    Film getFilmById(int id);

    List<Film> getAllFilms();

    List<Film> getFilmsSortedByLikes();

    void removeLike(int userId, int filmId);
}
