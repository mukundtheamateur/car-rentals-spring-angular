package com.cts.thundercars.services.impl;

import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cts.thundercars.entity.Address;
import com.cts.thundercars.entity.Bookings;
import com.cts.thundercars.entity.Car;
import com.cts.thundercars.entity.CarDealer;
import com.cts.thundercars.entity.User;
import com.cts.thundercars.exceptions.NotFoundException;
import com.cts.thundercars.repository.AddressRepository;
import com.cts.thundercars.repository.BookingsRepository;
import com.cts.thundercars.repository.CarDealerRepository;
import com.cts.thundercars.repository.CarRepository;
import com.cts.thundercars.repository.UserRepository;
import com.cts.thundercars.services.service.BookingsServices;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BookingServicesImpl implements BookingsServices{

	private final BookingsRepository bookingsRepository;
	private final CarRepository carRepository;
	private final CarDealerRepository carDealerRepository;
	private final AddressRepository addressRepository;
	private final UserRepository userRepository;
	
	@Autowired
	public BookingServicesImpl(BookingsRepository bookingsRepository, CarRepository carRepository, AddressRepository addressRepository, CarDealerRepository carDealerRepository, UserRepository userRepository) {
		this.bookingsRepository = bookingsRepository;
		this.carRepository = carRepository;
		this.addressRepository = addressRepository;
		this.carDealerRepository = carDealerRepository;
		this.userRepository = userRepository;
	}
	
	@Override
	public List<Bookings> getAllBookings() throws NotFoundException {
		log.info("Fetching all bookings");
		List<Bookings> bookings = bookingsRepository.findAll();
		if(!bookings.isEmpty()) {
			log.info("list of bookings is fetched");
			return bookingsRepository.findAll();
		}
		throw new NotFoundException("No Bookings found");
	}

	@Override
	public Optional<Bookings> getBookingById(Integer id) throws NotFoundException {
		log.info("Fetching booking with id: {}", id);
		Optional<Bookings> booking = bookingsRepository.findById(id);
		if(booking.isPresent()) {
			log.info("Booking fetched successfully with id: {}", id);
			return booking;
		}
		throw new NotFoundException("Booking does not exist with this id");
	}

	@Transactional
	@Override
	public Bookings saveBooking(Bookings booking){
		log.info("Saving booking with id");

		if(booking.getFromDate() != null && booking.getToDate() !=null) {
			Date firstDate = booking.getFromDate();
		    Date secondDate = booking.getToDate();
		    
		    long diffInMillies = Math.abs(secondDate.getTime() - firstDate.getTime());
	        long dayDiff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
	        int carPrice = carRepository.findById(booking.getCar().getId()).get().getPrice();
	        int totalPrice = (int) dayDiff * carPrice;
	        
	        booking.setPrice(totalPrice);
		    
		}
		User user = userRepository.findById(booking.getUser().getId()).get();
		Car car = carRepository.findById(booking.getCar().getId()).get();
		Address address = addressRepository.findById(booking.getAddress().getId()).get();
		CarDealer carDealer = carDealerRepository.findById(booking.getCarDealer().getId()).get();
		booking.setUser(user);
		booking.setCarDealer(carDealer);
		booking.setAddress(address);
		booking.setCar(car);
		
		return bookingsRepository.save(booking);
	}

	@Transactional
	@Override
	public String deleteBooking(Integer id) throws NotFoundException {
		log.info("Deleting booking with id: {}", id);
		if(!bookingsRepository.existsById(id)) {
			throw new NotFoundException("Booking does not exist with this id");
		}
		bookingsRepository.deleteById(id);
		return "Successfully deleted";
	}

	@Override
	public List<Bookings> getBookingsByUserId(Integer userId) {
		log.info("Fetching bookings for user with id: {}", userId);
		return bookingsRepository.findByUserId(userId);
	}

	@Override
	public List<Bookings> getBookingsByCarId(Integer carId) {
		log.info("Fetching bookings for car with id: {}", carId);
		return bookingsRepository.findByCarId(carId);
	}
	@Override
	public List<Bookings> getBookingsByCarDealerId(Integer carId) {
		log.info("Fetching bookings for car with id: {}", carId);
		return bookingsRepository.findByCarDealerId(carId);
	}
	
	@Transactional
	@Override
	public Bookings updateBooking(Bookings updatedBooking) throws NotFoundException {
	    log.info("Updating booking with id: {}", updatedBooking.getId());

	    Bookings existingBooking = bookingsRepository.findById(updatedBooking.getId())
	        .orElseThrow(() -> new NotFoundException("Booking not found with id: " + updatedBooking.getId()));

	    if (updatedBooking.getFromDate() != null) {
	        existingBooking.setFromDate(updatedBooking.getFromDate());
	    }
	    if (updatedBooking.getToDate() != null) {
	        existingBooking.setToDate(updatedBooking.getToDate());
	    }
	    if (updatedBooking.getStatus() != null) {
	        existingBooking.setStatus(updatedBooking.getStatus());
	    }
	    if (updatedBooking.getCancellation() != null) {
	        existingBooking.setCancellation(updatedBooking.getCancellation());
	    }
	    if (updatedBooking.getAmendments() != null) {
	        existingBooking.setAmendments(updatedBooking.getAmendments());
	    }
	    if (updatedBooking.getTheftProtection() != null) {
	        existingBooking.setTheftProtection(updatedBooking.getTheftProtection());
	    }
	    if (updatedBooking.getCollisionDamage() != null) {
	        existingBooking.setCollisionDamage(updatedBooking.getCollisionDamage());
	    }
	    if (updatedBooking.getFullInsurance() != null) {
	        existingBooking.setFullInsurance(updatedBooking.getFullInsurance());
	    }
	    if (updatedBooking.getAdditionalDriver() != null) {
	        existingBooking.setAdditionalDriver(updatedBooking.getAdditionalDriver());
	    }
	    if (updatedBooking.getPrice() != null) {
	        existingBooking.setPrice(updatedBooking.getPrice());
	    }
	    if (updatedBooking.getCancelRequest() != null) {
	        existingBooking.setCancelRequest(updatedBooking.getCancelRequest());
	    }
	    if (updatedBooking.getCarDealer() != null) {
	        existingBooking.setCarDealer(updatedBooking.getCarDealer());
	    }
	    if (updatedBooking.getUser() != null) {
	        existingBooking.setUser(updatedBooking.getUser());
	    }
	    if (updatedBooking.getCar() != null) {
	        existingBooking.setCar(updatedBooking.getCar());
	    }
	    if (updatedBooking.getAddress() != null) {
	        existingBooking.setAddress(updatedBooking.getAddress());
	    }

	    // Save and return the updated booking
	    return bookingsRepository.save(existingBooking);
	}
	
	
}
