package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.InMemoryFilmStorage;

import java.util.*;

@Service
public class FilmService {
    private InMemoryFilmStorage storage;

    @Autowired
    public FilmService(InMemoryFilmStorage storage){
        this.storage = storage;
    }

    public void addLike(int filmId, int userId) {
        if(storage.find(filmId)!=null){
            storage.find(filmId).addLike(userId);
        }
        else {
            throw new ObjectNotFoundException("Фильма с таким ID нет");
        }
    }

    public void removeLike(int filmId,int userId) {
        storage.find(filmId).getLikes().removeIf(f -> f == userId);
    }

    public List<Film> popularNFilms(int n) {
        List<Film> films = storage.getFilms();
        HashMap<Integer, Film> likesAndFilms = new HashMap<>();
        Collections.sort(films);
        int i = 1;
        for(Film film : films){
            likesAndFilms.put(i,film);
            i++;
        }
        List<Film> answer= new LinkedList<>();
        for(int k = 1;k<=n;++k){
            if(likesAndFilms.get(k) == null) break;
            answer.add(likesAndFilms.get(k));
        }
        return answer;
    }

    public InMemoryFilmStorage getStorage() {
        return storage;
    }
}
