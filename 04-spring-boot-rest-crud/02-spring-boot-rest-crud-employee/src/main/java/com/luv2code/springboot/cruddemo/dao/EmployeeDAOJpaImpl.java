package com.luv2code.springboot.cruddemo.dao;

import com.luv2code.springboot.cruddemo.entity.Employee;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EmployeeDAOJpaImpl implements EmployeeDAO {

    // define field for entity manager
    private EntityManager entityManager;

    // set up constructor injection
    // entity manager - (automatically created by Spring Boot) Constructor Injection
    @Autowired
    public EmployeeDAOJpaImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Employee> findAll() {

        // create a query
        TypedQuery<Employee> theQuery = this.entityManager.createQuery("FROM Employee", Employee.class);

        // execute query and get result list
        List<Employee> employees = theQuery.getResultList();

        // return the results
        return employees;
    }

    @Override
    public Employee findById(int theId) {

        // get employee
        Employee theEmployee = this.entityManager.find(Employee.class, theId);

        // return employee
        return theEmployee;
    }

    @Override
    public Employee save(Employee theEmployee) {

        // save or update the employee
        // (if id == 0 then SAVE/ INSERT else UPDATE)
        Employee dbEmployee = this.entityManager.merge(theEmployee);

        // return dbEmployee (it has updated id from the database) in the case of insert.
        return dbEmployee;
    }

    @Override
    public void deleteById(int theId) {

        // find the employee by id
        Employee theEmployee = this.entityManager.find(Employee.class, theId);

        // delete the employee
        this.entityManager.remove(theEmployee);
    }
}
