package com.cts.thundercars.repository;

import com.cts.thundercars.entity.Car;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CarRepository extends JpaRepository<Car, Integer> {
	
    Optional<Car> findById(Integer id);
    List<Car> findByDealerId(Integer dealerId);
    
    @Query("SELECT c FROM Car c WHERE c.carName LIKE %:carName%")
    List<Car> findByCarNameContains(@Param("carName") String carName);
    
    List<Car> findByPriceBetween(double minPrice, double maxPrice);

}
