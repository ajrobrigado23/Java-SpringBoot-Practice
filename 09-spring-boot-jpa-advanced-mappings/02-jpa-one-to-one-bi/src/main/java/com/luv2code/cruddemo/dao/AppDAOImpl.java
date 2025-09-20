package com.luv2code.cruddemo.dao;

import com.luv2code.cruddemo.entity.Instructor;
import com.luv2code.cruddemo.entity.InstructorDetail;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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

}
