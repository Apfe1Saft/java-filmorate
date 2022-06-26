package ru.yandex.practicum.filmorate.dao.impl;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dao.MPADBStorage;
import ru.yandex.practicum.filmorate.model.MPA;

import java.util.ArrayList;
import java.util.List;

@Component
public class MPADBStorageImpl implements MPADBStorage {
    @Override
    public MPA getMPAById(int id) {
        System.out.println("MPA");
        return new MPA(id);
    }

    @Override
    public List<MPA> getAllMPA() {
        List<MPA> mpaList = new ArrayList<>();
        mpaList.add(new MPA(1));
        mpaList.add(new MPA(2));
        mpaList.add(new MPA(3));
        mpaList.add(new MPA(4));
        mpaList.add(new MPA(5));
        return mpaList;
    }
}
