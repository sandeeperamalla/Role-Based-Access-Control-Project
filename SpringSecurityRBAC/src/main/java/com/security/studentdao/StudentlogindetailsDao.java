package com.security.studentdao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.security.exception.InvalidStudentLoginDetailsException;
import com.security.exception.StudentLoginDetailsCreationException;
import com.security.studentdto.StudentLoginDetails;
import com.security.studentrepo.StudentloginDetailsRepo;

@Repository
public class StudentlogindetailsDao {

    @Autowired
    StudentloginDetailsRepo repo;

    /**
     * Saves the student login details to the database.
     * 
     * @param details The login details of the student to be saved.
     * @return The saved StudentLoginDetails object after persistence.
     * @throws InvalidStudentLoginDetailsException If the provided login details are null.
     * @throws StudentLoginDetailsCreationException If there is an error during the saving process.
     */
    public StudentLoginDetails saveLoginDetailsRepo(StudentLoginDetails details) {
        // Check if details are null and throw a custom exception
        if (details == null) {
            throw new InvalidStudentLoginDetailsException("Student Login Details Should Not be Null");
        }

        try {
            // Save the student login details to the database and return the saved entity
            return repo.save(details);
        } catch (DataAccessException dae) {
            // Catch any database access issues and throw a custom exception with a message
            System.err.println("Data access error while creating Saving Student Details: " + dae.getMessage());
            throw new StudentLoginDetailsCreationException(
                    "Failed to Saving Student Details due to a data access error");
        } catch (Exception e) {
            // Catch any unexpected exceptions and throw a custom exception
            System.err.println("Unexpected error while Saving Student Details: " + e.getMessage());
            throw new StudentLoginDetailsCreationException(
                    "Failed to Saving Student Details due to an unexpected error");
        }
    }
}

