package com.luv2code.cruddemo.dao;

import com.luv2code.cruddemo.entity.Student;

import java.util.List;

// DAO - DATA ACCESS OBJECT (COMMUNICATING TO THE DATABASE)
// helper/dependency to help us out with this functionality
public interface StudentDAO {

    void save(Student theStudent);

    // Retrieve Student ID
    Student findById(Integer id);

    // Query multiple objects
    List<Student> findAll();

    // Query students by last name
    List<Student> findByLastName(String lastName);

    // Update students
    void update(Student theStudent);

    // Delete students
    void delete(Integer id);

    // Deleting all the students
    int deleteAll();
}
