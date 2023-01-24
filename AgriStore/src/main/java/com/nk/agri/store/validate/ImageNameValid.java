package com.nk.agri.store.validate;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = ImageNameValidator.class)
public @interface ImageNameValid {
    //Error message
    String message() default "Invalid Image Name";
    //Represent group of constraints
    Class<?>[] groups() default {};
    //Addition information about annotation
    Class<? extends Payload>[] payload() default {};
}
