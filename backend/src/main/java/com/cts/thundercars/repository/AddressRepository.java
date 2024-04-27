package com.cts.thundercars.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cts.thundercars.entity.Address;

public interface AddressRepository extends JpaRepository<Address, Integer>{

}
