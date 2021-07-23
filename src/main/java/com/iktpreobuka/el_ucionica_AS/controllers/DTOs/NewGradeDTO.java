package com.iktpreobuka.el_ucionica_AS.controllers.DTOs;

public class NewGradeDTO {

	private Integer studentID;
	private Integer teacherID;
	private Integer subjectID;
	//TODO 1-5
	private Integer grade;
	public NewGradeDTO() {
		super();
		// TODO Auto-generated constructor stub
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
