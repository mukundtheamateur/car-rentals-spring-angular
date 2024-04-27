package com.cts.thundercars.controllers;

import java.util.List;

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
import org.springframework.web.bind.annotation.RestController;

import com.cts.thundercars.entity.CarDealer;
import com.cts.thundercars.exceptions.AlreadyExistsException;
import com.cts.thundercars.exceptions.NotFoundException;
import com.cts.thundercars.services.service.CarDealerServices;

@RestController
@RequestMapping("/api/cardealer")
public class CarDealerController {

	private final CarDealerServices carDealerServices;
	
	@Autowired
	public CarDealerController(CarDealerServices carDealerServices) {
		this.carDealerServices = carDealerServices;
	}
	
	@GetMapping(value= {"", "/"})
    public ResponseEntity<List<CarDealer>> findAllCarDealer() throws NotFoundException {
        return carDealerServices.getAllCarDealers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarDealer> getCarDealerById(@PathVariable Integer id) throws NotFoundException {
        CarDealer carDealer =carDealerServices.getCarDealerById(id);
        return new ResponseEntity<>(carDealer,HttpStatus.OK);
    }
    @GetMapping("/email/{email}")
    public ResponseEntity<CarDealer> getCarDealerByEmailId(@PathVariable String email) throws NotFoundException {
        CarDealer carDealer =carDealerServices.getCarDealerByEmailId(email);
        return new ResponseEntity<>(carDealer,HttpStatus.OK);
    }

    @PostMapping(value= "/create")
    public ResponseEntity<CarDealer> createCarDealer(@RequestBody CarDealer carDealer) throws AlreadyExistsException, NotFoundException {
    	CarDealer updatedCarDealer= carDealerServices.saveCarDealer(carDealer);
        return new ResponseEntity<>(updatedCarDealer, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCarDealer(@PathVariable Integer id) throws NotFoundException {
        carDealerServices.deleteCarDealer(id);
        return new ResponseEntity<>("CarDealer deleted successfully", HttpStatus.OK);
    }
    
    @PutMapping("/update")
    public CarDealer updateCar(@RequestBody CarDealer carDealerUpdates) throws NotFoundException{
        return carDealerServices.updateCarDealer(carDealerUpdates);
    }
    

}
