package com.iktpreobuka.el_ucionica_AS.controllers.RequestDTOs;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class NewAdminDTO {

	@NotBlank (message = "Username must be provided")
	@Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters long")
	@Pattern(regexp = "^([a-zA-Z0-9]*)$", message = "Username can only contain letters and numbers")
	private String username;
	@Size(min = 3, max = 20, message = "Password must be between 3 and 20 characters long")
	private String password;
	private String confirmPassword;
	
	public NewAdminDTO() {
		super();
	}
	public NewAdminDTO(String username, String password, String confirmPassword) {
		super();
		this.username = username;
		this.password = password;
		this.confirmPassword = confirmPassword;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getConfirmPassword() {
		return confirmPassword;
	}
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
}
