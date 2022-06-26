package ru.yandex.practicum.filmorate.service;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.impl.UserDBStorageImpl;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;

import javax.xml.bind.ValidationException;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.LongStream;

@Service
@Data
public class UserService {
    UserDBStorageImpl storage;

    @Autowired
    public UserService(UserDBStorageImpl storage) {
        this.storage = storage;
    }

    public void addFriend(int firstUser, int secondUser) {
        storage.addFriend(firstUser, secondUser);
    }

    public void removeFriend(int firstUser, int secondUser)  {
        storage.removeFriend(firstUser, secondUser);
    }

    public List<User> mutualFriends(int firstUser, int secondUser) {
        return storage.getCommonFriends(firstUser, secondUser);
    }

    public List<User> getFriends(int id) {
        return storage.getAllFriends(id);
    }
}
