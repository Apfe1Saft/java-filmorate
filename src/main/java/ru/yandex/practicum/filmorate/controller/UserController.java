package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Validated
@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {
    private UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping
    public List<User> show() {
        return service.getStorage().getUsers();
    }

    @PostMapping
    public User create(@Valid @RequestBody User user, HttpServletResponse response, BindingResult result) {
        if (user.getId() < 0) throw new ValidationException("");
        if (result.hasErrors()) {
            throw new ValidationException("");
        }
        if (user.getName().equals("")) {
            user.setName(user.getLogin());
        }
        service.getStorage().add(user);
        log.info("/POST добавлен пользователь");
        response.setStatus(200);
        return user;
    }

    @PutMapping
    public User put(@Valid @RequestBody User user, HttpServletResponse response, BindingResult result) {
        if (user.getId() < 0) throw new ValidationException("");
        if (result.hasErrors()) {
            throw new ValidationException("");
        }
        boolean flag = false;
        for (User u : service.getStorage().getUsers()) {
            if (u.getId() == user.getId()) {
                flag = true;
                break;
            }
        }
        if (flag) {
            return update(user, response, result);
        } else return create(user, response, result);
    }

    @PatchMapping
    public User update(@RequestBody User user, HttpServletResponse response, BindingResult result) {
        if (user.getId() < 0) throw new ValidationException("");
        if (result.hasErrors()) {
            throw new ValidationException("");
        }
        if (user.getName().equals("")) {
            user.setName(user.getLogin());
        }
        service.getStorage().update(user);
        log.info("/PATCH обновлен пользователь");
        response.setStatus(200);
        return user;
    }

    @GetMapping("/{id}")
    public Optional<User> getById(@PathVariable int id) {
        if (id < 0) throw new ValidationException("");
        return service.getStorage().getUsers().stream().filter(x -> x.getId() == id).findFirst();
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public List<User> commonFriends(@PathVariable("id") int id, @PathVariable("otherId") int otherId, HttpServletResponse response) {
        if (otherId < 0 || id < 0) throw new ValidationException("");
        response.setStatus(200);
        return service.mutualFriends(id, otherId);
    }

    @PutMapping("/{id}/friends/{friendId}")
    public void addFriend(@PathVariable("id") int id, @PathVariable("friendId") int friendId, HttpServletResponse response) {
        if (friendId < 0 || id < 0) throw new ValidationException("");
        response.setStatus(200);
        service.addFriend(id, friendId);
    }

    @GetMapping("/{id}/friends")
    public List<User> getAllFriends(@PathVariable("id") int id, HttpServletResponse response) {
        if (id < 0) throw new ValidationException("");
        response.setStatus(200);
        return service.getFriends(id);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public void removeLike(@PathVariable("id") int id, @PathVariable("friendId") int friendId) {
        service.removeFriend(id, friendId);
    }

}
