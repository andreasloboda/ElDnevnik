package com.iktpreobuka.el_ucionica_AS.controllers.ResponseDTOs;

public class AdminDTO {

	private Integer id;
	private String username;
	public AdminDTO() {
		super();
	}
	public AdminDTO(Integer id, String username) {
		super();
		this.id = id;
		this.username = username;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
}
