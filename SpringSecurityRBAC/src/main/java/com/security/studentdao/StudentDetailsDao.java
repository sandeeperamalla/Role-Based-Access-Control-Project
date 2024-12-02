package com.security.studentdao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.security.exception.InvalidStudentDetailsException;
import com.security.exception.StudentDetailsCreationException;
import com.security.exception.StudentDetailsNotFoundWithId;
import com.security.studentdto.StudentDetails;
import com.security.studentrepo.StudentDetailsRepo;

/**
 * The StudentDetailsDao class is responsible for interacting with the database
 * for CRUD (Create, Read, Update, Delete) operations on student details. It
 * uses the StudentDetailsRepo to perform the actual data access.
 */
@Repository
public class StudentDetailsDao {

	@Autowired
	StudentDetailsRepo detailsRepo;

	/**
	 * Saves student details to the database. If the details are null, an exception
	 * is thrown. If any error occurs during the save operation, it handles data
	 * access exceptions and unexpected errors accordingly.
	 * 
	 * @param details the student details to be saved.
	 * @return the saved student details with any updates (e.g., generated ID).
	 * @throws InvalidStudentDetailsException  if the provided student details are
	 *                                         null.
	 * @throws StudentDetailsCreationException if an error occurs during saving the
	 *                                         details.
	 */
	public StudentDetails saveStudentDetailsDao(StudentDetails details) {
		// Check for null details
		if (details == null) {
			throw new InvalidStudentDetailsException("Student Details Should Not be Null");
		}

		try {
			// Save the student details to the database
			return detailsRepo.save(details);
		} catch (DataAccessException dae) {
			// Handle data access issues (e.g., database errors)
			System.err.println("Data access error while creating Saving Student Details: " + dae.getMessage());
			throw new StudentDetailsCreationException("Failed to Saving Student Details due to a data access error");
		} catch (Exception e) {
			// Handle unexpected errors
			System.err.println("Unexpected error while Saving Student Details: " + e.getMessage());
			throw new StudentDetailsCreationException("Failed to Saving Student Details due to an unexpected error");
		}
	}

	/**
	 * Retrieves student details by their unique student number (ID). If the student
	 * is not found, a custom exception is thrown.
	 * 
	 * @param stuNumber the unique student ID whose details are to be retrieved.
	 * @return the student details.
	 * @throws StudentDetailsNotFoundWithId if the student with the given ID is not
	 *                                      found.
	 */
	public StudentDetails getStudentDetailsByIdDao(int stuNumber) {
		// Attempt to find student details by ID
		Optional<StudentDetails> getStudentDetailsbyId = detailsRepo.findById(stuNumber);

		// If not found, throw an exception
		if (getStudentDetailsbyId.isEmpty()) {
			throw new StudentDetailsNotFoundWithId("Student Details With Id:" + stuNumber + " is Not Found");
		}

		return getStudentDetailsbyId.get();
	}

	/**
	 * Retrieves all student details from the database.
	 * 
	 * @return a list of all student details.
	 */
	public List<StudentDetails> getAllStudentDetails() {
		return detailsRepo.findAll();
	}

	/**
	 * Updates the details of an existing student in the database. If the student
	 * with the provided ID is not found, a custom exception is thrown.
	 * 
	 * @param details the updated student details.
	 * @return the updated student details.
	 * @throws StudentDetailsNotFoundWithId if the student with the given ID is not
	 *                                      found.
	 */
	public StudentDetails updateStudentDetailsDao(StudentDetails details) {
		// Check if student exists by ID
		Optional<StudentDetails> getStudentDetailsById = detailsRepo.findById(details.getStuNumber());

		// If not found, throw an exception
		if (getStudentDetailsById.isEmpty()) {
			throw new StudentDetailsNotFoundWithId(
					"Student Details With Id: " + details.getStuNumber() + " is Not Found");
		}

		// Update the student details
		StudentDetails existingStudentDetails = getStudentDetailsById.get();
		existingStudentDetails.setFatherName(details.getFatherName());
		existingStudentDetails.setFullName(details.getFullName());
		existingStudentDetails.setBranchName(details.getBranchName());

		// Save and return the updated details
		return detailsRepo.save(existingStudentDetails);
	}

	/**
	 * Deletes student details by their unique ID. If the student is not found, a
	 * custom exception is thrown. Once deleted, a success message is returned.
	 * 
	 * @param id the unique student ID of the student to be deleted.
	 * @return a message indicating whether the student details were successfully
	 *         deleted.
	 * @throws StudentDetailsNotFoundWithId if the student with the given ID is not
	 *                                      found.
	 */
	public String DeleteStudentDetailById(int id) {
		// Check if the student exists
		if (!detailsRepo.existsById(id)) {
			throw new StudentDetailsNotFoundWithId("Student Details with Id: " + id + " is Not Found");
		}

		// Delete the student by ID
		detailsRepo.deleteById(id);

		// Return success message
		return "Student Details with Id: " + id + " is Deleted";
	}
}
