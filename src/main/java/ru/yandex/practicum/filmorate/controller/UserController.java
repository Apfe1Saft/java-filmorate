package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {
    private final List<User> users = new ArrayList<>();
    int maxId = 0;

    @GetMapping
    public List<User> show() {
        return users;
    }

    @PostMapping
    public User create(@Valid @RequestBody User user, HttpServletResponse response, BindingResult result) {
        try {
            if (result.hasErrors()) {
                throw new ValidationException("");
            }
            if (user.getName().equals("")) {
                user.setName(user.getLogin());
            }
            if (user.getId() == 0) user.setId(maxId + 1);
            if (maxId < user.getId()) maxId = user.getId();
            users.add(user);
            log.info("/POST добавлен пользователь");
            response.setStatus(200);
            return user;
        } catch (Exception exception) {
            response.setStatus(400);
            return null;
        }
    }

    @PutMapping
    public void put(@Valid @RequestBody User user, HttpServletResponse response, BindingResult result) {
        try {
            if (result.hasErrors()) {
                throw new ValidationException("");
            }
            boolean flag = false;
            for (User u : users) {
                if (u.getId() == user.getId()) {
                    flag = true;
                    break;
                }
            }
            if (flag) {
                create(user, response, result);
            } else update(user, response, result);
        } catch (Exception e) {
            response.setStatus(400);
        }
    }

    @PatchMapping
    public void update(@RequestBody User user, HttpServletResponse response, BindingResult result) {
        try {
            if (result.hasErrors()) {
                throw new ValidationException("");
            }
            if (user.getName().equals("")) {
                user.setName(user.getLogin());
            }
            users.removeIf(u -> u.getId() == user.getId());
            users.add(user);
            log.info("/PATCH обновлен пользователь");
            response.setStatus(200);
        } catch (Exception exception) {
            response.setStatus(400);
        }
    }
}
