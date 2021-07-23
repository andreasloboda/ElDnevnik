package com.iktpreobuka.el_ucionica_AS.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.iktpreobuka.el_ucionica_AS.entities.enums.Schoolyear;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class SubjectEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private String name;
	private Integer hours;
	private Schoolyear year;
	
	@OneToMany (cascade = CascadeType.REFRESH, fetch = FetchType.LAZY, mappedBy = "subject")
	@JsonIgnore
	private List<TeachSubjEntity> teachers;

	public SubjectEntity() {
		super();
	}

	public SubjectEntity(Integer id, String name, Integer hours, Schoolyear year, List<TeachSubjEntity> teachers) {
		super();
		this.id = id;
		this.name = name;
		this.hours = hours;
		this.year = year;
		this.teachers = teachers;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public Schoolyear getYear() {
		return year;
	}

	public void setYear(Schoolyear year) {
		this.year = year;
	}

	public List<TeachSubjEntity> getTeachers() {
		return teachers;
	}

	public void setTeachers(List<TeachSubjEntity> teachers) {
		this.teachers = teachers;
	}
	
}
