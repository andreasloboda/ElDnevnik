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
public class TeacherEntity extends UserEntity {

	private String name;
	private String surname;
	
	@OneToMany (fetch = FetchType.LAZY, cascade = CascadeType.REFRESH, mappedBy = "teacher")
	@JsonIgnore
	private List<TeachSubjEntity> subjects;

	public TeacherEntity() {
		super();
	}

	public TeacherEntity(Integer id, String username, String password, UserRole role, String name, String surname,
			List<TeachSubjEntity> subjects) {
		super(id, username, password, role);
		this.name = name;
		this.surname = surname;
		this.subjects = subjects;
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

	public List<TeachSubjEntity> getSubjects() {
		return subjects;
	}

	public void setSubjects(List<TeachSubjEntity> subjects) {
		this.subjects = subjects;
	}
	
}
