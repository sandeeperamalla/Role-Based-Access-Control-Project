package com.security.studentcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.security.studentdto.StudentDetails;
import com.security.studentservice.StudentDetailsService;

/**
 * The StudentControllerModerator handles HTTP requests specific to
 * moderator-level operations related to students. This controller provides
 * EndPoint intended for moderators.
 * 
 * The base URL for all EndPoint in this controller is "/moderator".
 */
@RestController
@RequestMapping("/moderator")
public class StudentControllerModerator {

	@Autowired
	private StudentDetailsService studentDetailsService; // Service layer to handle student-related operations

	/**
	 * Test EndPoint to verify the functionality of the moderator controller.
	 * 
	 * @return a string message indicating the controller is functioning properly.
	 * 
	 *         Example: Request: GET /moderator/testModerator Response: "this is
	 *         from Moderator"
	 */
	@GetMapping("/testModerator")
	public String testModerator() {
		return "this is from Moderator";
	}

	/**
	 * EndPoint to update an existing student's details.
	 *
	 * @param details The updated student details provided in the request body.
	 * @return The updated student details wrapped in a ResponseEntity with an OK
	 *         (200) status.
	 */
	@PutMapping("/updateStudentDetalis")
	public ResponseEntity<StudentDetails> updateStudentDetailsController(@RequestBody StudentDetails details) {
		return ResponseEntity.status(HttpStatus.OK).body(studentDetailsService.UpdateStudentDetailsService(details));
	}

	/**
	 * EndPoint to retrieve all student details.
	 *
	 * @return A list of all student details wrapped in a ResponseEntity with an OK
	 *         (200) status.
	 */
	@GetMapping("/getAllStudent")
	public ResponseEntity<List<StudentDetails>> getAllStudentDetailsController() {
		return ResponseEntity.status(HttpStatus.OK).body(studentDetailsService.getAllStudentsService());
	}

}
