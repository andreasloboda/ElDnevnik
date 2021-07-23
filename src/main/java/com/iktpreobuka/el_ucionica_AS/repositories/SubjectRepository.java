package com.iktpreobuka.el_ucionica_AS.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.el_ucionica_AS.entities.SubjectEntity;

public interface SubjectRepository extends CrudRepository<SubjectEntity, Integer> {

	List<SubjectEntity> findAllByYear(Integer yearNo);

	boolean existsByName(String name);

	List<SubjectEntity> findAllByName(String name);

	List<SubjectEntity> findAllByTeachersTeacherId(Integer teachId);

}
