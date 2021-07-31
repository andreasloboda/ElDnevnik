package com.iktpreobuka.el_ucionica_AS.services;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.iktpreobuka.el_ucionica_AS.controllers.RequestDTOs.ChangeUserDTO;
import com.iktpreobuka.el_ucionica_AS.controllers.RequestDTOs.NewAdminDTO;
import com.iktpreobuka.el_ucionica_AS.controllers.RequestDTOs.NewParentDTO;
import com.iktpreobuka.el_ucionica_AS.controllers.RequestDTOs.NewTeachStudDTO;
import com.iktpreobuka.el_ucionica_AS.controllers.RequestDTOs.PasswordDTO;
import com.iktpreobuka.el_ucionica_AS.controllers.ResponseDTOs.AdminDTO;
import com.iktpreobuka.el_ucionica_AS.controllers.ResponseDTOs.ParentDTO;
import com.iktpreobuka.el_ucionica_AS.controllers.ResponseDTOs.StudentDTO;
import com.iktpreobuka.el_ucionica_AS.controllers.ResponseDTOs.TeacherDTO;
import com.iktpreobuka.el_ucionica_AS.controllers.ResponseDTOs.UsersDTO;
import com.iktpreobuka.el_ucionica_AS.controllers.util.Encryption;
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
	@Autowired
	private DtoServices dtos;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public ResponseEntity<?> getByRole(String role) {
		if (role.equalsIgnoreCase("admin")) {
			List<UserEntity> admins = userRepo.findAllByRole(UserRole.ROLE_ADMIN);
			if (admins.isEmpty())
				return new ResponseEntity<> ("No admins found", HttpStatus.NOT_FOUND);
			List<AdminDTO> response = new ArrayList<>();
			for (UserEntity user : admins)
				response.add(dtos.adminToDTO(user));
			return new ResponseEntity<> (response, HttpStatus.OK);
		}
		if (role.equalsIgnoreCase("teacher")) {
			List<TeacherEntity> teachers = (List<TeacherEntity>) teacherRepo.findAll();
			if (teachers.isEmpty())
				return new ResponseEntity<> ("No teachers found", HttpStatus.NOT_FOUND);
			List<TeacherDTO> response = new ArrayList<>();
			for (TeacherEntity user : teachers)
				response.add(dtos.teacherToDTO(user));
			return new ResponseEntity<> (response, HttpStatus.OK);
		}
		if (role.equalsIgnoreCase("student")) {
			List<StudentEntity> students = (List<StudentEntity>) studRepo.findAll();
			if (students.isEmpty())
				return new ResponseEntity<> ("No students found", HttpStatus.NOT_FOUND);
			List<StudentDTO> response = new ArrayList<>();
			for (StudentEntity user : students)
				response.add(dtos.studentToDTO(user));
			return new ResponseEntity<> (response, HttpStatus.OK);
		}
		if (role.equalsIgnoreCase("parent")) {
			List<ParentEntity> parents = (List<ParentEntity>) parentRepo.findAll();
			if (parents.isEmpty())
				return new ResponseEntity<> ("No parents found", HttpStatus.NOT_FOUND);
			List<ParentDTO> response = new ArrayList<>();
			for (ParentEntity user : parents)
				response.add(dtos.parentToDTO(user));
			return new ResponseEntity<> (response, HttpStatus.OK);
		}
		return new ResponseEntity<>("Error: invalid role", HttpStatus.BAD_REQUEST);
	}

	@Override
	public ResponseEntity<?> getByID(Integer id) {
		if (userRepo.existsById(id)) {
			UserRole type = userRepo.findById(id).get().getRole();
			if (type == UserRole.ROLE_ADMIN)
				return new ResponseEntity<> (dtos.adminToDTO(userRepo.findById(id).get()), HttpStatus.OK);
			if (type == UserRole.ROLE_PARENT)
				return new ResponseEntity<> (dtos.parentToDTO(parentRepo.findById(id).get()), HttpStatus.OK);
			if (type == UserRole.ROLE_STUDENT)
				return new ResponseEntity<> (dtos.studentToDTO(studRepo.findById(id).get()), HttpStatus.OK);
			if (type == UserRole.ROLE_TEACHER)
				return new ResponseEntity<> (dtos.teacherToDTO(teacherRepo.findById(id).get()), HttpStatus.OK);
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
		user.setPassword(Encryption.getPassEncoded(newAdmin.getPassword()));
		user.setRole(UserRole.ROLE_ADMIN);
		user.setUsername(newAdmin.getUsername());
		logger.info("New admin account created");
		return new ResponseEntity<>(dtos.adminToDTO(userRepo.save(user)), HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<?> makeNewTeachStud(NewTeachStudDTO user, UserRole role) {
		if (userRepo.existsByUsername(user.getUsername())) {
			if (role == UserRole.ROLE_STUDENT)
				logger.info("Attempted to make new Student account, but username was taken");
			if (role == UserRole.ROLE_TEACHER)
				logger.info("Attempted to make new Teacher account, but username was taken");
			return new ResponseEntity<>("Error: Username already taken", HttpStatus.BAD_REQUEST);
		}
		if (role == UserRole.ROLE_STUDENT) {
			StudentEntity newUser = new StudentEntity();
			newUser.setName(user.getName());
			newUser.setPassword(Encryption.getPassEncoded(user.getPassword()));
			newUser.setRole(UserRole.ROLE_STUDENT);
			newUser.setSurname(user.getSurname());
			newUser.setUsername(user.getUsername());
			logger.info("New student account created");
			return new ResponseEntity<>(dtos.studentToDTO(studRepo.save(newUser)), HttpStatus.OK);
		}
		if (role == UserRole.ROLE_TEACHER) {
			TeacherEntity newUser = new TeacherEntity();
			newUser.setName(user.getName());
			newUser.setPassword(Encryption.getPassEncoded(user.getPassword()));
			newUser.setRole(UserRole.ROLE_TEACHER);
			newUser.setSurname(user.getSurname());
			newUser.setUsername(user.getUsername());
			logger.info("New teacher account created");
			return new ResponseEntity<>(dtos.teacherToDTO(teacherRepo.save(newUser)), HttpStatus.OK);
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
		newUser.setPassword(Encryption.getPassEncoded(user.getPassword()));
		newUser.setRole(UserRole.ROLE_PARENT);
		newUser.setSurname(user.getSurname());
		newUser.setUsername(user.getUsername());
		newUser.setEmail(user.getEmail());
		logger.info("New parent account created");
		return new ResponseEntity<> (dtos.parentToDTO(parentRepo.save(newUser)), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> deleteUser(Integer id) {
		if (userRepo.existsById(id)) {
			UserRole role = userRepo.findById(id).get().getRole();
			if (role == UserRole.ROLE_ADMIN) {
				UserEntity user = userRepo.findById(id).get();
				userRepo.deleteById(id);
				logger.info("Deleted Admin account with ID " + user.getId());
				return new ResponseEntity<> (dtos.adminToDTO(user), HttpStatus.OK);
			}
			if (role == UserRole.ROLE_TEACHER) {
				TeacherEntity user = teacherRepo.findById(id).get();
				teacherRepo.deleteById(id);
				logger.info("Deleted Teacher account with ID " + user.getId());
				return new ResponseEntity<> (dtos.teacherToDTO(user), HttpStatus.OK);
			}
			if (role == UserRole.ROLE_STUDENT) {
				StudentEntity user = studRepo.findById(id).get();
				studRepo.deleteById(id);
				logger.info("Deleted Student account with ID " + user.getId());
				return new ResponseEntity<> (dtos.studentToDTO(user), HttpStatus.OK);
			}
			if (role == UserRole.ROLE_PARENT) {
				ParentEntity user = parentRepo.findById(id).get();
				parentRepo.deleteById(id);
				logger.info("Deleted Parent account with ID " + user.getId());
				return new ResponseEntity<> (dtos.parentToDTO(user), HttpStatus.OK);
			}
		}
		logger.info("Attempted to delete a non-existant account");
		return new ResponseEntity<> ("Error: User does not exist", HttpStatus.BAD_REQUEST);
		
	}

	@Override
	public ResponseEntity<?> changeUser(Integer id, ChangeUserDTO newInfo) {
		if(userRepo.existsById(id)) {
			UserRole role = userRepo.findById(id).get().getRole();
			if (role == UserRole.ROLE_ADMIN) {
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
				return new ResponseEntity<> (dtos.adminToDTO(userRepo.save(user)), HttpStatus.OK);
			}
			if (role == UserRole.ROLE_TEACHER) {
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
				return new ResponseEntity<> (dtos.teacherToDTO(teacherRepo.save(user)), HttpStatus.OK);
			}
			if (role == UserRole.ROLE_STUDENT) {
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
				return new ResponseEntity<> (dtos.studentToDTO(studRepo.save(user)), HttpStatus.OK);
			}
			if (role == UserRole.ROLE_PARENT) {
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
				return new ResponseEntity<> (dtos.parentToDTO(parentRepo.save(user)), HttpStatus.OK);
			}
		}
		return new ResponseEntity<> ("Error: User does not exist", HttpStatus.BAD_REQUEST);
	}

	@Override
	public ResponseEntity<?> changePassword(Integer id, PasswordDTO password) {
		if(userRepo.existsById(id)) {
			UserEntity user = userRepo.findById(id).get();
			if (Encryption.validatePassword(password.getOldPass(), user.getPassword())) {
				user.setPassword(Encryption.getPassEncoded(password.getNewPass()));
				logger.info("User " + id + " changed their password");
				return new ResponseEntity<> ("Password successfully altered", HttpStatus.OK);
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
		return new ResponseEntity<> (dtos.studentToDTO(studRepo.save(student)), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> getChildren(Integer id) {
		if (!parentRepo.existsById(id)) {
			return new ResponseEntity<> ("There is no parent with this id", HttpStatus.BAD_REQUEST);
		}
		if (!studRepo.existsByParentId(id)) {
			return new ResponseEntity<> ("There is no students associated with this parent", HttpStatus.BAD_REQUEST);
		}
		List<StudentEntity> children = studRepo.findAllByParentId(id);
		List<StudentDTO> result = new ArrayList<>();
		for (StudentEntity child : children)
			result.add(dtos.studentToDTO(child));
		return new ResponseEntity<> (result, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> getAll() {
		List<UserEntity> userList = (List<UserEntity>) userRepo.findAll();
		if (userList.isEmpty())
			return new ResponseEntity<> ("There are no users in database", HttpStatus.NOT_FOUND);
		List<AdminDTO> admins = new ArrayList<>();
		List<TeacherDTO> teachers = new ArrayList<>();
		List<StudentDTO> students = new ArrayList<>();
		List<ParentDTO> parents = new ArrayList<>();
		for (UserEntity user: userList) {
			if (user.getRole().equals(UserRole.ROLE_ADMIN))
				admins.add(dtos.adminToDTO(user));
			if (user.getRole().equals(UserRole.ROLE_TEACHER))
				teachers.add(dtos.teacherToDTO(teacherRepo.findById(user.getId()).get()));
			if (user.getRole().equals(UserRole.ROLE_STUDENT))
				students.add(dtos.studentToDTO(studRepo.findById(user.getId()).get()));
			if (user.getRole().equals(UserRole.ROLE_PARENT))
				parents.add(dtos.parentToDTO(parentRepo.findById(user.getId()).get()));
		}
		UsersDTO response = new UsersDTO();
		response.setAdmins(admins);
		response.setParents(parents);
		response.setStudents(students);
		response.setTeachers(teachers);
		return new ResponseEntity<> (response, HttpStatus.OK);
	}

}
