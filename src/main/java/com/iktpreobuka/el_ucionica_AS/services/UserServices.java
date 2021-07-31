package com.iktpreobuka.el_ucionica_AS.services;

import org.springframework.http.ResponseEntity;

import com.iktpreobuka.el_ucionica_AS.controllers.RequestDTOs.ChangeUserDTO;
import com.iktpreobuka.el_ucionica_AS.controllers.RequestDTOs.NewAdminDTO;
import com.iktpreobuka.el_ucionica_AS.controllers.RequestDTOs.NewParentDTO;
import com.iktpreobuka.el_ucionica_AS.controllers.RequestDTOs.NewTeachStudDTO;
import com.iktpreobuka.el_ucionica_AS.controllers.RequestDTOs.PasswordDTO;
import com.iktpreobuka.el_ucionica_AS.entities.enums.UserRole;

public interface UserServices {

	ResponseEntity<?> getByRole(String role);

	ResponseEntity<?> getByID(Integer id);

	ResponseEntity<?> makeNewAdmin(NewAdminDTO newAdmin);

	ResponseEntity<?> makeNewTeachStud(NewTeachStudDTO user, UserRole role);

	ResponseEntity<?> makeNewParent(NewParentDTO newParent);

	ResponseEntity<?> deleteUser(Integer id);

	ResponseEntity<?> changeUser(Integer id, ChangeUserDTO newInfo);

	ResponseEntity<?> changePassword(Integer id, PasswordDTO password);

	ResponseEntity<?> addParent(Integer studId, Integer parId);

	ResponseEntity<?> getChildren(Integer id);

	ResponseEntity<?> getAll();

	ResponseEntity<?> getStudentByName(String name);

	ResponseEntity<?> getTeacherByName(String name);

	ResponseEntity<?> getParentByName(String name);

	ResponseEntity<?> getAllByName(String name);

}
