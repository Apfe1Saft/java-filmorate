package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;


@Validated
@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController {
    private final List<Film> films = new ArrayList<>();
    private int maxId = 0;

    @GetMapping
    public List<Film> show() {
        log.info("/GET films");
        return films;
    }

    @PostMapping
    public void create(@Valid @RequestBody final Film film, HttpServletResponse response, BindingResult result) {
        try {
            if (result.hasErrors()) {
                throw new ValidationException("");
            }
            if (film.getId() == 0) film.setId(maxId + 1);
            if (maxId < film.getId()) maxId = film.getId();
            for (Film f : films) {
                if (f.getId() == film.getId()) {
                    film.setId(maxId + 1);
                    maxId++;
                }
            }
            films.add(film);
            log.info("/POST добавлен фильм");
            response.setStatus(200);
        } catch (ValidationException exception) {
            response.setStatus(400);
        }
    }

    @PutMapping
    public void put(@Valid @RequestBody final Film film, HttpServletResponse response, BindingResult result) {
        boolean flag = false;
        for (Film f : films) {
            if (f.getId() == film.getId()) {
                flag = true;
                break;
            }
        }
        if (flag) {
            create(film, response, result);
        } else update(film, response, result);
    }

    @PatchMapping
    public void update(@Valid @RequestBody final Film film, HttpServletResponse response, BindingResult result) {
        try {
            if (result.getErrorCount() != 0) {
                throw new ValidationException("");
            }
            films.removeIf(f -> f.getId() == film.getId());
            films.add(film);
            log.info("/PATCH обновлен фильм");
            response.setStatus(200);
        } catch (Exception exception) {
            response.setStatus(400);
        }
    }
}
