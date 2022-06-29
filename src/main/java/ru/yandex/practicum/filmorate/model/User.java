package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.time.LocalDate;
import java.util.*;

@Data
public class User {
    private int id;
    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String login;
    private String name;
    @BirthdayConstraint
    private LocalDate birthday;
    private List<Integer> friends = new ArrayList<>();

    public List<Integer> getFriends() {
        return friends;
    }

    public void addFriend(int id) {
        if (!friends.contains(id)) {
            friends.add(id);
        }
    }

    public User(int id, String name, String login, String email, String birthday) {
        this.id = id;
        this.name = name;
        this.login = login;
        this.email = email;
        this.birthday = LocalDate.parse(birthday);
    }

    public User(String name, String login, String email, String birthday) {
        this.name = name;
        this.login = login;
        this.email = email;
        this.birthday = LocalDate.parse(birthday);
    }

    public User() {

    }
}

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = BirthdayValidator.class)
@interface BirthdayConstraint {
    String message() default "{birthday is after now}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

class BirthdayValidator implements ConstraintValidator<BirthdayConstraint, LocalDate> {
    @Override
    public boolean isValid(LocalDate date, ConstraintValidatorContext constraintValidatorContext) {
        return date.isBefore(LocalDate.now());
    }
}
