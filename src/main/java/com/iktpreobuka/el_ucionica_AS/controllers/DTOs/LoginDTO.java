package com.iktpreobuka.el_ucionica_AS.controllers.DTOs;

public class LoginDTO {

	private String user;
	private String token;
	
	
	public LoginDTO() {
		super();
	}


	public LoginDTO(String user, String token) {
		super();
		this.user = user;
		this.token = token;
	}


	public String getUser() {
		return user;
	}


	public void setUser(String user) {
		this.user = user;
	}


	public String getToken() {
		return token;
	}


	public void setToken(String token) {
		this.token = token;
	}
	
	
}
