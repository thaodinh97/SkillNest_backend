package com.example.skillnest.validator;

import java.lang.annotation.*;

import jakarta.validation.Constraint;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {DobValidator.class})
public @interface DobConstraint {
    String message() default "Invalid Dob";

    int min();

    Class<?>[] groups() default {};

    Class<? extends Package>[] payload() default {};
}
