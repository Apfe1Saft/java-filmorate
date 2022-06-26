package ru.yandex.practicum.filmorate.dao;

import ru.yandex.practicum.filmorate.model.MPA;

import java.util.List;

public interface MPADBStorage {
    public MPA getMPAById(int id);

    public List<MPA> getAllMPA();
}
