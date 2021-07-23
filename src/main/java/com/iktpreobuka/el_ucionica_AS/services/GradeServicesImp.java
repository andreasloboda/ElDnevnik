package com.iktpreobuka.el_ucionica_AS.services;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iktpreobuka.el_ucionica_AS.controllers.DTOs.NewGradeDTO;
import com.iktpreobuka.el_ucionica_AS.entities.GradeEntity;
import com.iktpreobuka.el_ucionica_AS.entities.SutestEntity;
import com.iktpreobuka.el_ucionica_AS.repositories.GradeRepository;
import com.iktpreobuka.el_ucionica_AS.repositories.SutestRepository;

@Service
public class GradeServicesImp implements GradeServices{

	@Autowired
	private GradeRepository gradeRepo;
	@Autowired
	private SutestRepository stsRepo;
	
	@Override
	public Integer makeNewGrade(NewGradeDTO newGrade, GradeEntity grade) {
		SutestEntity sts = stsRepo.findByStudIdAndTsTeacherIdAndTsSubjectId(newGrade.getStudentID(), newGrade.getTeacherID(), newGrade.getSubjectID()).orElse(null);
		if (sts!=null) {
			grade.setInfo(sts);
			if ((newGrade.getGrade()>0)&&(newGrade.getGrade()<6))
				grade.setGrade(newGrade.getGrade());
			else return 2;
			grade.setYear(sts.getStud().getGroup().getYear());
			LocalDate date = LocalDate.now();
			if (date.getMonth().getValue()>=9)
				grade.setSemester(false);
			else grade.setSemester(true);
			grade = gradeRepo.save(grade);
			return 0;
		}
		return 1;
	}

}
