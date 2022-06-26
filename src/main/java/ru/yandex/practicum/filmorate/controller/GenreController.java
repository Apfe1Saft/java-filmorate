package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.GenreService;

import java.util.Comparator;
import java.util.stream.Stream;

@Validated
@RestController
@RequestMapping("/genres")
@Slf4j
public class GenreController {
    private GenreService service;

    public GenreController(GenreService service) {
        this.service = service;
    }

    @GetMapping
    public Stream<Film.Genre> getAllGenres() {
        log.info("/genres выполнен");
        return service.getStorage().getAllGenres().stream().sorted(Comparator.comparing(Film.Genre::getId));
    }

    @GetMapping("/{id}")
    public Film.Genre getGenreByID(@PathVariable("id") int id) {
        if (id < 1 || id > 6) throw new ValidationException("Wrong genre id.");
        log.info("/genres/{id} выполнен");
        return service.getStorage().getGenreById(id);
    }
}
