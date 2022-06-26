package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.impl.GenreDBStorageImpl;

@Service
public class GenreService {
    GenreDBStorageImpl storage;

    @Autowired
    public GenreService(GenreDBStorageImpl storage) {
        this.storage = storage;
    }

    public GenreDBStorageImpl getStorage() {
        return storage;
    }
}
