package com.iktpreobuka.el_ucionica_AS.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.el_ucionica_AS.entities.ParentEntity;

public interface ParentRepository extends CrudRepository<ParentEntity, Integer> {

	boolean existsByName(String name);

	List<ParentEntity> findAllByName(String name);

}
