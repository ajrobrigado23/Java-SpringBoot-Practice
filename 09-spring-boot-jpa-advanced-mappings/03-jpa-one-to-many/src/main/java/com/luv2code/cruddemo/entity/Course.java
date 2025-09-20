package com.luv2code.cruddemo.entity;

import jakarta.persistence.*;

@Entity
@Table(name="course")
public class Course {

    // annotate fields
    // define our fields
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="title")
    private String title;

    // setup the relationship (many to one)

    // (if we delete a course, do not delete the associated instructor)
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
                          CascadeType.DETACH, CascadeType.REFRESH},
                          fetch = FetchType.LAZY)
    // "instructor_id" is the foreign key that makes the relationship
    @JoinColumn(name = "instructor_id")
    private Instructor instructor;

    // define our constructors
    public Course() {

    }

    public Course(String title) {
        this.title = title;
    }

    // define getter setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Instructor getInstructor() {
        return instructor;
    }

    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }

    // define toString

    @Override
    public String toString() {
        return "Course{" +
                "title='" + title + '\'' +
                ", id=" + id +
                '}';
    }
}
