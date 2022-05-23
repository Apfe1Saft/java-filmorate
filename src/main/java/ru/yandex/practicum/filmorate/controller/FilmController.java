package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Validated
@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController {
    private FilmService service;

    public FilmController(FilmService service) {
        this.service = service;
    }

    @GetMapping
    public List<Film> show() {
        log.info("/GET films");
        if (service.getStorage().getFilms() != null)
            return service.getStorage().getFilms();
        else return null;
    }

    @PostMapping
    public @Valid Film create(@Valid @RequestBody final Film film, HttpServletResponse response, BindingResult result) {
        if (result.hasErrors() || film.getId() < 0) {
            throw new ValidationException("");
        }
        service.getStorage().add(film);
        log.info("/POST добавлен фильм");
        response.setStatus(200);
        return film;
    }

    @PutMapping
    public @Valid Film put(@Valid @RequestBody final Film film, HttpServletResponse response, BindingResult result) {
        boolean flag = false;
        if (film.getId() < 0) throw new ValidationException("");
        for (Film f : service.getStorage().getFilms()) {
            if (f.getId() == film.getId()) {
                flag = true;
                break;
            }
        }
        if (flag) {
            return update(film, response, result);
        } else return create(film, response, result);
    }

    @PatchMapping
    public @Valid Film update(@Valid @RequestBody final Film film, HttpServletResponse response, BindingResult result) {
        if (result.getErrorCount() != 0 || film.getId() < 0) {
            throw new ValidationException("");
        }
        if (service.getStorage().find(film.getId()) == null) {
            throw new ObjectNotFoundException("");
        }
        service.getStorage().update(film);
        log.info("/PATCH обновлен фильм");
        response.setStatus(200);
        return film;
    }

    @GetMapping("/{id}")
    public Optional<Film> getById(@PathVariable("id") int id, HttpServletResponse response) {
        Optional<Film> film = Optional.ofNullable(service.getStorage().find(id));
        if (film.isPresent()) {
            response.setStatus(200);
            return film;
        }
        response.setStatus(404);
        return Optional.empty();
    }

    @GetMapping("/popular")
    public List<Film> getPopularFilms(@RequestParam(defaultValue = "10") Integer count, HttpServletResponse response) {
        response.setStatus(200);
        return service.popularNFilms(count);
    }

    @PutMapping("/{id}/like/{userId}")
    public void setLike(@PathVariable("id") int id, @PathVariable("userId") int userId, HttpServletResponse response) {
        try {
            service.addLike(id, userId);
            response.setStatus(200);
        } catch (ObjectNotFoundException e) {
            response.setStatus(404);
        }
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void removeLike(@PathVariable("id") int id, @PathVariable("userId") int userId, HttpServletResponse response) {
        if (userId < 0 || id < 0) throw new ValidationException("");
        service.removeLike(id, userId);
        response.setStatus(200);
    }
}
