package com.iktpreobuka.el_ucionica_AS.services;

import org.springframework.stereotype.Service;

import com.iktpreobuka.el_ucionica_AS.controllers.ResponseDTOs.AdminDTO;
import com.iktpreobuka.el_ucionica_AS.controllers.ResponseDTOs.GradeDTO;
import com.iktpreobuka.el_ucionica_AS.controllers.ResponseDTOs.GroupDTO;
import com.iktpreobuka.el_ucionica_AS.controllers.ResponseDTOs.ParentDTO;
import com.iktpreobuka.el_ucionica_AS.controllers.ResponseDTOs.StsDTO;
import com.iktpreobuka.el_ucionica_AS.controllers.ResponseDTOs.StudentDTO;
import com.iktpreobuka.el_ucionica_AS.controllers.ResponseDTOs.SubjectDTO;
import com.iktpreobuka.el_ucionica_AS.controllers.ResponseDTOs.TeacherDTO;
import com.iktpreobuka.el_ucionica_AS.controllers.ResponseDTOs.TsDTO;
import com.iktpreobuka.el_ucionica_AS.entities.GradeEntity;
import com.iktpreobuka.el_ucionica_AS.entities.ParentEntity;
import com.iktpreobuka.el_ucionica_AS.entities.StudentEntity;
import com.iktpreobuka.el_ucionica_AS.entities.StudgroupEntity;
import com.iktpreobuka.el_ucionica_AS.entities.SubjectEntity;
import com.iktpreobuka.el_ucionica_AS.entities.SutestEntity;
import com.iktpreobuka.el_ucionica_AS.entities.TeachSubjEntity;
import com.iktpreobuka.el_ucionica_AS.entities.TeacherEntity;
import com.iktpreobuka.el_ucionica_AS.entities.UserEntity;

@Service
public class DtoServicesImp implements DtoServices{
	
	public GradeDTO gradeToDTO(GradeEntity grade) {
		GradeDTO dto = new GradeDTO();
		dto.setGrade(grade.getGrade());
		dto.setGradeId(grade.getId());
		dto.setYear(grade.getYear());
		if (grade.getSemester())
			dto.setSemester(2);
		else
			dto.setSemester(1);
		dto.setSubjectId(grade.getInfo().getTs().getSubject().getId());
		dto.setSubjectName(grade.getInfo().getTs().getSubject().getName());
		dto.setTeacherId(grade.getInfo().getTs().getTeacher().getId());
		dto.setTeacherName(grade.getInfo().getTs().getTeacher().getName() + " " + grade.getInfo().getTs().getTeacher().getSurname());
		dto.setStudentId(grade.getInfo().getStud().getId());
		dto.setStudentName(grade.getInfo().getStud().getName() + " " + grade.getInfo().getStud().getSurname());
		return dto;
	}
	
	public GroupDTO groupToDTO (StudgroupEntity group) {
		GroupDTO dto = new GroupDTO();
		dto.setId(group.getId());
		dto.setMark(group.getYear() + "-" + group.getStudgroup());
		return dto;
	}
	
	public AdminDTO adminToDTO(UserEntity admin) {
		AdminDTO dto = new AdminDTO();
		dto.setId(admin.getId());
		dto.setUsername(admin.getUsername());
		return dto;
	}
	
	public TeacherDTO teacherToDTO(TeacherEntity teacher) {
		TeacherDTO dto = new TeacherDTO();
		dto.setId(teacher.getId());
		dto.setUsername(teacher.getUsername());
		dto.setName(teacher.getName() + " " + teacher.getSurname());
		return dto;
	}
	
	public StudentDTO studentToDTO(StudentEntity student) {
		StudentDTO dto = new StudentDTO();
		dto.setId(student.getId());
		dto.setUsername(student.getUsername());
		dto.setName(student.getName() + " " + student.getSurname());
		if (student.getParent()!=null) {
			dto.setParentId(student.getParent().getId());
			dto.setParentName(student.getParent().getName() + " " + student.getParent().getName());
		}
		else {
			dto.setParentId(0);
			dto.setParentName("Unknown");
		}
		if (student.getStudgroup()!= null) {
			dto.setGroup(student.getStudgroup().getYear() + "-" + student.getStudgroup().getStudgroup());
			dto.setGroupId(student.getStudgroup().getId());
		}
		else {
			dto.setGroup("Unassigned");
			dto.setGroupId(0);
		}
		return dto;
	}
	
	public ParentDTO parentToDTO(ParentEntity parent) {
		ParentDTO dto = new ParentDTO();
		dto.setId(parent.getId());
		dto.setUsername(parent.getUsername());
		dto.setName(parent.getName() + " " + parent.getSurname());
		dto.setEmail(parent.getEmail());
		return dto;
	}

	@Override
	public SubjectDTO subjectToDTO(SubjectEntity subject) {
		SubjectDTO dto = new SubjectDTO();
		dto.setHours(subject.getHours());
		dto.setId(subject.getId());
		dto.setName(subject.getName() + " " + subject.getYear());
		return dto;
	}

	@Override
	public TsDTO tsToDTO(TeachSubjEntity tse) {
		TsDTO dto = new TsDTO();
		dto.setSubjectId(tse.getSubject().getId());
		dto.setSubjectName(tse.getSubject().getName() + " " + tse.getSubject().getYear());
		dto.setTeacherId(tse.getTeacher().getId());
		dto.setTeacherName(tse.getTeacher().getName() + " " + tse.getTeacher().getSurname());
		return dto;
	}

	@Override
	public StsDTO stsToDTO(SutestEntity stse) {
		StsDTO dto = new StsDTO();
		dto.setSubjectId(stse.getTs().getSubject().getId());
		dto.setSubjectName(stse.getTs().getSubject().getName() + " " + stse.getTs().getSubject().getYear());
		dto.setTeacherId(stse.getTs().getTeacher().getId());
		dto.setTeacherName(stse.getTs().getTeacher().getName() + " " + stse.getTs().getTeacher().getSurname());
		dto.setStudentId(stse.getStud().getId());
		dto.setStudentName(stse.getStud().getName() + " " + stse.getStud().getSurname());
		return dto;
	}
}
