package com.iktpreobuka.el_ucionica_AS.controllers.ResponseDTOs;

public class TeacherDTO extends AdminDTO{

	private String name;

	public TeacherDTO() {
		super();
	}

	public TeacherDTO(Integer id, String username, String name) {
		super(id, username);
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
}
