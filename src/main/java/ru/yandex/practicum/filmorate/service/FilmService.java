package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.FilmDBStorage;
import ru.yandex.practicum.filmorate.dao.impl.FilmDBStorageImpl;
import ru.yandex.practicum.filmorate.exception.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.InMemoryFilmStorage;

import javax.xml.bind.ValidationException;
import java.util.*;

@Service
public class FilmService {
    private FilmDBStorageImpl storage;

    @Autowired
    public FilmService(FilmDBStorageImpl storage) {
        this.storage = storage;
    }

    public void addLike(int filmId, int userId){
        storage.addLike(filmId, userId);
    }

    public void removeLike(int filmId, int userId){
        storage.removeLike(userId, filmId);
    }

    public List<Film> popularNFilms(int n) {
        if (n == 0) n = 10;
        List<Film> films = storage.getFilmsSortedByLikes();
        List<Film> filmsN = new ArrayList<>();
        if (films.size() == 0) return null;
        for (Film film : films) {
            filmsN.add(film);
            if (n == 0) break;
            n--;
        }
        return filmsN;
    }

    public FilmDBStorageImpl getStorage() {
        return storage;
    }
}
