package ru.yandex.practicum.filmorate.dao;

import ru.yandex.practicum.filmorate.model.MPA;

import java.util.List;

public interface MPADBStorage {
    MPA getMPAById(int id);

    List<MPA> getAllMPA();
}
