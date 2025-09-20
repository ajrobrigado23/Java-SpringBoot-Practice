package com.luv2code.springboot.cruddemo.service;

import com.luv2code.springboot.cruddemo.dao.EmployeeRepository;
import com.luv2code.springboot.cruddemo.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeRepository employeeRepository;

    // Inject the EmployeeDAO (Constructor Injection)
    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    // Delegating the calls to DAO
    @Override
    public List<Employee> findAll() {
        return this.employeeRepository.findAll();
    }

    @Override
    public Employee findById(int theId) {

        // JPA repository make use of 'Optional' (Pattern of coding introduced in JAVA 8)
        // Basic approach here for getting the data without having to explicitly check for null

        // Optional is different pattern instead of having check for nulls, instead we can see if a given value is present.
        Optional<Employee> result = this.employeeRepository.findById(theId);

        Employee theEmployee = null;

        // if the result is present (not null) then return true (retrieve the given value).
        if (result.isPresent()) {
            theEmployee = result.get();
        } else {
            // we didn't find the employee
            throw new RuntimeException("Didn't find employee id - " + theId);
        }

        return theEmployee;
    }

    // @Transactional has moved here because we delegate it into our service layer
    // Remove @Transactional since JPARepository provides this functionality
    @Override
    public Employee save(Employee theEmployee) {
        return this.employeeRepository.save(theEmployee);
    }

    // Remove @Transactional since JPARepository provides this functionality
    @Override
    public void deleteById(int theId) {
        this.employeeRepository.deleteById(theId);
    }
}
