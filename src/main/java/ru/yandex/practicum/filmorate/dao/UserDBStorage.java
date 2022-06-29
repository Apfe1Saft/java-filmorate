package ru.yandex.practicum.filmorate.dao;

import ru.yandex.practicum.filmorate.model.User;

import javax.xml.bind.ValidationException;
import java.util.List;

public interface UserDBStorage {
    User addUser(User user);

    void updateUser(User user);

    void removeUser(int id);

    void addFriend(int userId, int friendId);

    void removeFriend(int userId, int friendId);

    User findUserById(int id);

    List<User> getAllUsers();

    List<User> getAllFriends(int id);

    List<User> getCommonFriends(int userOne, int userTwo);

}
