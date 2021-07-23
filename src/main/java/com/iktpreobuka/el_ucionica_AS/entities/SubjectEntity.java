package com.iktpreobuka.el_ucionica_AS.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Version;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class SubjectEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private String name;
	private Integer hours;
	private Integer year;
	
	@Version
	private Integer version;
	
	@OneToMany (cascade = CascadeType.REFRESH, fetch = FetchType.LAZY, mappedBy = "subject")
	@JsonIgnore
	private List<TeachSubjEntity> teachers;

	public SubjectEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SubjectEntity(Integer id, String name, Integer hours, Integer year, Integer version,
			List<TeachSubjEntity> teachers) {
		super();
		this.id = id;
		this.name = name;
		this.hours = hours;
		this.year = year;
		this.version = version;
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

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public List<TeachSubjEntity> getTeachers() {
		return teachers;
	}

	public void setTeachers(List<TeachSubjEntity> teachers) {
		this.teachers = teachers;
	}

		
}
