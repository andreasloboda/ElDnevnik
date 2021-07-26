package com.iktpreobuka.el_ucionica_AS.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.el_ucionica_AS.entities.SutestEntity;

public interface SutestRepository extends CrudRepository<SutestEntity, Integer> {

	Optional<SutestEntity> findByStudIdAndTsTeacherIdAndTsSubjectId(Integer studentID, Integer teacherID, Integer subjectID);

	boolean existsByTsIdAndStudId(Integer id, Integer id2);

	boolean existsByStudIdAndTsTeacherIdAndTsSubjectId(Integer studentID, Integer teacherID, Integer subjectID);

	boolean existsByStudIdAndTsSubjectId(Integer studId, Integer subID);

	boolean existsByStudIdAndTsTeacherUsername(Integer stud, String username);

	boolean existsByStudIdAndTsSubjectIdAndTsTeacherUsername(Integer stud, Integer sub, String username);

}
