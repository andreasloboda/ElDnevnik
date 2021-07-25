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
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
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
import com.iktpreobuka.el_ucionica_AS.controllers.util.UsersValidator;
import com.iktpreobuka.el_ucionica_AS.entities.enums.UserRole;
import com.iktpreobuka.el_ucionica_AS.repositories.UserRepository;
import com.iktpreobuka.el_ucionica_AS.services.UserServices;

@RestController
public class UserController {

	@Autowired
	private UserRepository userRepo;
	@Autowired
	private UserServices userServ;
	@Autowired
	private UsersValidator passValidate;
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	
	@InitBinder
	protected void initBinder(final WebDataBinder binder) {
		binder.addValidators(passValidate);
	}
	
	@GetMapping("/users/search")
	public ResponseEntity<?> getAll() {
		return new ResponseEntity<> (userRepo.findAll(), HttpStatus.OK);
	}

	@GetMapping("/users/search/role/{role}")
	public ResponseEntity<?> getAllByRole(@PathVariable String role) {
		return userServ.getByRole(role);
	}
	
	@GetMapping("/users/search/id/{id}")
	public ResponseEntity<?> getById(@PathVariable Integer id) {
		return userServ.getByID(id);
	}
	
	@PostMapping("/users/new/admin/")
	public ResponseEntity<?> makeNewAdmin(@Valid @RequestBody NewAdminDTO newAdmin, BindingResult result) {
		if (result.hasErrors()) {
			logger.info("Attempted to make an Admin account, couldn't validate information");
			return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);
		}
		return userServ.makeNewAdmin(newAdmin);
	}
	
	@PostMapping("/users/new/teacher")
	public ResponseEntity<?> makeNewTeacher(@Valid @RequestBody NewTeachStudDTO newTeacher, BindingResult result) {
		if (result.hasErrors()) {
			logger.info("Attempted to make a Teacher account, couldn't validate information");
			return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);
		}
		return userServ.makeNewTeachStud(newTeacher, UserRole.UserRole_TEACHER);
	}
	
	@PostMapping("/users/new/student/")
	public ResponseEntity<?> makeNewStudent(@Valid @RequestBody NewTeachStudDTO newStud, BindingResult result) {
		if (result.hasErrors()) {
			logger.info("Attempted to make a Student account, couldn't validate information");
			return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);
		}
		return userServ.makeNewTeachStud(newStud, UserRole.UserRole_STUDENT);
	}
	
	@PostMapping("/users/new/parent")
	public ResponseEntity<?> makeNewParent(@Valid @RequestBody NewParentDTO newParent, BindingResult result) {
		if (result.hasErrors()) {
			logger.info("Attempted to make a Parent account, couldn't validate information");
			return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);
		}
		return userServ.makeNewParent(newParent);
	}
	
	@DeleteMapping("/users/delete/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable Integer id) {
		return userServ.deleteUser(id);
	}
	
	@PutMapping("/users/change/{id}")
	public ResponseEntity<?> changeInfo(@PathVariable Integer id, @Valid @RequestBody ChangeUserDTO newInfo, BindingResult result) {
		if (result.hasErrors()) {
			logger.info("Attempted to alter an account, couldn't validate information");
			return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);
		}
		return userServ.changeUser(id, newInfo);
	}
	
	@PutMapping("/users/change/{id}/password")
	public ResponseEntity<?> changeInfo(@PathVariable Integer id, @Valid @RequestBody PasswordDTO password, BindingResult result) {
		if (result.hasErrors()) {
			logger.info("Attempted to change a password, couldn't validate information");
			return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);
		}
		return userServ.changePassword(id, password);
	}
	
	@PutMapping("/users/student/{studId}/parent/{parId}")
	public ResponseEntity<?> addParentToStudent(@PathVariable Integer studId, @PathVariable Integer parId) {
		return userServ.addParent(studId, parId);
	}
	
	
	
	private String createErrorMessage(BindingResult result) {
		return result.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining("\n"));

	}
}
