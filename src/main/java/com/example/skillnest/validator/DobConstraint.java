package com.example.skillnest.validator;

import jakarta.validation.Constraint;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {DobValidator.class})
public @interface DobConstraint {
    String message() default "Invalid Dob";
    int min();
    Class<?>[] groups() default {};
    Class<? extends Package>[] payload() default {};
}
