package com.iktpreobuka.el_ucionica_AS.controllers.util;


import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.iktpreobuka.el_ucionica_AS.controllers.RequestDTOs.ChangeUserDTO;
import com.iktpreobuka.el_ucionica_AS.controllers.RequestDTOs.NewAdminDTO;
import com.iktpreobuka.el_ucionica_AS.controllers.RequestDTOs.PasswordDTO;

@Component
public class UsersValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		boolean yep = false;
		if(PasswordDTO.class.equals(clazz))
			yep = true;
		if (NewAdminDTO.class.isAssignableFrom(clazz))
			yep = true;
		if (ChangeUserDTO.class.equals(clazz))
			yep = true;
		return yep;
		
	}
	
	@Override
	public void validate(Object target, Errors errors) {
		if (target.getClass().equals(PasswordDTO.class)) {
			PasswordDTO password = (PasswordDTO) target;
			if (! password.getNewPass().equals(password.getConfirm())) {
				errors.reject("400", "Password and confirm password fields must match.");
			}
			if (password.getNewPass().matches("^([^0-9]*|[^A-Z]*|[^a-z]*|[a-zA-Z0-9]|[^\\s]*\\s.*)$")) {
				errors.reject("400", "Password must contain at least one uppercase letter, one lowercase letter, one number and cannot contain special characters");
			}
		}
		if (target.getClass().isAssignableFrom(NewAdminDTO.class)) {
			NewAdminDTO user = (NewAdminDTO) target;
			if (! user.getPassword().equals(user.getConfirmPassword())) {
				errors.reject("400", "Password and confirm password fields must match.");
			}
			if (user.getPassword().matches("^([^0-9]*|[^A-Z]*|[^a-z]*|[a-zA-Z0-9]|[^\\s]*\\s.*)$")) {
				errors.reject("400", "Password must contain at least one uppercase letter, one lowercase letter, one number and cannot contain special characters");
			}
		}

	}
}
