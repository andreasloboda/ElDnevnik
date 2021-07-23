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
import org.springframework.web.bind.annotation.RestController;

import com.iktpreobuka.el_ucionica_AS.controllers.DTOs.GroupDTO;
import com.iktpreobuka.el_ucionica_AS.repositories.StudgroupRepository;
import com.iktpreobuka.el_ucionica_AS.services.GroupServices;

@RestController
public class StudgroupController {

	@Autowired
	private StudgroupRepository groupRepo;
	@Autowired
	private GroupServices groupServ;
	
	@GetMapping("/groups/")
	public ResponseEntity<?> getAllGroups() {
		return new ResponseEntity<> (groupRepo.findAll(), HttpStatus.OK);
	}
	
	@GetMapping("/groups/active")
	public ResponseEntity<?> getActiveGroups() {
		return new ResponseEntity<> (groupRepo.findAllByActive(true), HttpStatus.OK);
	}
	
	@GetMapping("/groups/id/{id}")
	public ResponseEntity<?> getOneGroup(@PathVariable Integer id) {
		return groupServ.getGroupById(id);
	}
	
	@PostMapping("/groups")
	public ResponseEntity<?> makeNewGroup(@RequestBody GroupDTO newGroup){
		return groupServ.makeOrAlterGroup(newGroup, null);
	}

	@PutMapping("/groups/id/{id}")
	public ResponseEntity<?> alterGroup(@PathVariable Integer id, @RequestBody GroupDTO newGroup){
		return groupServ.makeOrAlterGroup(newGroup, id);
	}
	
	@PutMapping("/groups/id/{id}/advance")
	public ResponseEntity<?> advanceGroup(@PathVariable Integer id) {
		return groupServ.advanceGroup(id);
	}
	
	@PutMapping("/groups/id/{id}/switchStatus")
	public ResponseEntity<?> changeGroupStatus(@PathVariable Integer id) {
		return groupServ.switchStatus(id);
	}
	
	@DeleteMapping("/groups/id/{id}")
	public ResponseEntity<?> deleteGroup(@PathVariable Integer id) {
		return groupServ.deleteGroup(id);
	}
	
	@PutMapping("/users/student/{studId}/group/{groupId}")
	public ResponseEntity<?> assignStudentToGroup(@PathVariable Integer studId, @PathVariable Integer groupId) {
		return groupServ.assignStudent(studId, groupId);
	}
	
	//TODO assign new subject
}
