package ru.yandex.practicum.filmorate.dao.impl;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.dao.MPADBStorage;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class MPADBStorageImplTest {
    private final MPADBStorage storage;


    @Test
    void getMPAByIdG() {
        assertEquals(storage.getMPAById(1).getName(), "G");
    }

    @Test
    void getMPAByIdPG() {
        assertEquals(storage.getMPAById(2).getName(), "PG");
    }

    @Test
    void getMPAByIdPG13() {
        assertEquals(storage.getMPAById(3).getName(), "PG-13");
    }

    @Test
    void getMPAByIdR() {
        assertEquals(storage.getMPAById(4).getName(), "R");
    }

    @Test
    void getMPAByIdNC17() {
        assertEquals(storage.getMPAById(5).getName(), "NC-17");
    }

    @Test
    void getAllMPA() {
        assertEquals(storage.getAllMPA().size(), 5);
    }
}