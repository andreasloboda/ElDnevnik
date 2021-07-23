package com.iktpreobuka.el_ucionica_AS.services;

import com.iktpreobuka.el_ucionica_AS.controllers.DTOs.NewGradeDTO;
import com.iktpreobuka.el_ucionica_AS.entities.GradeEntity;

public interface GradeServices {

	public Integer makeNewGrade(NewGradeDTO newGrade, GradeEntity grade);
}
