package com.iktpreobuka.el_ucionica_AS.controllers;

import java.util.stream.Collectors;

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

import com.iktpreobuka.el_ucionica_AS.controllers.RequestDTOs.ChangeGroupDTO;
import com.iktpreobuka.el_ucionica_AS.controllers.RequestDTOs.NewGroupDTO;
import com.iktpreobuka.el_ucionica_AS.services.GroupServices;

@RestController
public class StudgroupController {

	@Autowired
	private GroupServices groupServ;
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Secured("ROLE_ADMIN")
	@GetMapping("/groups/")
	public ResponseEntity<?> getAllGroups() {
		return groupServ.getAllGroups();
	}
	
	@Secured("ROLE_ADMIN")
	@GetMapping("/groups/active")
	public ResponseEntity<?> getActiveGroups() {
		return groupServ.getActiveGroups();
	}
	
	@Secured("ROLE_ADMIN")
	@GetMapping("/groups/id/{id}")
	public ResponseEntity<?> getOneGroup(@PathVariable Integer id) {
		return groupServ.getGroupById(id);
	}
	
	@Secured("ROLE_ADMIN")
	@PostMapping("/groups")
	public ResponseEntity<?> makeNewGroup(@Valid @RequestBody NewGroupDTO newGroup, BindingResult result){
		if (result.hasErrors()) {
			logger.info("Attempted to make a group, couldn't validate information");
			return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);
		}
		return groupServ.makeNewGroup(newGroup);
	}

	@Secured("ROLE_ADMIN")
	@PutMapping("/groups/id/{id}")
	public ResponseEntity<?> alterGroup(@PathVariable Integer id, @Valid @RequestBody ChangeGroupDTO changeGroup, BindingResult result){
		if (result.hasErrors()) {
			logger.info("Attempted to alter a group, couldn't validate information");
			return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);
		}
		return groupServ.alterGroup(changeGroup, id);
	}
	
	@Secured("ROLE_ADMIN")
	@PutMapping("/groups/id/{id}/advance")
	public ResponseEntity<?> advanceGroup(@PathVariable Integer id) {
		return groupServ.advanceGroup(id);
	}
	
	@Secured("ROLE_ADMIN")
	@PutMapping("/groups/id/{id}/switchStatus")
	public ResponseEntity<?> changeGroupStatus(@PathVariable Integer id) {
		return groupServ.switchStatus(id);
	}
	
	@Secured("ROLE_ADMIN")
	@DeleteMapping("/groups/id/{id}")
	public ResponseEntity<?> deleteGroup(@PathVariable Integer id) {
		return groupServ.deleteGroup(id);
	}
	
	@Secured("ROLE_ADMIN")
	@PutMapping("/users/student/{studId}/group/{groupId}")
	public ResponseEntity<?> assignStudentToGroup(@PathVariable Integer studId, @PathVariable Integer groupId) {
		return groupServ.assignStudent(studId, groupId);
	}
	
	
	
	private String createErrorMessage(BindingResult result) {
		return result.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining("\n"));

	}
}
