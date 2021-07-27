package com.iktpreobuka.el_ucionica_AS.controllers.ResponseDTOs;

public class GradeDTO {

	private Integer gradeId;
	private Integer grade;		// 1 to 5
	private Integer year;		// 1 to 8
	private Integer semester; 	// false = first, true = second
	
	private Integer subjectId;
	private String subjectName;
	
	private Integer teacherId;
	private String teacherName;
	
	private Integer studentId;
	private String studentName;
	
	
	public GradeDTO() {
		super();
	}
	
	public Integer getGradeId() {
		return gradeId;
	}
	public void setGradeId(Integer gradeId) {
		this.gradeId = gradeId;
	}
	public Integer getGrade() {
		return grade;
	}
	public void setGrade(Integer grade) {
		this.grade = grade;
	}
	public Integer getYear() {
		return year;
	}
	public void setYear(Integer year) {
		this.year = year;
	}
	public Integer getSemester() {
		return semester;
	}
	public void setSemester(Integer semester) {
		this.semester = semester;
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

	public GradeDTO(Integer gradeId, Integer grade, Integer year, Integer semester, Integer subjectId,
			String subjectName, Integer teacherId, String teacherName, Integer studentId, String studentName) {
		super();
		this.gradeId = gradeId;
		this.grade = grade;
		this.year = year;
		this.semester = semester;
		this.subjectId = subjectId;
		this.subjectName = subjectName;
		this.teacherId = teacherId;
		this.teacherName = teacherName;
		this.studentId = studentId;
		this.studentName = studentName;
	}
	
}
