package com.iktpreobuka.el_ucionica_AS.services;

import org.springframework.http.ResponseEntity;

import com.iktpreobuka.el_ucionica_AS.controllers.DTOs.ChangeSubjectDTO;
import com.iktpreobuka.el_ucionica_AS.controllers.DTOs.NewSubjectDTO;

public interface SubjectServices {

	ResponseEntity<?> getSubByYear(Integer yearNo);

	ResponseEntity<?> getSubByName(String name);

	ResponseEntity<?> getSubById(Integer id);

	ResponseEntity<?> makeNewSubject(NewSubjectDTO newSub);

	ResponseEntity<?> alterSubject(Integer id, ChangeSubjectDTO sub);

	ResponseEntity<?> deleteSubjcet(Integer id);

	ResponseEntity<?> assignTeacher(Integer subId, Integer teachId);

	ResponseEntity<?> findTeachersForSub(Integer subId);

	ResponseEntity<?> findSubsForTeacher(Integer teachId);

	ResponseEntity<?> assingSubToStudent(Integer subId, Integer teachId, Integer studId);

	ResponseEntity<?> assingSubToGroup(Integer subId, Integer teachId, Integer groupId);

}
