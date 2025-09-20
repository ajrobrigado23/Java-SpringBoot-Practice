package com.luv2code.cruddemo;

import com.luv2code.cruddemo.dao.AppDAO;
import com.luv2code.cruddemo.entity.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class CruddemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(CruddemoApplication.class, args);
	}

	// Inject the AppDAO

	@Bean
	public CommandLineRunner commandLineRunner(AppDAO appDAO) {

		return runner -> {

			// Create course and students
			//createCourseAndStudents(appDAO);

			// findCourseAndStudents(appDAO);

			// findStudentAndCourses(appDAO);

			// addMoreCoursesForStudent(appDAO);

			// deleteCourse(appDAO);

			// deleteStudent(appDAO);

		};
	}

	private void createInstructor(AppDAO appDAO) {

		// Create the instructor
		Instructor tempInstructor =
				new Instructor("Madhu", "Patel", "madhu@luv2code.com");

		// Create the instructor detail
		InstructorDetail tempInstructorDetail =
				new InstructorDetail("http://www.luv2code.com/youtube",
						"Guitar");

		// associate the objects
		tempInstructor.setInstructorDetail(tempInstructorDetail);

		// save the instructor
		//
 		// NOTE: this will ALSO save the details object (Instructor Details)
 		// because of CascadeType.ALL
 		//
		System.out.println("Saving instructor: " + tempInstructor);

		// delegate to the DAO
		appDAO.save(tempInstructor);

		System.out.println("Done!");
	}

	private void findInstructor(AppDAO appDAO) {

		int theId = 1;
		System.out.println("Finding instructor id: " + theId);

		// delegate the call to DAO
		Instructor tempInstructor = appDAO.findInstructorById(theId);

		System.out.println("tempInstructor: " + tempInstructor);
		System.out.println("the associate instructorDetail only: " + tempInstructor.getInstructorDetail());
	}

	private void deleteInstructor(AppDAO appDAO){

		int theId = 1;
		System.out.println("Deleting instructor id: " + theId);

		appDAO.deleteInstructorById(theId);

		System.out.println("Done!");
	}

	// bi-directional One to One (Start to find the InstructorDetail and we'll get the associated Instructor)

	private void findInstructorDetail(AppDAO appDAO) {

		// get the instructor detail object
		int theId = 2;

		InstructorDetail tempInstructorDetail = appDAO.findInstructorDetailById(theId);

		// print the instructor detail
		System.out.println("tempInstructorDetail: " + tempInstructorDetail);

		// print the associated instructor
		System.out.println("the associated instructor: " + tempInstructorDetail.getInstructor());

		System.out.println("Done!");
	}

	private void deleteInstructorDetail(AppDAO appDAO) {

		int theId = 2;
		System.out.println("Deleting instructor detail id: " + theId);

		appDAO.deleteInstructorDetailById(theId);

		System.out.println("Done!");
	}

	private void createInstructorWithCourses(AppDAO appDAO) {

		// Create the instructor
		Instructor tempInstructor =
				new Instructor("Susan", "Public", "susan.public@luv2code.com");

		// Create the instructor detail
		InstructorDetail tempInstructorDetail =
				new InstructorDetail("http://www.youtube.com",
						"Video Games");

		// associate the objects
		tempInstructor.setInstructorDetail(tempInstructorDetail);

		// create some courses
		Course tempCourse1 = new Course("Air Guitar - The Ultimate Guide");
		Course tempCourse2 = new Course("The Pinball Masterclass");

		// add courses to instructor
		tempInstructor.add(tempCourse1);
		tempInstructor.add(tempCourse2);

		// save the instructor
		//
 		// NOTE: this will ALSO save the courses
		// because of CascadeType.PERSIST
		//
		System.out.println("Saving the instructor: " + tempInstructor);
		System.out.println("The courses: " + tempInstructor.getCourses());
		appDAO.save(tempInstructor);

		System.out.println("Done!");

	}

	// Use the Fetch type = EAGER (find all related courses)
	public void findInstructorWithCourses(AppDAO appDAO) {

		int theId = 1;
		System.out.println("Finding instructor id: " + theId);

		Instructor tempInstructor = appDAO.findInstructorById(theId);

		System.out.println("tempInstructor: " + tempInstructor);
		System.out.println("the associated courses: " + tempInstructor.getCourses());

		System.out.println("Done!");
	}

	// Find courses for instructor (Using the FETCH TYPE = LAZY)
	private void findCoursesForInstructor(AppDAO appDAO) {

		int theId = 1;

		// find instructor
		System.out.println("Finding instructor id: " + theId);

		Instructor tempInstructor = appDAO.findInstructorById(theId);

		// find courses for instructor
		System.out.println("Finding courses for instructor id: " + theId);
		List<Course> courses = appDAO.findCoursesByInstructorId(theId);

		// associate the objects
		tempInstructor.setCourses(courses);
		System.out.println("the associated courses: " + tempInstructor.getCourses());

		System.out.println("Done!");
	}

	// Retrieve both courses and instructor
	private void findInstructorWithCoursesJoinFetch(AppDAO appDAO) {

		int theId = 1;

		// find instructor
		System.out.println("Finding instructor id: " + theId);

		Instructor tempInstructor = appDAO.findInstructorByIdJoinFetch(theId);

		System.out.println("tempInstructor: " + tempInstructor);
		System.out.println("the associated courses: " + tempInstructor.getCourses());

		System.out.println("Done!");
	}

	// Update an instructor
	private void updateInstructor(AppDAO appDAO) {

		int theId = 1;

		// find the instructor
		System.out.println("Finding instructor id: " + theId);
		Instructor tempInstructor = appDAO.findInstructorById(theId);

		// update the instructor
		System.out.println("Update instructor id: " + theId);
		tempInstructor.setLastName("TESTER");

		appDAO.update(tempInstructor);

		System.out.println("Done!");
	}

	// Update the course
	private void updateCourse(AppDAO appDAO) {

		int theId = 10;

		// find the instructor
		System.out.println("Finding course id: " + theId);
		Course tempCourse = appDAO.findCourseById(theId);

		// update the instructor
		System.out.println("Update course id: " + theId);
		tempCourse.setTitle("Enjoy the Simple Things");

		appDAO.update(tempCourse);

		System.out.println("Done!");
	}

	private void deleteCourse(AppDAO appDAO) {

		int theId = 10;

		System.out.println("Deleting course id: " + theId);

		appDAO.deleteCourseById(theId);

		System.out.println("Done!");

	}

	// Save the course with a reviews (uni-directional)
	private void createCourseAndReviews(AppDAO appDAO) {

		// create a course
		Course tempCourse = new Course("Pacman - How To Score One Million Points");

		// add some reviews
		tempCourse.addReview(new Review("Great course ... loved it"));
		tempCourse.addReview(new Review("Cool course, job well done."));
		tempCourse.addReview(new Review("What a dumb review"));

		// save the course ... and leverage the cascade all
		System.out.println("Saving the course");
		System.out.println(tempCourse);
		System.out.println(tempCourse.getReviews());

		appDAO.save(tempCourse);

		System.out.println("Done!");
	}

	// Retrieve or get both courses and reviews
	private void retrieveCourseAndReviews(AppDAO appDAO) {

		// get the course and reviews
		int theId = 10;
		Course tempCourse = appDAO.findCourseAndReviewsByCourseId(theId);

		// print the course
		System.out.println(tempCourse);

		// print the reviews
		System.out.println(tempCourse.getReviews());

		System.out.println("Done!");

	}

	// Delete the courses and reviews
	private void deleteCourseAndReviews(AppDAO appDAO) {

		int theId = 10;

		System.out.println("Deleting course id: " + theId);

		// NOTE: Delete course and associated reviews, thanks to (CascadeType.ALL)
		appDAO.deleteCourseById(theId);

		System.out.println("Done!");
	}

	// Create both course and students
	private void createCourseAndStudents(AppDAO appDAO) {

		// create a course
		Course tempCourse = new Course("Pacman - How To Score One Million Points");

		// create the students
		Student tempStudent1 = new Student("John", "Doe", "john@luv2code.com");
		Student tempStudent2 = new Student("Mary", "Public", "mary@luv2code.com");

		// add students to the course
		tempCourse.addStudent(tempStudent1);
		tempCourse.addStudent(tempStudent2);

		// save the course and associated students
		System.out.println("Saving the course: " + tempCourse);
		System.out.println("associated students " + tempCourse.getStudents());

		// save operation or that persist operation will cascade to the actual students
		// (it will also save the associated students)
		appDAO.save(tempCourse);

		System.out.println("Done!");

	}

	// Find both course and students
	private void findCourseAndStudents(AppDAO appDAO) {

		int theId = 10;

		Course tempCourse = appDAO.findCourseAndStudentsByCourseId(theId);

		System.out.println("Loaded course: " + tempCourse);
		System.out.println("Students: " + tempCourse.getStudents());

		System.out.println("Done!");
	}

	// Find both using the studentId
	private void findStudentAndCourses(AppDAO appDAO) {

		int theId = 2;

		Student tempStudent = appDAO.findStudentAndCoursesByStudentId(theId);

		System.out.println("Loaded student: " + tempStudent);
		System.out.println("Courses: " + tempStudent.getCourses());

		System.out.println("Done!");
	}

	// Add more courses for our students
	private void addMoreCoursesForStudent(AppDAO appDAO) {

		int theId = 2;

		Student tempStudent = appDAO.findStudentAndCoursesByStudentId(theId);

		// create more courses for our student
		Course tempCourse1 = new Course("Rubik's Cube - How to Speed Cube");
		Course tempCourse2 = new Course("Atari 2600 - Game Development");

		// add courses to our students
		tempStudent.addCourse(tempCourse1);
		tempStudent.addCourse(tempCourse2);

		System.out.println("Updating student: " + tempStudent);
		System.out.println("associated courses: " + tempStudent.getCourses());

		appDAO.update(tempStudent);

		System.out.println("Done!");
	}

	// Delete the student form the course
	private void deleteStudent(AppDAO appDAO) {

		int theId = 1;
		System.out.println("Delete student id: " + theId);

		appDAO.deleteStudentById(theId);

		System.out.println("Done!");
	}
}