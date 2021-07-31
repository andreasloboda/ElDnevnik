package com.iktpreobuka.el_ucionica_AS.services;

import com.iktpreobuka.el_ucionica_AS.controllers.ResponseDTOs.AdminDTO;
import com.iktpreobuka.el_ucionica_AS.controllers.ResponseDTOs.GradeDTO;
import com.iktpreobuka.el_ucionica_AS.controllers.ResponseDTOs.GroupDTO;
import com.iktpreobuka.el_ucionica_AS.controllers.ResponseDTOs.ParentDTO;
import com.iktpreobuka.el_ucionica_AS.controllers.ResponseDTOs.StsDTO;
import com.iktpreobuka.el_ucionica_AS.controllers.ResponseDTOs.StudentDTO;
import com.iktpreobuka.el_ucionica_AS.controllers.ResponseDTOs.SubjectDTO;
import com.iktpreobuka.el_ucionica_AS.controllers.ResponseDTOs.TeacherDTO;
import com.iktpreobuka.el_ucionica_AS.controllers.ResponseDTOs.TsDTO;
import com.iktpreobuka.el_ucionica_AS.entities.GradeEntity;
import com.iktpreobuka.el_ucionica_AS.entities.ParentEntity;
import com.iktpreobuka.el_ucionica_AS.entities.StudentEntity;
import com.iktpreobuka.el_ucionica_AS.entities.StudgroupEntity;
import com.iktpreobuka.el_ucionica_AS.entities.SubjectEntity;
import com.iktpreobuka.el_ucionica_AS.entities.SutestEntity;
import com.iktpreobuka.el_ucionica_AS.entities.TeachSubjEntity;
import com.iktpreobuka.el_ucionica_AS.entities.TeacherEntity;
import com.iktpreobuka.el_ucionica_AS.entities.UserEntity;

public interface DtoServices {

	GradeDTO gradeToDTO(GradeEntity grade);
	
	GroupDTO groupToDTO (StudgroupEntity group);

	AdminDTO adminToDTO(UserEntity admin);

	TeacherDTO teacherToDTO(TeacherEntity teacher);

	StudentDTO studentToDTO(StudentEntity student);

	ParentDTO parentToDTO(ParentEntity parent);
	
	SubjectDTO subjectToDTO(SubjectEntity subject);
	
	TsDTO tsToDTO(TeachSubjEntity tse);
	
	StsDTO stsToDTO(SutestEntity stse);
}
