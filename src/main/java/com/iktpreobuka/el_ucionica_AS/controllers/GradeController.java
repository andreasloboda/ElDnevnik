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

import com.iktpreobuka.el_ucionica_AS.controllers.DTOs.ChangeGradeDTO;
import com.iktpreobuka.el_ucionica_AS.controllers.DTOs.NewGradeDTO;
import com.iktpreobuka.el_ucionica_AS.repositories.GradeRepository;
import com.iktpreobuka.el_ucionica_AS.services.GradeServices;
import com.iktpreobuka.el_ucionica_AS.services.OtherServices;

@RestController
public class GradeController {

	@Autowired
	private GradeRepository gradeRepo;
	@Autowired
	private GradeServices gradeServ;
	@Autowired
	private OtherServices otherServ;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Secured("ROLE_ADMIN")
	@GetMapping ("/grades")
	public ResponseEntity<?> getAll() {
		return new ResponseEntity<>(gradeRepo.findAll(), HttpStatus.OK);
	}
	
	@Secured({"ROLE_ADMIN", "ROLE_TEACHER", "ROLE_STUDENT", "ROLE_PARENT"})
	@GetMapping("/grades/{id}")
	public ResponseEntity<?> getOne(@PathVariable Integer id, HttpServletRequest request) {
		boolean allowed = otherServ.amIAdmin(request) || otherServ.isThisMyGrade(id, request);
		if (allowed)
			return gradeServ.findGrade(id);
		return new ResponseEntity<> ("You have no authority to see this information", HttpStatus.UNAUTHORIZED);
	}
	
	@Secured({"ROLE_ADMIN", "ROLE_TEACHER"})
	@PostMapping("/grades/")
	public ResponseEntity<?> makeNew(@Valid @RequestBody NewGradeDTO newGrade, BindingResult result, HttpServletRequest request){
		if (result.hasErrors()) {
			logger.info("Attempted to make a grade, couldn't validate information");
			return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);
		}
		boolean allowed = otherServ.amIAdmin(request) || (otherServ.isThisMe(newGrade.getTeacherID(), request) && otherServ.doIThechThemThis(newGrade.getStudentID(), newGrade.getSubjectID(), request));
		if (allowed)
			return gradeServ.makeNewGrade(newGrade);
		return new ResponseEntity<> ("You have no authority to grade this student", HttpStatus.UNAUTHORIZED);
	}
	
	@Secured({"ROLE_ADMIN", "ROLE_TEACHER"})
	@PutMapping("/grades/{id}")
	public ResponseEntity<?> changeExisting(@PathVariable Integer id, @Valid @RequestBody ChangeGradeDTO cGrade, BindingResult result, HttpServletRequest request){
		if (result.hasErrors()) {
			logger.info("Attempted to alter a grade, couldn't validate information");
			return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);
		}
		boolean allowed = otherServ.amIAdmin(request) || otherServ.isThisMyGrade(id, request);
		if (allowed)
			return gradeServ.changeGrade(id, cGrade);
		return new ResponseEntity<> ("You have no authority to alter this grade", HttpStatus.UNAUTHORIZED);
	}
	
	@Secured({"ROLE_ADMIN", "ROLE_TEACHER"})
	@DeleteMapping("grades/{id}")
	public ResponseEntity<?> deleteGrade(@PathVariable Integer id, HttpServletRequest request) {
		boolean allowed = otherServ.amIAdmin(request) || otherServ.isThisMyGrade(id, request);
		if (allowed)
			return gradeServ.deleteGrade(id);
		return new ResponseEntity<> ("You have no authority to delete this grade", HttpStatus.UNAUTHORIZED);
	}
	
	@Secured({"ROLE_ADMIN", "ROLE_STUDENT", "ROLE_PARENT"})
	@GetMapping("/users/student/{studId}/grades")
	public ResponseEntity<?> getAllFromStudent(@PathVariable Integer studId, HttpServletRequest request) {
		boolean allowed = otherServ.amIAdmin(request) || otherServ.isThisMe(studId, request) || otherServ.amITheirParent(studId, request);
		if (allowed)
			return gradeServ.getAllFromStudent(studId);
		return new ResponseEntity<> ("You have no authority to see this information", HttpStatus.UNAUTHORIZED);
	}
	
	
	@Secured({"ROLE_ADMIN", "ROLE_TEACHER", "ROLE_STUDENT", "ROLE_PARENT"})
	@GetMapping("/users/student/{studId}/grades/sub/{subID}")
	public ResponseEntity<?> getAllFromStudentBySubject(@PathVariable Integer studId, @PathVariable Integer subID, HttpServletRequest request) {
		boolean allowed = otherServ.amIAdmin(request) || otherServ.isThisMe(studId, request) || otherServ.amITheirParent(studId, request) || otherServ.doIThechThemThis(studId, subID, request);
		if (allowed)
			return gradeServ.getFromStudentForSubject(studId, subID);
		return new ResponseEntity<> ("You have no authority to see this information", HttpStatus.UNAUTHORIZED);
	}
	
	//TODO add another layer to this that sorts subjects by years and semesters and calculates the final GPA
	@Secured({"ROLE_ADMIN", "ROLE_STUDENT", "ROLE_PARENT"})
	@GetMapping("/users/student/{studId}/grades/final")
	public ResponseEntity<?> getStudentsAverages(@PathVariable Integer studId, HttpServletRequest request) {
		boolean allowed = otherServ.amIAdmin(request) || otherServ.amITheirParent(studId, request) || otherServ.isThisMe(studId, request);
		if (allowed)
			return gradeServ.getFinalGradesForStudent(studId);
		return new ResponseEntity<> ("You have no authority to see this information", HttpStatus.UNAUTHORIZED);
	}
	
	
	
	private String createErrorMessage(BindingResult result) {
		return result.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining("\n"));

	}
}
