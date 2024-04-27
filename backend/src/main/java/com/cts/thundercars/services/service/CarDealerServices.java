package com.cts.thundercars.services.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.cts.thundercars.entity.CarDealer;
import com.cts.thundercars.exceptions.AlreadyExistsException;
import com.cts.thundercars.exceptions.NotFoundException;

public interface CarDealerServices {
    ResponseEntity<List<CarDealer>> getAllCarDealers() throws NotFoundException;
    CarDealer getCarDealerById(Integer id) throws NotFoundException;
    CarDealer saveCarDealer(CarDealer carDealer) throws AlreadyExistsException, NotFoundException;
    ResponseEntity<String> deleteCarDealer(Integer id) throws NotFoundException;
    List<CarDealer> getCarDealersByRole(Integer roleId) throws NotFoundException;
    CarDealer updateCarDealer(CarDealer carDealer) throws NotFoundException;
	CarDealer getCarDealerByEmailId(String email) throws NotFoundException;
}

