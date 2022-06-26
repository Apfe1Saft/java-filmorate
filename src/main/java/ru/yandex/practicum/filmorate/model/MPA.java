package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
public class MPA {
    @Min(1)
    @Max(7)
    int id;
    String name;

    public MPA() {

    }

    public MPA(int id) {
        this.id = id;
        this.name = getMPANameById(id);
    }

    public void setId(int id) {
        this.id = id;
        this.name = getMPANameById(id);
    }

    public String getMPANameById(int id) {
        String answer = "G";
        switch (id) {
            case 1:
                answer = "G";
                break;
            case 2:
                answer = "PG";
                break;
            case 3:
                answer = "PG-13";
                break;
            case 4:
                answer = "R";
                break;
            case 5:
                answer = "NC-17";
                break;
        }
        return answer;
    }
}