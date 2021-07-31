package com.iktpreobuka.el_ucionica_AS.controllers;

import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.iktpreobuka.el_ucionica_AS.controllers.RequestDTOs.ChangeSubjectDTO;
import com.iktpreobuka.el_ucionica_AS.controllers.RequestDTOs.NewSubjectDTO;
import com.iktpreobuka.el_ucionica_AS.services.SecurityServices;
import com.iktpreobuka.el_ucionica_AS.services.SubjectServices;

@RestController
public class SubjectController {

	@Autowired
	private SubjectServices subServ;
	@Autowired
	private SecurityServices otherServ;
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Secured("ROLE_ADMIN")
	@GetMapping("/subjects")
	public ResponseEntity<?> getAllSubjects() {
		return subServ.getAllSubs();
	}
	
	@Secured("ROLE_ADMIN")
	@GetMapping("/subjects/year/{yearNo}")
	public ResponseEntity<?> getSubjectsByYear(@PathVariable Integer yearNo) {
		return subServ.getSubByYear(yearNo);
	}
	
	@Secured("ROLE_ADMIN")
	@GetMapping("/subjects/name/{name}")
	public ResponseEntity<?> getSubjecsByName(@PathVariable String name) {
		return subServ.getSubByName(name);
	}
	
	@Secured("ROLE_ADMIN")
	@GetMapping("/subjects/{id}")
	public ResponseEntity<?> getSubjectById(@PathVariable Integer id) {
		return subServ.getSubById(id);
	}
	
	@Secured("ROLE_ADMIN")
	@PostMapping("/subjects")
	public ResponseEntity<?> makeNewSubject(@Valid @RequestBody NewSubjectDTO newSub, BindingResult result){
		if (result.hasErrors()) {
			logger.info("Attempted to make a subject, couldn't validate information");
			return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);
		}
		return subServ.makeNewSubject(newSub);
	}
	
	@Secured("ROLE_ADMIN")
	@PutMapping("/subjects/{id}")
	public ResponseEntity<?> alterSubject(@PathVariable Integer id, @Valid @RequestBody ChangeSubjectDTO sub, BindingResult result){
		if (result.hasErrors()) {
			logger.info("Attempted to alter a subject, couldn't validate information");
			return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);
		}
		return subServ.alterSubject(id, sub);
	}
	
	@Secured("ROLE_ADMIN")
	@DeleteMapping("/subjects/{id}")
	public ResponseEntity<?> deleteSubject(@PathVariable Integer id) {
		return subServ.deleteSubjcet(id);
	}
	
	@Secured("ROLE_ADMIN")
	@PostMapping("/subjects/{subId}/teacher/{teachId}")
	public ResponseEntity<?> assignTeacher(@PathVariable Integer subId, @PathVariable Integer teachId) {
		return subServ.assignTeacher(subId, teachId);
	}
	
	@Secured("ROLE_ADMIN")
	@GetMapping("/subjects/{subId}/teacher")
	public ResponseEntity<?> findTeachersForSubject(@PathVariable Integer subId) {
		return subServ.findTeachersForSub(subId);
	}
	
	@Secured({"ROLE_ADMIN", "ROLE_TEACHER"})
	@GetMapping("/users/teachers/{teachId}/subjects")
	public ResponseEntity<?> findSubjectsOfTeacher(@PathVariable Integer teachId, HttpServletRequest request) {
		boolean allowed = otherServ.isThisMe(teachId, request) || otherServ.amIAdmin(request);
		if (allowed)
			return subServ.findSubsForTeacher(teachId);
		return new ResponseEntity<>("You have no authority to look at this account", HttpStatus.UNAUTHORIZED);
	}
	
	@Secured("ROLE_ADMIN")
	@PostMapping("/subjects/{subId}/teacher/{teachId}/student/{studId}")
	public ResponseEntity<?> assignToStudent(@PathVariable Integer subId, @PathVariable Integer teachId, @PathVariable Integer studId) {
		return subServ.assingSubToStudent(subId, teachId, studId);
	}
	
	@Secured("ROLE_ADMIN")
	@PostMapping("/subjects/{subId}/teacher/{teachId}/group/{groupId}")
	public ResponseEntity<?> assignToGroup(@PathVariable Integer subId, @PathVariable Integer teachId, @PathVariable Integer groupId) {
		return subServ.assingSubToGroup(subId, teachId, groupId);
	}
	
	@Secured("ROLE_ADMIN")
	@DeleteMapping("/subjects/{subId}/teacher/{teachId}/student/{studId}")
	public ResponseEntity<?> removeFromStudent(@PathVariable Integer subId, @PathVariable Integer teachId, @PathVariable Integer studId) {
		return subServ.removeSubFromStudent(subId, teachId, studId);
	}
	
	@Secured("ROLE_ADMIN")
	@DeleteMapping("/subjects/{subId}/teacher/{teachId}")
	public ResponseEntity<?> unassignTeacher(@PathVariable Integer subId, @PathVariable Integer teachId) {
		return subServ.removeTeacherFromSubject(subId, teachId);
	}
	
	@Secured({"ROLE_ADMIN", "ROLE_STUDENT", "ROLE_PARENT"})
	@GetMapping("/users/student/{studId}/subjects")
	public ResponseEntity<?> getSubjetsFromStudent(@PathVariable Integer studId, HttpServletRequest request) {
		boolean allowed = otherServ.amIAdmin(request) || otherServ.isThisMe(studId, request) || otherServ.amITheirParent(studId, request);
		if (allowed)
			return subServ.getSubsFromStudent(studId);
		return new ResponseEntity<> ("You have no authority to see this information", HttpStatus.UNAUTHORIZED);
	}
	
	
	private String createErrorMessage(BindingResult result) {
		return result.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining("\n"));

	}
	
}
