package com.iktpreobuka.el_ucionica_AS.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Version;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class SutestEntity {

	//subject - teacher - student

	@Id
	@JsonIgnore
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@ManyToOne (cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "stud")
	private StudentEntity stud;
	@ManyToOne (cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "ts")
	private TeachSubjEntity ts;
	@Version
	@JsonIgnore
	private Integer version;
	
	@OneToMany (cascade = CascadeType.REFRESH, fetch = FetchType.LAZY, mappedBy = "info")
	@JsonIgnore
	private List<GradeEntity> grades;

	public SutestEntity() {
		super();
	}

	public SutestEntity(StudentEntity stud, TeachSubjEntity ts, List<GradeEntity> grades) {
		super();
		this.stud = stud;
		this.ts = ts;
		this.grades = grades;
	}

	public StudentEntity getStud() {
		return stud;
	}

	public void setStud(StudentEntity stud) {
		this.stud = stud;
	}

	public TeachSubjEntity getTs() {
		return ts;
	}

	public void setTs(TeachSubjEntity ts) {
		this.ts = ts;
	}

	public List<GradeEntity> getGrades() {
		return grades;
	}

	public void setGrades(List<GradeEntity> grades) {
		this.grades = grades;
	}
}
