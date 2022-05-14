package ru.yandex.practicum.filmorate.model;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.time.Duration;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;


@Data
public class Film {
    @Min(0)
    private int id;
    @NotBlank
    @Size(min = 1)
    private String name;
    @NotBlank
    @Size(min = 1, max = 200)
    private String description;
    @LocalDateConstraint
    private LocalDate releaseDate;
    @Min(1)
    private int duration;
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
