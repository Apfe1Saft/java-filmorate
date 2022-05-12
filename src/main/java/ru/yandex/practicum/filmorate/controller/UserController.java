package ru.yandex.practicum.filmorate.controller;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.data.StaticData;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final List<User> users = new ArrayList<>();
    int maxId = 0;

    @GetMapping
    public List<User> show() {
        return users;
    }

    @PostMapping
    public User create(@RequestBody String body, HttpServletResponse response) {
        try {
            Gson gson = StaticData.gsonForAll;
            User user = gson.fromJson(body, User.class);
            if (!user.getEmail().equals("") && user.getEmail().contains("@")) {
                if (!user.getLogin().equals("") && !user.getLogin().contains(" ")) {
                    if (user.getName().equals("")) {
                        user.setName(user.getLogin());
                    }
                    if (user.getBirthday().isBefore(LocalDate.now())) {
                        if(user.getId() == 0) user.setId(maxId+1);
                        if(maxId<user.getId())maxId  = user.getId();
                        users.add( user);
                        log.info("/POST добавлен пользователь");
                        response.setStatus(200);
                        return user;
                    }
                }
            }
            log.error("ОШИБКА /POST : Не правильно введен пользователь");
            throw new ValidationException("Не правильно введен пользователь");
        }catch (Exception exception){
            response.setStatus(400);
            return null;
        }
    }

    @PutMapping
    public void put(@RequestBody String body,HttpServletResponse response) {
        Gson gson = StaticData.gsonForAll;
        User user;
        try {
            user = gson.fromJson(body, User.class);
        } catch (Exception e) {
            gson = StaticData.gsonForMinutes;
            user = gson.fromJson(body, User.class);
            boolean flag = false;
            for(User u: users){
                if (u.getId() == user.getId()) {
                    flag = true;
                    break;
                }
            }
            if (flag) {
                create(body, response);
            } else update(body,response);
        }
    }

    @PatchMapping
    public void update(@RequestBody String body, HttpServletResponse response) {
        try {
            Gson gson = StaticData.gsonForAll;
            User user = gson.fromJson(body, User.class);
            if (!user.getEmail().equals("") && user.getEmail().contains("@")) {
                if (!user.getLogin().equals("") && !user.getLogin().contains(" ")) {
                    if (user.getName().equals("")) {
                        user.setName(user.getLogin());
                    }
                    if (user.getBirthday().isBefore(LocalDate.now())) {
                        users.removeIf(u -> u.getId() == user.getId());
                        users.add(user);
                        log.info("/PATCH обновлен пользователь");
                        response.setStatus(200);
                        return;
                    }
                }
            }
            log.error("ОШИБКА /PATCH : Не правильно введен пользователь");
            throw new ValidationException("Не правильно введен пользователь");
        }catch (Exception exception){
            response.setStatus(400);
        }
    }
}
