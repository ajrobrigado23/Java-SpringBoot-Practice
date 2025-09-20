package com.luv2code.springboot.thymeleafdemo.controller;

import com.luv2code.springboot.thymeleafdemo.entity.Employee;
import com.luv2code.springboot.thymeleafdemo.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
// Base mapping for url requests
@RequestMapping("/employees")
public class EmployeeController {

    private EmployeeService employeeService;

    // Constructor injection
    @Autowired
    public EmployeeController(EmployeeService theEmployeeService) {
        this.employeeService = theEmployeeService;
    }

    // add mapping for "/list"
    @GetMapping("/list")
    public String listEmployee(Model theModel) {

        // get the employee from db
        List<Employee> theEmployees = this.employeeService.findAll();

        // add to the spring model
        theModel.addAttribute("employees", theEmployees);

        return "employees/list-employees";
    }

    // show the html form (save employee)
    @GetMapping("/showFormForAdd")
    public String showFormForAdd(Model theModel) {

        // create model attribute to bind form data
        Employee theEmployee = new Employee();

        // thymeleaf template will access this for binding the data
        theModel.addAttribute("employee", theEmployee);

        // return the actual template we want to use for this form
        return "employees/employee-form";
    }

    @GetMapping("/showFormForUpdate")
    public String showFormForUpdate(@RequestParam("employeeId") int theId,
                                    Model theModel) {

        // get the employee from the service
        Employee theEmployee = this.employeeService.findById(theId);

        // set employee in the model to prepopulate the form
        theModel.addAttribute("employee", theEmployee);

        // send over to our form
        return "employees/employee-form";
    }

    @PostMapping("/save")
    public String saveEmployee(@ModelAttribute("employee") Employee theEmployee) {

        // save the employee
        this.employeeService.save(theEmployee);

        // use a redirect to prevent duplicate submissions ("Post/Redirect/Get") pattern
        return "redirect:/employees/list";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("employeeId") int theId) {

        // delete the employee
        this.employeeService.deleteById(theId);

        // redirect to the employees/list
        return "redirect:/employees/list";
    }
}
