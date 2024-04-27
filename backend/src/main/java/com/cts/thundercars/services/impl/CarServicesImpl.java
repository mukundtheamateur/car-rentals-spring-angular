package com.cts.thundercars.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cts.thundercars.entity.Car;
import com.cts.thundercars.entity.CarDealer;
import com.cts.thundercars.exceptions.AlreadyExistsException;
import com.cts.thundercars.exceptions.NotFoundException;
import com.cts.thundercars.repository.CarDealerRepository;
import com.cts.thundercars.repository.CarRepository;
import com.cts.thundercars.services.service.CarServices;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CarServicesImpl implements CarServices {

	private final CarRepository carRepository;
	private final CarDealerRepository carDealerRepository;

    @Autowired
    public CarServicesImpl(CarRepository carRepository,CarDealerRepository carDealerRepository) {
        this.carRepository = carRepository;
        this.carDealerRepository = carDealerRepository;
    }

    @Override
    public List<Car> getAllCars() throws NotFoundException{
        log.info("Fetching all cars");
        List<Car> cars = carRepository.findAll();
        if(!cars.isEmpty()) {
        	log.info("all cars fetched");
        	return cars;
        }
        log.error("Cars not found! NotFoundException");
        throw new NotFoundException("No Cars exist!");
    }

    @Override
    public Car getCarById(Integer id) throws NotFoundException {
        log.info("Fetching car with id: {}", id);
        Optional<Car> optionalCar = carRepository.findById(id);
        if(optionalCar.isPresent()) {
        	return optionalCar.get();
        }
        log.error("Error occurred while fetching car with id: {}", id);
        throw new NotFoundException("Error: Car with this id is not found!");
    }

    
    @Override
    public Car saveCar(Car car) throws AlreadyExistsException {
        log.info("Saving car: {}", car);
        Boolean isExists = carRepository.existsById(car.getId());
        if(!isExists ) {
        	
        	CarDealer carDealer = carDealerRepository.findById(car.getDealer().getId()).get();
        	log.info("Car saved successfully: {}", car);
        	car.setDealer(carDealer);
        	
        	Car createdCar = carRepository.save(car);
        	return createdCar;
        }
        log.error("Error occurred while saving car: {}", car);
        throw new AlreadyExistsException("Car with this id already exists");

    }

    @Transactional
    @Override
    public ResponseEntity<String> deleteCar(Integer id) throws NotFoundException {
    	log.info("Deleting car Dealer with id: {}", id);
        Boolean isExists = carRepository.existsById(id);
        if(isExists) {
        	Optional<Car> optionalCar = carRepository.findById(id);
        	Car car = optionalCar.get();
        	car.setDealer(null);
        	carRepository.delete(car);
        	log.info("Car with id: {} deleted successfully", id);
        	return new ResponseEntity<String>("Car deleted successfully", HttpStatus.OK);
        }
        log.error("Error occurred while deleting car with id: {}", id);
        throw new NotFoundException("Car with this id does not exists");

    }

    @Override
    public List<Car> getCarsByDealerId(Integer dealerId) throws NotFoundException {
        log.info("Fetching cars by dealer id: {}", dealerId);
        List<Car> cars = carRepository.findByDealerId(dealerId);
        if(!cars.isEmpty()) {
        	return cars;
        }
        log.error("Error occurred while fetching cars by dealer id: {}", dealerId);
        throw new NotFoundException("Error: No Cars found with this dealer id ");
        
    }

    @Override
    public List<Car> getCarsByCarName(String carName) throws NotFoundException {
        log.info("Fetching cars by carName: {}", carName);
        List<Car> cars = carRepository.findByCarNameContains(carName);
        if(!cars.isEmpty()) {
            return cars;
        } 
        log.error("Error occurred while fetching cars by model: {}", carName);
        throw new NotFoundException("Could not fetch cars by model: " + carName);
    }


    @Override
    public List<Car> getCarsByPriceRange(double minPrice, double maxPrice) throws NotFoundException {
        log.info("Fetching cars by price range: {} - {}", minPrice, maxPrice);
        List<Car> cars = carRepository.findByPriceBetween(minPrice, maxPrice);
        if(!cars.isEmpty()) {
        	return cars;
        }
        log.error("Error occurred while fetching cars by price range: {} - {}", minPrice, maxPrice);
        throw new NotFoundException("Could not fetch cars by price range: " + minPrice + " - " + maxPrice);
        
    }

    @Transactional
    @Override
    public Car updateCar(Integer id, Car carUpdates) throws NotFoundException {
        log.info("Updating car: {}", carUpdates);
        Optional<Car> optionalCar = carRepository.findById(id);
        if(optionalCar.isPresent()) {
            Car car = optionalCar.get();
            if(carUpdates.getCarName() != null) {
                car.setCarName(carUpdates.getCarName());
            }
            if(carUpdates.getDealer() != null) {
                car.setDealer(carUpdates.getDealer());
            }
            if(carUpdates.getPrice() != null) {
                car.setPrice(carUpdates.getPrice());
            }
            if(carUpdates.getDeposit() != null) {
                car.setDeposit(carUpdates.getDeposit());
            }
            if(carUpdates.getFuelType() != null) {
                car.setFuelType(carUpdates.getFuelType());
            }
            if(carUpdates.getGearbox() != null) {
                car.setGearbox(carUpdates.getGearbox());
            }
            if(carUpdates.getImage() != null) {
                car.setImage(carUpdates.getImage());
            }
            if(carUpdates.getSeats() != null) {
                car.setSeats(carUpdates.getSeats());
            }
            if(carUpdates.getDoors() != null) {
                car.setDoors(carUpdates.getDoors());
            }
            if(carUpdates.getFuelpolicy() != null) {
                car.setFuelpolicy(carUpdates.getFuelpolicy());
            }
            if(carUpdates.getMileage() != null) {
                car.setMileage(carUpdates.getMileage());
            }
            if(carUpdates.getCancellation() != null) {
                car.setCancellation(carUpdates.getCancellation());
            }
            if(carUpdates.getAmendments() != null) {
                car.setAmendments(carUpdates.getAmendments());
            }
            if(carUpdates.getTheftProtection() != null) {
                car.setTheftProtection(carUpdates.getTheftProtection());
            }
            if(carUpdates.getCollisionDamage() != null) {
                car.setCollisionDamage(carUpdates.getCollisionDamage());
            }
            if(carUpdates.getFullInsurance() != null) {
                car.setFullInsurance(carUpdates.getFullInsurance());
            }
            if(carUpdates.getAdditionalDriver() != null) {
                car.setAdditionalDriver(carUpdates.getAdditionalDriver());
            }
            if(carUpdates.getWheelDrive() != null) {
                car.setWheelDrive(carUpdates.getWheelDrive());
            }
            if(carUpdates.getCreatedBy() != null) {
                car.setCreatedBy(carUpdates.getCreatedBy());
            }
            if(carUpdates.getUpdatedBy() != null) {
                car.setUpdatedBy(carUpdates.getUpdatedBy());
            }
            Car updatedCar = carRepository.save(car);
            log.info("Car updated successfully: {}", updatedCar);
            return updatedCar;
        }
        throw new NotFoundException("Car not found!");
    }

}
