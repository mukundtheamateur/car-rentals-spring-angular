package com.cts.thundercars.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cts.thundercars.entity.Address;
import com.cts.thundercars.exceptions.AlreadyExistsException;
import com.cts.thundercars.exceptions.NotFoundException;
import com.cts.thundercars.repository.AddressRepository;
import com.cts.thundercars.services.service.AddressServices;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AddressServicesImpl implements AddressServices{

	@Autowired
    private AddressRepository addressRepository;

    public List<Address> getAllAddresses() throws NotFoundException {
    	log.info("fetching all addresses...");
    	List<Address> addresses = addressRepository.findAll();
    	if(!addresses.isEmpty()) {
    		log.info("addresses fetched: ", addresses);
    		return addresses;
    	}
    	log.error("Addresses can not be found");
    	throw new NotFoundException("No Address found");
    }


    public Address getAddressById(Integer id) throws NotFoundException {
        log.info("Fetching Addresses with id: {}", id);
    	Optional<Address> optionalAddress = addressRepository.findById(id);
        if(optionalAddress.isPresent()) {
        	log.info("Address fetched");
        	return optionalAddress.get();
        }
        log.error("Address with this id can not be found");
        throw new NotFoundException("Address can not be found with this id");
    }

    public ResponseEntity<Address> saveAddress(Address address) throws AlreadyExistsException {
        
    	log.info("Creating address starated:");
    	Optional<Address> optionalAddress = addressRepository.findById(address.getId());
    	if(optionalAddress.isEmpty()) {
    		addressRepository.save(address);
    		log.info("address saved");
    		return new ResponseEntity<>(address, HttpStatus.OK);
    	}
    	log.error("Error: Address already exist");
    	throw new AlreadyExistsException("Address already exists");
    	
    }

    public ResponseEntity<String> deleteAddress(Integer id) throws NotFoundException {
        log.info("deleting address with id: {}", id);
        Boolean isExists = addressRepository.existsById(id);
        if(isExists) {
        	Optional<Address> address = addressRepository.findById(id);
        	addressRepository.delete(address.get());
        	return new ResponseEntity<String>("address deleted successfully", HttpStatus.OK);
        }
        log.error("Address deleted successfully");
        throw new NotFoundException("Address can't be deleted");
    }


    public Address updateAddress(Integer id,Address updateAddress) throws NotFoundException {
        log.info("update process getting started...");
    	Optional<Address> optionalAddress = addressRepository.findById(id);
        if(optionalAddress.isPresent()) {
        	Address address = optionalAddress.get();
        	if(updateAddress.getPickupAddress() != null) {
                address.setPickupAddress(updateAddress.getPickupAddress());
            }
            if(updateAddress.getDropAddress() != null) {
                address.setDropAddress(updateAddress.getDropAddress());
            }
            Address updatedAddress = addressRepository.save(address);
            log.info("Address updated successfully: {}", updatedAddress);
            return updatedAddress;
        }
        throw new NotFoundException("Address with this id can't be found!");
    }

	
}
