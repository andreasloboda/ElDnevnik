package com.iktpreobuka.el_ucionica_AS.controllers.ResponseDTOs;

public class StsDTO extends TsDTO{

	private Integer studentId;
	private String studentName;
	
	
	public StsDTO() {
		super();
	}
	public StsDTO(Integer subjectId, String subjectName, Integer teacherId, String teacherName, Integer studentId,
			String studentName) {
		super(subjectId, subjectName, teacherId, teacherName);
		this.studentId = studentId;
		this.studentName = studentName;
	}
	public Integer getStudentId() {
		return studentId;
	}
	public void setStudentId(Integer studentId) {
		this.studentId = studentId;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
}
