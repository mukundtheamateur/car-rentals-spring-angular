package com.cts.thundercars.repository;

import com.cts.thundercars.entity.Bookings;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingsRepository extends JpaRepository<Bookings, Integer> {

	List<Bookings> findByUserId(Integer userId);

	List<Bookings> findByCarId(Integer carId);

	List<Bookings> findByCarDealerId(Integer carId);
}



