package com.iktpreobuka.el_ucionica_AS.controllers.DTOs;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

public class ChangeUserDTO {

	@Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters long")
	//TODO only letters and numbers
	private String username;
	@Size(min = 2, max = 15, message = "Name must be between 2 and 15 letters long")
	//TODO Only letters
	private String name;
	@Size(min = 2, max = 15, message = "Surame must be between 2 and 15 letters long")
	//TODO Only letters
	private String surname;
	@Email (message = "Value provided is not an email")
	private String email;
	public ChangeUserDTO() {
		super();
	}
	public ChangeUserDTO(String username, String name, String surname, String email) {
		super();
		this.username = username;
		this.name = name;
		this.surname = surname;
		this.email = email;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
}
