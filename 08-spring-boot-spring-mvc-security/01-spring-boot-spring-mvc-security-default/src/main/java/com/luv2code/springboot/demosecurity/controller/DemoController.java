package com.luv2code.springboot.demosecurity.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DemoController {

    @GetMapping("/")
    public String showHome() {

        return "html/home";
    }

    // add a request mapping for /leaders
    @GetMapping("/leaders")
    public String showLeaders() {

        return "html/leaders";
    }

    // add a request mapping for /systems
    @GetMapping("/systems")
    public String showSystems() {

        return "html/systems";
    }
}