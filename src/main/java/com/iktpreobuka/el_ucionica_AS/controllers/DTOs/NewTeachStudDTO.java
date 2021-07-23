package com.iktpreobuka.el_ucionica_AS.controllers.DTOs;

public class NewTeachStudDTO extends NewAdminDTO{

	private String name;
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
