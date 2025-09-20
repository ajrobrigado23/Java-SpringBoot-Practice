package com.luv2code.springboot.thymeleafdemo.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HelloWorldController {

    // *update - change it to GET requests
    // need a controller method to show initial HTML form
    @GetMapping("/showForm")
    public String showForm() {
        // background -> src/main/resources/templates/helloworld-form.html
        return "helloworld-form";
    }

    // need a controller to process the HTML form
    @RequestMapping("/processForm") // send it to the process form endpoint
    public String processForm() {
        return "helloworld";
    }

    // need a controller method to read form data and
    // add to the model
    @RequestMapping("/processFormVersionTwo")
    public String letsShoutDude(HttpServletRequest request, Model model) {

        // read the request parameter from the HTML form
        String theName = request.getParameter("studentName");

        // convert the data to all caps
        theName = theName.toUpperCase();

        // create message
        String result = "Yo! " + theName;

        // add message to the model (Name, Value)
        model.addAttribute("message", result);

        return "helloworld";
    }

    // Use the @RequestParam(HTML form field name) bindings, assign theName to value of request parameter
    @PostMapping("/processFormVersionThree")
    public String processFormVersionThree(@RequestParam("studentName") String theName,
                                          Model model) {

        // convert the data to all caps
        theName = theName.toUpperCase();

        // create message
        String result = "Hey my friend from v3! " + theName;

        // add message to the model (Name, Value)
        model.addAttribute("message", result);

        return "helloworld";
    }
}
