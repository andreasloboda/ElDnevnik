package com.iktpreobuka.el_ucionica_AS.controllers.ResponseDTOs;

import java.util.List;

public class UsersDTO {

	List<AdminDTO> admins;
	List<TeacherDTO> teachers;
	List<StudentDTO> students;
	List<ParentDTO> parents;
	public UsersDTO() {
		super();
	}
	public UsersDTO(List<AdminDTO> admins, List<TeacherDTO> teachers, List<StudentDTO> students,
			List<ParentDTO> parents) {
		super();
		this.admins = admins;
		this.teachers = teachers;
		this.students = students;
		this.parents = parents;
	}
	public List<AdminDTO> getAdmins() {
		return admins;
	}
	public void setAdmins(List<AdminDTO> admins) {
		this.admins = admins;
	}
	public List<TeacherDTO> getTeachers() {
		return teachers;
	}
	public void setTeachers(List<TeacherDTO> teachers) {
		this.teachers = teachers;
	}
	public List<StudentDTO> getStudents() {
		return students;
	}
	public void setStudents(List<StudentDTO> students) {
		this.students = students;
	}
	public List<ParentDTO> getParents() {
		return parents;
	}
	public void setParents(List<ParentDTO> parents) {
		this.parents = parents;
	}
	
}
