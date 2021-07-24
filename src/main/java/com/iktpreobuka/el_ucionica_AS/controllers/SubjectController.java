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

import com.iktpreobuka.el_ucionica_AS.controllers.DTOs.ChangeSubjectDTO;
import com.iktpreobuka.el_ucionica_AS.controllers.DTOs.NewSubjectDTO;
import com.iktpreobuka.el_ucionica_AS.repositories.SubjectRepository;
import com.iktpreobuka.el_ucionica_AS.services.SubjectServices;

@RestController
public class SubjectController {

	@Autowired
	private SubjectRepository subRepo;
	@Autowired
	private SubjectServices subServ;
	
	@GetMapping("/subjects")
	public ResponseEntity<?> getAllSubjects() {
		return new ResponseEntity<> (subRepo.findAll(), HttpStatus.OK);
	}
	
	@GetMapping("/subjects/year/{yearNo}")
	public ResponseEntity<?> getSubjectsByYear(@PathVariable Integer yearNo) {
		return subServ.getSubByYear(yearNo);
	}
	
	@GetMapping("/subjects/name/{name}")
	public ResponseEntity<?> getSubjecsByName(@PathVariable String name) {
		return subServ.getSubByName(name);
	}
	
	@GetMapping("/subjects/{id}")
	public ResponseEntity<?> getSubjectById(@PathVariable Integer id) {
		return subServ.getSubById(id);
	}
	
	@PostMapping("/subjects")
	public ResponseEntity<?> makeNewSubject(@Valid @RequestBody NewSubjectDTO newSub) {
		return subServ.makeNewSubject(newSub);
	}
	
	@PutMapping("/subjects/{id}")
	public ResponseEntity<?> alterSubject(@PathVariable Integer id, @Valid @RequestBody ChangeSubjectDTO sub) {
		return subServ.alterSubject(id, sub);
	}
	
	@DeleteMapping("/subjects/{id}")
	public ResponseEntity<?> deleteSubject(@PathVariable Integer id) {
		return subServ.deleteSubjcet(id);
	}
	
	@PostMapping("/subjects/{subId}/teacher/{teachId}")
	public ResponseEntity<?> assignTeacher(@PathVariable Integer subId, @PathVariable Integer teachId) {
		return subServ.assignTeacher(subId, teachId);
	}
	
	@GetMapping("/subjects/{subId}/teacher")
	public ResponseEntity<?> findTeachersForSubject(@PathVariable Integer subId) {
		return subServ.findTeachersForSub(subId);
	}
	
	@GetMapping("/users/teachers/{teachId}/subjects")
	public ResponseEntity<?> findSubjectsOfTeacher(@PathVariable Integer teachId) {
		return subServ.findSubsForTeacher(teachId);
	}
	
	@PostMapping("/subjects/{subId}/teacher/{teachId}/student/{studId}")
	public ResponseEntity<?> assignToStudent(@PathVariable Integer subId, @PathVariable Integer teachId, @PathVariable Integer studId) {
		return subServ.assingSubToStudent(subId, teachId, studId);
	}
	
	@PostMapping("/subjects/{subId}/teacher/{teachId}/group/{groupId}")
	public ResponseEntity<?> assignToGroup(@PathVariable Integer subId, @PathVariable Integer teachId, @PathVariable Integer groupId) {
		return subServ.assingSubToGroup(subId, teachId, groupId);
	}
}
