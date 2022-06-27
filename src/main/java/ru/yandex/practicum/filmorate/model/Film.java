package ru.yandex.practicum.filmorate.model;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.time.LocalDate;
import java.util.*;

import lombok.*;
import ru.yandex.practicum.filmorate.controller.GenreController;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import javax.validation.constraints.*;


@Data
public class Film implements Comparable<Film> {
    private int id;
    @NotBlank
    @Size(min = 1)
    private String name;
    int rate;
    @NotBlank
    @Size(min = 1, max = 200)
    private String description;
    @LocalDateConstraint
    private LocalDate releaseDate;
    @Min(1)
    private int duration;
    private Set<Genre> genres = null;
    private Set<Integer> likes = new HashSet<>();//contains user id
    @NotNull
    private MPA mpa;


    public void setGenres(Object genres) {
        if (genres == null) {
            this.genres = null;
        } else {
            System.out.println("GENRES: " + genres.toString());
            if (Objects.equals(genres.toString(), "[]")) {
                this.genres = new HashSet<>();
                return;
            }
            this.genres = new HashSet<>();
            String line = genres.toString();
            line = line.replace("[", "").replace("]", "").replace("{", "")
                    .replace("}", "").replaceAll("id=", "").replaceAll(" ", "");
            String[] mass = line.split(",");
            for (String s : mass) {
                int id = Integer.parseInt(s);
                addGenre(id);
            }
        }

    }

    public Film() {
    }

    public Film(int id, String name, String description, String releaseDate, int duration) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.releaseDate = LocalDate.parse(releaseDate);
        this.duration = duration;
    }

    public Film(int id, String name, String description, String releaseDate, int duration, int mpa) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.releaseDate = LocalDate.parse(releaseDate);
        this.duration = duration;
        this.setMpa(new MPA(mpa));
    }

    public void addGenre(int id) {
        if (genres == null) genres = new HashSet<>();
        genres.add(new Genre(id));
    }


    public Film(int id, String name, String description, String releaseDate, int duration, MPA mpa, Set<Genre> genres) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.releaseDate = LocalDate.parse(releaseDate);
        this.duration = duration;
        this.mpa.id = mpa.id;
        this.genres = genres;
    }

    public Film(String name, String releaseDate, String description, int duration, int rate, MPA mpa) {
        this.name = name;
        this.releaseDate = LocalDate.parse(releaseDate);
        this.description = description;
        this.duration = duration;
        this.mpa = mpa;
    }

    public Film(String name, String releaseDate, String description, int duration) {//},int rate){
        this.name = name;
        this.releaseDate = LocalDate.parse(releaseDate);
        this.description = description;
        this.duration = duration;
    }

    public Film(String name, String releaseDate, String description, int duration, int mpa) {//},int rate){
        this.name = name;
        this.releaseDate = LocalDate.parse(releaseDate);
        this.description = description;
        this.duration = duration;
        this.setMpa(new MPA(mpa));
    }

    public void addLike(int l) {
        likes.add(l);
    }

    public int likesCount() {
        return likes.size();
    }

    @Override
    public int compareTo(Film o) {
        Integer firstCount = this.likesCount();
        Integer secondCount = o.likesCount();
        return secondCount.compareTo(firstCount);
    }
}

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = LocalDateValidator.class)
@interface LocalDateConstraint {
    String message() default "{localDate is before 1895-12-28}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

class LocalDateValidator implements ConstraintValidator<LocalDateConstraint, LocalDate> {
    @Override
    public boolean isValid(LocalDate date, ConstraintValidatorContext constraintValidatorContext) {
        return date.isAfter(LocalDate.of(1895, 12, 28));
    }
}
