package com.iktpreobuka.el_ucionica_AS.services;

import org.springframework.http.ResponseEntity;

import com.iktpreobuka.el_ucionica_AS.controllers.DTOs.GroupDTO;

public interface GroupServices {

	ResponseEntity<?> getGroupById(Integer id);

	ResponseEntity<?> makeOrAlterGroup(GroupDTO newGroup, Integer groupId);

	ResponseEntity<?> advanceGroup(Integer id);

	ResponseEntity<?> switchStatus(Integer id);

	ResponseEntity<?> deleteGroup(Integer id);

}
