package com.cts.thundercars.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cts.thundercars.entity.Car;
import com.cts.thundercars.exceptions.AlreadyExistsException;
import com.cts.thundercars.exceptions.NotFoundException;
import com.cts.thundercars.services.service.CarServices;

@RestController
@RequestMapping("/api/cars")
public class CarController {

    private final CarServices carServices;
    
    @Autowired
    public CarController(CarServices carServices) {
    	this.carServices = carServices;
    }
    
    @GetMapping(value= {"", "/"})
    public ResponseEntity<List<Car>> findAllCars() throws NotFoundException {
        return new ResponseEntity<>(carServices.getAllCars(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Car> getCarById(@PathVariable Integer id) throws NotFoundException {
        Car car =carServices.getCarById(id);
        return new ResponseEntity<>(car,HttpStatus.OK);
    }

    @PostMapping(value= "/create")
    public ResponseEntity<Car> createCar(@RequestBody Car car) throws AlreadyExistsException {
        Car updatedCar= carServices.saveCar(car);
        return new ResponseEntity<Car>(updatedCar, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String,String>> deleteCar(@PathVariable Integer id) throws NotFoundException {
        carServices.deleteCar(id);
        Map<String,String> res = new HashMap<>();
        res.put("message", "Car deleted successfully");
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
    
    @PutMapping("/update/{id}")
    public Car updateCar(@PathVariable Integer id, @RequestBody Car carUpdates) throws NotFoundException{
        return carServices.updateCar(id, carUpdates);
    }
    
    @GetMapping("/dealer/{dealerId}")
    public ResponseEntity<List<Car>> getCarsByDealerId(@PathVariable("dealerId") Integer dealerId) throws NotFoundException {
        List<Car> cars = carServices.getCarsByDealerId(dealerId);
        return new ResponseEntity<>(cars, HttpStatus.OK);
    }

    @GetMapping("/model/{model}")
    public ResponseEntity<List<Car>> getCarsByCarName(@PathVariable String model) throws NotFoundException {
        List<Car> cars = carServices.getCarsByCarName(model);
        return new ResponseEntity<>(cars, HttpStatus.OK);
    }

    @GetMapping("/price")
    public ResponseEntity<List<Car>> getCarsByPriceRange(@RequestParam double minPrice, @RequestParam double maxPrice) throws NotFoundException {
        List<Car> cars = carServices.getCarsByPriceRange(minPrice, maxPrice);
        return new ResponseEntity<>(cars, HttpStatus.OK);
    }


    
}
