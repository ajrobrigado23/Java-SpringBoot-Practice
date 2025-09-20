package com.luv2code.cruddemo.dao;

import com.luv2code.cruddemo.entity.Course;
import com.luv2code.cruddemo.entity.Instructor;
import com.luv2code.cruddemo.entity.InstructorDetail;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class AppDAOImpl implements AppDAO{

    // define field for entity manager

    private EntityManager entityManager;

    // inject entity manager using constructor injection
    public AppDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    // Use @Transactional since we're using persisting(save, update) the entity
    @Transactional
    public void save(Instructor theInstructor) {
        this.entityManager.persist(theInstructor);
    }

    @Override
    public Instructor findInstructorById(int theId) {
        // Return also the detail objects (Instructor details)
        // - because the default behavior of @OneToOne (fetch type) is eager
        return this.entityManager.find(Instructor.class, theId);
    }

    @Override
    // Do a modification to the database must use the @Transactional annotation
    @Transactional
    public void deleteInstructorById(int theId) {

        // retrieve the instructor
        Instructor tempInstructor = this.entityManager.find(Instructor.class, theId);

        // get the courses
        List<Course> courses = tempInstructor.getCourses();

        // break association of all courses for the instructor
        for (Course tempCourse: courses) {
            // If you don't remove instructor from courses you will have a constraint violation
            tempCourse.setInstructor(null);
        }

        // delete the instructor
        this.entityManager.remove(tempInstructor);
    }

    @Override
    public InstructorDetail findInstructorDetailById(int theId) {
        return this.entityManager.find(InstructorDetail.class, theId);
    }

    @Override
    @Transactional
    public void deleteInstructorDetailById(int theId) {

        // retrieve instructor detail
        InstructorDetail tempInstructorDetail = this.entityManager.find(InstructorDetail.class, theId);

        // remove the associated object reference
        // break bi-directional link
        //
        tempInstructorDetail.getInstructor().setInstructorDetail(null);

        // delete the instructor detail
        this.entityManager.remove(tempInstructorDetail);
    }

    // Find courses for instructor
    @Override
    public List<Course> findCoursesByInstructorId(int theId) {

        // create query
        TypedQuery<Course> query = this.entityManager.createQuery(
                "FROM Course WHERE instructor.id = :data", Course.class);

        query.setParameter("data", theId);

        // execute query
        List<Course> courses = query.getResultList();

        return courses;
    }

    // Retrieve the instructor and courses
    @Override
    public Instructor findInstructorByIdJoinFetch(int theId) {

        // create query
        // JOIN FETCH - retrieve both instrutor and courses
        TypedQuery<Instructor> query = this.entityManager.createQuery(
                         "SELECT i FROM Instructor i " +
                            "JOIN FETCH i.courses " +
                            "WHERE i.id = :data", Instructor.class);

        query.setParameter("data", theId);

        Instructor instructor = query.getSingleResult();

        return instructor;
    }

    @Override
    @Transactional
    // Update the instructor
    public void update(Instructor tempInstructor) {
        // update an existing entity
        this.entityManager.merge(tempInstructor);
    }

    // Find the course by id
    @Override
    public Course findCourseById(int theId) {
        return this.entityManager.find(Course.class, theId);
    }

    // Update the course
    @Override
    @Transactional
    public void update(Course tempCourse) {
        this.entityManager.merge(tempCourse);
    }

    @Override
    @Transactional
    public void deleteCourseById(int theId) {

        // retrieve the course
        Course tempCourse = this.entityManager.find(Course.class, theId);

        // delete the course
        this.entityManager.remove(tempCourse);
    }

    @Override
    @Transactional
    public void save(Course theCourse) {
        this.entityManager.persist(theCourse);
    }

    // NOTE: FIND THE COURSE AND REVIEWS (JOIN FETCH)
    @Override
    public Course findCourseAndReviewsByCourseId(int theId) {

        // create query
        TypedQuery<Course> query = this.entityManager.createQuery(
                     "SELECT c FROM Course c " +
                        "JOIN FETCH c.reviews " +
                        "WHERE c.id = :data", Course.class);

        query.setParameter("data", theId);

        // execute query
        Course course = query.getSingleResult();

        return course;
    }
}
