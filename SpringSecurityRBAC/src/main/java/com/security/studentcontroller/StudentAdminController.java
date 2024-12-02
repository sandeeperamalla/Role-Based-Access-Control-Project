package com.security.studentcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.security.studentservice.StudentDetailsService;

/**
 * The StudentAdminController handles HTTP requests specific to administrative
 * operations related to students. This controller provides EndPoints for
 * administrative functionality.
 * 
 * The base URL for all EndPoints in this controller is "/admin".
 */
@RestController
@RequestMapping("/admin")
public class StudentAdminController {

	@Autowired
	private StudentDetailsService studentDetailsService; // Service layer to handle student-related operations

	/**
	 * Test end point to verify the functionality of the Admin controller.
	 * 
	 * @return a string message indicating the controller is working correctly.
	 * 
	 *         Example: Request: GET /admin/testAdmin Response: "This is From admin
	 *         Controller admin"
	 */
	@GetMapping("/testAdmin")
	public String test() {
		return "This is From admin Contoller admin";
	}

	/**
	 * EndPoint to delete a student by their ID.
	 *
	 * @param id The ID of the student to be deleted.
	 * @return A confirmation message wrapped in a ResponseEntity with an OK (200)
	 *         status.
	 */
	@DeleteMapping("/DeleteStudentById/{id}")
	public ResponseEntity<String> deletStudentbyId(@PathVariable int id) {
		return ResponseEntity.status(HttpStatus.OK).body(studentDetailsService.DeleteStudentById(id));
	}

}
