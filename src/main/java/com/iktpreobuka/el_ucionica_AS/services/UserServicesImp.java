package com.iktpreobuka.el_ucionica_AS.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.iktpreobuka.el_ucionica_AS.controllers.DTOs.ChangeUserDTO;
import com.iktpreobuka.el_ucionica_AS.controllers.DTOs.NewAdminDTO;
import com.iktpreobuka.el_ucionica_AS.controllers.DTOs.NewParentDTO;
import com.iktpreobuka.el_ucionica_AS.controllers.DTOs.NewTeachStudDTO;
import com.iktpreobuka.el_ucionica_AS.controllers.DTOs.PasswordDTO;
import com.iktpreobuka.el_ucionica_AS.entities.ParentEntity;
import com.iktpreobuka.el_ucionica_AS.entities.StudentEntity;
import com.iktpreobuka.el_ucionica_AS.entities.TeacherEntity;
import com.iktpreobuka.el_ucionica_AS.entities.UserEntity;
import com.iktpreobuka.el_ucionica_AS.entities.enums.UserRole;
import com.iktpreobuka.el_ucionica_AS.repositories.ParentRepository;
import com.iktpreobuka.el_ucionica_AS.repositories.StudentRepository;
import com.iktpreobuka.el_ucionica_AS.repositories.TeacherRepository;
import com.iktpreobuka.el_ucionica_AS.repositories.UserRepository;


@Service
public class UserServicesImp implements UserServices{
	
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private TeacherRepository teacherRepo;
	@Autowired
	private StudentRepository studRepo;
	@Autowired
	private ParentRepository parentRepo;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public ResponseEntity<?> getByRole(String role) {
		if (role.equalsIgnoreCase("admin")) {
			return new ResponseEntity<> (userRepo.findAllByRole(UserRole.UserRole_ADMIN), HttpStatus.OK);
		}
		if (role.equalsIgnoreCase("teacher")) {
			return new ResponseEntity<> (teacherRepo.findAll(), HttpStatus.OK);
		}
		if (role.equalsIgnoreCase("student")) {
			return new ResponseEntity<> (studRepo.findAll(), HttpStatus.OK);
		}
		if (role.equalsIgnoreCase("parent")) {
			return new ResponseEntity<> (parentRepo.findAll(), HttpStatus.OK);
		}
		return new ResponseEntity<>("Error: invalid role", HttpStatus.BAD_REQUEST);
	}

	@Override
	public ResponseEntity<?> getByID(Integer id) {
		if (userRepo.existsById(id)) {
			UserRole type = userRepo.findById(id).get().getRole();
			if (type == UserRole.UserRole_ADMIN)
				return new ResponseEntity<> (userRepo.findById(id), HttpStatus.OK);
			if (type == UserRole.UserRole_PARENT)
				return new ResponseEntity<> (parentRepo.findById(id), HttpStatus.OK);
			if (type == UserRole.UserRole_STUDENT)
				return new ResponseEntity<> (studRepo.findById(id), HttpStatus.OK);
			if (type == UserRole.UserRole_TEACHER)
				return new ResponseEntity<> (teacherRepo.findById(id), HttpStatus.OK);
		}
		return new ResponseEntity<> ("Error: User does not exist", HttpStatus.BAD_REQUEST);
	}

