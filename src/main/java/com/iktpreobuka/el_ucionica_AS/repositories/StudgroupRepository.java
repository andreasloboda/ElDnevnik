package com.iktpreobuka.el_ucionica_AS.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.el_ucionica_AS.entities.StudgroupEntity;

public interface StudgroupRepository extends CrudRepository<StudgroupEntity, Integer> {

	List<StudgroupEntity> findAllByActive(boolean active);

}
