package com.iktpreobuka.el_ucionica_AS.controllers;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.iktpreobuka.el_ucionica_AS.controllers.DTOs.ChangeUserDTO;
import com.iktpreobuka.el_ucionica_AS.controllers.DTOs.LoginDTO;
import com.iktpreobuka.el_ucionica_AS.controllers.DTOs.NewAdminDTO;
import com.iktpreobuka.el_ucionica_AS.controllers.DTOs.NewParentDTO;
import com.iktpreobuka.el_ucionica_AS.controllers.DTOs.NewTeachStudDTO;
import com.iktpreobuka.el_ucionica_AS.controllers.DTOs.PasswordDTO;
import com.iktpreobuka.el_ucionica_AS.controllers.util.Encryption;
import com.iktpreobuka.el_ucionica_AS.controllers.util.UsersValidator;
import com.iktpreobuka.el_ucionica_AS.entities.UserEntity;
import com.iktpreobuka.el_ucionica_AS.entities.enums.UserRole;
import com.iktpreobuka.el_ucionica_AS.repositories.UserRepository;
import com.iktpreobuka.el_ucionica_AS.services.OtherServices;
import com.iktpreobuka.el_ucionica_AS.services.UserServices;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
public class UserController {

	@Autowired
	private UserRepository userRepo;
	@Autowired
	private UserServices userServ;
	@Autowired
	private UsersValidator passValidate;
	@Autowired
	private OtherServices otherServ;
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Value("${spring.security.secret-key}")
	private String secretKey;
	@Value("${spring.security.token-duration}")
	private Integer tokenDuration;

	@InitBinder
	protected void initBinder(final WebDataBinder binder) {
		binder.addValidators(passValidate);
	}

	@Secured("ROLE_ADMIN")
	@GetMapping("/users/search")
	public ResponseEntity<?> getAll() {
		return new ResponseEntity<>(userRepo.findAll(), HttpStatus.OK);
	}
	
	@Secured("ROLE_ADMIN")
	@GetMapping("/users/search/role/{role}")
	public ResponseEntity<?> getAllByRole(@PathVariable String role) {
		return userServ.getByRole(role);
	}

	@Secured({"ROLE_ADMIN", "ROLE_STUDENT", "ROLE_PARENT", "ROLE_TEACHER"})
	@GetMapping("/users/search/id/{id}")
	public ResponseEntity<?> getById(@PathVariable Integer id, HttpServletRequest request) {
		boolean allowed = otherServ.isThisMe(id, request) || otherServ.amIAdmin(request) || otherServ.amITheirParent(id, request) || otherServ.amITheirTeacher(id, request);
		if (allowed)
			return userServ.getByID(id);
		logger.info("Attempt was made to alter an account not belonging to the user.");
		return new ResponseEntity<>("User has no authority to look at this account", HttpStatus.UNAUTHORIZED);
	}

