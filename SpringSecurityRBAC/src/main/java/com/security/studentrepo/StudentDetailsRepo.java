package com.security.studentrepo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.security.studentdto.StudentDetails;

/**
 * Repository interface for performing CRUD operations on the StudentDetails entity.
 * Extends JpaRepository to provide built-in methods like save(), findById(), findAll(), deleteById().
 * 
 */
public interface StudentDetailsRepo extends JpaRepository<StudentDetails, Integer> {
    
}

