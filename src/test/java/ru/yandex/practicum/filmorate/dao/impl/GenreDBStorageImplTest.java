package ru.yandex.practicum.filmorate.dao.impl;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.dao.GenreDBStorage;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class GenreDBStorageImplTest {
    private final GenreDBStorage storage;

    @Test
    void getGenreByIdComedy() {
        assertEquals(storage.getGenreById(1).getName(), "Комедия");
    }

    @Test
    void getGenreByIdDrama() {
        assertEquals(storage.getGenreById(2).getName(), "Драма");
    }

    @Test
    void getGenreByIdCartoon() {
        assertEquals(storage.getGenreById(3).getName(), "Мультфильм");
    }

    @Test
    void getAllGenres() {
        assertEquals(storage.getAllGenres().size(), 6);
    }
}