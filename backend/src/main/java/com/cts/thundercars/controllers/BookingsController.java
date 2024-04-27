package com.cts.thundercars.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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

import com.cts.thundercars.entity.Bookings;
import com.cts.thundercars.exceptions.AlreadyExistsException;
import com.cts.thundercars.exceptions.NotFoundException;
import com.cts.thundercars.services.service.BookingsServices;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/bookings")
public class BookingsController {

	private final BookingsServices bookingsServices;

	@Autowired
	public BookingsController(BookingsServices bookingsServices) {
		this.bookingsServices = bookingsServices;
	}

	@GetMapping
	public ResponseEntity<List<Bookings>> getAllBookings() throws NotFoundException {
		List<Bookings> bookings = bookingsServices.getAllBookings();
		return ResponseEntity.ok(bookings);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Optional<Bookings>> getBookingById(@PathVariable("id") Integer id) throws NotFoundException {
		Optional<Bookings> booking = bookingsServices.getBookingById(id);
		return ResponseEntity.ok(booking);
	}

	@PostMapping("/create")
	public ResponseEntity<Bookings> saveBooking(@RequestBody Bookings booking) throws AlreadyExistsException {
		Bookings savedBooking = bookingsServices.saveBooking(booking);
		log.info("booking created successfully with booking id: {} , and user Id: {}" ,savedBooking.getId(),savedBooking.getUser().getId());
		return ResponseEntity.ok(savedBooking);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, String>> deleteBooking(@PathVariable("id") Integer id) throws NotFoundException {
		String message = bookingsServices.deleteBooking(id);
		log.info("booking cancelled" );
		Map<String,String> res = new HashMap<>();
        res.put("message", message);
        return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@GetMapping("/user/{userId}")
	public ResponseEntity<List<Bookings>> getBookingsByUserId(@PathVariable("userId") Integer userId) {
		List<Bookings> bookings = bookingsServices.getBookingsByUserId(userId);
		return ResponseEntity.ok(bookings);
	}

	@GetMapping("/car/{carId}")
	public ResponseEntity<List<Bookings>> getBookingsByCarId(@PathVariable Integer carId) {
		List<Bookings> bookings = bookingsServices.getBookingsByCarId(carId);
		return ResponseEntity.ok(bookings);
	}
	@GetMapping("/carDealer/{carDealerId}")
	public ResponseEntity<List<Bookings>> getBookingsByCarDealerId(@PathVariable Integer carDealerId) {
		List<Bookings> bookings = bookingsServices.getBookingsByCarDealerId(carDealerId);
		return ResponseEntity.ok(bookings);
	}
	@PutMapping("/update")
	public ResponseEntity<Bookings> updateBooking(@RequestBody Bookings booking) throws NotFoundException{
		Bookings updatedBookings = bookingsServices.updateBooking(booking);
		return new ResponseEntity<>(updatedBookings, HttpStatus.OK);
	}
}
