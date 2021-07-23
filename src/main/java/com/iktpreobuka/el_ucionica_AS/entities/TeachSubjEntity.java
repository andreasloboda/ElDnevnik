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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class TeachSubjEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@ManyToOne (fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	@JoinColumn(name = "teacher")
	private TeacherEntity teacher;
	@ManyToOne (fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	@JoinColumn(name = "subject")
	private SubjectEntity subject;
	
	@OneToMany (fetch = FetchType.LAZY, cascade = CascadeType.REFRESH, mappedBy = "ts")
	@JsonIgnore
	private List<SutestEntity> students;

	public TeachSubjEntity() {
		super();
	}

	public TeachSubjEntity(Integer id, TeacherEntity teacher, SubjectEntity subject, List<SutestEntity> students) {
		super();
		this.id = id;
		this.teacher = teacher;
		this.subject = subject;
		this.students = students;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public TeacherEntity getTeacher() {
		return teacher;
	}

	public void setTeacher(TeacherEntity teacher) {
		this.teacher = teacher;
	}

	public SubjectEntity getSubject() {
		return subject;
	}

	public void setSubject(SubjectEntity subject) {
		this.subject = subject;
	}

	public List<SutestEntity> getStudents() {
		return students;
	}

	public void setStudents(List<SutestEntity> students) {
		this.students = students;
	}
}
