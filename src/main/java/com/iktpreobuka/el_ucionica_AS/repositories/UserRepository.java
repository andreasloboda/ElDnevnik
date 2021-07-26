package com.iktpreobuka.el_ucionica_AS.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.el_ucionica_AS.entities.UserEntity;
import com.iktpreobuka.el_ucionica_AS.entities.enums.UserRole;

public interface UserRepository extends CrudRepository<UserEntity, Integer> {

	List<UserEntity> findAllByRole(UserRole role);

	boolean existsByUsername(String username);

	Optional<UserEntity> findByUsername(String username);

}
