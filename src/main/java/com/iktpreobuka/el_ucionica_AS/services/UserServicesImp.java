package com.iktpreobuka.el_ucionica_AS.services;

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
		//TODO move password confirmation to validation level
		if (newAdmin.getPassword().equals(newAdmin.getConfirmPassword())) {
			if (userRepo.existsByUsername(newAdmin.getUsername()))
				return new ResponseEntity<> ("Error: Username already taken", HttpStatus.BAD_REQUEST);
			UserEntity user = new UserEntity();
			user.setPassword(newAdmin.getPassword());
			user.setRole(UserRole.UserRole_ADMIN);
			user.setUsername(newAdmin.getUsername());
			return new ResponseEntity<> (userRepo.save(user), HttpStatus.CREATED);
		}
		return new ResponseEntity<> ("Error: Passwords do not match!", HttpStatus.BAD_REQUEST);
	}

	@Override
	public ResponseEntity<?> makeNewTeachStud(NewTeachStudDTO user, UserRole role) {
		//TODO move password confirmation to validation level
		if(user.getPassword().equals(user.getConfirmPassword())) {
			if (userRepo.existsByUsername(user.getUsername()))
				return new ResponseEntity<> ("Error: Username already taken", HttpStatus.BAD_REQUEST);
			if (role == UserRole.UserRole_STUDENT) {
				StudentEntity newUser = new StudentEntity();
				newUser.setName(user.getName());
				newUser.setPassword(user.getPassword());
				newUser.setRole(UserRole.UserRole_STUDENT);
				newUser.setSurname(user.getSurname());
				newUser.setUsername(user.getUsername());
				return new ResponseEntity<> (studRepo.save(newUser), HttpStatus.OK);
			}
			if (role == UserRole.UserRole_TEACHER) {
				TeacherEntity newUser = new TeacherEntity();
				newUser.setName(user.getName());
				newUser.setPassword(user.getPassword());
				newUser.setRole(UserRole.UserRole_TEACHER);
				newUser.setSurname(user.getSurname());
				newUser.setUsername(user.getUsername());
				return new ResponseEntity<> (teacherRepo.save(newUser), HttpStatus.OK);
			}
			return new ResponseEntity<> ("Error: Wrong Role", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<> ("Error: Passwords do not match!", HttpStatus.BAD_REQUEST);
	}

	@Override
	public ResponseEntity<?> makeNewParent(NewParentDTO user) {
		//TODO move password confirmation to validation level
		if(user.getPassword().equals(user.getConfirmPassword())) {
			if (userRepo.existsByUsername(user.getUsername()))
				return new ResponseEntity<> ("Error: Username already taken", HttpStatus.BAD_REQUEST);
			ParentEntity newUser = new ParentEntity();
			newUser.setName(user.getName());
			newUser.setPassword(user.getPassword());
			newUser.setRole(UserRole.UserRole_PARENT);
			newUser.setSurname(user.getSurname());
			newUser.setUsername(user.getUsername());
			newUser.setEmail(user.getEmail());
			return new ResponseEntity<> (parentRepo.save(newUser), HttpStatus.OK);
		}
		return new ResponseEntity<> ("Error: Passwords do not match!", HttpStatus.BAD_REQUEST);
	}

	@Override
	public ResponseEntity<?> deleteUser(Integer id) {
		if (userRepo.existsById(id)) {
			UserRole role = userRepo.findById(id).get().getRole();
			if (role == UserRole.UserRole_ADMIN) {
				UserEntity user = userRepo.findById(id).get();
				userRepo.deleteById(id);
				return new ResponseEntity<> (user, HttpStatus.OK);
			}
			if (role == UserRole.UserRole_TEACHER) {
				TeacherEntity user = teacherRepo.findById(id).get();
				teacherRepo.deleteById(id);
				return new ResponseEntity<> (user, HttpStatus.OK);
			}
			if (role == UserRole.UserRole_STUDENT) {
				StudentEntity user = studRepo.findById(id).get();
				studRepo.deleteById(id);
				return new ResponseEntity<> (user, HttpStatus.OK);
			}
			if (role == UserRole.UserRole_PARENT) {
				ParentEntity user = parentRepo.findById(id).get();
				parentRepo.deleteById(id);
				return new ResponseEntity<> (user, HttpStatus.OK);
			}
		}
		return new ResponseEntity<> ("Error: User does not exist", HttpStatus.BAD_REQUEST);
		
	}

	@Override
	public ResponseEntity<?> changeUser(Integer id, ChangeUserDTO newInfo) {
		if (newInfo == null)
			return new ResponseEntity<> ("Error: No new information provided", HttpStatus.BAD_REQUEST);
		if(userRepo.existsById(id)) {
			UserRole role = userRepo.findById(id).get().getRole();
			if (role == UserRole.UserRole_ADMIN) {
				UserEntity user = userRepo.findById(id).get();
				if (newInfo.getUsername()!=null) {
					if ((userRepo.existsByUsername(newInfo.getUsername()))&&(!newInfo.getUsername().equals(user.getUsername()))) {
						return new ResponseEntity<> ("Error: Username not available", HttpStatus.BAD_GATEWAY);
					}
					else
						user.setUsername(newInfo.getUsername());
				}
				return new ResponseEntity<> (userRepo.save(user), HttpStatus.OK);
			}
			if (role == UserRole.UserRole_TEACHER) {
				TeacherEntity user = teacherRepo.findById(id).get();
				if (newInfo.getUsername()!=null) {
					if ((userRepo.existsByUsername(newInfo.getUsername()))&&(!newInfo.getUsername().equals(user.getUsername()))) {
						return new ResponseEntity<> ("Error: Username not available", HttpStatus.BAD_GATEWAY);
					}
					else
						user.setUsername(newInfo.getUsername());
				}
				if(newInfo.getName()!=null)
					user.setName(newInfo.getName());
				if(newInfo.getSurname()!=null)
					user.setSurname(newInfo.getSurname());
				return new ResponseEntity<> (teacherRepo.save(user), HttpStatus.OK);
			}
			if (role == UserRole.UserRole_STUDENT) {
				StudentEntity user = studRepo.findById(id).get();
				if (newInfo.getUsername()!=null) {
					if ((userRepo.existsByUsername(newInfo.getUsername()))&&(!newInfo.getUsername().equals(user.getUsername()))) {
						return new ResponseEntity<> ("Error: Username not available", HttpStatus.BAD_GATEWAY);
					}
					else
						user.setUsername(newInfo.getUsername());
				}
				if(newInfo.getName()!=null)
					user.setName(newInfo.getName());
				if(newInfo.getSurname()!=null)
					user.setSurname(newInfo.getSurname());
				return new ResponseEntity<> (studRepo.save(user), HttpStatus.OK);
			}
			if (role == UserRole.UserRole_PARENT) {
				ParentEntity user = parentRepo.findById(id).get();
				if (newInfo.getUsername()!=null) {
					if ((userRepo.existsByUsername(newInfo.getUsername()))&&(!newInfo.getUsername().equals(user.getUsername()))) {
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
				return new ResponseEntity<> (parentRepo.save(user), HttpStatus.OK);
			}
		}
		return new ResponseEntity<> ("Error: User does not exist", HttpStatus.BAD_REQUEST);
	}

	@Override
	public ResponseEntity<?> changePassword(Integer id, PasswordDTO password) {
		//TODO Passwords through validation
		if (!password.getNewPass().equals(password.getConfirm()))
			return new ResponseEntity<> ("Error: Passwords do not match", HttpStatus.BAD_REQUEST);
		if(userRepo.existsById(id)) {
			UserEntity user = userRepo.findById(id).get();
			if (user.getPassword().equals(password.getOldPass())) {
				user.setPassword(password.getNewPass());
				return new ResponseEntity<> (userRepo.save(user), HttpStatus.OK);
			}
		}
		return new ResponseEntity<> ("Error: Password does not match the user", HttpStatus.BAD_REQUEST);
	}

	@Override
	public ResponseEntity<?> addParent(Integer studId, Integer parId) {
		if (!studRepo.existsById(studId))
			return new ResponseEntity<> ("Error: Wrong ID for the student", HttpStatus.BAD_REQUEST);
		if (!parentRepo.existsById(parId))
			return new ResponseEntity<> ("Error: Wrong ID for the parent", HttpStatus.BAD_REQUEST);
		StudentEntity student = studRepo.findById(studId).get();
		student.setParent(parentRepo.findById(parId).get());
		return new ResponseEntity<> (studRepo.save(student), HttpStatus.OK);
	}

}
