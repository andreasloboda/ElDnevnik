package com.iktpreobuka.el_ucionica_AS.controllers.ResponseDTOs;

import java.util.List;

public class GpaDTO {

	private String studentName;
	private String studentSurname;
	private Integer studentYear;
	
	private Float[] gpa;
	
	private List<FinalGradeDTO> FinalGrades;

	public GpaDTO() {
		super();
	}

	public GpaDTO(String studentName, String studentSurname, Integer studentYear, Float[] gpa,
			List<FinalGradeDTO> finalGrades) {
		super();
		this.studentName = studentName;
		this.studentSurname = studentSurname;
		this.studentYear = studentYear;
		this.gpa = gpa;
		FinalGrades = finalGrades;
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

	public Integer getStudentYear() {
		return studentYear;
	}

	public void setStudentYear(Integer studentYear) {
		this.studentYear = studentYear;
	}

	public Float[] getGpa() {
		return gpa;
	}

	public void setGpa(Float[] gpa) {
		this.gpa = gpa;
	}

	public List<FinalGradeDTO> getFinalGrades() {
		return FinalGrades;
	}

	public void setFinalGrades(List<FinalGradeDTO> finalGrades) {
		FinalGrades = finalGrades;
	}
	
	
}
