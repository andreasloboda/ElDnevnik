package com.iktpreobuka.el_ucionica_AS.services;

import org.springframework.http.ResponseEntity;

import com.iktpreobuka.el_ucionica_AS.controllers.DTOs.ChangeGroupDTO;

public interface GroupServices {

	ResponseEntity<?> getGroupById(Integer id);

	ResponseEntity<?> makeOrAlterGroup(ChangeGroupDTO newGroup, Integer groupId);

	ResponseEntity<?> advanceGroup(Integer id);

	ResponseEntity<?> switchStatus(Integer id);

	ResponseEntity<?> deleteGroup(Integer id);

	ResponseEntity<?> assignStudent(Integer studId, Integer groupId);

}
