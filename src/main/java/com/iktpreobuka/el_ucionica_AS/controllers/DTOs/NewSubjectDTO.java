package com.iktpreobuka.el_ucionica_AS.controllers.DTOs;

public class NewSubjectDTO {

	private String name;
	private Integer hours;
	private Integer year;
	
	
	public NewSubjectDTO() {
		super();
	}
	public NewSubjectDTO(String name, Integer hours, Integer year) {
		super();
		this.name = name;
		this.hours = hours;
		this.year = year;
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
	public Integer getYear() {
		return year;
	}
	public void setYear(Integer year) {
		this.year = year;
	}
}
