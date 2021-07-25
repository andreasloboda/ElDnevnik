package com.iktpreobuka.el_ucionica_AS.controllers;

import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

@RestController
public class GradeController {

	@Autowired
	private GradeRepository gradeRepo;
	@Autowired
	private GradeServices gradeServ;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@GetMapping ("/grades")
	public ResponseEntity<?> getAll() {
		return new ResponseEntity<>(gradeRepo.findAll(), HttpStatus.OK);
	}
	
	@GetMapping("/grades/{id}")
	public ResponseEntity<?> getOne(@PathVariable Integer id) {
		return gradeServ.findGrade(id);
	}
	
	@PostMapping("/grades/")
	public ResponseEntity<?> makeNew(@Valid @RequestBody NewGradeDTO newGrade, BindingResult result){
		if (result.hasErrors()) {
			logger.info("Attempted to make a grade, couldn't validate information");
			return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);
		}
		return gradeServ.makeNewGrade(newGrade);
	}
	
	@PutMapping("/grades/{id}")
	public ResponseEntity<?> changeExisting(@PathVariable Integer id, @Valid @RequestBody ChangeGradeDTO cGrade, BindingResult result){
		if (result.hasErrors()) {
			logger.info("Attempted to alter a grade, couldn't validate information");
			return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);
		}
		return gradeServ.changeGrade(id, cGrade);
	}
	
	@DeleteMapping("grades/{id}")
	public ResponseEntity<?> deleteGrade(@PathVariable Integer id) {
		return gradeServ.deleteGrade(id);
	}
	
	@GetMapping("/users/student/{studId}/grades")
	public ResponseEntity<?> getAllFromStudent(@PathVariable Integer studId) {
		return gradeServ.getAllFromStudent(studId);
	}
	
	@GetMapping("/users/student/{studId}/grades/sub/{subID}")
	public ResponseEntity<?> getAllFromStudentBySubject(@PathVariable Integer studId, @PathVariable Integer subID) {
		return gradeServ.getFromStudentForSubject(studId, subID);
	}
	
	//TODO add another layer to this that sorts subjects by years and semesters and calculates the final GPA
	@GetMapping("/users/student/{studId}/grades/final")
	public ResponseEntity<?> getStudentsAverages(@PathVariable Integer studId) {
		return gradeServ.getFinalGradesForStudent(studId);
	}
	
	
	
	private String createErrorMessage(BindingResult result) {
		return result.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining("\n"));

	}
}
