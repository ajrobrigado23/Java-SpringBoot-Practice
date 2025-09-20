package com.luv2code.springdemo.mvc.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// (Helper class that contains business rules)
@Constraint(validatedBy = CourseCodeConstraintValidator.class)
// Target where we can apply this new annotation (method or field)
@Target({ElementType.METHOD, ElementType.FIELD})
// How long will the marked annotation be stored or used?
@Retention(RetentionPolicy.RUNTIME) // Retain this annotation in the Java class file. Process it at runtime
public @interface CourseCode {

    // define the attributes we can pass in to our annotation

    // define default course code
    @CourseCode(value="LUV", message="must start with LUV")
    String value() default "LUV"; // default value is 'LUV'

    // define default error message
    String message() default "must start with LUV";

    // define default groups (group the validation constraints together)
    Class<?>[] groups() default { };

    // define default payloads (give additional information about the validation error)
    Class<? extends Payload>[] payload() default {
        // Payloads: provides custom details about validation failure
            // (severity level, error code etc)
    };
}
