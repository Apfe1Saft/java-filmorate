package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.impl.MPADBStorageImpl;

@Service
public class MPAService {
    private MPADBStorageImpl storage;

    @Autowired
    public MPAService(MPADBStorageImpl storage) {
        this.storage = storage;
    }

    public MPADBStorageImpl getStorage() {
        return storage;
    }
}
