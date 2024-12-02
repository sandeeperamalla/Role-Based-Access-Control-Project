package com.security.studentrepo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.security.studentdto.StudentLoginDetails;

/**
 * Repository interface for accessing student login details in the database.
 * Extends JpaRepository to provide CRUD operations and additional custom queries.
 * 
 * This interface allows interaction with the StudentLoginDetails entity,
 * enabling the retrieval of student login information by userName.
 */
public interface StudentloginDetailsRepo extends JpaRepository<StudentLoginDetails, Integer> {

    /**
     * Finds a student's login details by their userName.
     * 
     * @param userName The userName of the student to be retrieved.
     * @return The StudentLoginDetails associated with the given userName.
     *         If no student is found with the provided userName, returns null.
     */
    StudentLoginDetails findByUserName(String userName);
}

