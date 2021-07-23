package com.iktpreobuka.el_ucionica_AS.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.iktpreobuka.el_ucionica_AS.controllers.DTOs.NewGradeDTO;
import com.iktpreobuka.el_ucionica_AS.entities.GradeEntity;
import com.iktpreobuka.el_ucionica_AS.repositories.GradeRepository;
import com.iktpreobuka.el_ucionica_AS.services.GradeServices;

@RestController
public class GradeController {

	@Autowired
	private GradeRepository gradeRepo;
	@Autowired
	private GradeServices gradeServ;
	
	@GetMapping ("/grades/all")
	public ResponseEntity<?> getAll() {
		return new ResponseEntity<>(gradeRepo.findAll(), HttpStatus.OK);
	}
	
	@GetMapping("/grades/{id}")
	public ResponseEntity<?> getOne(@PathVariable Integer id) {
		if (gradeRepo.existsById(id))
			return new ResponseEntity<> (gradeRepo.findById(id).get(), HttpStatus.OK);
		return new ResponseEntity<> ("Grade does not exist", HttpStatus.NOT_FOUND);
	}
	
	//TODO alter in service
	@PostMapping("/grades/new")
	public ResponseEntity<?> makeNew(@RequestBody NewGradeDTO newGrade) {
		GradeEntity grade = new GradeEntity();
		Integer success = gradeServ.makeNewGrade(newGrade, grade);
		if (success == 0)
			return new ResponseEntity<>(grade, HttpStatus.CREATED);
		if (success == 1)
			return new ResponseEntity<>("Error: Invalid combination of teacher subject and student", HttpStatus.BAD_REQUEST);
		if (success == 2)
			return new ResponseEntity<> ("Error: Grade out of bounds", HttpStatus.BAD_REQUEST);
		return new ResponseEntity<> ("Error: Unknown error", HttpStatus.BAD_REQUEST);
	}
	
	@PutMapping("/grades/{id}")
	public ResponseEntity<?> changeExisting(@PathVariable Integer id, @RequestBody GradeEntity newGrade) {
		return new ResponseEntity<> ("Change Grade", HttpStatus.NOT_IMPLEMENTED);
	}
	
	@DeleteMapping("grades/{id}")
	public ResponseEntity<?> deleteGrade(@PathVariable Integer id) {
		return new ResponseEntity<> ("Delete Grade", HttpStatus.NOT_IMPLEMENTED);
	}
	
	@GetMapping("/users/student/{studId}/grades")
	public ResponseEntity<?> getAllFromStudent(@PathVariable Integer studID) {
		return new ResponseEntity<> ("Get All Grades From Student", HttpStatus.NOT_IMPLEMENTED);
	}
	
	@GetMapping("/users/student/{studId}/grades/{subID}")
	public ResponseEntity<?> getAllFromStudentBySubject(@PathVariable Integer studId, @PathVariable Integer subID) {
		return new ResponseEntity<> ("Get Grades From Student By Subject", HttpStatus.NOT_IMPLEMENTED);
	}
	
	@GetMapping("/users/student/{studId}/grades/average")
	public ResponseEntity<?> getStudentsAverages(@PathVariable Integer studId, @RequestParam Integer year, @RequestParam Integer semester) {
		return new ResponseEntity<> ("Get Grade Average per Year and Semester", HttpStatus.NOT_IMPLEMENTED);
	}
}
