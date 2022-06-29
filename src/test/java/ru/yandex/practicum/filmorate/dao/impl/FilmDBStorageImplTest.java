package ru.yandex.practicum.filmorate.dao.impl;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.dao.FilmDBStorage;
import ru.yandex.practicum.filmorate.model.Film;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class FilmDBStorageImplTest {
    private final FilmDBStorage storage;

    @BeforeEach
    void insertIntoDatabase() {
        Film film = new Film("Name", "1979-04-17", "description", 3, 1);
        storage.createFilm(film);

    }

    @AfterEach
    void clearDatabase() {
        storage.removeFilm(1);
    }

    @Test
    void createFilm() {
        assertNotNull(storage.getFilmById(1));
    }

    @Test
    void updateFilm() {
        storage.updateFilm(new Film(1, "New Name", "description", "1979-04-17", 3, 1));
        assertEquals(storage.getFilmById(1).toString(), "Film(id=1, name=New Name, rate=0, description=description, releaseDate=1979-04-17, duration=3, genres=null, likes=[], mpa=MPA(id=1, name=G))");
    }

    @Test
    void removeFilm() {
        storage.removeFilm(1);
        assertNull(storage.getFilmById(1));
    }

    @Test
    void addLike() {
        storage.addLike(1, 1);
        assertEquals(storage.getFilmById(1).getLikes().size(), 1);
    }

    @Test
    void getFilmById() {
        assertNotNull(storage.getFilmById(1));
    }

    @Test
    void getAllFilms() {
        storage.createFilm(new Film("Name", "1979-04-17", "description", 3, 1));
        assertEquals(storage.getAllFilms().size(), 2);
        storage.removeFilm(2);
    }

    @Test
    void getFilmsSortedByLikes() {
        storage.createFilm(new Film("Name", "1979-04-17", "description", 3, 1));
        storage.addLike(2, 1);
        storage.addLike(2, 2);
        storage.addLike(1, 1);
        assertEquals(storage.getFilmsSortedByLikes().get(0), storage.getFilmById(2));
        storage.removeFilm(2);
    }

    @Test
    void removeLike() {
        storage.addLike(1, 1);
        storage.removeLike(1, 1);
        assertEquals(storage.getFilmById(1).getLikes().size(), 0);
    }
}