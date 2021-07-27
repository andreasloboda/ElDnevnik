package com.iktpreobuka.el_ucionica_AS.controllers.RequestDTOs;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

public class ChangeGroupDTO {

	@Min(value = 1, message = "Year must be a value between 1 and 8")
	@Max(value = 8, message = "Year must be a value between 1 and 8")
	private Integer year;
	private Integer studgroup;
	
	public ChangeGroupDTO() {
		super();
	}
	public ChangeGroupDTO(Integer year, Integer studgroup) {
		super();
		this.year = year;
		this.studgroup = studgroup;
	}
	public Integer getYear() {
		return year;
	}
	public void setYear(Integer year) {
		this.year = year;
	}
	public Integer getStudgroup() {
		return studgroup;
	}
	public void setStudgroup(Integer studgroup) {
		this.studgroup = studgroup;
	}
}
