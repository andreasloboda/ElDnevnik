package com.iktpreobuka.el_ucionica_AS.services;

import org.springframework.http.ResponseEntity;

import com.iktpreobuka.el_ucionica_AS.controllers.DTOs.ChangeGroupDTO;
import com.iktpreobuka.el_ucionica_AS.controllers.DTOs.NewGroupDTO;

public interface GroupServices {

	ResponseEntity<?> getGroupById(Integer id);
	
	ResponseEntity<?> advanceGroup(Integer id);

	ResponseEntity<?> switchStatus(Integer id);

	ResponseEntity<?> deleteGroup(Integer id);

	ResponseEntity<?> assignStudent(Integer studId, Integer groupId);

	ResponseEntity<?> makeNewGroup(NewGroupDTO newGroup);

	ResponseEntity<?> alterGroup(ChangeGroupDTO changeGroup, Integer id);

}
