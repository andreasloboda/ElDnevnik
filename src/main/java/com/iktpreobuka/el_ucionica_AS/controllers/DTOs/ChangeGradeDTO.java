package com.iktpreobuka.el_ucionica_AS.controllers.DTOs;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

public class ChangeGradeDTO {
	private Integer studentID;
	private Integer teacherID;
	private Integer subjectID;
	@Min(value = 1, message = "Grade must be between 1 and 5")
	@Max(value = 5, message = "Grade must be between 1 and 5")
	private Integer grade;
	@Min(value = 1, message = "Semester can have value 1 or 2")
	@Max(value = 2, message = "Semester can have value 1 or 2")
	private Integer semester;
	
	public ChangeGradeDTO() {
		super();
	}
	public ChangeGradeDTO(Integer studentID, Integer teacherID, Integer subjectID, Integer grade) {
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
	public Integer getSemester() {
		return semester;
	}
	public void setSemester(Integer semester) {
		this.semester = semester;
	}
	
	
}
