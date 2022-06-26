package ru.yandex.practicum.filmorate.dao.impl;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.dao.UserDBStorage;
import ru.yandex.practicum.filmorate.model.User;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class UserDBStorageImplTest {
    private final UserDBStorage storage;

    @BeforeEach
    void insertIntoDatabase() {
        User user = new User("Name", "Login", "email@.email", "1946-08-20");
        storage.addUser(user);

    }

    @AfterEach
    void clearDatabase() {
        storage.removeUser(1);
    }

    @Test
    void addUser() {
        assertNotNull(storage.findUserById(1));
    }

    @Test
    void updateUser() {
        storage.updateUser(new User(1, "New Name", "Login", "email@.email", "1946-08-20"));
        assertEquals(storage.findUserById(1).getName(), "New Name");
    }

    @Test
    void removeUser() {
        storage.removeUser(1);
        assertNull(storage.findUserById(1));
    }

    @Test
    void addFriend() {
        User user = new User("second user", "Login", "email@.email", "1946-08-20");
        storage.addUser(user);
        storage.addFriend(1, 2);
        assertEquals(storage.findUserById(1).getFriends().size(), 1);
        storage.removeUser(2);
    }

    @Test
    void removeFriend() {
        User user = new User("second user", "Login", "email@.email", "1946-08-20");
        storage.addUser(user);
        storage.addFriend(1, 2);
        storage.removeFriend(1, 2);
        assertEquals(storage.findUserById(1).getFriends().size(), 0);
        storage.removeUser(2);
    }

    @Test
    void findUserById() {
        assertEquals(storage.findUserById(1), new User(1, "Name", "Login", "email@.email", "1946-08-20"));
    }

    @Test
    void getAllUsers() {
        User user = new User("second user", "Login", "email@.email", "1946-08-20");
        storage.addUser(user);
        assertEquals(storage.getAllUsers().size(), 2);
        storage.removeUser(2);
    }

    @Test
    void getAllFriends() {
        User user = new User("second user", "Login", "email@.email", "1946-08-20");
        storage.addUser(user);
        User user1 = new User("third user", "Login", "email@.email", "1946-08-20");
        storage.addUser(user1);
        storage.addFriend(1, 2);
        storage.addFriend(1, 3);
        assertEquals(storage.getAllFriends(1).size(), 2);
        storage.removeUser(2);
        storage.removeUser(3);
    }

    @Test
    void getCommonFriends() {
        User user = new User("second user", "Login", "email@.email", "1946-08-20");
        storage.addUser(user);
        User user1 = new User("third user", "Login", "email@.email", "1946-08-20");
        storage.addUser(user1);
        storage.addFriend(1, 2);
        storage.addFriend(1, 3);
        storage.addFriend(2, 3);
        assertEquals(storage.getCommonFriends(1, 2).size(), 1);
        storage.removeUser(2);
        storage.removeUser(3);
    }
}