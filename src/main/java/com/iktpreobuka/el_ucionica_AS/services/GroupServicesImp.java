package com.iktpreobuka.el_ucionica_AS.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.iktpreobuka.el_ucionica_AS.controllers.RequestDTOs.ChangeGroupDTO;
import com.iktpreobuka.el_ucionica_AS.controllers.RequestDTOs.NewGroupDTO;
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
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
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
				if (group.getYear()==8) {
					group.setActive(false);
					logger.info("Group " + group.getYear() + "-" + group.getStudgroup() +" (ID: " + group.getId() + ") has graduated!");
				}
				else {
					group.setYear(group.getYear()+1);
					logger.info("Group " + group.getId() + " has advanced to next year.");
				}
				return new ResponseEntity<> (groupRepo.save(group), HttpStatus.OK);
			}
			logger.info("Attempted to advance an inactive class");
			return new ResponseEntity<> ("Error: The group is not active", HttpStatus.BAD_REQUEST);
		}
		logger.info("Attempted to advance class that doesn't exist");
		return new ResponseEntity<> ("Error: Group not found", HttpStatus.BAD_REQUEST);
	}

	@Override
	public ResponseEntity<?> switchStatus(Integer id) {
		if (groupRepo.existsById(id)) {
			StudgroupEntity group = groupRepo.findById(id).get();
			if (group.isActive()) {
				logger.warn("Group " + group.getId() + " has been deactivated. Please, reassign the students manually.");
				group.setActive(false);
			}
			else {
				if (group.getYear()==8) {
					logger.info("Attempted to reactivated a graduated group.");
					return new ResponseEntity<> ("Error: 8th grade cannot be reactivated", HttpStatus.BAD_REQUEST);
				}
				logger.info("Group " + group.getId() + " has been reactivated. Please add students to the group.");
				group.setActive(true);
			}
			return new ResponseEntity<> (groupRepo.save(group), HttpStatus.OK);
		}
		logger.info("Attempted to change status of a group that doesn't exist.");
		return new ResponseEntity<> ("Error: Group not found", HttpStatus.BAD_REQUEST);
	}

	@Override
	public ResponseEntity<?> deleteGroup(Integer id) {
		if (groupRepo.existsById(id)) {
			StudgroupEntity group = groupRepo.findById(id).get();
			groupRepo.deleteById(id);
			logger.info("Group " + group.getId() + " deleted. Please. reassign the students.");
			return new ResponseEntity<> (group, HttpStatus.OK);
		}
		logger.info("Attempted to delete a group that doesn't exist");
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
					logger.info("Student " + studId + " added to group " + groupId +".");
					return new ResponseEntity<> (studRepo.save(student), HttpStatus.OK);
				}
				logger.info("Attempted to add a student to an inactive group.");
				return new ResponseEntity<> ("Error: Group is not active", HttpStatus.BAD_REQUEST);
			}
			logger.info("Attempted to add a student to non-existant group");
			return new ResponseEntity<> ("Error: Group not found", HttpStatus.BAD_REQUEST);
		}
		logger.info("Attempted to add a non-existant student to a group.");
		return new ResponseEntity<> ("Error: Student not found", HttpStatus.BAD_REQUEST);
	}

	@Override
	public ResponseEntity<?> makeNewGroup(NewGroupDTO newGroup) {
		StudgroupEntity group = new StudgroupEntity();
		group.setActive(true);
		if (newGroup.getYear()!=null)
			group.setYear(newGroup.getYear());
		else
			group.setYear(1);
		group.setStudgroup(newGroup.getStudgroup());
		logger.info("Created a new group, designation " + group.getYear() + "-" + group.getStudgroup() + " and ID " + group.getId() + ". Please, add students.");
		return new ResponseEntity<> (groupRepo.save(group), HttpStatus.OK);
		}

	@Override
	public ResponseEntity<?> alterGroup(ChangeGroupDTO changeGroup, Integer id) {
		if (!groupRepo.existsById(id)) {
			logger.info("attempted to alter a non-existing group.");
			return new ResponseEntity<> ("Error: Group not found", HttpStatus.BAD_REQUEST);
		}
		StudgroupEntity group = groupRepo.findById(id).get();
		if (changeGroup.getStudgroup()!=null)
			group.setStudgroup(changeGroup.getStudgroup());
		if (changeGroup.getYear()!=null)
			group.setYear(changeGroup.getYear());
		logger.warn("Group " + group.getId() + " altered. Please, check the information.");
		return new ResponseEntity<> (groupRepo.save(group), HttpStatus.OK);
	}

}
