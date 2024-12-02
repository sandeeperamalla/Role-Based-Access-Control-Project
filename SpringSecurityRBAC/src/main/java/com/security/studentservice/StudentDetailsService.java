package com.security.studentservice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.security.studentdao.StudentDetailsDao;
import com.security.studentdto.StudentDetails;

/**
 * This service handles the business logic related to student details. It
 * interacts with the database through the StudentDetailsDao to perform CRUD
 * (Create, Read, Update, Delete) operations on student data.
 */
@Service
public class StudentDetailsService {

	@Autowired
	StudentDetailsDao dao;

	/**
	 * Saves a new student's details into the database.
	 * 
	 * @param details the student details to be saved.
	 * @return the saved student details with any updates applied (e.g., generated
	 *         ID).
	 */
	public StudentDetails saveStudentDetailsService(StudentDetails details) {
		return dao.saveStudentDetailsDao(details);
	}

	/**
	 * Retrieves the details of a student by their ID.
	 * 
	 * @param id the unique ID of the student whose details are to be retrieved.
	 * @return the student details, or null if the student is not found.
	 */
	public StudentDetails getStudentDetailsService(int id) {
		return dao.getStudentDetailsByIdDao(id);
	}

	/**
	 * Retrieves the details of all students from the database.
	 * 
	 * @return a list of all student details.
	 */
	public List<StudentDetails> getAllStudentsService() {
		return dao.getAllStudentDetails();
	}

	/**
	 * Deletes a student's details from the database by their ID.
	 * 
	 * @param id the ID of the student to be deleted.
	 * @return a message indicating whether the deletion was successful or not.
	 */
	public String DeleteStudentById(int id) {
		return dao.DeleteStudentDetailById(id);
	}

	/**
	 * Updates the details of an existing student in the database.
	 * 
	 * @param details the student details to be updated.
	 * @return the updated student details.
	 */
	public StudentDetails UpdateStudentDetailsService(StudentDetails details) {
		return dao.updateStudentDetailsDao(details);
	}

}
