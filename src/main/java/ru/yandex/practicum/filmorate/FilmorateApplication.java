package ru.yandex.practicum.filmorate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Arrays;

@SpringBootApplication
public class FilmorateApplication {
    static ConfigurableApplicationContext context;
    public static void main(String[] args) {
        context =SpringApplication.run(FilmorateApplication.class, args);

    }
    public static void stop() {
        context.close();
    }
}
