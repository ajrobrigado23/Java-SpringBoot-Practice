package com.luv2code.springboot.cruddemo.dao;

import com.luv2code.springboot.cruddemo.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

// Our Repository (Entity type, Primary Key)
@RepositoryRestResource(path="members") // http://localhost:8080/magic-api/members
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

     ///  that's it ... no need to write any code LOL!

}
