package com.iktpreobuka.el_ucionica_AS.entities;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Version;


@Entity
public class GradeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	// TODO 1-5
	private Integer grade;
	private Integer year;
	// TODO false = first, true = second
	private Boolean semester;
	@Version
	private Integer version;
	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "info")
	private SutestEntity info;

	public GradeEntity() {
		super();
	}

	public GradeEntity(Integer id, Integer grade, Integer year, Boolean semester, SutestEntity info) {
		super();
		this.id = id;
		this.grade = grade;
		this.year = year;
		this.semester = semester;
		this.info = info;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getGrade() {
		return grade;
	}

	public void setGrade(Integer grade) {
		this.grade = grade;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Boolean getSemester() {
		return semester;
	}

	public void setSemester(Boolean semester) {
		this.semester = semester;
	}

	public SutestEntity getInfo() {
		return info;
	}

	public void setInfo(SutestEntity info) {
		this.info = info;
	}
}
