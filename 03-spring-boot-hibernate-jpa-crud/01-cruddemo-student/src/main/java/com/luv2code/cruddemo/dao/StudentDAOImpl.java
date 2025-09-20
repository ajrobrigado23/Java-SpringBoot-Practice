package com.luv2code.cruddemo.dao;

import com.luv2code.cruddemo.entity.Student;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

//
// STUDENTDAO IMPLEMENTATION
//

// Support component scanning
// Translate JDBC exceptions (checked - unchecked)
@Repository
public class StudentDAOImpl implements StudentDAO {

    // define field for entity manager
    private EntityManager entityManager;

    // inject entity manager using constructor injection
    @Autowired
    public StudentDAOImpl(EntityManager entityManager) {
        // JPA Entity Manager - is the main component for saving/ retrieving entities
        this.entityManager = entityManager;
    }

    // implement save method
    @Override
    // since we're performing an update on the database, we're actually saving or storing an object in the database
    // handle the transactional management accordingly
    @Transactional // from the spring framework
    public void save(Student theStudent) {
        // saves the student to the database
        this.entityManager.persist(theStudent);
    }

    // implement the read/ retrieve method
    // No @Transactional annotation because we're just retrieving/ reading data
    @Override
    public Student findById(Integer id) {
        // find(entity class, Primary key)
        return this.entityManager.find(Student.class, id);
    }

    @Override
    public List<Student> findAll() {

        // create query
        // All JPQL syntax is based on entity nae and entity fields (not on database)
        TypedQuery<Student> theQuery = this.entityManager.createQuery("FROM Student order by lastName asc", Student.class);

        // return query results
        return theQuery.getResultList();
    }

    // Query by last name
    @Override
    public List<Student> findByLastName(String theLastName) {

        // create query
        // JPQL Named Parameter are prefixed with a colon -> :theData
        // Placeholder that is filled in later
        TypedQuery<Student> theQuery = this.entityManager.createQuery(
                "FROM Student WHERE lastName=:theData", Student.class);

        // set query parameters
        // theLastName (parameter) -> can pass any last name you want
        theQuery.setParameter("theData", theLastName);

        // return query results
        return theQuery.getResultList();
    }

    // Update the students
    @Override
    @Transactional // Performing an update to the student database
    public void update(Student theStudent) {
        this.entityManager.merge(theStudent);
    }

    // Delete the student
    @Override
    @Transactional
    public void delete(Integer id) {

        // retrieve the student
        Student theStudent = this.entityManager.find(Student.class, id);

        // delete the student
        this.entityManager.remove(theStudent);
    }

    @Override
    @Transactional
    public int deleteAll() {
        int numRowsDeleted = this.entityManager.createQuery("DELETE FROM Student").executeUpdate();

        return numRowsDeleted;
    }
}
