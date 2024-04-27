package com.cts.thundercars.services.impl;

import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cts.thundercars.entity.CarDealer;
import com.cts.thundercars.entity.Roles;
import com.cts.thundercars.exceptions.AlreadyExistsException;
import com.cts.thundercars.exceptions.NotFoundException;
import com.cts.thundercars.repository.CarDealerRepository;
import com.cts.thundercars.repository.RolesRepository;
import com.cts.thundercars.services.service.CarDealerServices;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CarDealerServicesImpl implements CarDealerServices{

	
	private final CarDealerRepository carDealerRepository;
	private final RolesRepository rolesRepository;
	
	@Autowired
	public CarDealerServicesImpl(CarDealerRepository carDealerRepository, RolesRepository rolesRepository ) {
		this.carDealerRepository = carDealerRepository;
		this.rolesRepository = rolesRepository;
	}
	
	@Override
	public ResponseEntity<List<CarDealer>> getAllCarDealers() throws NotFoundException {
		log.info("fetching car dealer details...");
		List<CarDealer> carDealers = carDealerRepository.findAll();
		if(!carDealers.isEmpty()) {
			log.info("Car Dealers fetched : {}", carDealers);
			return new ResponseEntity<>(carDealers, HttpStatus.OK);
		}
		log.error("Error: No car Dealers found... ");
		throw new NotFoundException("No Car Dealers found!");
	}

	@Override
	public CarDealer getCarDealerById(Integer id) throws NotFoundException {
	    log.info("Fetching car dealer by id : {} ...", id);
	    Boolean isExists = carDealerRepository.existsById(id);
	    if(isExists) {
	        CarDealer carDealer = carDealerRepository.findById(id).get();
	        log.info("car dealer fetched: {}", carDealer);
	        return carDealer;
	    }
	    log.error("Car dealer does not exist with this Id");
	    throw new NotFoundException("Car dealer does not exist with this Id");
	}
	@Override
	public CarDealer getCarDealerByEmailId(String email) throws NotFoundException {
	    log.info("Fetching car dealer by id : {} ...", email);
	    Boolean isExists = carDealerRepository.existsByEmail(email);
	    if(isExists) {
	        CarDealer carDealer = carDealerRepository.findByEmail(email).get();
	        log.info("car dealer fetched: {}", carDealer);
	        return carDealer;
	    }
	    log.error("Car dealer does not exist with this Id");
	    throw new NotFoundException("Car dealer does not exist with this Id");
	}


	@Override
	public CarDealer saveCarDealer(CarDealer carDealer) throws AlreadyExistsException, NotFoundException {
	    log.info("Checking if user already exists");
	    Optional<CarDealer> optionalCarDealer = carDealerRepository.findByEmail(carDealer.getEmail());
	    if(!optionalCarDealer.isPresent()) {
	        log.info("Creating car Dealer");
	        if (carDealer.getRole() != null) {
	            Optional<Roles> optionalRole = rolesRepository.findById(carDealer.getRole().getId());
	            if (optionalRole.isPresent()) {
	                carDealer.setRole(optionalRole.get());
	            } else {
	                log.error("Role not found with id: {}", carDealer.getRole().getId());
	                throw new NotFoundException("Role not found with id: " + carDealer.getRole().getId());
	            }
	        }
	        CarDealer newCarDealer = carDealerRepository.save(carDealer);
	        return newCarDealer;
	    }
	    log.error("This Car Dealer already exists");
	    throw new AlreadyExistsException("This Car Dealer already exists");
	}


	@Transactional
	@Override
	public ResponseEntity<String>deleteCarDealer(Integer id) throws NotFoundException {
        log.info("Deleting car Dealer with id: {}", id);
        Boolean isExists = carDealerRepository.existsById(id);
        if(isExists) {
        	Optional<CarDealer> optionalCarDealer = carDealerRepository.findById(id);
        	CarDealer carDealer = optionalCarDealer.get();
        	carDealer.setRole(null);
        	carDealerRepository.delete(carDealer);
        	log.info("Car Dealer with id: {} deleted successfully", id);
        	return new ResponseEntity<String>("Car Dealer deleted successfully", HttpStatus.OK);
        }
        log.error("Error occurred while deleting car Dealer with id: {}", id);
        throw new NotFoundException("Car Dealer with this id does not exists");

	}



	@Override
	public List<CarDealer> getCarDealersByRole(Integer roleId) throws NotFoundException {
		log.info("Fetch cal dealers with role: ", roleId);
		List<CarDealer> carDealers = carDealerRepository.findByRoleId(roleId);
		if(!carDealers.isEmpty()) {
			log.info("Car dealers fetched which have role id: {}", roleId);
			return carDealers;
		}
		throw new NotFoundException("No car dealers found with role id : "+ roleId);
	}

	@Transactional
	@Override
	public CarDealer updateCarDealer(CarDealer carDealerUpdates) throws NotFoundException {
		String email = carDealerUpdates.getEmail();
		Optional<CarDealer> optionalCarDealer = carDealerRepository.findByEmail(email);
		if(optionalCarDealer.isPresent()) {
			CarDealer carDealer = optionalCarDealer.get();
			if(carDealerUpdates.getDealerName() != null) {
                carDealer.setDealerName(carDealerUpdates.getDealerName());
            }
			if(carDealerUpdates.getEmail()!=null) {
				carDealer.setEmail(carDealerUpdates.getEmail());
			}
			if(carDealerUpdates.getRefreshToken()!=null) {
				carDealer.setRefreshToken(carDealerUpdates.getRefreshToken());
			}
			if(carDealerUpdates.getPhone()!=null) {
				carDealer.setPhone(carDealerUpdates.getPhone());
			}
			if(carDealerUpdates.getVerified()!=null) {
				carDealer.setVerified(carDealerUpdates.getVerified());
			}
			if(carDealerUpdates.getAllCities()!=null) {
				carDealer.setAllCities(carDealerUpdates.getAllCities());
			}
			if(carDealerUpdates.getAvatar()!=null) {
				carDealer.setAvatar(carDealerUpdates.getAvatar());
			}
			Roles role = rolesRepository.findById(carDealer.getRole().getId()).get();
			carDealer.setRole(role);
			CarDealer updatedCarDealer = carDealerRepository.save(carDealer);
            log.info("CarDealer updated successfully: {}", updatedCarDealer);
            return updatedCarDealer;
		}
		throw new NotFoundException("Car dealer not found");
	}
	
	
	

}