	@Override
	public ResponseEntity<?> makeNewAdmin(NewAdminDTO newAdmin) {
		if (userRepo.existsByUsername(newAdmin.getUsername())) {
			logger.info("Attempted to make new Admin account, but username was taken");
			return new ResponseEntity<>("Error: Username already taken", HttpStatus.BAD_REQUEST);
		}
		UserEntity user = new UserEntity();
		user.setPassword(newAdmin.getPassword());
		user.setRole(UserRole.UserRole_ADMIN);
		user.setUsername(newAdmin.getUsername());
		logger.info("New admin account created");
		return new ResponseEntity<>(userRepo.save(user), HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<?> makeNewTeachStud(NewTeachStudDTO user, UserRole role) {
		if (userRepo.existsByUsername(user.getUsername())) {
			if (role == UserRole.UserRole_STUDENT)
				logger.info("Attempted to make new Student account, but username was taken");
			if (role == UserRole.UserRole_TEACHER)
				logger.info("Attempted to make new Teacher account, but username was taken");
			return new ResponseEntity<>("Error: Username already taken", HttpStatus.BAD_REQUEST);
		}
		if (role == UserRole.UserRole_STUDENT) {
			StudentEntity newUser = new StudentEntity();
			newUser.setName(user.getName());
			newUser.setPassword(user.getPassword());
			newUser.setRole(UserRole.UserRole_STUDENT);
			newUser.setSurname(user.getSurname());
			newUser.setUsername(user.getUsername());
			logger.info("New student account created");
			return new ResponseEntity<>(studRepo.save(newUser), HttpStatus.OK);
		}
		if (role == UserRole.UserRole_TEACHER) {
			TeacherEntity newUser = new TeacherEntity();
			newUser.setName(user.getName());
			newUser.setPassword(user.getPassword());
			newUser.setRole(UserRole.UserRole_TEACHER);
			newUser.setSurname(user.getSurname());
			newUser.setUsername(user.getUsername());
			logger.info("New teacher account created");
			return new ResponseEntity<>(teacherRepo.save(newUser), HttpStatus.OK);
		}
		return new ResponseEntity<>("Error: Wrong Role", HttpStatus.BAD_REQUEST);
	}

	@Override
	public ResponseEntity<?> makeNewParent(NewParentDTO user) {
		if (userRepo.existsByUsername(user.getUsername())) {
			logger.info("Attempted to make new Parent account, but username was taken");
			return new ResponseEntity<> ("Error: Username already taken", HttpStatus.BAD_REQUEST);
		}
		ParentEntity newUser = new ParentEntity();
		newUser.setName(user.getName());
		newUser.setPassword(user.getPassword());
		newUser.setRole(UserRole.UserRole_PARENT);
		newUser.setSurname(user.getSurname());
		newUser.setUsername(user.getUsername());
		newUser.setEmail(user.getEmail());
		logger.info("New parent account created");
		return new ResponseEntity<> (parentRepo.save(newUser), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> deleteUser(Integer id) {
		if (userRepo.existsById(id)) {
			UserRole role = userRepo.findById(id).get().getRole();
			if (role == UserRole.UserRole_ADMIN) {
				UserEntity user = userRepo.findById(id).get();
				userRepo.deleteById(id);
				logger.info("Deleted Admin account with ID " + user.getId());
				return new ResponseEntity<> (user, HttpStatus.OK);
			}
			if (role == UserRole.UserRole_TEACHER) {
				TeacherEntity user = teacherRepo.findById(id).get();
				teacherRepo.deleteById(id);
				logger.info("Deleted Teacher account with ID " + user.getId());
				return new ResponseEntity<> (user, HttpStatus.OK);
			}
			if (role == UserRole.UserRole_STUDENT) {
				StudentEntity user = studRepo.findById(id).get();
				studRepo.deleteById(id);
				logger.info("Deleted Student account with ID " + user.getId());
				return new ResponseEntity<> (user, HttpStatus.OK);
			}
			if (role == UserRole.UserRole_PARENT) {
				ParentEntity user = parentRepo.findById(id).get();
				parentRepo.deleteById(id);
				logger.info("Deleted Parent account with ID " + user.getId());
				return new ResponseEntity<> (user, HttpStatus.OK);
			}
		}
		logger.info("Attempted to delete a non-existant account");
		return new ResponseEntity<> ("Error: User does not exist", HttpStatus.BAD_REQUEST);
		
	}

	@Override
	public ResponseEntity<?> changeUser(Integer id, ChangeUserDTO newInfo) {
		if(userRepo.existsById(id)) {
			UserRole role = userRepo.findById(id).get().getRole();
			if (role == UserRole.UserRole_ADMIN) {
				UserEntity user = userRepo.findById(id).get();
				if (newInfo.getUsername()!=null) {
					if ((userRepo.existsByUsername(newInfo.getUsername()))&&(!newInfo.getUsername().equals(user.getUsername()))) {
						logger.info("Attempted to alter an Admin account, but username was already taken.");
						return new ResponseEntity<> ("Error: Username not available", HttpStatus.BAD_GATEWAY);
					}
					else
						user.setUsername(newInfo.getUsername());
				}
				logger.warn("Altered an Admin account, please check the information");
				return new ResponseEntity<> (userRepo.save(user), HttpStatus.OK);
			}
			if (role == UserRole.UserRole_TEACHER) {
				TeacherEntity user = teacherRepo.findById(id).get();
				if (newInfo.getUsername()!=null) {
					if ((userRepo.existsByUsername(newInfo.getUsername()))&&(!newInfo.getUsername().equals(user.getUsername()))) {
						logger.info("Attempted to alter a Teacher account, but username was already taken.");
						return new ResponseEntity<> ("Error: Username not available", HttpStatus.BAD_GATEWAY);
					}
					else
						user.setUsername(newInfo.getUsername());
				}
				if(newInfo.getName()!=null)
					user.setName(newInfo.getName());
				if(newInfo.getSurname()!=null)
					user.setSurname(newInfo.getSurname());
				logger.warn("Altered a Teacher account, please check the information");
				return new ResponseEntity<> (teacherRepo.save(user), HttpStatus.OK);
			}
			if (role == UserRole.UserRole_STUDENT) {
				StudentEntity user = studRepo.findById(id).get();
				if (newInfo.getUsername()!=null) {
					if ((userRepo.existsByUsername(newInfo.getUsername()))&&(!newInfo.getUsername().equals(user.getUsername()))) {
						logger.info("Attempted to alter a Student account, but username was already taken.");
						return new ResponseEntity<> ("Error: Username not available", HttpStatus.BAD_GATEWAY);
					}
					else
						user.setUsername(newInfo.getUsername());
				}
				if(newInfo.getName()!=null)
					user.setName(newInfo.getName());
				if(newInfo.getSurname()!=null)
					user.setSurname(newInfo.getSurname());
				logger.warn("Altered a Student account, please check the information");
				return new ResponseEntity<> (studRepo.save(user), HttpStatus.OK);
			}
			if (role == UserRole.UserRole_PARENT) {
				ParentEntity user = parentRepo.findById(id).get();
				if (newInfo.getUsername()!=null) {
					if ((userRepo.existsByUsername(newInfo.getUsername()))&&(!newInfo.getUsername().equals(user.getUsername()))) {
						logger.info("Attempted to alter a Parent account, but username was already taken.");
						return new ResponseEntity<> ("Error: Username not available", HttpStatus.BAD_GATEWAY);
					}
					else
						user.setUsername(newInfo.getUsername());
				}
				if(newInfo.getName()!=null)
					user.setName(newInfo.getName());
				if(newInfo.getSurname()!=null)
					user.setSurname(newInfo.getSurname());
				if(newInfo.getEmail()!=null)
					user.setEmail(newInfo.getEmail());
				logger.warn("Altered a Parent account, please check the information");
				return new ResponseEntity<> (parentRepo.save(user), HttpStatus.OK);
			}
		}
		return new ResponseEntity<> ("Error: User does not exist", HttpStatus.BAD_REQUEST);
	}

	@Override
	public ResponseEntity<?> changePassword(Integer id, PasswordDTO password) {
		if(userRepo.existsById(id)) {
			UserEntity user = userRepo.findById(id).get();
			if (user.getPassword().equals(password.getOldPass())) {
				user.setPassword(password.getNewPass());
				logger.info("User " + id + " changed their password");
				return new ResponseEntity<> (userRepo.save(user), HttpStatus.OK);
			}
		}
		logger.info("Attempted to change password, but a mismatch happened");
		return new ResponseEntity<> ("Error: Wrong username or password", HttpStatus.BAD_REQUEST);
	}

	@Override
	public ResponseEntity<?> addParent(Integer studId, Integer parId) {
		if (!studRepo.existsById(studId)) {
			logger.info("Attempted to add parent to non-existant student");
			return new ResponseEntity<> ("Error: Wrong ID for the student", HttpStatus.BAD_REQUEST);
		}
		if (!parentRepo.existsById(parId)) {
			logger.info("Attempted to add a non-existant parent to a student");
			return new ResponseEntity<> ("Error: Wrong ID for the parent", HttpStatus.BAD_REQUEST);
		}
		StudentEntity student = studRepo.findById(studId).get();
		student.setParent(parentRepo.findById(parId).get());
		logger.info("Added parent " + parId + " to student " + studId);
		return new ResponseEntity<> (studRepo.save(student), HttpStatus.OK);
	}

}
