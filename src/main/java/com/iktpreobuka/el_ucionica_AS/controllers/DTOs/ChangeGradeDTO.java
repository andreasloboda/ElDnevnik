package com.iktpreobuka.el_ucionica_AS.controllers.DTOs;

public class ChangeGradeDTO {
	private Integer studentID;
	private Integer teacherID;
	private Integer subjectID;
	// 1-5
	private Integer grade;
	//1-2
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
