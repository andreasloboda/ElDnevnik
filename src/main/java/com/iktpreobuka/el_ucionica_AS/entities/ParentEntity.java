package com.iktpreobuka.el_ucionica_AS.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.iktpreobuka.el_ucionica_AS.entities.enums.UserRole;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ParentEntity extends UserEntity {

	private String name;
	private String surname;
	private String email;
	@OneToMany (cascade = CascadeType.REFRESH, fetch = FetchType.LAZY, mappedBy = "parent")
	@JsonIgnore
	private List<StudentEntity> children;
	
	public ParentEntity() {
		super();
	}
	public ParentEntity(Integer id, String username, String password, UserRole role, String name, String surname,
			String email, List<StudentEntity> children) {
		super(id, username, password, role);
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.children = children;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public List<StudentEntity> getChildren() {
		return children;
	}
	public void setChildren(List<StudentEntity> children) {
		this.children = children;
	}
}
