package com.iktpreobuka.el_ucionica_AS.controllers.DTOs;

public class NewAdminDTO {

	private String username;
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
