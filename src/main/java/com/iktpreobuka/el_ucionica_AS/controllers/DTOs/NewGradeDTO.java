package com.iktpreobuka.el_ucionica_AS.controllers.DTOs;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class NewGradeDTO {

	@NotNull (message = "Student ID must be provided")
	private Integer studentID;
	@NotNull (message = "Teacher ID must be provided")
	private Integer teacherID;
	@NotNull (message = "Subject ID must be provided")
	private Integer subjectID;
	@NotNull (message = "Grade must be provided")
	@Min(value = 1, message = "Grade must be between 1 and 5")
	@Max(value = 5, message = "Grade must be between 1 and 5")
	private Integer grade;
	
	public NewGradeDTO() {
		super();
	}
	public NewGradeDTO(Integer studentID, Integer teacherID, Integer subjectID, Integer grade) {
		super();
		this.studentID = studentID;
		this.teacherID = teacherID;
		this.subjectID = subjectID;
		this.grade = grade;
	}
	public Integer getStudentID() {
		return studentID;
	}
	public void setStudentID(Integer studentID) {
		this.studentID = studentID;
	}
	public Integer getTeacherID() {
		return teacherID;
	}
	public void setTeacherID(Integer teacherID) {
		this.teacherID = teacherID;
	}
	public Integer getSubjectID() {
		return subjectID;
	}
	public void setSubjectID(Integer subjectID) {
		this.subjectID = subjectID;
	}
	public Integer getGrade() {
		return grade;
	}
	public void setGrade(Integer grade) {
		this.grade = grade;
	}
	
	
}
