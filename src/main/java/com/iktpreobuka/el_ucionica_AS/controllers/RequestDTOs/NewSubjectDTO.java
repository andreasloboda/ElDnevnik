package com.iktpreobuka.el_ucionica_AS.controllers.RequestDTOs;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class NewSubjectDTO {

	@Size(min = 2, max = 20, message = "Name must be between 2 and 20 characters")
	@NotBlank (message = "Subject name must be provided")
	@Pattern(regexp = "^([a-zA-Z]*)$", message = "Name can only contain letters")
	private String name;
	@Min(value = 1, message = "Number of hours per week must be between 1 and 6")
	@Max(value = 6, message = "Number of hours per week must be between 1 and 6")
	@NotNull (message = "Number of hours per week must be provided")
	private Integer hours;
	@Min(value = 1, message = "Year must be a value between 1 and 8")
	@Max(value = 8, message = "Year must be a value between 1 and 8")
	@NotNull (message = "Year at which subject is taught must be provided")
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
