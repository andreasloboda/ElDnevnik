package com.iktpreobuka.el_ucionica_AS.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.el_ucionica_AS.entities.GradeEntity;

public interface GradeRepository extends CrudRepository<GradeEntity, Integer> {

	List<GradeEntity> findAllByInfoStudId(Integer studID);

	List<GradeEntity> findAllByInfoStudIdAndInfoTsSubjectId(Integer studId, Integer subID);

}
