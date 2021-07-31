package com.iktpreobuka.el_ucionica_AS.services;

import javax.servlet.http.HttpServletRequest;

public interface SecurityServices {

	boolean isThisMe(Integer id, HttpServletRequest request);

	boolean amIAdmin (HttpServletRequest request);
	
	boolean amITheirParent(Integer stud, HttpServletRequest request);
	
	boolean amITheirTeacher(Integer stud, HttpServletRequest request);
	
	boolean doIThechThemThis (Integer stud, Integer sub, HttpServletRequest request);

	boolean isThisMyGrade(Integer id, HttpServletRequest request);

}
