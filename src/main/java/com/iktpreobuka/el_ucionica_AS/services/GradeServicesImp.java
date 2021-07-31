package com.iktpreobuka.el_ucionica_AS.services;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.iktpreobuka.el_ucionica_AS.controllers.RequestDTOs.ChangeGradeDTO;
import com.iktpreobuka.el_ucionica_AS.controllers.RequestDTOs.NewGradeDTO;
import com.iktpreobuka.el_ucionica_AS.controllers.ResponseDTOs.FinalGradeDTO;
import com.iktpreobuka.el_ucionica_AS.controllers.ResponseDTOs.GpaDTO;
import com.iktpreobuka.el_ucionica_AS.controllers.ResponseDTOs.GradeDTO;
import com.iktpreobuka.el_ucionica_AS.entities.GradeEntity;
import com.iktpreobuka.el_ucionica_AS.entities.StudentEntity;
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
	private DtoServices dtos;
	@Autowired
	private OtherServices otherServ;
		
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public ResponseEntity<?> makeNewGrade(NewGradeDTO newGrade) {
		if (!stsRepo.existsByStudIdAndTsTeacherIdAndTsSubjectId(newGrade.getStudentID(), newGrade.getTeacherID(), newGrade.getSubjectID())) {
			logger.info("Attempt at making new grade failed; wrong combination of student, teacher and subject");
			return new ResponseEntity<> ("Error: Student, Teacher and Subject are not connected", HttpStatus.BAD_REQUEST);
		}
		SutestEntity sts = stsRepo.findByStudIdAndTsTeacherIdAndTsSubjectId(newGrade.getStudentID(), newGrade.getTeacherID(), newGrade.getSubjectID()).get();
		GradeEntity grade = new GradeEntity();
		grade.setGrade(newGrade.getGrade());
		grade.setInfo(sts);
		if (sts.getTs().getSubject().getYear()==sts.getStud().getStudgroup().getYear())
			grade.setYear(sts.getStud().getStudgroup().getYear());
		else {
			logger.info("Attempt at making new grade failed; Attempted to grade a student in subject they aren't listening to this year");
			return new ResponseEntity<> ("Error: The subject is not taught at the year student is in!", HttpStatus.BAD_REQUEST);
		}
		if (LocalDate.now().getMonthValue()>=9)
			grade.setSemester(false);
		else
			grade.setSemester(true);
		logger.info("New grade made successfully. Student " + newGrade.getStudentID() + " got " + newGrade.getGrade() + " in Subject" + newGrade.getSubjectID() + " from teacher " + newGrade.getTeacherID() + ".");
		otherServ.informParentAboutGrade(grade);
		return new ResponseEntity<> (dtos.gradeToDTO(gradeRepo.save(grade)), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> findGrade(Integer id) {
		if (gradeRepo.existsById(id))
			return new ResponseEntity<> (dtos.gradeToDTO(gradeRepo.findById(id).get()), HttpStatus.OK);
		return new ResponseEntity<> ("Grade does not exist", HttpStatus.NOT_FOUND);
	}

	@Override
	public ResponseEntity<?> changeGrade(Integer id, ChangeGradeDTO cGrade) {
		if (!gradeRepo.existsById(id)) {
			logger.info("Attempt was made to change non-existing grade.");
			return new ResponseEntity<> ("Grade does not exist", HttpStatus.NOT_FOUND);
		}
		GradeEntity grade = gradeRepo.findById(id).get();
		String log = "Grade " + grade.getId() + " is being altered: ";
		if (cGrade.getGrade()!=null) {
			grade.setGrade(cGrade.getGrade());
			log = log + "grade changed to " + cGrade.getGrade() + ", ";
		}
		int temp = 0;
		if (cGrade.getStudentID()!=null) {
			temp = temp+1;
			log = log + "student changed to " + cGrade.getStudentID() + ", ";
		}
		if (cGrade.getTeacherID()!=null) {
			temp = temp+2;
			log = log + ("teacher changed to " + cGrade.getTeacherID() + ", ");
		}
		if (cGrade.getSubjectID()!=null) {
			temp = temp+4;
			log = log + ("Subject changed to " + cGrade.getSubjectID() + ", ");
		}
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
			if (!stsRepo.existsByStudIdAndTsTeacherIdAndTsSubjectId(stId, teId, suId)) {
				logger.info("Attempt was made to change grade by ID " + id + ". Failed due to bad combination of subject, teacher and student.");
				return new ResponseEntity<> ("Error: Student, Teacher and Subject are not connected", HttpStatus.BAD_REQUEST);
			}
			grade.setInfo(stsRepo.findByStudIdAndTsTeacherIdAndTsSubjectId(stId, teId, suId).get());
		}
		if (grade.getYear()!=grade.getInfo().getTs().getSubject().getYear())
			grade.setYear(grade.getInfo().getTs().getSubject().getYear());
		if (cGrade.getSemester()!=null) {
			log = log + ("semester adjusted, ");
			if (cGrade.getSemester()==1)
				grade.setSemester(false);
			if (cGrade.getSemester()==2)
				grade.setSemester(true);
		}
		log = log + ("PLEASE CHECK FOR ACCURACY.");
		logger.warn(log);
		return new ResponseEntity<> (dtos.gradeToDTO(gradeRepo.save(grade)), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> deleteGrade(Integer id) {
		if (!gradeRepo.existsById(id)) {
			logger.info("Attempt to delete grade that doesn't exist.");
			return new ResponseEntity<> ("Grade does not exist", HttpStatus.NOT_FOUND);
		}
		GradeEntity grade = gradeRepo.findById(id).get();
		gradeRepo.deleteById(id);
		logger.info("Deleted a grade by ID " + grade.getId());
		return new ResponseEntity<> (dtos.gradeToDTO(grade), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> getAllFromStudent(Integer studID) {
		if (!studRepo.existsById(studID))
			return new ResponseEntity<> ("Student does not exist", HttpStatus.NOT_FOUND);
		List<GradeEntity> gradesList = (List<GradeEntity>) gradeRepo.findAllByInfoStudId(studID);
		if (gradesList.isEmpty())
			return new ResponseEntity<> ("Student has no grades", HttpStatus.NOT_FOUND);
		List<GradeDTO> response = new ArrayList<GradeDTO>();
		for (GradeEntity grade : gradesList)
			response.add(dtos.gradeToDTO(grade));
		return new ResponseEntity<> (response, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> getFromStudentForSubject(Integer studId, Integer subID) {
		if (!studRepo.existsById(studId))
			return new ResponseEntity<> ("Student does not exist", HttpStatus.NOT_FOUND);
		if (!stsRepo.existsByStudIdAndTsSubjectId(studId, subID))
			return new ResponseEntity<> ("Student does not listen to this subject", HttpStatus.NOT_FOUND);
		List<GradeEntity> gradesList = (List<GradeEntity>) gradeRepo.findAllByInfoStudIdAndInfoTsSubjectId(studId, subID);
		if (gradesList.isEmpty())
			return new ResponseEntity<> ("Student has no grades for this subject", HttpStatus.NOT_FOUND);
		List<GradeDTO> response = new ArrayList<GradeDTO>();
		for (GradeEntity grade : gradesList)
			response.add(dtos.gradeToDTO(grade));
		return new ResponseEntity<> (response, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> getFinalGradesForStudent(Integer studId) {
		if (!studRepo.existsById(studId))
			return new ResponseEntity<> ("Student does not exist", HttpStatus.NOT_FOUND);
		StudentEntity student = studRepo.findById(studId).get();
		if (student.getStudgroup()==null)
			return new ResponseEntity<> ("Student information missing", HttpStatus.NOT_FOUND);
		List<FinalGradeDTO> subjectList = new ArrayList<FinalGradeDTO>();
		List<GradeEntity> gradesList = gradeRepo.findAllByInfoStudId(student.getId());
		if (gradesList.isEmpty())
			return new ResponseEntity<> ("Student does not have grades", HttpStatus.NOT_FOUND);
		for (GradeEntity grade : gradesList) {
			if(subjectList.isEmpty())
				subjectList.add(addNewSubject(grade));
			addGradeToSubject(grade, subjectList);
		}
		GpaDTO gpa = new GpaDTO();
		gpa.setStudentName(student.getName());
		gpa.setStudentSurname(student.getSurname());
		gpa.setStudentYear(student.getStudgroup().getYear());
		int[] gpaSum = new int[gpa.getStudentYear()];
		int[] gpaNum = new int[gpa.getStudentYear()];
		Float[] gpaAvg = new Float[gpa.getStudentYear()];
		for (FinalGradeDTO subject : subjectList) {
			gpaSum[subject.getYear()-1] = gpaSum[subject.getYear()-1] + subject.getAverage();
			gpaNum[subject.getYear()-1] ++;
		}
		for (int i = 0; i<gpa.getStudentYear(); i++)
			gpaAvg[i] = (float) gpaSum[i] / gpaNum[i];
		gpa.setGpa(gpaAvg);
		gpa.setFinalGrades(subjectList);
		return new ResponseEntity<> (gpa, HttpStatus.OK);
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
		subject.setSubjectId(grade.getInfo().getTs().getSubject().getId());
		subject.setSubjectName(grade.getInfo().getTs().getSubject().getName());
		subject.setYear(grade.getInfo().getTs().getSubject().getYear());
		subject.setAverage(0);
		subject.setGradesNumber(0);
		subject.setGradeSum(0);
		return subject;
	}

	@Override
	public ResponseEntity<?> getAllGrades() {
		List<GradeEntity> gradesList = (List<GradeEntity>) gradeRepo.findAll();
		if (gradesList.isEmpty())
			return new ResponseEntity<> ("No grades in database", HttpStatus.NOT_FOUND);
		List<GradeDTO> response = new ArrayList<GradeDTO>();
		for (GradeEntity grade : gradesList)
			response.add(dtos.gradeToDTO(grade));
		return new ResponseEntity<> (response, HttpStatus.OK);
	}
}
