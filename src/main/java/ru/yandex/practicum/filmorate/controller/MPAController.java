package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.MPA;
import ru.yandex.practicum.filmorate.service.MPAService;

import java.util.List;


@Validated
@RestController
@RequestMapping("/mpa")
@Slf4j
public class MPAController {
    private MPAService service;

    public MPAController(MPAService service) {
        this.service = service;
    }

    @GetMapping
    public List<MPA> getAllMPA() {
        log.info("/genres выполнен");
        return service.getStorage().getAllMPA();
    }

    @GetMapping("/{id}")
    public MPA getMPAById(@PathVariable("id") int id) {
        if (id < 1 || id > 5) throw new ValidationException("Wrong mpa id.");
        log.info("/genres/{id} выполнен");
        return service.getStorage().getMPAById(id);
    }
}