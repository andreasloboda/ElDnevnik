package com.iktpreobuka.el_ucionica_AS.controllers.DTOs;

public class ChangeGroupDTO {

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
