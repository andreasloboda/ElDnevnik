package com.iktpreobuka.el_ucionica_AS.services;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.iktpreobuka.el_ucionica_AS.controllers.DTOs.ChangeGradeDTO;
import com.iktpreobuka.el_ucionica_AS.controllers.DTOs.FinalGradeDTO;
import com.iktpreobuka.el_ucionica_AS.controllers.DTOs.NewGradeDTO;
import com.iktpreobuka.el_ucionica_AS.entities.GradeEntity;
import com.iktpreobuka.el_ucionica_AS.entities.SutestEntity;
import com.iktpreobuka.el_ucionica_AS.repositories.GradeRepository;
import com.iktpreobuka.el_ucionica_AS.repositories.StudentRepository;
import com.iktpreobuka.el_ucionica_AS.repositories.SutestRepository;

@Service
public class GradeServicesImp implements GradeServices{

	@Autowired
	private GradeRepository gradeRepo;
	@Autowired
	private SutestRepository stsRepo;
	@Autowired
	private StudentRepository studRepo;
	@Autowired
	private EmailServices emailServ;
	
	@Override
	public ResponseEntity<?> makeNewGrade(NewGradeDTO newGrade) {
		if (!stsRepo.existsByStudIdAndTsTeacherIdAndTsSubjectId(newGrade.getStudentID(), newGrade.getTeacherID(), newGrade.getSubjectID()))
			return new ResponseEntity<> ("Error: Student, Teacher and Subject are not connected", HttpStatus.BAD_REQUEST);
		SutestEntity sts = stsRepo.findByStudIdAndTsTeacherIdAndTsSubjectId(newGrade.getStudentID(), newGrade.getTeacherID(), newGrade.getSubjectID()).get();
		GradeEntity grade = new GradeEntity();
		grade.setGrade(newGrade.getGrade());
		grade.setInfo(sts);
		if (sts.getTs().getSubject().getYear()==sts.getStud().getStudgroup().getYear())
			grade.setYear(sts.getStud().getStudgroup().getYear());
		else
			return new ResponseEntity<> ("Error: The subject is not taught at the year student is in!", HttpStatus.BAD_REQUEST);
		if (LocalDate.now().getMonthValue()>=9)
			grade.setSemester(false);
		else
			grade.setSemester(true);
		emailServ.informParentAboutGrade(grade);
		return new ResponseEntity<> (gradeRepo.save(grade), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> findGrade(Integer id) {
		if (gradeRepo.existsById(id))
			return new ResponseEntity<> (gradeRepo.findById(id).get(), HttpStatus.OK);
		return new ResponseEntity<> ("Grade does not exist", HttpStatus.NOT_FOUND);
	}

	@Override
	public ResponseEntity<?> changeGrade(Integer id, ChangeGradeDTO cGrade) {
		if (!gradeRepo.existsById(id))
			return new ResponseEntity<> ("Grade does not exist", HttpStatus.NOT_FOUND);
		GradeEntity grade = gradeRepo.findById(id).get();
		if (cGrade.getGrade()!=null)
			grade.setGrade(cGrade.getGrade());
		int temp = 0;
		if (cGrade.getStudentID()!=null)
			temp = temp+1;
		if (cGrade.getTeacherID()!=null)
			temp = temp+2;
		if (cGrade.getSubjectID()!=null)
			temp = temp+4;
		if (temp != 0) {
			int suId = 0;
			int teId = 0;
			int stId = 0; 
			if ((temp==1)||(temp==3)||(temp==5)||(temp==7))
				stId = cGrade.getStudentID();
			else
				stId = grade.getInfo().getStud().getId();
			if ((temp==2)||(temp==3)||(temp==6)||(temp==7))
				teId = cGrade.getTeacherID();
			else
				teId = grade.getInfo().getTs().getTeacher().getId();
			if ((temp==4)||(temp==5)||(temp==6)||(temp==7))
				suId = cGrade.getSubjectID();
			else
				suId = grade.getInfo().getTs().getSubject().getId();
			if (!stsRepo.existsByStudIdAndTsTeacherIdAndTsSubjectId(stId, teId, suId))
				return new ResponseEntity<> ("Error: Student, Teacher and Subject are not connected", HttpStatus.BAD_REQUEST);
			grade.setInfo(stsRepo.findByStudIdAndTsTeacherIdAndTsSubjectId(stId, teId, suId).get());
		}
		if (grade.getYear()!=grade.getInfo().getTs().getSubject().getYear())
			grade.setYear(grade.getInfo().getTs().getSubject().getYear());
		if (cGrade.getSemester()!=null) {
			if (cGrade.getSemester()==1)
				grade.setSemester(false);
			if (cGrade.getSemester()==2)
				grade.setSemester(true);
		}
		return new ResponseEntity<> (gradeRepo.save(grade), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> deleteGrade(Integer id) {
		if (!gradeRepo.existsById(id))
			return new ResponseEntity<> ("Grade does not exist", HttpStatus.NOT_FOUND);
		GradeEntity grade = gradeRepo.findById(id).get();
		gradeRepo.deleteById(id);
		return new ResponseEntity<> (grade, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> getAllFromStudent(Integer studID) {
		if (!studRepo.existsById(studID))
			return new ResponseEntity<> ("Student does not exist", HttpStatus.NOT_FOUND);
		return new ResponseEntity<> (gradeRepo.findAllByInfoStudId(studID), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> getFromStudentForSubject(Integer studId, Integer subID) {
		if (!studRepo.existsById(studId))
			return new ResponseEntity<> ("Student does not exist", HttpStatus.NOT_FOUND);
		if (!stsRepo.existsByStudIdAndTsSubjectId(studId, subID))
			return new ResponseEntity<> ("Student does not listen to this subject", HttpStatus.NOT_FOUND);
		return new ResponseEntity<> (gradeRepo.findAllByInfoStudIdAndInfoTsSubjectId(studId, subID), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> getFinalGradesForStudent(Integer studId) {
		if (!studRepo.existsById(studId))
			return new ResponseEntity<> ("Student does not exist", HttpStatus.NOT_FOUND);
		List<FinalGradeDTO> subjectList = new ArrayList<FinalGradeDTO>();
		List<GradeEntity> gradesList = gradeRepo.findAllByInfoStudId(studId);
		if (gradesList.isEmpty())
			return new ResponseEntity<> ("Student does not have grades", HttpStatus.NOT_FOUND);
		for (GradeEntity grade : gradesList) {
			if(subjectList.isEmpty())
				subjectList.add(addNewSubject(grade));
			addGradeToSubject(grade, subjectList);
		}
		return new ResponseEntity<> (subjectList, HttpStatus.OK);
	}
	
	private void addGradeToSubject(GradeEntity grade, List<FinalGradeDTO> subjectList) {
		for (FinalGradeDTO subject : subjectList) {
			if (subject.getSubjectId() == grade.getInfo().getTs().getSubject().getId()) {
				subject.setGradeSum(subject.getGradeSum()+grade.getGrade());
				subject.setGradesNumber(subject.getGradesNumber()+1);
				subject.setAverage(Math.round((float) subject.getGradeSum() / subject.getGradesNumber()));
				return;
			}
		}
		FinalGradeDTO subject = addNewSubject(grade);
		subject.setGradeSum(subject.getGradeSum()+grade.getGrade());
		subject.setGradesNumber(subject.getGradesNumber()+1);
		subject.setAverage(Math.round((float) subject.getGradeSum() / subject.getGradesNumber()));
		subjectList.add(subject);
		return;
	}

	private FinalGradeDTO addNewSubject(GradeEntity grade) {
		FinalGradeDTO subject = new FinalGradeDTO();
		subject.setStudentName(grade.getInfo().getStud().getName());
		subject.setStudentSurname(grade.getInfo().getStud().getSurname());
		subject.setSubjectId(grade.getInfo().getTs().getSubject().getId());
		subject.setSubjectName(grade.getInfo().getTs().getSubject().getName());
		subject.setYear(grade.getInfo().getTs().getSubject().getYear());
		subject.setAverage(0);
		subject.setGradesNumber(0);
		subject.setGradeSum(0);
		return subject;
	}
}
