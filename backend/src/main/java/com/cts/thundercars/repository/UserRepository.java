package com.cts.thundercars.repository;

import com.cts.thundercars.entity.User;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
	
	Optional<User> findByEmail(String email);
    List<User> findByRoleId(Integer roleId);
    Boolean existsByEmail(String email);
    boolean existsById(Integer id);
}
