package com.iktpreobuka.el_ucionica_AS.services;

import org.springframework.http.ResponseEntity;

import com.iktpreobuka.el_ucionica_AS.controllers.DTOs.ChangeUserDTO;
import com.iktpreobuka.el_ucionica_AS.controllers.DTOs.NewAdminDTO;
import com.iktpreobuka.el_ucionica_AS.controllers.DTOs.NewParentDTO;
import com.iktpreobuka.el_ucionica_AS.controllers.DTOs.NewTeachStudDTO;
import com.iktpreobuka.el_ucionica_AS.controllers.DTOs.PasswordDTO;
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

}
