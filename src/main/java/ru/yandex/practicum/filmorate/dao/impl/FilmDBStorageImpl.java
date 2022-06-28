package ru.yandex.practicum.filmorate.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.controller.GenreController;
import ru.yandex.practicum.filmorate.controller.MPAController;
import ru.yandex.practicum.filmorate.dao.FilmDBStorage;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.MPA;

import java.util.ArrayList;
import java.util.List;

@Component
public class FilmDBStorageImpl implements FilmDBStorage {
    private final Logger log = LoggerFactory.getLogger(FilmDBStorageImpl.class);
    private final JdbcTemplate jdbcTemplate;

    public FilmDBStorageImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int getMaxId() {
        List<Film> films = getAllFilms();
        int maxId = 0;
        for (Film film : films) {
            if (maxId < film.getId()) maxId = film.getId();
        }
        return maxId + 1;
    }

    @Override
    public void createFilm(Film film) {
        if (film.getId() == 0) {
            film.setId(getMaxId());
        }
        int filmRows = jdbcTemplate.update("insert into films(id,film_name,description,release_date,duration,mpa_id) values(?,?,?,?,?,?)",
                film.getId(), film.getName(), film.getDescription(), film.getReleaseDate(), film.getDuration(), film.getMpa().getId());
        for (int id : film.getLikes()) {
            int likeRows = jdbcTemplate.update("insert into likes(film_id,user_id) values(?,?)", film.getId(), id);
        }
        if (film.getGenres() != null) {
            for (Genre genres : film.getGenres()) {
                int likeRows = jdbcTemplate.update("insert into genre_list(film_id,genre_id) values(?,?)", film.getId(), genres.getId());
            }
        }
    }

    @Override
    public void updateFilm(Film film) {
        removeFilm(film.getId());
        createFilm(film);
    }

    @Override
    public void removeFilm(int id) {
        int filmRows = jdbcTemplate.update("delete from films where id = ?", id);
        int likesRows = jdbcTemplate.update("delete from likes where film_id = ?", id);
        likesRows = jdbcTemplate.update("delete from genre_list where film_id = ?", id);
    }

    @Override
    public void addLike(int filmId, int userId){
            int likeRows = jdbcTemplate.update("insert into likes(film_id,user_id) values (?,?);", filmId, userId);
    }

    @Override
    public Film getFilmById(int id) {
        SqlRowSet filmRows = jdbcTemplate.queryForRowSet("select * from films where id = ?", id);
        if (filmRows.next()) {
            Film film = new Film(
                    filmRows.getInt("id"),
                    filmRows.getString("film_name"),
                    filmRows.getString("description"),
                    filmRows.getString("release_date"),
                    filmRows.getInt("duration")
            );
            film.setMpa(MPAController.getMPAById(filmRows.getInt("mpa_id")));
            filmRows = jdbcTemplate.queryForRowSet("select * from genre_list where film_id = ?", id);
            while (filmRows.next()) {
                film.addGenre(filmRows.getInt("genre_id"),
                        GenreController.getGenreByID(filmRows.getInt("genre_id")).getName());
            }
            log.info("Найден фильм: {} {}", film.getId(), film.getName());

            SqlRowSet likesRows = jdbcTemplate.queryForRowSet("select * from likes where film_id = ?", film.getId());
            while (likesRows.next()) {
                film.addLike(likesRows.getInt("user_id"));
            }
            return film;
        } else {
            log.info("Фильм с идентификатором {} не найден.", id);
            return null;
        }
    }

    @Override
    public List<Film> getAllFilms() {
        SqlRowSet filmRows = jdbcTemplate.queryForRowSet("select * from films ");
        List<Film> films = new ArrayList<>();
        while (filmRows.next()) {
            int id = filmRows.getInt("id");
            films.add(getFilmById(id));
        }
        return films;
    }


    @Override
    public List<Film> getFilmsSortedByLikes() {
        SqlRowSet genreRows = jdbcTemplate.queryForRowSet("select distinct film_id,count(user_id) as cui from likes group by film_id order by count(user_id) desc;");
        List<Film> sortedFilms = new ArrayList<>();
        while (genreRows.next()) {
            sortedFilms.add(getFilmById(genreRows.getInt("film_id")));
        }
        if (sortedFilms.size() == 0) {
            for (Film film : getAllFilms()) {
                if (film.getLikes().size() == 0) {
                    sortedFilms.add(film);
                }
            }
        }
        return sortedFilms;
    }

    @Override
    public void removeLike(int userId, int filmId) {
            int filmRows = jdbcTemplate.update("delete from likes where user_id = ? and film_id = ?", userId, filmId);
    }
}
