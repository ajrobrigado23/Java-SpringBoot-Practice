package com.luv2code.springboot.demo.mycoolapp.rest;

// Building a REST Controller

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FunRestController {

    // expose '/' that return "Hello world"

    @GetMapping
    public String sayHello() {
        return "Hello World";
    }
}