package com.luv2code.springboot.cruddemo.service;

import com.luv2code.springboot.cruddemo.entity.Employee;

import java.util.List;

public interface EmployeeService {

    List<Employee> findAll();

    // Get a single employee
    Employee findById(int theId);

    // Add or Update employee
    Employee save(Employee theEmployee);

    // Delete an existing employee
    void deleteById(int theId);
}
