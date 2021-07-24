package com.iktpreobuka.el_ucionica_AS.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.iktpreobuka.el_ucionica_AS.controllers.DTOs.ChangeGroupDTO;
import com.iktpreobuka.el_ucionica_AS.controllers.DTOs.NewGroupDTO;
import com.iktpreobuka.el_ucionica_AS.entities.StudentEntity;
import com.iktpreobuka.el_ucionica_AS.entities.StudgroupEntity;
import com.iktpreobuka.el_ucionica_AS.repositories.StudentRepository;
import com.iktpreobuka.el_ucionica_AS.repositories.StudgroupRepository;

@Service
public class GroupServicesImp implements GroupServices{

	@Autowired
	private StudgroupRepository groupRepo;
	@Autowired
	private StudentRepository studRepo;
	
	@Override
	public ResponseEntity<?> getGroupById(Integer id) {
		if (groupRepo.existsById(id))
			return new ResponseEntity<> (groupRepo.findById(id), HttpStatus.OK);
		return new ResponseEntity<> ("Error: Student Group not found", HttpStatus.BAD_REQUEST);
	}

	

	@Override
	public ResponseEntity<?> advanceGroup(Integer id) {
		if (groupRepo.existsById(id)) {
			StudgroupEntity group = groupRepo.findById(id).get();
			if (group.isActive()) {
				if (group.getYear()==8)
					group.setActive(false);
				else
					group.setYear(group.getYear()+1);
				return new ResponseEntity<> (groupRepo.save(group), HttpStatus.OK);
			}
			return new ResponseEntity<> ("Error: The group is not active", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<> ("Error: Group not found", HttpStatus.BAD_REQUEST);
	}

	@Override
	public ResponseEntity<?> switchStatus(Integer id) {
		if (groupRepo.existsById(id)) {
			StudgroupEntity group = groupRepo.findById(id).get();
			if (group.isActive())
				group.setActive(false);
			else {
				if (group.getYear()==8)
					return new ResponseEntity<> ("Error: 8th grade cannot be reactivated", HttpStatus.BAD_REQUEST);
				group.setActive(true);
			}
			return new ResponseEntity<> (groupRepo.save(group), HttpStatus.OK);
		}
		return new ResponseEntity<> ("Error: Group not found", HttpStatus.BAD_REQUEST);
	}

	@Override
	public ResponseEntity<?> deleteGroup(Integer id) {
		if (groupRepo.existsById(id)) {
			StudgroupEntity group = groupRepo.findById(id).get();
			groupRepo.deleteById(id);
			return new ResponseEntity<> (group, HttpStatus.OK);
		}
		return new ResponseEntity<> ("Error: Group not found", HttpStatus.BAD_REQUEST);
	}

	@Override
	public ResponseEntity<?> assignStudent(Integer studId, Integer groupId) {
		if (studRepo.existsById(studId)) {
			if (groupRepo.existsById(groupId)) {
				StudgroupEntity group = groupRepo.findById(groupId).get();
				if (group.isActive()) {
					StudentEntity student = studRepo.findById(studId).get();
					student.setStudgroup(group);
					return new ResponseEntity<> (studRepo.save(student), HttpStatus.OK);
				}
				return new ResponseEntity<> ("Error: Group is not active", HttpStatus.BAD_REQUEST);
			}
			return new ResponseEntity<> ("Error: Group not found", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<> ("Error: Student not found", HttpStatus.BAD_REQUEST);
	}

	@Override
	public ResponseEntity<?> makeNewGroup(NewGroupDTO newGroup) {
		StudgroupEntity group = new StudgroupEntity();
		group.setActive(true);
		group.setYear(newGroup.getYear());
		group.setStudgroup(newGroup.getStudgroup());
		return new ResponseEntity<> (groupRepo.save(group), HttpStatus.OK);
		}

	@Override
	public ResponseEntity<?> alterGroup(ChangeGroupDTO changeGroup, Integer id) {
		if (groupRepo.existsById(id))
			return new ResponseEntity<> ("Error: Group not found", HttpStatus.BAD_REQUEST);
		StudgroupEntity group = groupRepo.findById(id).get();
		if (changeGroup.getStudgroup()!=null)
			group.setStudgroup(changeGroup.getStudgroup());
		if (changeGroup.getYear()!=null) {
			if ((changeGroup.getYear()>0)&&(changeGroup.getYear()<9))
				group.setYear(changeGroup.getYear());
			else
				return new ResponseEntity<> ("Error: Year out of bounds!", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<> (groupRepo.save(group), HttpStatus.OK);
	}

}
