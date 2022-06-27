package ru.yandex.practicum.filmorate.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.yandex.practicum.filmorate.controller.GenreController;

@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Genre {
    @EqualsAndHashCode.Include
    private int id;
    private String name;

    public Genre(int id) {
        this.id = id;
        this.name = GenreController.getGenreByID(id).getName();
    }

    public Genre(int id, String name) {
        this.id = id;
        this.name = name;
    }
}