package ru.yandex.practicum.filmorate.storage.film;

import lombok.Data;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.*;

@Component
@Data
public class InMemoryFilmStorage implements FilmStorage {

    private List<Film> films = new ArrayList<>();
    private int maxId = 1;

    @Override
    public void add(Film film) {
        Set<Integer> ids = new HashSet<>();
        for (Film f : films) {
            ids.add(f.getId());
        }
        if (ids.contains(film.getId())) {
            return;
        }
        if (film.getId() == 0) {
            film.setId(maxId);
            maxId++;
        }
        films.add(film);
    }

    @Override
    public void remove(long l) {
        films.remove(l);
    }

    @Override
    public void update(Film film) {
        //System.out.println(films);
        films.removeIf(f -> f.getId() == film.getId());
        //System.out.println(films);
        films.add(film);
    }

    public Film find(int id) {
        Optional<Film> film = films.stream().filter(x -> x.getId() == id).findFirst();
        return film.orElse(null);
    }

}
