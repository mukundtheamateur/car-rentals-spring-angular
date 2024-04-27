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

import com.cts.thundercars.entity.Address;
import com.cts.thundercars.exceptions.AlreadyExistsException;
import com.cts.thundercars.exceptions.NotFoundException;
import com.cts.thundercars.exceptions.RoleIdNotFoundException;
import com.cts.thundercars.services.service.AddressServices;

@RestController
@RequestMapping("/api/address")
public class AddressController {

	private final AddressServices addressServices;

    @Autowired
    public AddressController(AddressServices addressServices) {
        this.addressServices = addressServices;
    }

    @GetMapping(value= {"", "/"})
    public ResponseEntity<List<Address>> getAllAddress() throws NotFoundException {
        return new ResponseEntity<>(addressServices.getAllAddresses(), HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Address> getAddressById(@PathVariable Integer id) throws NotFoundException {
        Address address =addressServices.getAddressById(id);
        return new ResponseEntity<>(address,HttpStatus.OK);
    }

    @PostMapping(value= {"/create",""})
    public ResponseEntity<Address> createAddress(@RequestBody Address address) throws AlreadyExistsException {
        return addressServices.saveAddress(address);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAddress(@PathVariable Integer id) throws NotFoundException {
        addressServices.deleteAddress(id);
        return new ResponseEntity<>("Address deleted successfully", HttpStatus.OK);
    }

    
    @PutMapping("/update/{id}")
    public Address updateAddress(@PathVariable Integer id, @RequestBody Address addressUpdates) throws NotFoundException, RoleIdNotFoundException {
        return addressServices.updateAddress(id, addressUpdates);
    }

    
}
