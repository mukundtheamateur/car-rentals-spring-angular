package com.cts.thundercars.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api")
public class HomeController {

	@GetMapping(value= {"", "/", "/home"})
	public ResponseEntity<String> displayHome() {
		log.info("display home start");
		log.info("END");
		return new ResponseEntity<>("homepage", HttpStatus.OK);
	}
	
}
