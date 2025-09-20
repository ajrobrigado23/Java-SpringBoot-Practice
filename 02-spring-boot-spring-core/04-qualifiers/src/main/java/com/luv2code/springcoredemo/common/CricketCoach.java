package com.luv2code.springcoredemo.common;

import org.springframework.stereotype.Component;

// Mark the class as a spring bean makes it available for dependency injection.
@Component
public class CricketCoach implements Coach {

    @Override
    public String getDailyWorkOut() {
        return "Practice fast bowling for 15 minutes ;)";
    }
}
