package com.iktpreobuka.el_ucionica_AS.services;

import org.springframework.http.ResponseEntity;

import com.iktpreobuka.el_ucionica_AS.controllers.RequestDTOs.ChangeGradeDTO;
import com.iktpreobuka.el_ucionica_AS.controllers.RequestDTOs.NewGradeDTO;

public interface GradeServices {

	ResponseEntity<?> makeNewGrade(NewGradeDTO newGrade);

	ResponseEntity<?> findGrade(Integer id);

	ResponseEntity<?> changeGrade(Integer id, ChangeGradeDTO cGrade);

	ResponseEntity<?> deleteGrade(Integer id);

	ResponseEntity<?> getAllFromStudent(Integer studID);

	ResponseEntity<?> getFromStudentForSubject(Integer studId, Integer subID);

	ResponseEntity<?> getFinalGradesForStudent(Integer studId);

}
