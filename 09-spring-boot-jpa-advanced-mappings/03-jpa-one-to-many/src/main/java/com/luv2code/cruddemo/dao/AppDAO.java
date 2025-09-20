package com.luv2code.cruddemo.dao;

import com.luv2code.cruddemo.entity.Course;
import com.luv2code.cruddemo.entity.Instructor;
import com.luv2code.cruddemo.entity.InstructorDetail;

import java.util.List;

public interface AppDAO {

    void save(Instructor theInstructor);

    Instructor findInstructorById(int theId);

    void deleteInstructorById(int theId);

    InstructorDetail findInstructorDetailById(int theId);

    void deleteInstructorDetailById(int theId);

    // Add new method to find courses for instructor
    List<Course> findCoursesByInstructorId(int theId);

    // Retrieve the instructor and courses (using JOIN)
    Instructor findInstructorByIdJoinFetch(int theId);

    // Update the instructor
    void update(Instructor tempInstructor);

    // Update the course
     void update(Course tempCourse);

     // find the course by id
    Course findCourseById(int theId);

    // delete the course by id
    void deleteCourseById(int theId);
}
