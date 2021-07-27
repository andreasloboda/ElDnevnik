package com.iktpreobuka.el_ucionica_AS.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.iktpreobuka.el_ucionica_AS.controllers.RequestDTOs.ChangeSubjectDTO;
import com.iktpreobuka.el_ucionica_AS.controllers.RequestDTOs.NewSubjectDTO;
import com.iktpreobuka.el_ucionica_AS.entities.StudentEntity;
import com.iktpreobuka.el_ucionica_AS.entities.SubjectEntity;
import com.iktpreobuka.el_ucionica_AS.entities.SutestEntity;
import com.iktpreobuka.el_ucionica_AS.entities.TeachSubjEntity;
import com.iktpreobuka.el_ucionica_AS.repositories.StudentRepository;
import com.iktpreobuka.el_ucionica_AS.repositories.StudgroupRepository;
import com.iktpreobuka.el_ucionica_AS.repositories.SubjectRepository;
import com.iktpreobuka.el_ucionica_AS.repositories.SutestRepository;
import com.iktpreobuka.el_ucionica_AS.repositories.TeachSubjRepository;
import com.iktpreobuka.el_ucionica_AS.repositories.TeacherRepository;

@Service
public class SubjectServicesImp implements SubjectServices{

	@Autowired
	private SubjectRepository subRepo;
	@Autowired
	private TeacherRepository teachRepo;
	@Autowired
	private TeachSubjRepository tsRepo;
	@Autowired
	private StudentRepository studRepo;
	@Autowired
	private SutestRepository stsRepo;
	@Autowired
	private StudgroupRepository groupRepo;
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public ResponseEntity<?> getSubByYear(Integer yearNo) {
		if ((yearNo>0)&&(yearNo<9))
			return new ResponseEntity<> (subRepo.findAllByYear(yearNo), HttpStatus.OK);
		return new ResponseEntity<> ("Error: Year must be between 1 and 8", HttpStatus.BAD_REQUEST);
	}

	@Override
	public ResponseEntity<?> getSubByName(String name) {
		if (subRepo.existsByName(name))
			return new ResponseEntity<> (subRepo.findAllByName(name), HttpStatus.OK);
		return new ResponseEntity<> ("Error: Subject not found", HttpStatus.BAD_REQUEST);
	}

	@Override
	public ResponseEntity<?> getSubById(Integer id) {
		if (subRepo.existsById(id))
			return new ResponseEntity<> (subRepo.findById(id).get(), HttpStatus.OK);
		return new ResponseEntity<> ("Error: Subject not found", HttpStatus.BAD_REQUEST);
	}