	@Secured("ROLE_ADMIN")
	@PostMapping("/users/new/admin/")
	public ResponseEntity<?> makeNewAdmin(@Valid @RequestBody NewAdminDTO newAdmin, BindingResult result) {
		if (result.hasErrors()) {
			logger.info("Attempted to make an Admin account, couldn't validate information");
			return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);
		}
		return userServ.makeNewAdmin(newAdmin);
	}

	@Secured("ROLE_ADMIN")
	@PostMapping("/users/new/teacher")
	public ResponseEntity<?> makeNewTeacher(@Valid @RequestBody NewTeachStudDTO newTeacher, BindingResult result) {
		if (result.hasErrors()) {
			logger.info("Attempted to make a Teacher account, couldn't validate information");
			return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);
		}
		return userServ.makeNewTeachStud(newTeacher, UserRole.ROLE_TEACHER);
	}

	@Secured("ROLE_ADMIN")
	@PostMapping("/users/new/student/")
	public ResponseEntity<?> makeNewStudent(@Valid @RequestBody NewTeachStudDTO newStud, BindingResult result) {
		if (result.hasErrors()) {
			logger.info("Attempted to make a Student account, couldn't validate information");
			return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);
		}
		return userServ.makeNewTeachStud(newStud, UserRole.ROLE_STUDENT);
	}

	@Secured("ROLE_ADMIN")
	@PostMapping("/users/new/parent")
	public ResponseEntity<?> makeNewParent(@Valid @RequestBody NewParentDTO newParent, BindingResult result) {
		if (result.hasErrors()) {
			logger.info("Attempted to make a Parent account, couldn't validate information");
			return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);
		}
		return userServ.makeNewParent(newParent);
	}

	@Secured("ROLE_ADMIN")
	@DeleteMapping("/users/delete/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable Integer id) {
		return userServ.deleteUser(id);
	}

	@Secured({"ROLE_ADMIN", "ROLE_STUDENT", "ROLE_PARENT", "ROLE_TEACHER"})
	@PutMapping("/users/change/{id}")
	public ResponseEntity<?> changeInfo(@PathVariable Integer id, @Valid @RequestBody ChangeUserDTO newInfo,
			BindingResult result, HttpServletRequest request) {
		if (result.hasErrors()) {
			logger.info("Attempted to alter an account, couldn't validate information");
			return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);
		}
		boolean allowed = otherServ.isThisMe(id, request) || otherServ.amIAdmin(request);
		if (allowed)
			return userServ.changeUser(id, newInfo);
		logger.info("Attempt was made to alter an account not belonging to the user.");
		return new ResponseEntity<>("User has no authority to change this account", HttpStatus.UNAUTHORIZED);
	}
	
	@Secured({"ROLE_ADMIN", "ROLE_STUDENT", "ROLE_PARENT", "ROLE_TEACHER"})
	@PutMapping("/users/change/{id}/password")
	public ResponseEntity<?> changeInfo(@PathVariable Integer id, @Valid @RequestBody PasswordDTO password,
			BindingResult result, HttpServletRequest request) {
		if (result.hasErrors()) {
			logger.info("Attempted to change a password, couldn't validate information");
			return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);
		}
		boolean allowed = otherServ.isThisMe(id, request);
		if (allowed)
			return userServ.changePassword(id, password);
		logger.info("Attempt was made to alter password not belonging to the user.");
		return new ResponseEntity<>("User has no authority to change this account's password", HttpStatus.UNAUTHORIZED);
	}

	@Secured("ROLE_ADMIN")
	@PutMapping("/users/student/{studId}/parent/{parId}")
	public ResponseEntity<?> addParentToStudent(@PathVariable Integer studId, @PathVariable Integer parId) {
		return userServ.addParent(studId, parId);
	}

	@Secured("ROLE_ADMIN")
	@GetMapping("/admin/download/log")
	public ResponseEntity<?> downloadLog() {
		return otherServ.downloadLog();
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestParam String username, @RequestParam String password) {
		if (userRepo.existsByUsername(username)) {
			UserEntity user = userRepo.findByUsername(username).get();
			if (Encryption.validatePassword(password, user.getPassword())) {
				String token = getJWTToken(user);
				LoginDTO userDTO = new LoginDTO();
				userDTO.setUser(username);
				userDTO.setToken(token);
				logger.info("User " + user.getId() + " logged in.");
				return new ResponseEntity<>(userDTO, HttpStatus.OK);
			}
		}
		logger.info("Unsuccessful login occured");
		return new ResponseEntity<>("Wrong credentials", HttpStatus.UNAUTHORIZED);
	}

	//TODO get all students of a parent
	
	// >>>>> Private methods below this line

	private String createErrorMessage(BindingResult result) {
		return result.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining("\n"));

	}

	private String getJWTToken(UserEntity user) {
		List<GrantedAuthority> grantedAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList(user.getRole().toString());
		String token = Jwts.builder()
				.setId("softtekJWT").setSubject(user.getUsername())
				.claim("authorities", grantedAuthorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + tokenDuration))
				.signWith(SignatureAlgorithm.HS512, secretKey).compact();
		return "Bearer " + token;
	}

}
