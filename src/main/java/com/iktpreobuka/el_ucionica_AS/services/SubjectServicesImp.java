package com.iktpreobuka.el_ucionica_AS.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.iktpreobuka.el_ucionica_AS.controllers.DTOs.ChangeSubjectDTO;
import com.iktpreobuka.el_ucionica_AS.controllers.DTOs.NewSubjectDTO;
import com.iktpreobuka.el_ucionica_AS.entities.StudentEntity;
import com.iktpreobuka.el_ucionica_AS.entities.SubjectEntity;
import com.iktpreobuka.el_ucionica_AS.entities.SutestEntity;
import com.iktpreobuka.el_ucionica_AS.entities.TeachSubjEntity;
import com.iktpreobuka.el_ucionica_AS.repositories.StudentRepository;
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
			return new ResponseEntity<> (subRepo.save(subject), HttpStatus.OK);
		}
		return new ResponseEntity<> ("Error: Subject not found", HttpStatus.BAD_REQUEST);
	}

	@Override
	public ResponseEntity<?> deleteSubjcet(Integer id) {
		if (subRepo.existsById(id)) {
			SubjectEntity subject = subRepo.findById(id).get();
			subRepo.deleteById(id);
			return new ResponseEntity<> (subject, HttpStatus.OK);
		}
		return new ResponseEntity<> ("Error: Subject not found", HttpStatus.BAD_REQUEST);
	}

	@Override
	public ResponseEntity<?> assignTeacher(Integer subId, Integer teachId) {
		if (subRepo.existsById(subId)) {
			if (teachRepo.existsById(teachId)) {
				if (tsRepo.existsByTeacherIdAndSubjectId(teachId, subId)) {
					return new ResponseEntity<> ("Error: Teacher and Subject already connected", HttpStatus.BAD_REQUEST);
				}
				TeachSubjEntity tse = new TeachSubjEntity();
				tse.setSubject(subRepo.findById(subId).get());
				tse.setTeacher(teachRepo.findById(teachId).get());
				return new ResponseEntity<> (tsRepo.save(tse), HttpStatus.OK);
			}
			return new ResponseEntity<> ("Error: Teacher not found", HttpStatus.BAD_REQUEST);
		}
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
		
		if (!subRepo.existsById(subId))
			return new ResponseEntity<> ("Error: Subject not found", HttpStatus.BAD_REQUEST);
		if (!teachRepo.existsById(teachId)) 
			return new ResponseEntity<> ("Error: Teacher not found", HttpStatus.BAD_REQUEST);
		if (!studRepo.existsById(studId))
			return new ResponseEntity<> ("Error: Student not found", HttpStatus.BAD_REQUEST);
		if (!tsRepo.existsByTeacherIdAndSubjectId(teachId, subId))
			return new ResponseEntity<> ("Error: Teacher and Subject not connected", HttpStatus.BAD_REQUEST);
		
		TeachSubjEntity tse = tsRepo.findByTeacherIdAndSubjectId(teachId, subId).get();
		StudentEntity stud = studRepo.findById(studId).get();
		
		if (stsRepo.existsByTsIdAndStudId(tse.getId(), stud.getId()))
			return new ResponseEntity<> ("Error: Student already assigned to this subject", HttpStatus.BAD_REQUEST);
		if (tse.getSubject().getYear()!=stud.getStudgroup().getYear())
			return new ResponseEntity<> ("Error: Subject is not meant for student's year", HttpStatus.BAD_REQUEST);
		
		SutestEntity stse = new SutestEntity();
		stse.setTs(tse);
		stse.setStud(stud);
		return new ResponseEntity<> (stsRepo.save(stse), HttpStatus.OK);
	}
}
