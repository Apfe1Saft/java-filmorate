package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;

public interface UserStorage {
    default void add(User user) {
    }

    default void remove(long l) {
    }

    default void update(User user) {
    }
}
