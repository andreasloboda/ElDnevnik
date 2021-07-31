package com.iktpreobuka.el_ucionica_AS.services;

import org.springframework.http.ResponseEntity;

import com.iktpreobuka.el_ucionica_AS.entities.GradeEntity;

public interface OtherServices {
	
	void informParentAboutGrade(GradeEntity grade);

	ResponseEntity<?> downloadLog();

}
