package com.cts.thundercars.repository;

import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cts.thundercars.entity.CarDealer;

public interface CarDealerRepository extends JpaRepository<CarDealer, Integer>{

	Optional<CarDealer> findByEmail(String email);
	List<CarDealer> findByRoleId(Integer roleId);
	Boolean existsByEmail(String email);

}
