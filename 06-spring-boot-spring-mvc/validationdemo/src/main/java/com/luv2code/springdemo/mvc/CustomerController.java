package com.luv2code.springdemo.mvc;

import jakarta.validation.Valid;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CustomerController {

    // add initbinder ... to convert trim input strings
    // remove leading and trailing whitespace
    // resolve issue for our validation
    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {

        // removes whitespace - leading and trailing (true -> means empty string to null)
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);

        // Register this as a custom editor
        // For every String class, apply the StringTrimmerEditor
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @GetMapping("/")
    public String showForm(Model theModel) {

        theModel.addAttribute("customer", new Customer());

        return "customer-form";
    }

    @PostMapping("/processForm")
    public String processForm(
            // @Valid -> tell spring to perform validation
            // @ModelAttribute -> read form data form that model attribute customer
            @Valid @ModelAttribute("customer") Customer theCustomer,
            // hold results of the validation
            BindingResult theBindingResult) {

        System.out.println("Last name: |" + theCustomer.getLastName() + "|");

        // if it has errors then go back to the customer-form
        if (theBindingResult.hasErrors()) {
            return "customer-form";
        } else {
            // if successful then go to the confirmation page
            return "customer-confirmation";
        }
    }
}
