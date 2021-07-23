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
public class StudgroupEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private Schoolyear year;
	private Integer studgroup;
	
	@OneToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY, mappedBy = "studgroup")
	@JsonIgnore
	private List<StudentEntity> students;

	public StudgroupEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public StudgroupEntity(Integer id, Schoolyear year, Integer studgroup, List<StudentEntity> students) {
		super();
		this.id = id;
		this.year = year;
		this.studgroup = studgroup;
		this.students = students;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Schoolyear getYear() {
		return year;
	}

	public void setYear(Schoolyear year) {
		this.year = year;
	}

	public Integer getStudgroup() {
		return studgroup;
	}

	public void setStudgroup(Integer studgroup) {
		this.studgroup = studgroup;
	}

	public List<StudentEntity> getStudents() {
		return students;
	}

	public void setStudents(List<StudentEntity> students) {
		this.students = students;
	}

}