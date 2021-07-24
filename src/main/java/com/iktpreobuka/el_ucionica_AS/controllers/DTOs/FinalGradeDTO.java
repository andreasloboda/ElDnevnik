package com.iktpreobuka.el_ucionica_AS.controllers.DTOs;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class FinalGradeDTO {

	@JsonIgnore
	private Integer subjectId;
	@JsonIgnore
	private Integer gradesNumber;
	@JsonIgnore
	private Integer gradeSum;

	private String studentName;
	private String studentSurname;
	private String subjectName;
	private Integer year;
	private Integer average;

	public FinalGradeDTO() {
		super();
	}

	public FinalGradeDTO(Integer subjectId, String studentName, String studentSurname, String subjectName, Integer year,
			Integer average) {
		super();
		this.subjectId = subjectId;
		this.studentName = studentName;
		this.studentSurname = studentSurname;
		this.subjectName = subjectName;
		this.year = year;
		this.average = average;
	}

	public Integer getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(Integer subjectId) {
		this.subjectId = subjectId;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public String getStudentSurname() {
		return studentSurname;
	}

	public void setStudentSurname(String studentSurname) {
		this.studentSurname = studentSurname;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Integer getAverage() {
		return average;
	}

	public void setAverage(Integer average) {
		this.average = average;
	}

	public Integer getGradesNumber() {
		return gradesNumber;
	}

	public void setGradesNumber(Integer gradesNumber) {
		this.gradesNumber = gradesNumber;
	}

	public Integer getGradeSum() {
		return gradeSum;
	}

	public void setGradeSum(Integer gradeSum) {
		this.gradeSum = gradeSum;
	}
}
