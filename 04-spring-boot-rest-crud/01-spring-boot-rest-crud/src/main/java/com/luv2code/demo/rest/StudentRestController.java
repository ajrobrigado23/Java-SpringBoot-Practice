package com.luv2code.demo.rest;

import com.luv2code.demo.entity.Student;
import jakarta.annotation.PostConstruct;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class StudentRestController {

    // refactor the code
    private List<Student> theStudents;
    // define @PostConstructor to load the student data ... only once!

    // we only load the data once
    // PostConstructor - executed after all injection is done, and it populates the data  at the time.
    // * PostConstructor job is to be executed AFTER all beans are created
    @PostConstruct
    public void loadData() {

        this.theStudents = new ArrayList<>();

        this.theStudents.add(new Student("Poornima", "Patel"));
        this.theStudents.add(new Student("Mario", "Rossi"));
        this.theStudents.add(new Student("Mary", "Smith"));
    }

    // define endpoints for "/students" - return a list of students
    @GetMapping("/students")
    public List<Student> getStudent() {
        return theStudents;
    }

    // define endpoint or "/students/{studentId}" - return student at index (single student)
    @GetMapping("/students/{studentId}")
    public Student getStudent(@PathVariable int studentId) {

        // Step 3 - Update REST service to throw exception
        // check the studentId again list size
        if (this.theStudents.size() <= studentId || (studentId < 0)) {
            throw new StudentNotFoundException("Student id not found - " + studentId);
        }

        // just index into the list ... keep it simple for now
        return this.theStudents.get(studentId);
    }
}
