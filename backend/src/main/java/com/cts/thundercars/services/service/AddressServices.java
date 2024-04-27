package com.cts.thundercars.services.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.cts.thundercars.entity.Address;
import com.cts.thundercars.exceptions.AlreadyExistsException;
import com.cts.thundercars.exceptions.NotFoundException;

public interface AddressServices{

	List<Address> getAllAddresses() throws NotFoundException;
	Address getAddressById(Integer id) throws NotFoundException;
	ResponseEntity<Address> saveAddress( Address address) throws AlreadyExistsException;
	ResponseEntity<String> deleteAddress(Integer id) throws NotFoundException;
	Address updateAddress(Integer id,Address address) throws NotFoundException;

}
