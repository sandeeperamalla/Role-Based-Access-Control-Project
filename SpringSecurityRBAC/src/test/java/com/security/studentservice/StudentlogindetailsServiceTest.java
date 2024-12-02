package com.security.studentservice;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.security.studentdao.StudentlogindetailsDao;
import com.security.studentdto.Role;
import com.security.studentdto.StudentLoginDetails;

@ExtendWith(MockitoExtension.class) // Enables Mockito extension for JUnit 5 to support mocking
public class StudentlogindetailsServiceTest {

    // Mocking the DAO to simulate the database interaction
    @Mock
    StudentlogindetailsDao dao;

    // Injecting the mock DAO into the service class
    @InjectMocks
    StudentlogindetailsService service;

    // Creating a BCryptPasswordEncoder to handle password encoding
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(12);

    // Test method for saving student login details securely
    @Test
    void saveStudentloginDetailsServiceTestMethod() {
        
        // Sample password for the test
        String password = "Michael";
        
        // Creating a StudentLoginDetails object to simulate a student's login information
        StudentLoginDetails details = new StudentLoginDetails();
        details.setUid(1); // Setting the unique identifier (UID) for the student
        details.setUserName("John Michael Smith"); // Setting the userName of the student
        details.setPassword(bCryptPasswordEncoder.encode(password)); // Encoding the password
        details.setRole(Role.USER); // Assigning the user role

        // Mocking the DAO method to return the student details with the encoded password
        // when saveLoginDetailsRepo() is called
        Mockito.when(dao.saveLoginDetailsRepo(details)).thenReturn(details);

        // Calling the service method to save the student login details
        StudentLoginDetails testResult = service.saveStudentloginDetailsService(details);
        
        // Asserting that the result is not null (i.e., the student details were saved)
        Assertions.assertNotNull(testResult, "The result should not be null");
        
     // Asserting that the UID (unique identifier) of the saved student matches the expected value of 1
        Assertions.assertEquals(1, testResult.getUid(), "The uId should match");


        // Asserting that the userName returned from the service matches the expected userName
        Assertions.assertEquals("John Michael Smith", testResult.getUserName(), "The username should match");

        // Asserting that the role of the student returned from the service is the expected role (USER)
        Assertions.assertEquals(Role.USER, testResult.getRole(), "The role should match");

        // Asserting that the password has been encoded (i.e., it should not match the plain password)
        Assertions.assertNotEquals(password, testResult.getPassword(), "The password should be encoded");

        // Verifying that the DAO method was called exactly once with any StudentLoginDetails object
        verify(dao, times(1)).saveLoginDetailsRepo(any(StudentLoginDetails.class));
    }
}

