package com.iktpreobuka.el_ucionica_AS.controllers.ResponseDTOs;

public class GroupDTO {

	private Integer id;
	private String mark;
	public GroupDTO() {
		super();
	}
	public GroupDTO(Integer id, String mark) {
		super();
		this.id = id;
		this.mark = mark;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getMark() {
		return mark;
	}
	public void setMark(String mark) {
		this.mark = mark;
	}
}
