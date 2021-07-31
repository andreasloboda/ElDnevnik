package com.iktpreobuka.el_ucionica_AS.controllers.ResponseDTOs;

public class TsDTO {
	private Integer subjectId;
	private String subjectName;
	
	private Integer teacherId;
	private String teacherName;
	public TsDTO() {
		
		super();
	}
	public TsDTO(Integer subjectId, String subjectName, Integer teacherId, String teacherName) {
		super();
		this.subjectId = subjectId;
		this.subjectName = subjectName;
		this.teacherId = teacherId;
		this.teacherName = teacherName;
	}
	public Integer getSubjectId() {
		return subjectId;
	}
	public void setSubjectId(Integer subjectId) {
		this.subjectId = subjectId;
	}
	public String getSubjectName() {
		return subjectName;
	}
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	public Integer getTeacherId() {
		return teacherId;
	}
	public void setTeacherId(Integer teacherId) {
		this.teacherId = teacherId;
	}
	public String getTeacherName() {
		return teacherName;
	}
	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}
}
