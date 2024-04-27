package com.cts.thundercars.services.service;

import com.cts.thundercars.entity.Bookings;
import com.cts.thundercars.exceptions.AlreadyExistsException;
import com.cts.thundercars.exceptions.NotFoundException;

import java.util.List;
import java.util.Optional;

public interface BookingsServices {
    List<Bookings> getAllBookings() throws NotFoundException;
    Optional<Bookings> getBookingById(Integer id) throws NotFoundException;
    Bookings saveBooking(Bookings booking) throws AlreadyExistsException;
    String deleteBooking(Integer id) throws NotFoundException;
    List<Bookings> getBookingsByUserId(Integer userId);
    List<Bookings> getBookingsByCarDealerId(Integer carDealerId);
    List<Bookings> getBookingsByCarId(Integer carId);
	Bookings updateBooking(Bookings updatedBooking) throws NotFoundException;
}

