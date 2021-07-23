package com.iktpreobuka.el_ucionica_AS.controllers.DTOs;

public class NewParentDTO extends NewTeachStudDTO{

	private String email;

	public NewParentDTO() {
		super();
	}

	public NewParentDTO(String username, String password, String confirmPassword, String name, String surname,
			String email) {
		super(username, password, confirmPassword, name, surname);
		this.email = email;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
}
