package ru.yandex.practicum.filmorate.service;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;

import java.util.LinkedList;
import java.util.List;

@Service
@Data
public class UserService {
    InMemoryUserStorage storage;

    @Autowired
    public UserService(InMemoryUserStorage storage) {
        this.storage = storage;
    }

    public void addFriend(int firstUser, int secondUser) {
        storage.find(firstUser).addFriend(secondUser);
        storage.find(secondUser).addFriend(firstUser);
    }

    public void removeFriend(int firstUser, int secondUser) {
        storage.find(firstUser).getFriends().removeIf(f -> f == secondUser);
        storage.find(secondUser).getFriends().removeIf(f -> f == firstUser);
    }

    public List<User> mutualFriends(int firstUser, int secondUser) {
        List<User> answer = new LinkedList<>();
        for (Integer l : storage.find(firstUser).getFriends()) {
            if (storage.find(secondUser).getFriends().contains(l)) {
                answer.add(storage.find(l));
            }
        }
        return answer;
    }

    public List<User> getFriends(int id) {
        List<User> answer = new LinkedList<>();
        for (int i : storage.find(id).getFriends()) {
            answer.add(storage.find(i));
        }
        return answer;
    }
}
