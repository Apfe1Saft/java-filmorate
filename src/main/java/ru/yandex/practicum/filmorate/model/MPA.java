package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.controller.MPAController;
import ru.yandex.practicum.filmorate.service.MPAService;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;


@Validated
@RestController
@RequestMapping("/mpa")
@Slf4j
@Data
public class MPA {
    private MPAService service;
    @Min(1)
    @Max(7)
    int id;
    String name;
    public MPA(){
    }
    public MPA(MPAService service){
        this.service = service;
    }
    public MPA(int id) {
        this.id = id;
        this.name = MPAController.getMPAById(id).getName();
    }
    public MPA(int id,String name){
        this.id = id;
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
        this.name = MPAController.getMPAById(id).getName();
    }
}