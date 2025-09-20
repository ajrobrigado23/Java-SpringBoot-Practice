package com.luv2code.springdemo.mvc.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CourseCodeConstraintValidator implements ConstraintValidator<CourseCode, String> {

    // It says that we validate it again a given prefix
    private String coursePrefix;

    @Override
    public void initialize(CourseCode theCourseCode) {
        // Assign the value passed in from our annotation ("L U V")
        this.coursePrefix = theCourseCode.value();
    }

    // Write the business logic that return true or false if the given string validates

    // Parameter - (HTML Form DATA entered by the user, Give additional error message if we need it)
    @Override
    public boolean isValid(String theCode,
                           ConstraintValidatorContext constraintValidatorContext) {

        // does it start with "LUV"
        boolean result;

        // check if null (user didn't enter anything)
        if (theCode != null) {
            result = theCode.startsWith(coursePrefix);
        }
        else {
            // Field not required so return true
            return true;
        }

        return result;
    }
}
