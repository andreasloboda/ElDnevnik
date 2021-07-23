package com.iktpreobuka.el_ucionica_AS.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.iktpreobuka.el_ucionica_AS.entities.enums.UserRole;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class StudentEntity extends UserEntity {

	private String name;
	private String surname;
	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "parent")
	private ParentEntity parent;
	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "studgroup")
	private StudgroupEntity studgroup;
	
	@OneToMany (cascade = CascadeType.REFRESH, fetch = FetchType.LAZY, mappedBy = "stud")
	@JsonIgnore
	private List<SutestEntity> subjects;

	public StudentEntity() {
		super();
	}

	public StudentEntity(Integer id, String username, String password, UserRole role, String name, String surname,
			ParentEntity parent, StudgroupEntity studgroup, List<SutestEntity> subjects) {
		super(id, username, password, role);
		this.name = name;
		this.surname = surname;
		this.parent = parent;
		this.studgroup = studgroup;
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

	public ParentEntity getParent() {
		return parent;
	}

	public void setParent(ParentEntity parent) {
		this.parent = parent;
	}

	public StudgroupEntity getGroup() {
		return studgroup;
	}

	public void setGroup(StudgroupEntity group) {
		this.studgroup = group;
	}

	public List<SutestEntity> getSubjects() {
		return subjects;
	}

	public void setSubjects(List<SutestEntity> subjects) {
		this.subjects = subjects;
	}
	
	
}
