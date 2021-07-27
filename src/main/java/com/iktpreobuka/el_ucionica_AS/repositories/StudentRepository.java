package com.iktpreobuka.el_ucionica_AS.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.el_ucionica_AS.entities.StudentEntity;

public interface StudentRepository extends CrudRepository<StudentEntity, Integer> {

	List<StudentEntity> findAllByStudgroupId(Integer groupId);

	boolean existsByParentId(Integer id);

	List<StudentEntity> findAllByParentId(Integer id);

}
