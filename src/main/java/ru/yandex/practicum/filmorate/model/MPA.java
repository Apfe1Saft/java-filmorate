package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;


@Data
@Validated
public class MPA {
    @Min(1)
    @Max(7)
    int id;
    String name;

    public MPA() {
    }

    public MPA(int id) {
        this.id = id;
    }

    public MPA(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }
}