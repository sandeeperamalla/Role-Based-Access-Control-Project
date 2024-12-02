package com.security.studentcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.security.studentdto.StudentDetails;
import com.security.studentservice.StudentDetailsService;



@RestController
@RequestMapping("/user") // Base URL for all EndPoint in this controller
public class StudentController {
	
	

    @Autowired
    private StudentDetailsService studentDetailsService; // Service layer to handle student-related operations

    /**
     * EndPoint to save student details.
     *
     * @param details The student details provided in the request body.
     * @return The saved student details wrapped in a ResponseEntity with a CREATED (201) status.
     */
    @PostMapping("/saveStudent")
    public ResponseEntity<StudentDetails> saveStudentController(@RequestBody StudentDetails details) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(studentDetailsService.saveStudentDetailsService(details));
    }

    /**
     * EndPoint to retrieve a student's details by their ID.
     *
     * @param id The ID of the student.
     * @return The student details wrapped in a ResponseEntity with an OK (200) status.
     */
    @GetMapping("/getStudentById/{id}")
    public ResponseEntity<StudentDetails> getStudentDetailsController(@PathVariable int id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(studentDetailsService.getStudentDetailsService(id));
    }

    

    

    
}

