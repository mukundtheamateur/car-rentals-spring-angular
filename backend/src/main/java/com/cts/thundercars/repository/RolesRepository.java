package com.cts.thundercars.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cts.thundercars.entity.Roles;

public interface RolesRepository extends JpaRepository<Roles, Integer>{

	Optional<Roles> findById(Roles role);

}
