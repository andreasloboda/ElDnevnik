package com.iktpreobuka.el_ucionica_AS.controllers.ResponseDTOs;

public class ParentDTO extends TeacherDTO{

	private String email;

	
	public ParentDTO() {
		super();
	}

	public ParentDTO(Integer id, String username, String name, String email) {
		super(id, username, name);
		this.email = email;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
}
