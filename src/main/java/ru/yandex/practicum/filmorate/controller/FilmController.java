package ru.yandex.practicum.filmorate.controller;

import com.sun.net.httpserver.HttpExchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import com.google.gson.*;
import ru.yandex.practicum.filmorate.data.StaticData;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/films")
public class FilmController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final List<Film> films = new ArrayList<>();
    int maxId = 0;

    @GetMapping
    public List<Film> show() {
        log.info("/GET films");
        return films;
    }

    @PostMapping
    public void create(@RequestBody String body, HttpServletResponse response) {
        try {
            Gson gson = StaticData.gsonForAll;
            Film film;
            try {
                 film = gson.fromJson(body, Film.class);
            }catch (Exception e){
                gson = StaticData.gsonForMinutes;
                film = gson.fromJson(body, Film.class);
            }
            if (film.getName() != null ) {
                if(!film.getName().equals("")) {
                    if (film.getDescription().length() <= 200 & film.getDescription().length()>0) {
                        if (film.getReleaseDate().isAfter(LocalDate.of(1895, 12, 28))) {
                            if (!film.getDuration().isNegative()) {
                                if (film.getId() == 0) film.setId(maxId + 1);
                                if (maxId < film.getId()) maxId = film.getId();
                                films.add(film);
                                log.info("/POST добавлен фильм");
                                response.setStatus(200);
                                return;
                            }
                        }
                    }
                }
            }
            log.error("ОШИБКА /POST : Не правильно введен фильм");
            throw new ValidationException("Не правильно введен фильм");
        }catch (ValidationException exception){
            response.setStatus(400);
        }
    }

    @PutMapping
    public void put(@RequestBody String body,HttpServletResponse response) {
        Gson gson = StaticData.gsonForAll;
        Film film;
        try {
            film = gson.fromJson(body, Film.class);
        } catch (Exception e) {
            gson = StaticData.gsonForMinutes;
            film = gson.fromJson(body, Film.class);
            boolean flag = false;
            for(Film f: films){
                if (f.getId() == film.getId()) {
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
    public void update(@RequestBody String body,HttpServletResponse response) {
        try {
            Gson gson = StaticData.gsonForAll;
            Film film;
            try {
                film = gson.fromJson(body, Film.class);
            }catch (Exception e){
                gson = StaticData.gsonForMinutes;
                film = gson.fromJson(body, Film.class);
            }
            if (!film.getName().equals("") & film.getName() != null) {
                if (film.getDescription().length() <= 200) {
                    if (film.getReleaseDate().isAfter(LocalDate.of(1895, 12, 28))) {
                        if (!film.getDuration().isNegative()) {
                            Film finalFilm = film;
                            films.removeIf(f -> f.getId() == finalFilm.getId());
                            films.add(film);
                            log.info("/PATCH обновлен фильм");
                            response.setStatus(200);
                            return;
                        }
                    }
                }
            }
            log.error("ОШИБКА /PATCH : Не правильно введен фильм");
            throw new ValidationException("Не правильно введен фильм");
        }catch (Exception exception){
            response.setStatus(400);
        }
    }
}