	@Override
	public ResponseEntity<?> makeNewSubject(NewSubjectDTO newSub) {
		SubjectEntity subject = new SubjectEntity();
		subject.setName(newSub.getName());
		subject.setHours(newSub.getHours());
		subject.setYear(newSub.getYear());
		logger.info("Created new subject named " + subject.getName() + " with " + subject.getHours() + " hours a week, for year " + subject.getYear());
		return new ResponseEntity<> (subRepo.save(subject), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> alterSubject(Integer id, ChangeSubjectDTO sub) {
		if (subRepo.existsById(id)) {
			SubjectEntity subject = subRepo.findById(id).get();
			if (sub.getHours()!=null)
				subject.setHours(sub.getHours());
			if (sub.getName()!=null)
				subject.setName(sub.getName());
			if (sub.getYear()!=null)
				subject.setYear(sub.getYear());
			logger.warn("Altered subject with ID " + subject.getId() + ". Please, check details.");
			return new ResponseEntity<> (subRepo.save(subject), HttpStatus.OK);
		}
		logger.info("Attempted to alter a non-existant subject.");
		return new ResponseEntity<> ("Error: Subject not found", HttpStatus.BAD_REQUEST);
	}

	@Override
	public ResponseEntity<?> deleteSubjcet(Integer id) {
		if (subRepo.existsById(id)) {
			SubjectEntity subject = subRepo.findById(id).get();
			subRepo.deleteById(id);
			logger.info("Deleted subject with ID " + subject.getId());
			return new ResponseEntity<> (subject, HttpStatus.OK);
		}
		logger.info("Attempted to delete a non-existant subject.");
		return new ResponseEntity<> ("Error: Subject not found", HttpStatus.BAD_REQUEST);
	}

	@Override
	public ResponseEntity<?> assignTeacher(Integer subId, Integer teachId) {
		if (subRepo.existsById(subId)) {
			if (teachRepo.existsById(teachId)) {
				if (tsRepo.existsByTeacherIdAndSubjectId(teachId, subId)) {
					logger.info("Attempted to assign teacher to a subject they already teach.");
					return new ResponseEntity<> ("Error: Teacher and Subject already connected", HttpStatus.BAD_REQUEST);
				}
				TeachSubjEntity tse = new TeachSubjEntity();
				tse.setSubject(subRepo.findById(subId).get());
				tse.setTeacher(teachRepo.findById(teachId).get());
				logger.info("Teacher " + teachId + " is assigned to subject " + subId);
				return new ResponseEntity<> (tsRepo.save(tse), HttpStatus.OK);
			}
			logger.info("Attempted to add a non-existant teacher to a subject.");
			return new ResponseEntity<> ("Error: Teacher not found", HttpStatus.BAD_REQUEST);
		}
		logger.info("Attempted to add a teacher to a non-existant subject.");
		return new ResponseEntity<> ("Error: Subject not found", HttpStatus.BAD_REQUEST);
	}

	@Override
	public ResponseEntity<?> findTeachersForSub(Integer subId) {
		if (subRepo.existsById(subId)) {
			if (tsRepo.existsBySubjectId(subId)) {
				return new ResponseEntity<> (teachRepo.findAllBySubjectsSubjectId(subId), HttpStatus.OK);
			}
			return new ResponseEntity<> ("Error: Subject not connected to any teacher", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<> ("Error: Subject not found", HttpStatus.BAD_REQUEST);
	}

	@Override
	public ResponseEntity<?> findSubsForTeacher(Integer teachId) {
		if (teachRepo.existsById(teachId)) {
			if (tsRepo.existsByTeacherId(teachId)) {
				return new ResponseEntity<> (subRepo.findAllByTeachersTeacherId(teachId), HttpStatus.OK);
			}
			return new ResponseEntity<> ("Error: Teacher not connected to any subjects", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<> ("Error: Teacher not found", HttpStatus.BAD_REQUEST);
	}

	@Override
	public ResponseEntity<?> assingSubToStudent(Integer subId, Integer teachId, Integer studId) {
		if (!studRepo.existsById(studId)) {
			logger.info("Attempted to add a subject to a non-existant student.");
			return new ResponseEntity<> ("Error: Student not found", HttpStatus.BAD_REQUEST);
		}
		if (!tsRepo.existsByTeacherIdAndSubjectId(teachId, subId)) {
			logger.info("Attempted to assign a non existand combination of subject and teacher to a student.");
			return new ResponseEntity<> ("Error: Teacher and Subject not connected", HttpStatus.BAD_REQUEST);
		}		
		TeachSubjEntity tse = tsRepo.findByTeacherIdAndSubjectId(teachId, subId).get();
		StudentEntity stud = studRepo.findById(studId).get();
		
		if (stsRepo.existsByTsIdAndStudId(tse.getId(), stud.getId())) {
			logger.info("Attempted to assign a student the subject they already listen to.");
			return new ResponseEntity<> ("Error: Student already assigned to this subject", HttpStatus.BAD_REQUEST);
		}
		if (tse.getSubject().getYear()!=stud.getStudgroup().getYear()) {
			logger.info("Attempted to assign a student the subject not meant for their year.");
			return new ResponseEntity<> ("Error: Subject is not meant for student's year", HttpStatus.BAD_REQUEST);
		}
		SutestEntity stse = new SutestEntity();
		stse.setTs(tse);
		stse.setStud(stud);
		logger.info("Student " + studId + " assigned to subject " + subId + " taught by teacher " + teachId);
		return new ResponseEntity<> (stsRepo.save(stse), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> assingSubToGroup(Integer subId, Integer teachId, Integer groupId) {
		if (!groupRepo.existsById(groupId)) {
			logger.info("Attempted to add a subject to a non-existant group.");
			return new ResponseEntity<> ("Error: Student group not found", HttpStatus.BAD_REQUEST);
		}
		if (!tsRepo.existsByTeacherIdAndSubjectId(teachId, subId)) {
			logger.info("Attempted to assign a non existand combination of subject and teacher to a group.");
			return new ResponseEntity<> ("Error: Teacher and Subject not connected", HttpStatus.BAD_REQUEST);
		}
		TeachSubjEntity tse = tsRepo.findByTeacherIdAndSubjectId(teachId, subId).get();
		
		if (tse.getSubject().getYear()!=groupRepo.findById(groupId).get().getYear()) {
			logger.info("Attempted to assign a group the subject not meant for their year.");
			return new ResponseEntity<> ("Error: Subject is not meant for the group's year", HttpStatus.BAD_REQUEST);
		}
		List<StudentEntity> students = studRepo.findAllByStudgroupId(groupId);
		int i = 0;
		for (StudentEntity se : students) {
			if (!stsRepo.existsByTsIdAndStudId(tse.getId(), se.getId())) {
				SutestEntity stse = new SutestEntity();
				stse.setTs(tse);
				stse.setStud(se);
				stsRepo.save(stse);
				i++;
			}		
		}
		logger.info("Assign subject " + subId + " taught by " + teachId + " to " + i + " students in group " + groupId);
		return new ResponseEntity<> (students, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> removeSubFromStudent(Integer subId, Integer teachId, Integer studId) {
		if (stsRepo.existsByStudIdAndTsTeacherIdAndTsSubjectId(studId, teachId, subId)) {
			SutestEntity sts = stsRepo.findByStudIdAndTsTeacherIdAndTsSubjectId(studId, teachId, subId).get();
			stsRepo.deleteById(sts.getId());
			logger.info("Removed subject " + subId + " from student " + studId);
			return new ResponseEntity<> (sts, HttpStatus.OK);
		}
		logger.info("Unsuccessful attempt at removing a subject from a student occured.");
		return new ResponseEntity<> ("Error: This student isn't taught this subject by the teacher in question", HttpStatus.BAD_REQUEST);
	}
	
	@Override
	public ResponseEntity<?> removeTeacherFromSubject(Integer subId, Integer teachId) {
		if (tsRepo.existsByTeacherIdAndSubjectId(teachId, subId)) {
			TeachSubjEntity tse = tsRepo.findByTeacherIdAndSubjectId(teachId, subId).get();
			tsRepo.deleteById(tse.getId());
			logger.info("Unassigned teacher " + teachId + " from subject " + subId);
			return new ResponseEntity<> (tse, HttpStatus.OK);
		}
		logger.info("Unsuccessful attempt at unassigning teacher from a subject occured.");
		return new ResponseEntity<> ("Error: This teacher isn't assigned to this subject", HttpStatus.BAD_REQUEST);
	}

	@Override
	public ResponseEntity<?> getSubsFromStudent(Integer studId) {
		if (studRepo.existsById(studId)) {
			return new ResponseEntity<> (subRepo.findAllByTeachersStudentsStudId(studId), HttpStatus.OK);
		}
		return new ResponseEntity<> ("Error: This student doesn't exist", HttpStatus.BAD_REQUEST);
	}
}
