package ru.yandex.practicum.filmorate.storage.user;

import lombok.Data;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;

@Component
@Data
public class InMemoryUserStorage implements UserStorage {
    private List<User> users = new ArrayList<>();
    private int maxId = 1;

    @Override
    public void add(User user) {
        Set<Integer> ids = new HashSet<>();
        for (User f : users) {
            ids.add(f.getId());
        }
        if (ids.contains(user.getId())) {
            return;
        }
        if (user.getId() == 0) {
            user.setId(maxId);
            maxId++;
        }
        users.add(user);
    }

    @Override
    public void remove(long l) {
        users.remove(l);
    }

    @Override
    public void update(User user) {
        users.removeIf(f -> f.getId() == user.getId());
        users.add(user);
    }

    public User find(int id) {
        Optional<User> user = users.stream().filter(x -> x.getId() == id).findFirst();
        return user.orElse(null);
    }

}
