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

import com.iktpreobuka.el_ucionica_AS.controllers.DTOs.ChangeUserDTO;
import com.iktpreobuka.el_ucionica_AS.controllers.DTOs.NewAdminDTO;
import com.iktpreobuka.el_ucionica_AS.controllers.DTOs.NewParentDTO;
import com.iktpreobuka.el_ucionica_AS.controllers.DTOs.NewTeachStudDTO;
import com.iktpreobuka.el_ucionica_AS.controllers.DTOs.PasswordDTO;
import com.iktpreobuka.el_ucionica_AS.entities.enums.UserRole;
import com.iktpreobuka.el_ucionica_AS.repositories.UserRepository;
import com.iktpreobuka.el_ucionica_AS.services.UserServices;

@RestController
public class UserController {

	@Autowired
	private UserRepository userRepo;
	@Autowired
	private UserServices userServ;
	
	@GetMapping("/users/all")
	public ResponseEntity<?> getAll() {
		return new ResponseEntity<> (userRepo.findAll(), HttpStatus.OK);
	}

	@GetMapping("/users/role/{role}")
	public ResponseEntity<?> getAllByRole(@PathVariable String role) {
		return userServ.getByRole(role);
	}
	
	@GetMapping("/users/id/{id}")
	public ResponseEntity<?> getById(@PathVariable Integer id) {
		return userServ.getByID(id);
	}
	
	@PostMapping("/users/new/admin")
	public ResponseEntity<?> makeNewAdmin(@RequestBody NewAdminDTO newAdmin) {
		return userServ.makeNewAdmin(newAdmin);
	}
	
	@PostMapping("/users/new/teacher")
	public ResponseEntity<?> makeNewTeacher(@RequestBody NewTeachStudDTO newTeacher) {
		return userServ.makeNewTeachStud(newTeacher, UserRole.UserRole_TEACHER);
	}
	
	@PostMapping("/users/new/student")
	public ResponseEntity<?> makeNewStudent(@RequestBody NewTeachStudDTO newStud) {
		return userServ.makeNewTeachStud(newStud, UserRole.UserRole_STUDENT);
	}
	
	@PostMapping("/users/new/parent")
	public ResponseEntity<?> makeNewParent(@RequestBody NewParentDTO newParent) {
		return userServ.makeNewParent(newParent);
	}
	
	@DeleteMapping("/users/delete/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable Integer id) {
		return userServ.deleteUser(id);
	}
	
	@PutMapping("/users/change/{id}")
	public ResponseEntity<?> changeInfo(@PathVariable Integer id, @RequestBody ChangeUserDTO newInfo) {
		return userServ.changeUser(id, newInfo);
	}
	
	@PutMapping("/users/change/{id}/password")
	public ResponseEntity<?> changeInfo(@PathVariable Integer id, @RequestBody PasswordDTO password) {
		return userServ.changePassword(id, password);
	}
	
	@PutMapping("/users/student/{studId}/parent/{parId}")
	public ResponseEntity<?> addParentToStudent(@PathVariable Integer studId, @PathVariable Integer parId) {
		return userServ.addParent(studId, parId);
	}
}
