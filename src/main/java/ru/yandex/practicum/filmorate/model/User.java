package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.time.LocalDate;

@Data
public class User {
    @Min(1)
    private int id;
    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String login;
    private String name;
    @BirthdayConstraint
    private LocalDate birthday;
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
