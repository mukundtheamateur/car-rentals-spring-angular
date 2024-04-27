package com.cts.thundercars.services.service;

import com.cts.thundercars.entity.Car;
import com.cts.thundercars.exceptions.AlreadyExistsException;
import com.cts.thundercars.exceptions.NotFoundException;

import java.util.List;

import org.springframework.http.ResponseEntity;

public interface CarServices {
    List<Car> getAllCars() throws NotFoundException;
    Car getCarById(Integer id) throws NotFoundException;
    Car saveCar(Car car) throws AlreadyExistsException;
    ResponseEntity<String> deleteCar(Integer id) throws NotFoundException;
    Car updateCar(Integer id, Car car) throws NotFoundException;
    List<Car> getCarsByDealerId(Integer dealerId) throws NotFoundException;
    List<Car> getCarsByCarName(String model) throws NotFoundException;
    List<Car> getCarsByPriceRange(double minPrice, double maxPrice) throws NotFoundException ;
}
