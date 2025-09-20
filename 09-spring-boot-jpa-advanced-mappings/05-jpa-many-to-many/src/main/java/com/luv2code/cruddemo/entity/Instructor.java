package com.luv2code.cruddemo.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;


// annotate the class as an entity and map to db table
@Entity
@Table(name="instructor")
public class Instructor {

    // define fields

    // annotate the fields with db column names

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="first_name")
    private String firstName;

    @Column(name="last_name")
    private String lastName;

    @Column(name="email")
    private String email;

    // ** set up mapping to InstructorDetail entity
    // CascadeType.All - apply to any operations for persisting, deleting, or anything regarding this object
    // (update the associated object accordingly)
    @OneToOne(cascade = CascadeType.ALL)

    // Foreign key is configured to reference id field in 'instructor_detail table'
    @JoinColumn(name = "instructor_detail_id")
    private InstructorDetail instructorDetail;

    // "instructor" - relate to the field in the course class
    @OneToMany(mappedBy = "instructor",
                fetch = FetchType.LAZY,
                cascade = {CascadeType.PERSIST, CascadeType.MERGE,
                        CascadeType.DETACH, CascadeType.REFRESH})
    private List<Course> courses;

    // create constructors

    public Instructor() {

    }

    public Instructor(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    // generate getter/setter methods

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public InstructorDetail getInstructorDetail() {
        return instructorDetail;
    }

    public void setInstructorDetail(InstructorDetail instructorDetail) {
        this.instructorDetail = instructorDetail;
    }

    // getter and setter for our courses

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    // add convenience methods for bi-directional relationship
    public void add(Course tempCourse) {

        if (courses == null) {
            this.courses = new ArrayList<>();
        }

        // add this course to the list of courses
        courses.add(tempCourse);

        // setup the bi-directional relationship (here's your new instructor)
        tempCourse.setInstructor(this);
    }

    // generate toString() method

    @Override
    public String toString() {
        return "Instructor{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", instructorDetail=" + instructorDetail +
                '}';
    }
}
