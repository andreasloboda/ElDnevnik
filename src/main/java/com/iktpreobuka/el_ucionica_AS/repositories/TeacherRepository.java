package com.iktpreobuka.el_ucionica_AS.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.el_ucionica_AS.entities.TeacherEntity;

public interface TeacherRepository extends CrudRepository<TeacherEntity, Integer> {

	List<TeacherEntity> findAllBySubjectsSubjectId(Integer subId);

	List<TeacherEntity> findAllByName(String name);

	boolean existsByName(String name);

}
