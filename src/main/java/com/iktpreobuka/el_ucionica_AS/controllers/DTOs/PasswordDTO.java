package com.iktpreobuka.el_ucionica_AS.controllers.DTOs;

import javax.validation.constraints.NotBlank;

public class PasswordDTO {

	//TODO Special validator
	
	@NotBlank (message = "Old password must be provided")
	private String oldPass;
	@NotBlank (message = "New password must be provided")
	private String newPass;
	@NotBlank (message = "You must confirm the password")
	private String confirm;
	
	public PasswordDTO() {
		super();
	}

	public PasswordDTO(String oldPass, String newPass, String confirm) {
		super();
		this.oldPass = oldPass;
		this.newPass = newPass;
		this.confirm = confirm;
	}

	public String getOldPass() {
		return oldPass;
	}

	public void setOldPass(String oldPass) {
		this.oldPass = oldPass;
	}

	public String getNewPass() {
		return newPass;
	}

	public void setNewPass(String newPass) {
		this.newPass = newPass;
	}

	public String getConfirm() {
		return confirm;
	}

	public void setConfirm(String confirm) {
		this.confirm = confirm;
	}
}
