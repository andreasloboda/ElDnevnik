package com.iktpreobuka.el_ucionica_AS.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.el_ucionica_AS.entities.TeachSubjEntity;

public interface TeachSubjRepository extends CrudRepository<TeachSubjEntity, Integer> {

	boolean existsBySubjectId(Integer subId);

	boolean existsByTeacherIdAndSubjectId(Integer teachId, Integer subId);

	boolean existsByTeacherId(Integer teachId);

	Optional<TeachSubjEntity> findByTeacherIdAndSubjectId(Integer teachId, Integer subId);

}
