package com.cts.thundercars.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.thundercars.entity.CarDealer;
import com.cts.thundercars.exceptions.AlreadyExistsException;
import com.cts.thundercars.exceptions.NotFoundException;
import com.cts.thundercars.repository.CarDealerRepository;
import com.cts.thundercars.security.AuthenticationResponse;
import com.cts.thundercars.security.JwtUtil;
import com.cts.thundercars.security.MyUserDetailsService;
import com.cts.thundercars.services.service.CarDealerServices;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/register/carDealer")
public class CarDealerRegisterController {

	@Autowired
	private CarDealerRepository carDealersDao;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private CarDealerServices carDealerService;

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private MyUserDetailsService myUserDetails;

	@PostMapping
	public ResponseEntity<?> registerCarDealer(@RequestBody CarDealer carDealer, HttpServletResponse res)
			throws AlreadyExistsException, NotFoundException {
		Boolean isExists = carDealersDao.existsByEmail(carDealer.getEmail());

		if (isExists) {
			throw new AlreadyExistsException("carDealer Already exists");
		}
		carDealer.setPassword(passwordEncoder.encode(carDealer.getPassword()));
		carDealerService.saveCarDealer(carDealer);
		UserDetails userDetail = myUserDetails.loadUserByUsername(carDealer.getEmail());
		System.out.println(".........................." + userDetail.getUsername());
		String jwt = jwtUtil.generateToken(userDetail);
		res.addHeader("jwt", jwt);
		return ResponseEntity.ok(new AuthenticationResponse("CarDealer Registered successfully"));
	}
}