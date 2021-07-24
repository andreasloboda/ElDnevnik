package com.iktpreobuka.el_ucionica_AS.controllers.DTOs;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class NewTeachStudDTO extends NewAdminDTO{

	@NotBlank(message = "Name must be provided")
	@Size(min = 2, max = 15, message = "Name must be between 2 and 15 letters long")
	//TODO Only letters
	private String name;
	@NotBlank(message = "Surame must be provided")
	@Size(min = 2, max = 15, message = "Surame must be between 2 and 15 letters long")
	//TODO Only letters
	private String surname;
	
	
	public NewTeachStudDTO() {
		super();
	}
	public NewTeachStudDTO(String username, String password, String confirmPassword, String name, String surname) {
		super(username, password, confirmPassword);
		this.name = name;
		this.surname = surname;
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
	
	
}
