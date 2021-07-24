package com.iktpreobuka.el_ucionica_AS.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
	
	@GetMapping ("/grades")
	public ResponseEntity<?> getAll() {
		return new ResponseEntity<>(gradeRepo.findAll(), HttpStatus.OK);
	}
	
	@GetMapping("/grades/{id}")
	public ResponseEntity<?> getOne(@PathVariable Integer id) {
		return gradeServ.findGrade(id);
	}
	
	@PostMapping("/grades/")
	public ResponseEntity<?> makeNew(@Valid @RequestBody NewGradeDTO newGrade) {
		return gradeServ.makeNewGrade(newGrade);
	}
	
	@PutMapping("/grades/{id}")
	public ResponseEntity<?> changeExisting(@PathVariable Integer id, @Valid @RequestBody ChangeGradeDTO cGrade) {
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
	
	@GetMapping("/users/student/{studId}/grades/final")
	public ResponseEntity<?> getStudentsAverages(@PathVariable Integer studId) {
		return gradeServ.getFinalGradesForStudent(studId);
	}
}
