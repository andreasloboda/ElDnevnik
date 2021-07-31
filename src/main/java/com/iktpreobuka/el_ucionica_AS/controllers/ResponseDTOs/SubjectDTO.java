package com.iktpreobuka.el_ucionica_AS.controllers.ResponseDTOs;

public class SubjectDTO {

	private Integer id;
	private String name;
	private Integer hours;

	public SubjectDTO() {
		super();
	}

	public SubjectDTO(Integer id, String name, Integer hours) {
		super();
		this.id = id;
		this.name = name;
		this.hours = hours;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getHours() {
		return hours;
	}

	public void setHours(Integer hours) {
		this.hours = hours;
	}
}
